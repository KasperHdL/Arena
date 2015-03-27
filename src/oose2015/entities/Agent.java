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

public class Agent extends MovableEntity {
    public float curHealth;
    public float maxHealth;

    public boolean isAlive = true;


    public void takeDamage(float damage){
        curHealth -= damage;
        if(curHealth <= 0)
            isAlive = false;
    }

    public float getDamage(){return 0;}

    protected void attack(){

    }


}
