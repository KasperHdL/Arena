package oose2015.input;

import org.newdawn.slick.geom.Vector2f;

/**
 * Created by kaholi on 6/23/15.
 */
public class KeyboardWrapper extends InputWrapper {


////////////////////
// Update

    @Override
    public void update() {

    }

////////////////////
// Actions

    @Override
    public boolean getActionDown(Action action) {
        return false;
    }

    @Override
    public boolean getAction(Action action) {
        return false;
    }

    @Override
    public boolean getActionUp(Action action) {
        return false;
    }

    @Override
    public float getActionAxis(Action action) {
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
