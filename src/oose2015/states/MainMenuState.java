package oose2015.states;

import oose2015.Assets;
import oose2015.Main;
import oose2015.Settings;
import oose2015.gui.elements.TextBox;
import oose2015.input.Action;
import oose2015.input.ControllerScheme;
import oose2015.input.ControllerWrapper;
import oose2015.input.InputHandler;
import org.lwjgl.input.Controllers;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * Created by @Kasper on 26/03/2015
 * <p/>
 * Description:
 * Sets up main menu state.
 * <p/>
 */

public class MainMenuState extends CustomGameState {

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
    private int[] joinTime = {0, 0, 0, 0};
    private int ignoreTime = 100;

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.stateBasedGame = stateBasedGame;
        actions = Action.values();
        controllerBox = new TextBox[4];

        instructionBox = new TextBox("Press Pause(/Start) to start the game", new Vector2f(Main.SCREEN_WIDTH / 2, 100), TextBox.Align.CENTER);
        for (int i = 0; i < controllerBox.length; i++) {
            controllerBox[i] = new TextBox("Press Any Button to join", new Vector2f(sizeX * i + sizeX / 2, Main.SCREEN_HEIGHT - 20), TextBox.Align.CENTER);
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
            if (controllerWrappers[i] == null || Main.TIME < joinTime[i] + ignoreTime) continue;

            if (controllerWrappers[i].getActionDown(Action.Pause))
                startGame();
            else if (controllerWrappers[i].getActionDown(Action.Left))
                changeColor(i, true);
            else if (controllerWrappers[i].getActionDown(Action.Right))
                changeColor(i, false);
            else if (controllerWrappers[i].getActionDown(Action.Down))
                toggleController(controllerWrappers[i].getControllerIndex());

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
        for (int j = 0; j < inputIndices.length; j++) {
            if (inputIndices[j] == -2) {
                //keyboard

            } else if (inputIndices[j] != -1) {
                GamePlayState g = (GamePlayState)stateBasedGame.getState(1);
                g.world.createPlayer(new Vector2f(Main.SCREEN_WIDTH / 2, Main.SCREEN_HEIGHT / 2), colors[playerColors[j]], j);
            }
        }

        createSettingsFile(InputHandler.CONTROLLER_SCHEMES);

        stateBasedGame.enterState(1);
    }

    /**
     * Start calibration for a controller creating a new controller scheme
     */
    public void prepCalibration(int controllerIndex, int index) {
        CalibrationState calibration = (CalibrationState) stateBasedGame.getState(4);

        calibration.controllerIndex = controllerIndex;
        calibration.playerIndex = index;
        calibrateIndex = controllerIndex;

        instructionBox.text = "Controller Scheme not found for controller. Press Any key to initiate Calibration for " + Controllers.getController(controllerIndex).getName();


    }

    //File

    private void readSettingsFile() {
        settings = new File(Settings.PATH);
        if (settings.exists()) {
            //does exists - read file
            try {
                InputStream in = Files.newInputStream(settings.toPath());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!(line.isEmpty() || line.startsWith("|"))) {
                        int i = 0;

                        ControllerScheme scheme = new ControllerScheme();
                        scheme.name = line;
                        scheme.buttons = new int[InputHandler.NUM_ACTIONS];
                        scheme.axis = new int[InputHandler.NUM_ACTIONS];

                        //fill with -1
                        for (int j = 0; j < InputHandler.NUM_ACTIONS; j++) {
                            scheme.buttons[j] = -1;
                            scheme.axis[j] = -1;
                        }

                        while ((line = reader.readLine()) != null && !line.startsWith("|")) {
                            int comma = line.indexOf(",");
                            if (comma != -1) {
                                convertLineToIndicis(line.substring(0, comma), i, scheme);
                                line = line.substring(comma + 1);
                            }

                            convertLineToIndicis(line, i, scheme);

                            i++;
                        }

                        InputHandler.CONTROLLER_SCHEMES.add(scheme);

                    }
                }
                System.out.println(InputHandler.CONTROLLER_SCHEMES.size() + " schemes loaded");
                for (int i = 0; i < InputHandler.CONTROLLER_SCHEMES.size(); i++) {
                    System.out.println("    " + InputHandler.CONTROLLER_SCHEMES.get(i));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void convertLineToIndicis(String line, int i, ControllerScheme scheme) {
        if (line.startsWith("a"))
            scheme.axis[i] = Integer.parseInt(line.substring(1));
        else if (line.startsWith("b"))
            scheme.buttons[i] = Integer.parseInt(line.substring(1));

            //special cases for x, y, z, rz
        else if (line.startsWith("x"))
            scheme.axis[i] = -2;
        else if (line.startsWith("y"))
            scheme.axis[i] = -3;
        else if (line.startsWith("z"))
            scheme.axis[i] = -4;
        else if (line.startsWith("rz"))
            scheme.axis[i] = -5;
    }

    private void createSettingsFile(ArrayList<ControllerScheme> schemes) {
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
                            "|Attack                 (R1)                1\n" +
                            "|Select item            (X)                 2\n" +
                            "|Up                     (Up)                3\n" +
                            "|Right                  (Right)             4\n" +
                            "|Down                   (Down)              5\n" +
                            "|Left                   (Left)              6\n" +
                            "|Movement   Axis X      (Left Stick X)      7\n" +
                            "|Movement   Axis Y      (Left Stick Y)      8\n" +
                            "|Direction  Axis X      (Right Stick X)     9\n" +
                            "|Direction  Axis Y      (Right Stick Y)     10\n" +
                            "|\n" +
                            "| if prefixed with b then a button index\n" +
                            "| if prefixed with a then an axis is used\n" +
                            "| x,y,z,rz means that it will use the specified named axis if any\n"
            );

            for (int i = 0; i < schemes.size(); i++) {
                settingsOut.write("|--\n");
                settingsOut.write(schemes.get(i).name + "\n");
                for (int j = 0; j < InputHandler.NUM_ACTIONS; j++) {
                    if (schemes.get(i).buttons[j] != -1)
                        settingsOut.write("b" + schemes.get(i).buttons[j] + (schemes.get(i).axis[j] != -1 ? "," : ""));
                    if (schemes.get(i).axis[j] != -1) {
                        if (schemes.get(i).axis[j] < -1)
                            switch (schemes.get(i).axis[j]) {
                                case -2:
                                    settingsOut.write("x");
                                    break;
                                case -3:
                                    settingsOut.write("y");
                                    break;
                                case -4:
                                    settingsOut.write("z");
                                    break;
                                case -5:
                                    settingsOut.write("rz");
                                    break;
                            }
                        else
                            settingsOut.write("a" + schemes.get(i).axis[j]);
                    }

                    settingsOut.write("\n");
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
                controllerWrappers[i] = null;
                controllerBox[i].blinkText("Player " + (i + 1) + " is disconnected", Color.red);
                controllerBox[i].text = "Press Start on the Controller";
                break;
            }
        }
        if (emptyIndex != -1) {
            inputIndices[emptyIndex] = conIndex;
            controllerBox[emptyIndex].stopBlinkText();
            controllerBox[emptyIndex].text = "Player " + (emptyIndex + 1) + " is connected";
            joinTime[emptyIndex] = Main.TIME;

            //check if controller scheme exists for controller
            String name = org.lwjgl.input.Controllers.getController(conIndex).getName();

            boolean schemeExists = false;

            for (int i = 0; i < InputHandler.CONTROLLER_SCHEMES.size(); i++) {
                if (name.equals(InputHandler.CONTROLLER_SCHEMES.get(i).name)) {
                    schemeExists = true;
                    controllerScheme[emptyIndex] = i;
                }
            }

            if (!schemeExists) {
                prepCalibration(conIndex, emptyIndex);
                controllerScheme[emptyIndex] = InputHandler.CONTROLLER_SCHEMES.size();
            } else {
                System.out.println(InputHandler.CONTROLLER_SCHEMES.get(controllerScheme[emptyIndex]).name + " assigned to player " + emptyIndex + " - controller index " + conIndex);
                controllerWrappers[emptyIndex] = new ControllerWrapper(conIndex, InputHandler.CONTROLLER_SCHEMES.get(controllerScheme[emptyIndex]));
            }

            changeColor(emptyIndex, false);
        }
    }

    @Override
    public void controllerButtonReleased(int i, int i1) {

    }


    @Override
    public void controllerLeftPressed(int i) {

    }

    @Override
    public void controllerLeftReleased(int i) {

    }

    @Override
    public void controllerRightPressed(int i) {

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
