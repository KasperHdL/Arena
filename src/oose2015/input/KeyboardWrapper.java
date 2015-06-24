package oose2015.input;

import org.newdawn.slick.geom.Vector2f;

/**
 * Created by kaholi on 6/23/15.
 */
public class KeyboardWrapper extends InputWrapper {

    @Override
    public boolean getActionAsBoolean(Action action) {
        return false;
    }

    @Override
    public float getActionAsFloat(Action action) {
        return 0;
    }

    @Override
    public Vector2f getDirection() {
        return null;
    }

    @Override
    public Vector2f getMovement() {
        return null;
    }
}
