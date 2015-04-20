package oose2015.entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.Color;
/**
 * Created by @Kasper on 26/03/2015
 * <p/>
 * Description:
 * ---
 * <p/>
 * Usage:
 * ---
 */

public class Projectile extends MovableEntity {
	public Agent owner;
	public Vector2f spawnPoint;
	public float range;

	
	public Projectile(Agent owner, float range){
		System.out.println("Projectile Created");
		this.owner = owner;
		this.range = range;
		rotation = owner.rotation;
		spawnPoint = owner.position;
		position = spawnPoint;
		size = new Vector2f(2,2);
	}
	
	public void move(int dt){
		Vector2f direction = new Vector2f(1,0);
		
		direction.add(rotation);
		
    	direction.scale(speedForce/mass);
    	acceleration.add(direction);
    	
		super.move(dt);
		System.out.println(direction);
	}
	
	public void update(int dt){
    	move(dt);
	}
	
	public void render(Graphics graphics){
		graphics.setColor(Color.yellow);
		graphics.fillOval(position.x - size.x/2, position.y - size.x/2, size.x, size.y);
	}
	
}
