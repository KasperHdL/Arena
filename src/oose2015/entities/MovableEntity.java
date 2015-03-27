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
    public float friction = 0f;

    //if solid then Entities will stop when collides
    public boolean isSolid = true;


    protected void move(int dt){
        acceleration.scale(friction);

        if(velocity.length() > maxVelocity){
            velocity = velocity.getNormal().scale(maxVelocity);
        }

        velocity.add(acceleration);
        position.add(velocity);
    }
}
