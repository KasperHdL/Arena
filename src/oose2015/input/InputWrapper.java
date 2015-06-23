package oose2015.input;

import org.newdawn.slick.geom.Vector2f;

/**
 * Created by kaholi on 6/23/15.
 */
public abstract class InputWrapper {

    public abstract boolean getActionAsBoolean(Action action);

    public abstract float getActionAsFloat(Action action);

    public Vector2f getActionAsVector(Action x, Action y) {
        return new Vector2f(getActionAsFloat(x), getActionAsFloat(y));
    }
}
