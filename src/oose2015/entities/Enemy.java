	package oose2015.entities;

import oose2015.EntityHandler;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
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

public class Enemy extends Agent {
	
	 float golddrop;
     boolean isidle;
     float attackRadius = 100f;
     float disengageradious;
     
	
    public Entity target;

    /**
     * Constructor for Enemy
     * @param position spawn position
     */
    public Enemy(Vector2f position){
        System.out.println("Enemy created");

        curHealth = 10;
        maxHealth = curHealth;

        size = new Vector2f(25.0f, 25.0f);

        this.position = position;

        maxVelocity = 2f;

        speedForce = .2f;
        mass = 100f;
        friction = .99f;
        inertia = .60f;

      
    }
    
    public void detection(int dt){
    	
    	Player player = EntityHandler.players.get(0);
        Vector2f playerPosition = player.position.copy();
    	Vector2f delta = playerPosition.sub(position);
    	float radius = delta.length();
    	if(radius <= attackRadius){
    		delta.normalise();
    		delta.scale(speedForce/mass);
        	acceleration.add(delta);
        	super.move(dt);
    	}
    }

    @Override
    public void update(int dt){
    	detection(dt);
    }


    @Override
    public void render(Graphics graphics){
        graphics.setColor(Color.red);
        graphics.fillOval(position.x - size.x/2, position.y - size.y/2, size.x, size.y);

    }
}
