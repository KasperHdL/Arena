package oose2015.entities.agents;

import oose2015.ParticleFactory;
import oose2015.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

import oose2015.entities.MovableEntity;

/**
 * Created by @Kasper on 26/03/2015
 * <p/>
 * Description:
 * Child class of MovableEntity class. Parent class for objects that can die.
 * <p/>
 */

public abstract class Agent extends MovableEntity {
    public float curHealth;
    public float maxHealth;

    public boolean isAlive = true;
    public int level;

    /**
     * The Agent takes damage ie. subtracts damage from health and checks if it is still alive
     * @param damage damage
     */
    public boolean takeDamage(Agent attacker,float damage){
        if(!isAlive) return false;

        curHealth -= damage;
        if(curHealth <= 0){
            die();
            ParticleFactory.createDeathSplatter(position.copy(), new Color(200,50,50));

            return true;
        }else
            ParticleFactory.createBloodSplatter(position.copy(),new Vector2f((World.RANDOM.nextFloat()*2)-1,(World.RANDOM.nextFloat()*2)-1), new Color(200,50,50));

        return false;
    }

    /**
     * Kills agent and makes it possible for other objects to go through it.
     */
    public void die(){
        isAlive = false;
        isSolid = false;
    }

    /**
     * Returns angle between agents.
     * @param other - other agent
     * @return - float angle
     */
    protected float calculateAngleToTarget(Agent other){
    // Calculates rotation from target position and own position.
    	return (float)other.position.copy().sub(position).getTheta();
    }
    
    public abstract float getDamage();

    protected abstract void attack();


}
