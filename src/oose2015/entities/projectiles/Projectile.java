package oose2015.entities.projectiles;

import oose2015.EntityHandler;
import oose2015.World;

import oose2015.entities.Entity;
import oose2015.entities.MovableEntity;
import oose2015.entities.agents.Agent;
import oose2015.entities.agents.Enemy;
import oose2015.entities.agents.Player;
import org.newdawn.slick.Graphics;
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
	public float damage;
	public float flyTime = 1500;

	
	public Projectile(Agent owner, float range, float damage, float speedForce){
		name = "projectile";
		this.owner = owner;
		this.range = range;
		this.damage = damage;
		rotation = owner.rotation;
		direction = new Vector2f(1,0).add(owner.rotation);
		position = owner.position.copy().add(direction.copy().scale(owner.size.x/2));
		spawnPoint = position;
		size = new Vector2f(10,10);
		spawnTime = World.TIME;
		isSolid = false;
		this.speedForce = speedForce;
		friction = 0.99f;
		inertia = 0.999f;
	}
	
	@Override
	protected void move(float dt){
		direction.scale(speedForce/mass);
    	if(World.TIME > spawnTime+flyTime){
			EntityHandler.entities.remove(this);
    	}
    	
		super.move(dt,direction);
	}
	
    @Override
    public void collides(Entity other){
    	if(other instanceof Enemy && owner instanceof Player){
    		Enemy enemy = (Enemy) other;
        	if(enemy.isAlive) {
				if (enemy.takeDamage(damage)) {
					if (owner instanceof Player) {
						Player shooter = (Player) owner;
						shooter.addExp(enemy.expDrop);
					}
				}
				
				enemy.isShot = true;
				enemy.shooter = (Player)owner;
				EntityHandler.entities.remove(this);
			}
        }
    	
    	if(other instanceof Player && owner instanceof Enemy){
    		Enemy shooter = (Enemy) owner;
    		Player player = (Player) other;
        	if(player.isAlive) {
        		player.takeDamage(damage);
        		
        		shooter.isShot = false;
				EntityHandler.entities.remove(this);
			}
        }
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