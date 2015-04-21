package oose2015.entities;

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
    public boolean takeDamage(float damage){
        if(!isAlive) return false;

        curHealth -= damage;
        if(curHealth <= 0){
            die();
            return true;
        }
        return false;
    }

    public void die(){
        isAlive = false;
        isSolid = false;
    }

    public float getDamage() {
        return 0;
    }

    protected void attack(){

    }


}
