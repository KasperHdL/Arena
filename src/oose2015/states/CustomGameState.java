package oose2015.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by kaholi on 6/27/15.
 * <p/>
 * Custom Game State that hides a stuff not needed when using the Input system implemented
 * Everything can still be accessed by overriding methods, BUT REMEMBER to override isAcceptingInput() and set it to return true
 */
public abstract class CustomGameState implements GameState {

    @Override
    public abstract int getID();

    @Override
    public abstract void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException;

    @Override
    public abstract void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException;

    @Override
    public abstract void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException;

    @Override
    public abstract void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException;

    @Override
    public abstract void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException;

    @Override
    public boolean isAcceptingInput() {
        return false;
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
    public void controllerButtonPressed(int i, int i1) {

    }

    @Override
    public void controllerButtonReleased(int i, int i1) {

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
    public void inputEnded() {

    }

    @Override
    public void inputStarted() {

    }
}
