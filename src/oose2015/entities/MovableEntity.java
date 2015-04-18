package oose2015.entities;

import org.newdawn.slick.geom.Vector2f;

/**
 * Created by @Kasper on 26/03/2015
 * <p/>
 * Description:
 * ---
 * <p/>
 * Usage:
 * ---
 */

public class MovableEntity extends Entity{

    public Vector2f velocity;
    public Vector2f acceleration;

    //limits Entity velocity to set value
    public float maxVelocity = 100f;

    //physical forces, speedForce is applied to acceleration
    public float speedForce = 1f;
    public float mass = 1f;
    public float friction = .9f;
    public float inertia = .85f;

    public MovableEntity(){
        velocity = new Vector2f(0,0);
        acceleration = new Vector2f(0,0);
    }

    /**
     * Moves the Entity according to the correct physical forces of the Entity. applies friction, inertia and limits the velocity. then adds acceleration to velocity and then velocity to position
     * @param dt delta time
     */

    protected void move(float dt){
        move(dt,new Vector2f());
    }
    protected void move(float dt,Vector2f input){
        acceleration.scale(inertia);
        velocity.scale(friction);

        acceleration.add(input.scale(dt));

        if(velocity.length() > maxVelocity){
            velocity = velocity.getNormal().scale(maxVelocity);
        }

        velocity.scale(dt);

        velocity.add(acceleration);

        position.add(velocity);
    }
}
