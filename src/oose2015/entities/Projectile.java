package oose2015.entities;

import oose2015.World;

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
	public float spawnTime;
	public float range;
	public Vector2f direction;

	
	public Projectile(Agent owner, float range){
		name = "projectile";
		this.owner = owner;
		this.range = range;
		rotation = owner.rotation;
		direction = new Vector2f(1,0).add(owner.rotation);
		position = owner.position.copy().add(direction.copy().scale(owner.size.x/2));
		spawnPoint = position;
		size = new Vector2f(10,10);
		spawnTime = World.time;
		isSolid = false;
		speedForce = 10f;
		friction = 0.99f;
		inertia = 0.999f;
	}
	
	@Override
	protected void move(float dt){
    	direction.scale(speedForce/mass);
    	
		super.move(dt,direction);
	}
	

	
	@Override
	public void update(float dt){
    	move(dt);
	}

	
	@Override
	public void render(Graphics graphics){
		graphics.pushTransform();
		graphics.translate(position.x, position.y);
		graphics.rotate(0,0,rotation);
		graphics.setColor(Color.red);
	
		graphics.fillOval(-size.x/2, -size.x/2, size.x, size.y);
		graphics.popTransform();
	}
	
}
