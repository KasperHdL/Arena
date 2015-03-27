package oose2015.entities;

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
    public Vector2f scale;
    public Texture texture;

    public Entity(){
        scale = new Vector2f(1f,1f);
    }

    public void update(int dt){

    }

    public void render(Graphics graphics){

    }
}
