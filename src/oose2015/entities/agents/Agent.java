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
 * ---
 * <p/>
 * Usage:
 * ---
 */

public class Agent extends MovableEntity {
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
            ParticleFactory.createDeathSplatter(position.copy(), Color.red);

            return true;
        }else
            ParticleFactory.createBloodSplatter(position.copy(),new Vector2f((World.RANDOM.nextFloat()*2)-1,(World.RANDOM.nextFloat()*2)-1), Color.red);

        return false;
    }

    public void die(){
        isAlive = false;
        isSolid = false;
    }

    protected float calculateAngleToTarget(Agent other){
    // Calculates rotation from target position and own position.
    	Vector2f tVector = new Vector2f(other.position.x-position.x, other.position.y-position.y);
    	return (float)tVector.getTheta();
    }
    
    public float getDamage() {
        return 0;
    }

    protected void attack(){

    }


}
