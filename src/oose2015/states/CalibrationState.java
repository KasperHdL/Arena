package oose2015.states;

import oose2015.Main;
import oose2015.gui.elements.TextBox;
import oose2015.input.ControllerScheme;
import org.lwjgl.input.Controller;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by kaholi on 6/23/15.
 * <p/>
 * Index must be set outside before entering this state
 */
public class CalibrationState implements GameState {

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
            "Calibration Finished!"
    };

    String[] subMessages = {
            "begins when you start pressing buttons",
            "before the timer runs out",
            "(To ensure that Accelerometers and Gyroscopes do not effect the calibration)",
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

        if (index == 1 || index == 3) {
            float s = ((float) (timerStop - Main.TIME) / timerLength);
            graphics.fillRect(0, Main.SCREEN_HEIGHT / 2, (1 - s) * Main.SCREEN_WIDTH, 5);
        }

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int dt) throws SlickException {
        if ((index == 1 || index == 3) && Main.TIME > timerStop) {
            if (index == 3)
                getBaseline();
            next();
        } else if (index > 3) {
            getChange();
        }
    }

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
                buttonChanged[numButtonsChanged++] = i;
            }
        }
        for (int i = 0; i < c.getAxisCount(); i++) {
            float base = baseAxis[i];
            float check = c.getAxisValue(i);

            if (base != check) {

                axisChanged[numAxisChanged] = i;
                axisValues[numAxisChanged++] = check;

            }
        }


        System.out.print("\nButtons Changed: ");
        for (int i = 0; i < numButtonsChanged; i++) {
            System.out.print(buttonChanged[i] + " ");
        }
        System.out.print("\nAxis Changed: ");
        for (int i = 0; i < numAxisChanged; i++) {
            int j = axisChanged[i];
            System.out.print(c.getAxisName(j) + "[" + j + "](" + baseAxis[j] + ", " + axisValues[i] + ") ");
        }
    }

    private void next() {
        index++;

        messageBox.text = messages[index];
        subMessageBox.text = subMessages[index];
    }


    //State Change

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        scheme = new ControllerScheme();
        index = 0;

        title.text = "Calibration for Player " + (playerIndex + 1) + " - Controller Index " + controllerIndex;
        messageBox.text = messages[index];
        subMessageBox.text = subMessages[index];

        c = org.lwjgl.input.Controllers.getController(controllerIndex);

        baseBtns = new boolean[c.getButtonCount()];
        baseAxis = new float[c.getAxisCount()];
    }

    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        controllerIndex = -1;
        scheme = null;
        c = null;
    }


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
            timerLength = length_wait;
            timerStop = Main.TIME + timerLength;
            next();
        }
    }


    //////////////////
    //Not used

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
