package oose2015.input;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by kaholi on 6/20/15.
 */
public abstract class PlayerInput {

    public abstract void update(Input input);

    public abstract Vector2f getMovement();

    public abstract Vector2f getDirection();

    public abstract boolean getAttackPressed();

}
