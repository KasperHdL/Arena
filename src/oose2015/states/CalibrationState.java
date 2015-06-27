package oose2015.states;

import oose2015.Main;
import oose2015.gui.elements.TextBox;
import oose2015.input.Action;
import oose2015.input.ControllerScheme;
import oose2015.input.ControllerWrapper;
import oose2015.input.InputHandler;
import org.lwjgl.input.Controller;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by kaholi on 6/23/15.
 * <p/>
 * Index must be set outside before entering this state
 */
public class CalibrationState extends CustomGameState {

    public int playerIndex;
    public int controllerIndex;
    StateBasedGame stateBasedGame;
    ControllerScheme scheme;
    Controller c;
    int index = 0;
    int buttonOffset = 4;
    String[] messages = {
            "Press EVERY button, trigger and stick before the timer runs out",
            "PRESS EVERYTHING...",
            "Place the Controller anywhere, so it does not move, Then press press space on the keyboard and wait..",
            "Wait...",
            "Press and Hold the button to pause with",
            "Press and Hold the button to attack with",
            "Press and Hold the button to interact with",
            "Press and Hold the button to up with",
            "Press and Hold the button to right with",
            "Press and Hold the button to down with",
            "Press and Hold the button to left with",
            "Calibration Finished! Press Pause to Exit"
    };
    String[] subMessages = {
            "begins when you start pressing buttons",
            "before the timer runs out",
            "(Please leave the controller there and try not to move it from here on out, also when pressing buttons)",
            "(and do not touch the controller)",
            "(For PS3 controllers recommended button is Start)",
            "(For PS3 controllers recommended button is R1)",
            "(For PS3 controllers recommended button is X [Cross])",
            "(For PS3 controllers recommended button is Up)",
            "(For PS3 controllers recommended button is Right)",
            "(For PS3 controllers recommended button is Down)",
            "(For PS3 controllers recommended button is Left)",
            "if you want to recalibrate press Enter on the Keyboard"
    };
    TextBox title;
    TextBox exitBox;
    TextBox messageBox;
    TextBox subMessageBox;
    int timerStop;
    int timerLength;
    int length_pressAllButtons = 10000;
    int length_wait = 1000;
    boolean[] baseBtns;
    float[] baseAxis;
    float cumHeight = Main.SCREEN_HEIGHT * 0.2f;
    float colOffset = 2f;
    float[] cumBtns;
    float[] cumAxis;
    float[] minAxis;
    float registerHardLimit = 100f;
    float registerColorLimit = 90f;
    private boolean debugChanges = false;

    @Override
    public int getID() {
        return 4;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.stateBasedGame = stateBasedGame;

        String s = "To Exit press Escape on the keyboard";
        exitBox = new TextBox(s, new Vector2f((Main.SCREEN_WIDTH - 5) - gameContainer.getGraphics().getFont().getWidth(s) / 2, 5), TextBox.Align.CENTER);

        title = new TextBox("Calibration for Player " + playerIndex + " - Controller Index " + controllerIndex, new Vector2f(Main.SCREEN_WIDTH / 2, Main.SCREEN_HEIGHT * 0.05f), TextBox.Align.CENTER);
        messageBox = new TextBox(messages[0], new Vector2f(Main.SCREEN_WIDTH / 2, Main.SCREEN_HEIGHT * 0.45f), TextBox.Align.CENTER);
        subMessageBox = new TextBox(subMessages[0], new Vector2f(Main.SCREEN_WIDTH / 2, Main.SCREEN_HEIGHT * 0.55f), TextBox.Align.CENTER);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setColor(Color.white);
        exitBox.render(graphics);

        title.render(graphics);
        messageBox.render(graphics);
        subMessageBox.render(graphics);

        if (timerStop != 0) {
            float s = ((float) (timerStop - Main.TIME) / timerLength);
            graphics.fillRect(0, Main.SCREEN_HEIGHT / 2, (1 - s) * Main.SCREEN_WIDTH, 5);
        }

        if (index > 3) {
            float numCols = cumBtns.length + cumAxis.length;
            float colWidth = (Main.SCREEN_WIDTH / numCols) - colOffset;
            for (int i = 0; i < numCols; i++) {
                float value;
                if (i < cumBtns.length) {
                    value = cumBtns[i] / registerColorLimit;
                } else {
                    value = cumAxis[i - cumBtns.length] / registerColorLimit;
                }
                if (value > 1f)
                    graphics.setColor(Color.red);
                else
                    graphics.setColor(Color.white);
                graphics.fillRect(i * (colWidth + colOffset), Main.SCREEN_HEIGHT - (value) * cumHeight, colWidth, (value) * cumHeight);
            }
        }

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int dt) throws SlickException {
        if ((index == 1 || index == 3) && index < messages.length - 1 && Main.TIME > timerStop && timerStop != 0) {

            if (index == 3)
                getBaseline();
            next();
        } else if (index > 3 && index < messages.length - 1) {

            getChange();

            if (checkIfChangesHitLimit()) {
                recordChanges();
                next();
            }

        }
    }

    /////////////

    private void getBaseline() {
        for (int i = 0; i < c.getButtonCount(); i++)
            baseBtns[i] = c.isButtonPressed(i);
        for (int i = 0; i < c.getAxisCount(); i++)
            baseAxis[i] = c.getAxisValue(i);
    }

    private void getChange() {

        int[] buttonChanged = new int[c.getButtonCount()];
        int numButtonsChanged = 0;

        int[] axisChanged = new int[c.getAxisCount()];
        float[] axisValues = new float[c.getAxisCount()];
        int numAxisChanged = 0;

        for (int i = 0; i < c.getButtonCount(); i++) {
            boolean base = baseBtns[i];
            boolean check = c.isButtonPressed(i);

            if (base != check) {
                cumBtns[i] += 1;
                buttonChanged[numButtonsChanged++] = i;
            }
        }
        for (int i = 0; i < c.getAxisCount(); i++) {
            float base = baseAxis[i];
            float check = c.getAxisValue(i);

            float value = Math.abs(check - base);
            if (value > 1f)
                value /= 2f;
            cumAxis[i] += Math.ceil(value);

            if (base != check) {

                axisChanged[numAxisChanged] = i;
                axisValues[numAxisChanged++] = check;

            }
        }

        if (debugChanges) {
            System.out.print("\n" + numButtonsChanged + " Buttons Changed: ");
            for (int i = 0; i < numButtonsChanged; i++)
                System.out.print(buttonChanged[i] + " ");

            System.out.print("\n" + numAxisChanged + " Axis Changed: ");
            for (int i = 0; i < numAxisChanged; i++) {
                int j = axisChanged[i];
                System.out.print(c.getAxisName(j) + "[" + j + "](" + baseAxis[j] + ", " + axisValues[i] + ") - ");
            }
        }
    }

    private boolean checkIfChangesHitLimit() {
        int total = cumBtns.length + cumAxis.length;
        boolean hardLimitHit = false;
        for (int i = 0; i < total; i++) {
            float value = (i < cumBtns.length ? cumBtns[i] : cumAxis[i - cumBtns.length]);
            if (value > registerHardLimit) hardLimitHit = true;
        }
        return hardLimitHit;
    }

    private void recordChanges() {
        int total = cumBtns.length + cumAxis.length;

        for (int i = 0; i < total; i++) {
            boolean isBtn = i < cumBtns.length;
            int a = i - cumBtns.length;
            float value = (isBtn ? cumBtns[i] : cumAxis[a]);

            if (value > registerColorLimit) {
                if (isBtn)
                    scheme.buttons[index - buttonOffset] = i;
                else
                    scheme.axis[index - buttonOffset] = a;
            }
        }
    }

    private void next() {
        index++;
        timerStop = 0;

        cumAxis = new float[c.getAxisCount()];
        cumBtns = new float[c.getButtonCount()];

        if (index == messages.length - 1) {
            System.out.println(scheme);
            prepExit();
        } else if (index >= messages.length)
            return;
        messageBox.text = messages[index];
        subMessageBox.text = subMessages[index];
    }

    private void prepExit() {
        InputHandler.CONTROLLER_SCHEMES.add(scheme);
        int c = -2;
        for (int i = Action.Movement_X.ordinal(); i < Action.Direction_Y.ordinal() + 1; i++, c--) {
            if (scheme.axis[i] == -1)
                scheme.axis[i] = c;
        }

        MainMenuState menu = (MainMenuState) stateBasedGame.getState(0);

        System.out.println(InputHandler.CONTROLLER_SCHEMES.get(menu.controllerScheme[playerIndex]).name + " assigned to player " + playerIndex + " - controller index " + controllerIndex);
        menu.controllerWrappers[playerIndex] = new ControllerWrapper(controllerIndex, scheme);
        menu.instructionBox.text = "Press Pause(/Start) to start the game";

        System.out.println("exiting calibration");
        stateBasedGame.enterState(0);
    }

/////////////
    //State Change

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        c = org.lwjgl.input.Controllers.getController(controllerIndex);

        scheme = new ControllerScheme();
        scheme.name = c.getName();
        scheme.buttons = new int[InputHandler.NUM_ACTIONS];
        scheme.axis = new int[InputHandler.NUM_ACTIONS];
        minAxis = new float[c.getAxisCount()];

        //fill with -1
        for (int i = 0; i < InputHandler.NUM_ACTIONS; i++) {
            scheme.buttons[i] = -1;
            scheme.axis[i] = -1;
        }


        index = 0;

        title.text = "Calibration for Player " + (playerIndex + 1) + " - Controller Index " + controllerIndex;
        messageBox.text = messages[index];
        subMessageBox.text = subMessages[index];


        baseBtns = new boolean[c.getButtonCount()];
        baseAxis = new float[c.getAxisCount()];
    }

    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        controllerIndex = -1;
        scheme = null;
        c = null;
    }

/////////////
    //input

    @Override
    public void controllerButtonPressed(int controllerIndex, int buttonIndex) {
        if (controllerIndex != this.controllerIndex) return;
        if (index == 0) {
            next();
            timerLength = length_pressAllButtons;
            timerStop = Main.TIME + timerLength;
        }
    }

    @Override
    public void controllerButtonReleased(int controllerIndex, int index) {

    }

    @Override
    public void keyPressed(int i, char c) {
        if (i == Input.KEY_ESCAPE) {
            //exit
            stateBasedGame.enterState(0);
        } else if (index == 2 && i == Input.KEY_SPACE) {
            next();
            timerLength = length_wait;
            timerStop = Main.TIME + timerLength;
        }
    }

    @Override
    public boolean isAcceptingInput() {
        return true;
    }

}
