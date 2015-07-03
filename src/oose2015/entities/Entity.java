package oose2015.entities;

import oose2015.EntityHandler;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by @Kasper on 26/03/2015
 * <p/>
 * Description:
 * Entity base class. Holds base values for all entities.
 * An entity is any interactable ingame object.
 * <p/>
 */

public abstract class Entity {
    public String name;

    public Vector2f position;
    public Vector2f size;

    public float rotation;
    public Vector2f scale = new Vector2f(1f,1f);
    public Image image;

    //if can be pushed then it will be affected by other entities
    public boolean isMovable = true;
    public boolean isSolid = true;

    //do not send collision messages for this object

    //Completely skips all collision for that entity
    public boolean ignoreCollision = false;

    /**
     * Adds entity to the EntityHandlers ArrayList
     */
    public Entity(){
        EntityHandler.entities.add(this);
    }

    public abstract void update(float dt);

    public abstract void render(Graphics graphics);

    public abstract void collides(Entity other);

    @Override
    public String toString(){
        if(name == null)
            return super.toString();

        return name;
    }
}
