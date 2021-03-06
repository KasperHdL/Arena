package oose2015.states;

import oose2015.Assets;
import oose2015.Main;
import oose2015.gui.elements.TextBox;
import oose2015.input.Action;
import oose2015.input.ControllerWrapper;
import oose2015.input.InputHandler;
import oose2015.input.KeyboardMouseWrapper;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

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

        instructionBox = new TextBox("Press Pause(/Start) to start the game, To add Keyboard Player press space ( enter to start)", new Vector2f(Main.SCREEN_WIDTH / 2, 100), TextBox.Align.CENTER);
        for (int i = 0; i < controllerBox.length; i++) {
            controllerBox[i] = new TextBox("Press Any Button to join", new Vector2f(sizeX * i + sizeX / 2, Main.SCREEN_HEIGHT - 20), TextBox.Align.CENTER);
            controllerBox[i].blinkTextLength = 1500;
        }
        
        new Assets();

        oose2015.settings.File.read();

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

                GamePlayState g = (GamePlayState) stateBasedGame.getState(1);
                g.world.createPlayer(new Vector2f(Main.SCREEN_WIDTH / 2, Main.SCREEN_HEIGHT / 2), colors[playerColors[j]], j, new KeyboardMouseWrapper());


            } else if (inputIndices[j] != -1) {
                GamePlayState g = (GamePlayState)stateBasedGame.getState(1);
                g.world.createPlayer(new Vector2f(Main.SCREEN_WIDTH / 2, Main.SCREEN_HEIGHT / 2), colors[playerColors[j]], j, controllerWrappers[j]);
            }
        }

        oose2015.settings.File.create(InputHandler.CONTROLLER_SCHEMES);

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

    public void addKeyboard() {
        boolean noKeyboard = true;
        int emptyIndex = -1;
        for (int i = inputIndices.length - 1; i >= 0; i--) {
            if (inputIndices[i] == -2)
                noKeyboard = false;
            if (inputIndices[i] == -1) {
                emptyIndex = i;
            }
        }
        if (!noKeyboard) return;

        if (emptyIndex != -1) {
            inputIndices[emptyIndex] = -2;
            controllerBox[emptyIndex].stopBlinkText();
            controllerBox[emptyIndex].text = "Player " + (emptyIndex + 1) + " is connected";
            changeColor(emptyIndex, false);
        }
    }

    public void removeKeyboard() {
        for (int i = inputIndices.length - 1; i >= 0; i--) {
            if (inputIndices[i] == -2) {
                inputIndices[i] = -1;
                playerColors[i] = -1;
                controllerWrappers[i] = null;
                controllerBox[i].blinkText("Player " + (i + 1) + " is disconnected", Color.red);
                controllerBox[i].text = "Press Start on the Controller";
                break;
            }
        }
    }
    @Override
    public void keyPressed(int i, char c) {
        if (i == Keyboard.KEY_SPACE)
            addKeyboard();
        else if (i == Keyboard.KEY_ESCAPE)
            removeKeyboard();
        else if (i == Keyboard.KEY_LEFT || i == Keyboard.KEY_RIGHT) {
            boolean goLeft = i == Keyboard.KEY_LEFT;

            int keyboard = -1;
            for (int index = inputIndices.length - 1; index >= 0; index--) {
                if (inputIndices[index] == -2) {
                    keyboard = index;
                }
            }
            if (keyboard != -1)
                changeColor(keyboard, goLeft);
        } else if (i == Keyboard.KEY_RETURN) {
            int keyboard = -1;
            for (int index = inputIndices.length - 1; index >= 0; index--) {
                if (inputIndices[index] == -2) {
                    keyboard = index;
                }
            }
            if (keyboard != -1)
                startGame();
        }
    }

    @Override
    public boolean isAcceptingInput() {
        return true;
    }

}
