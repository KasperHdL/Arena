package oose2015.entities;

import oose2015.EntityHandler;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.opengl.Texture;

/**
 * Created by @Kasper on 26/03/2015
 * <p/>
 * Description:
 * ---
 * <p/>
 * Usage:
 * ---
 */

public class Entity {
    public String name;

    public Vector2f position;
    public Vector2f size;

    public float rotation;
    public Vector2f scale = new Vector2f(1f,1f);
    public Texture texture;

    //if can be pushed then it will be affected by other entities
    public boolean isMovable = true;
    public boolean isSolid = true;

    //do not send collision messages for this object

    //Completly skips all collision for that entity
    public boolean ignoreCollision = false;

    public Entity(){
        EntityHandler.entities.add(this);
    }

    public void update(float dt){

    }

    public void render(Graphics graphics){

    }

    public void collides(Entity other){

    }

    @Override
    public String toString(){
        if(name == null)
            return super.toString();

        return name;
    }
}
