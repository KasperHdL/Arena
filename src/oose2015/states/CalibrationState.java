package oose2015.states;

import oose2015.input.ControllerScheme;
import org.lwjgl.input.Controller;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by kaholi on 6/23/15.
 * <p/>
 * Index must be set outside before entering this state
 */
public class CalibrationState implements GameState {

    public int controllerIndex;

    StateBasedGame stateBasedGame;

    ControllerScheme scheme;
    Controller c;


    @Override
    public int getID() {
        return 4;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.stateBasedGame = stateBasedGame;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int dt) throws SlickException {
        for (int i = 0; i < c.getAxisCount(); i++) {
            System.out.println(c.getAxisName(i) + " = " + c.getAxisValue(i));
        }
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        scheme = new ControllerScheme();

        c = org.lwjgl.input.Controllers.getController(controllerIndex);

    }

    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        controllerIndex = -1;
        scheme = null;
        c = null;
    }

    @Override
    public void controllerButtonPressed(int controllerIndex, int index) {

    }

    @Override
    public void controllerButtonReleased(int controllerIndex, int index) {

    }

    @Override
    public void keyPressed(int i, char c) {
        if (i == Input.KEY_ESCAPE || i == Input.KEY_SPACE) {
            //exit
            stateBasedGame.enterState(0);
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
