package oose2015.particles;

import oose2015.EntityHandler;
import oose2015.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by @Kasper on 05/05/2015
 * <p/>
 * Description:
 * Particle base class.
 * <p/>
 */

public class Particle {

    public Vector2f position;
    public Vector2f size;
    public float rotation;

    public Vector2f velocity;
    public float angularVelocity;

    public float renderSize;

    public Color color;

    private int endTime;
    private int length;

    //fading
    private boolean fadeOut = false;



    /**
     * Overloaded Particle constructor
     * @param position Position
     * @param size size
     * @param rotation rotation
     * @param velocity velocity
     * @param angularVelocity angular velocity
     * @param time time
     * @param color color
     */
    public Particle(Vector2f position,int size,float rotation,Vector2f velocity,float angularVelocity,int time, Color color) {
        this(position,size,rotation,velocity,angularVelocity,time,false,color);
    }

    /**
     * Particle constructor
     * @param position Position
     * @param size size
     * @param rotation rotation
     * @param velocity velocity
     * @param angularVelocity angular velocity
     * @param time time
     * @param fadeOut if true particle will fade to be transparent
     * @param color color
     */
    public Particle(Vector2f position,int size,float rotation,Vector2f velocity,float angularVelocity,int time,boolean fadeOut, Color color){
        EntityHandler.particles.add(this);
        this.position = position;
        this.size = new Vector2f(size,size);
        this.rotation = rotation;

        this.velocity = velocity;
        this.angularVelocity = angularVelocity;

        this.color = color;

        this.fadeOut = fadeOut;
        length = time;
        endTime = World.TIME + length;

        renderSize = this.size.length();
    }


    /**
     * Update particle state
     * @param dt - delta time
     */
    public void update(float dt){
        if(endTime < World.TIME)
            destroy();

        if(fadeOut){
            color.a = (float)(endTime - World.TIME)/length;
        }


        rotation += angularVelocity * dt;
        position.add(velocity.copy().scale(dt));
    }

    /**
     * Remove particle object from EntityHandler ArrayList
     */
    protected void destroy(){
        EntityHandler.particles.remove(this);
    }

    /**
     * Render particle graphics.
     * @param graphics Graphics reference
     */
    public void render(Graphics graphics){
        graphics.pushTransform();
        graphics.translate(position.x, position.y);
        graphics.rotate(0, 0, rotation);

        graphics.setColor(color);
        graphics.fillRect(-size.x/2,-size.y/2,size.x,size.y);

        graphics.popTransform();
    }


}
