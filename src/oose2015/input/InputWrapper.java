package oose2015.input;

import org.newdawn.slick.geom.Vector2f;

/**
 * Created by kaholi on 6/23/15.
 * abstract wrapper to be extended by keyboard, controller wrappers
 */
public abstract class InputWrapper {

    public abstract void update();

    public abstract boolean getActionDown(Action action);

    public abstract boolean getAction(Action action);

    public abstract boolean getActionUp(Action action);

    public abstract float getActionAxis(Action action);

    public abstract Vector2f getActionVector(Action x, Action y);

    public abstract Vector2f getMovement();

    public abstract Vector2f getDirection();

    public Vector2f getDirection(Vector2f playerPosition) {
        return getDirection();
    }

///////////
//Print

    public void printActions() {
        String s = "";
        Action[] actions = Action.values();
        for (int i = 0; i < InputHandler.NUM_ACTIONS; i++) {
            switch (actions[i]) {
                case Pause:
                    s += getAction(Action.Pause);
                    break;
                case Attack:
                    s += getAction(Action.Attack);
                    break;
                case Movement_X:
                    s += getMovement();
                    i++;
                    break;
                case Direction_X:
                    s += getDirection();
                    i++;
                    break;
                case Select:
                    s += getAction(Action.Select);
                    break;
                case Up:
                    s += getAction(Action.Up);
                    break;
                case Right:
                    s += getAction(Action.Right);
                    break;
                case Down:
                    s += getAction(Action.Down);
                    break;
                case Left:
                    s += getAction(Action.Left);
                    break;
            }
        }
    }
}
