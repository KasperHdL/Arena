package oose2015.states;

import oose2015.Assets;
import oose2015.Main;
import oose2015.Settings;
import oose2015.gui.elements.TextBox;
import oose2015.input.Action;
import oose2015.input.ControllerScheme;
import oose2015.input.ControllerWrapper;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.*;
import java.nio.file.Files;

/**
 * Created by @Kasper on 26/03/2015
 * <p/>
 * Description:
 * Sets up main menu state.
 * <p/>
 */

public class MainMenuState implements GameState{

    public int[] inputIndices = {-1, -1, -1, -1};//-1 not used -2 for keyboard a
    public int[] playerColors = {-1,-1,-1,-1};
    public int[] controllerScheme = {-1, -1, -1, -1};
    public ControllerWrapper[] controllerWrappers = {null, null, null, null};
    public Color[] colors = {
            new Color(255,116,56),
            new Color(0,155,155),
            new Color(189,122,246),
            new Color(255,105,180),
            new Color(139,69,19),
            new Color(175,175,175)
    };
    public TextBox instructionBox;
    public TextBox[] controllerBox;
    Action[] actions;
    StateBasedGame stateBasedGame;

    int sizeX = Main.SCREEN_WIDTH/4;

    File settings;

    int calibrateIndex = -1;

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.stateBasedGame = stateBasedGame;
        actions = Action.values();
        controllerBox = new TextBox[4];

        instructionBox = new TextBox("Press Select to start the game", new Vector2f(Main.SCREEN_WIDTH/2,100), TextBox.Align.CENTER);
        for (int i = 0; i < controllerBox.length; i++) {
            controllerBox[i] = new TextBox("Press Start on the Controller", new Vector2f(sizeX * i + sizeX/2,Main.SCREEN_HEIGHT-20), TextBox.Align.CENTER);
            controllerBox[i].blinkTextLength = 1500;
        }
        
        new Assets();

        readSettingsFile();

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setColor(Color.white);
        instructionBox.render(graphics);

        for (int i = 0; i < inputIndices.length; i++) {
            if (inputIndices[i] != -1) {
                for (int j = 0; j < colors.length; j++) {
                    int miniX = (sizeX/colors.length);
                    graphics.setColor(colors[j]);
                    graphics.fillRect(sizeX * i + miniX*j, Main.SCREEN_HEIGHT - 70, miniX, 20);
                }
                graphics.setColor(colors[playerColors[i]]);
                graphics.fillRect(sizeX * i, Main.SCREEN_HEIGHT - 50, sizeX, 20);
            }
            controllerBox[i].render(graphics);

        }

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int dt) throws SlickException {
        for (int i = 0; i < controllerWrappers.length; i++) {
            if (controllerWrappers[i] == null) continue;

            if (controllerWrappers[i].getActionAsBoolean(Action.Pause)) {
                startGame();
            }

            if (Settings.DEBUG_CONTROLLER) {
                String s = i + ": ";
                for (int j = 0; j < Settings.NUM_ACTIONS; j++)
                    s += actions[j].name() + "(" + controllerWrappers[i].getActionAsBoolean(actions[j]) + "," + controllerWrappers[i].getActionAsFloat(actions[j]) + ") ";
                System.out.println(s);
            }
        }
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    }

    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    	
    }

    /**
     * Starts game.
     */
    private void startGame(){
        stateBasedGame.enterState(1);
        for (int j = 0; j < inputIndices.length; j++) {
            if (inputIndices[j] == -2) {
                //keyboard

            } else if (inputIndices[j] != -1) {
                GamePlayState g = (GamePlayState)stateBasedGame.getState(1);
                g.world.createPlayer(new Vector2f(Main.SCREEN_WIDTH / 2, Main.SCREEN_HEIGHT / 2), colors[playerColors[j]], controllerWrappers[j]);
            }
        }
    }

    /**
     * Start calibration for a controller creating a new controller scheme
     */
    public void prepCalibration(int controllerIndex) {
        ((CalibrationState) stateBasedGame.getState(4)).controllerIndex = controllerIndex;
        calibrateIndex = controllerIndex;

        instructionBox.text = "Controller Scheme not found for controller. Press Any key to initiate Calibration for that controller";


    }

    //File

    private void readSettingsFile() {
        settings = new File(Settings.PATH);
        if (settings.exists()) {
            //does exists - read file
            try {
                InputStream in = Files.newInputStream(settings.toPath());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line = null;
                String name = null;
                int[] btnIndices;
                int[] axisIndices;
                while ((line = reader.readLine()) != null) {
                    if (!(line.isEmpty() || line.startsWith("|"))) {
                        int i = 0;
                        name = line;
                        btnIndices = new int[Settings.NUM_ACTIONS];
                        axisIndices = new int[Settings.NUM_ACTIONS];

                        while ((line = reader.readLine()) != null && !line.startsWith("|")) {
                            if (line.startsWith("a")) {
                                axisIndices[i] = Integer.parseInt(line.substring(1));
                                btnIndices[i] = -1;
                            } else if (line.startsWith("b")) {
                                btnIndices[i] = Integer.parseInt(line.substring(1));
                                axisIndices[i] = -1;
                            }
                            i++;
                        }

                        ControllerScheme scheme = new ControllerScheme();
                        scheme.name = name;
                        scheme.buttons = btnIndices;
                        scheme.axis = axisIndices;

                        Settings.CONTROLLER_SCHEMES.add(scheme);

                    }
                }
                System.out.println(Settings.CONTROLLER_SCHEMES.size() + " schemes loaded");
                for (int i = 0; i < Settings.CONTROLLER_SCHEMES.size(); i++) {
                    System.out.println("    " + Settings.CONTROLLER_SCHEMES.get(i));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createSettingsFile(ControllerScheme[] schemes) {
        try {
            BufferedWriter settingsOut = new BufferedWriter(new FileWriter("settings.txt"));
            settingsOut.write("|Arena Settings\n" +
                    "|Made by Itai Yavin & Kasper HdL\n" +
                    "|2015\n" +
                    "|\n" +
                    "| lines with \"|\" is ignored\n" +
                    "|\n" +
                    "|Action                 (Default Button)    Number:\n" +
                    "|Name for controller scheme                 -\n" +
                    "|Pause                  (Select)            0\n" +
                    "|Attack                 (L1)                1\n" +
                    "|Movement   Axis X      (Left Stick X)      2\n" +
                    "|Movement   Axis Y      (Left Stick Y)      3\n" +
                    "|Direction  Axis X      (Right Stick X)     4\n" +
                    "|Direction  Axis Y      (Right Stick Y)     5\n" +
                    "|Select item            (X)                 6\n" +
                    "|Up                     (Up)                7\n" +
                    "|Right                  (Right)             8\n" +
                    "|Down                   (Down)              9\n" +
                    "|Left                   (Left)              10\n" +
                    "|\n" +
                    "| if prefixed with b then a button index\n" +
                    "| if prefixed with a then an axis is used\n" +
                    "|--");

            for (int i = 0; i < schemes.length; i++) {
                settingsOut.write(schemes[i].name + "\n");
                for (int j = 0; j < Settings.NUM_ACTIONS; j++) {
                    if (schemes[i].buttons[j] != -1)
                        settingsOut.write("b" + schemes[i].buttons[j] + "\n");
                    else if (schemes[i].axis[j] != -1)
                        settingsOut.write("a" + schemes[i].axis[j] + "\n");
                }
            }
            settingsOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Change colour of player.
     * @param index - playerColour array index
     * @param goLeft - direction of color to be picked
     */
    public void changeColor(int index, boolean goLeft){
        boolean foundColor = false;
        int c = playerColors[index] + (goLeft ? -1:1);
        while(!foundColor){
            if(c < 0)c = colors.length - 1;
            else if(c >= colors.length) c = 0;

            boolean otherPlayerHasColor = false;

            for (int i = 0; i < playerColors.length; i++) {
                if(c == playerColors[i]){
                    c += (goLeft ? -1:1);
                    otherPlayerHasColor = true;
                    break;
                }
            }
            if(!otherPlayerHasColor){
                foundColor = true;
            }

        }
        playerColors[index] = c;
    }

    /**
     *
     */
    @Override
    public void controllerButtonPressed(int conIndex, int btnIndex) {
        //System.out.println("con: " + conIndex + ", btn: " + btnIndex);
        if (conIndex == calibrateIndex) {
            calibrateIndex = -1;
            stateBasedGame.enterState(4);
        }
        boolean inputExists = false;
        for (int i = 0; i < inputIndices.length; i++) {
            if (conIndex == inputIndices[i]) {
                inputExists = true;
                break;
            }
        }

        if (!inputExists)
            toggleController(conIndex);
    }

    /**
     * Adds controller and saves its index.
     *
     * @param conIndex - Controller index.
     */
    public void toggleController(int conIndex) {
        int emptyIndex = -1;
        for (int i = inputIndices.length - 1; i >= 0; i--) {
            if (inputIndices[i] == -1) {
                emptyIndex = i;
            } else if (inputIndices[i] == conIndex) {
                inputIndices[i] = -1;
                emptyIndex = -1;
                playerColors[i] = -1;
                controllerBox[i].blinkText("Player " + (i + 1) + " is disconnected", Color.red);
                controllerBox[i].text = "Press Start on the Controller";
                break;
            }
        }
        if (emptyIndex != -1) {
            inputIndices[emptyIndex] = conIndex;
            controllerBox[emptyIndex].stopBlinkText();
            controllerBox[emptyIndex].text = "Player " + (emptyIndex + 1) + " is connected";

            //check if controller scheme exists for controller
            String name = org.lwjgl.input.Controllers.getController(conIndex).getName();

            boolean schemeExists = false;

            for (int i = 0; i < Settings.CONTROLLER_SCHEMES.size(); i++) {
                if (name.equals(Settings.CONTROLLER_SCHEMES.get(i).name)) {
                    schemeExists = true;
                    controllerScheme[emptyIndex] = i;
                }
            }

            if (!schemeExists) {
                prepCalibration(conIndex);
                controllerScheme[emptyIndex] = Settings.CONTROLLER_SCHEMES.size();
            } else {
                System.out.println(Settings.CONTROLLER_SCHEMES.get(controllerScheme[emptyIndex]).name + " assigned to player " + emptyIndex + " - controller index " + conIndex);
                controllerWrappers[emptyIndex] = new ControllerWrapper(conIndex, Settings.CONTROLLER_SCHEMES.get(controllerScheme[emptyIndex]));
            }

            changeColor(emptyIndex, false);
        }
    }

    @Override
    public void controllerButtonReleased(int i, int i1) {

    }


    @Override
    public void controllerLeftPressed(int i) {
        for (int j = 0; j < inputIndices.length; j++) {
            if (i == inputIndices[j]){
                changeColor(j, true);
            }
        }
    }

    @Override
    public void controllerLeftReleased(int i) {

    }

    @Override
    public void controllerRightPressed(int i) {
        for (int j = 0; j < inputIndices.length; j++) {
            if (i == inputIndices[j]){
                changeColor(j, false);
            }
        }
    }

    @Override
    public void controllerRightReleased(int i) {

    }

    @Override
    public void controllerUpPressed(int i) {

    }

    @Override
    public void controllerUpReleased(int i) {

    }

    @Override
    public void controllerDownPressed(int i) {
    	
    }

    @Override
    public void controllerDownReleased(int i) {
    	
    }


    @Override
    public void keyPressed(int i, char c) {

    }

    @Override
    public void keyReleased(int i, char c) {

    }

    @Override
    public void mouseWheelMoved(int i) {

    }

    @Override
    public void mouseClicked(int i, int i1, int i2, int i3) {

    }

    @Override
    public void mousePressed(int i, int i1, int i2) {

    }

    @Override
    public void mouseReleased(int i, int i1, int i2) {

    }

    @Override
    public void mouseMoved(int i, int i1, int i2, int i3) {

    }

    @Override
    public void mouseDragged(int i, int i1, int i2, int i3) {

    }

    @Override
    public void setInput(Input input) {

    }

    @Override
    public boolean isAcceptingInput() {
        return true;
    }

    @Override
    public void inputEnded() {

    }

    @Override
    public void inputStarted() {

    }
}
