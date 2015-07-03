package oose2015.input;

import oose2015.Main;
import oose2015.World;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by kaholi on 6/23/15.
 */
public class KeyboardMouseWrapper extends InputWrapper {

    State[] actionStates;

    public KeyboardMouseWrapper() {

        actionStates = new State[InputHandler.NUM_ACTIONS];
        for (int i = 0; i < actionStates.length; i++)
            actionStates[i] = State.None;

        InputHandler.addWrapper(this);

    }

////////////////////
// Update

    @Override
    public void update() {
        for (int i = 0; i < InputHandler.NUM_ACTIONS; i++) {
            if (KeyboardMouseScheme.keys[i] != -1) {
                actionStates[i] = updateState(actionStates[i], Keyboard.isKeyDown(KeyboardMouseScheme.keys[i]));
            }
            if (KeyboardMouseScheme.mouseKeys[i] != -1) {
                actionStates[i] = updateState(actionStates[i], Mouse.isButtonDown(KeyboardMouseScheme.mouseKeys[i]));
            }
        }
    }


    private State updateState(State state, boolean isPressed) {
        switch (state) {
            case None:
                if (isPressed)
                    state = State.Down;
                else
                    state = State.None;
                break;
            case Down:
                if (isPressed)
                    state = State.Hold;
                else
                    state = State.Up;
                break;
            case Hold:
                if (!isPressed)
                    state = State.Up;
                else
                    state = State.Hold;
                break;
            case Up:
                if (isPressed)
                    state = State.Down;
                else
                    state = State.None;
                break;
        }

        return state;
    }

////////////////////
// Actions

    @Override
    public boolean getActionDown(Action action) {
        int index = action.ordinal();
        return actionStates[index] == State.Down;
    }

    @Override
    public boolean getAction(Action action) {
        int index = action.ordinal();
        return actionStates[index] == State.Hold;
    }

    @Override
    public boolean getActionUp(Action action) {
        int index = action.ordinal();
        return actionStates[index] == State.Up;
    }

    @Override
    public float getActionAxis(Action action) {
        System.out.println("Keyboard wrapper has no axis");
        return 0;
    }

    @Override
    public Vector2f getActionVector(Action x, Action y) {
        return new Vector2f(getActionAxis(x), getActionAxis(y)).normalise();
    }

    @Override
    public Vector2f getDirection() {
        return getDirection(new Vector2f());
    }


    @Override
    public Vector2f getDirection(Vector2f playerPosition) {
        if (World.camera == null) return null;

        Vector2f mouse = new Vector2f(Mouse.getX(), (Mouse.getY() * -1) + Main.SCREEN_HEIGHT);

        return World.camera.screenPointToWorldPoint(mouse).sub(playerPosition.copy()).normalise();
    }

    @Override
    public Vector2f getMovement() {
        float x, y;

        State right = actionStates[Action.Movement_X.ordinal()];
        State left = actionStates[Action.Direction_X.ordinal()];
        State up = actionStates[Action.Movement_Y.ordinal()];
        State down = actionStates[Action.Direction_Y.ordinal()];

        if (right == State.Hold || right == State.Down)
            x = 1;
        else if (left == State.Hold || left == State.Down)
            x = -1;
        else
            x = 0;

        if (up == State.Hold || up == State.Down)
            y = -1;
        else if (down == State.Hold || down == State.Down)
            y = 1;
        else
            y = 0;

        return new Vector2f(x, y).normalise();
    }


    public enum State {
        None,
        Down,
        Hold,
        Up
    }
}
