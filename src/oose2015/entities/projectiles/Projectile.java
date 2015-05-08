package oose2015.entities.projectiles;

import oose2015.EntityHandler;
import oose2015.ParticleFactory;
import oose2015.Settings;
import oose2015.World;
import oose2015.artifacts.Artifact;
import oose2015.entities.Entity;
import oose2015.entities.MovableEntity;
import oose2015.entities.Wall;
import oose2015.entities.agents.Agent;
import oose2015.entities.agents.Enemy;
import oose2015.entities.agents.Player;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.Color;

/**
 * Created by @Itai on 26/03/2015
 * <p/>
 * Description:
 * Creates projectile class. 
 * <p/>
 */
public class Projectile extends MovableEntity {
	public Agent owner;
	
	public Vector2f spawnPoint;
	public Vector2f direction;
	
	public float spawnTime;
	public float damage;
	public float flyTime = Settings.PROJECTILE_FLY_TIME;

	public Projectile(Agent owner, float damage){
		this(owner,damage,Settings.PROJECTILE_SPEED_FORCE);
	}

	/**
	 * Projectile constructor
	 * @param owner - agent that spawned the projectile
	 * @param damage - Projectile damage
	 */
	public Projectile(Agent owner, float damage,float speedForce){
		name = "projectile";
		this.owner = owner;
		this.damage = damage;
		rotation = owner.rotation;
		direction = new Vector2f(1,0).add(owner.rotation);
		position = owner.position.copy().add(direction.copy().scale(owner.size.x / 2));
		spawnPoint = position;
		size = new Vector2f(15,15);
		spawnTime = World.TIME;
		isMovable = false;
		isSolid = false;
		this.speedForce = speedForce;
		maxVelocity = Settings.PROJECTILE_MAX_SPEED;
		friction = Settings.PROJECTILE_FRICTION;
		inertia = Settings.PROJECTILE_INERTIA;
	}
	
	/**
	 * Moves projectile.
	 */
	@Override
	protected void move(float dt){
		direction.normalise();
		direction.scale(speedForce/mass);
    	if(World.TIME > spawnTime + flyTime){
			System.out.println("spawn artifact");
			new Artifact(position,new Vector2f(size.x*2, size.y/3),rotation,Color.red.darker(0.3f));
			EntityHandler.entities.remove(this);
    	}
    	
		super.move(dt,direction);
	}
	
	/**
	 * Checks for collision.
	 * If owner is an Enemy then it checks for collisions with players.
	 * If owner is a player then it checks for collisions with enemies.
	 * Removes projectile upon collision.
	 * If collision is a wall then removes projectile.
	 */
    @Override
    public void collides(Entity other){
		System.out.println("collided " + other);
    	if(other instanceof Enemy && owner instanceof Player){
    		Enemy enemy = (Enemy) other;
        	if(enemy.isAlive) {
				if (enemy.takeDamage(owner,damage)) {
					if (owner instanceof Player) {
						Player shooter = (Player) owner;
						shooter.addExp(enemy.expDrop);
					}
				}
				
				enemy.isShot = true;
				enemy.shooter = (Player)owner;
				EntityHandler.entities.remove(this);
			}
        }else if(other instanceof Player && owner instanceof Enemy){
    		Enemy shooter = (Enemy) owner;
    		Player player = (Player) other;
        	if(player.isAlive) {
        		player.takeDamage(owner,damage);
        		
        		shooter.isShot = false;
				EntityHandler.entities.remove(this);
			}
        }else if(other instanceof Wall){
            EntityHandler.entities.remove(this);
		}
    }
	
    /**
     * Updates projectile position and particle effects.
     */
	@Override
	public void update(float dt){

		ParticleFactory.createProjectileTrail(position, direction.copy().scale(-1f));

		move(dt);
	}

	/**
	 * Render projectile graphics.
	 */
	@Override
	public void render(Graphics graphics){
		graphics.pushTransform();
		graphics.translate(position.x, position.y);
		graphics.rotate(0,0,rotation);
		graphics.setColor(Color.red);
	
		graphics.fillOval(-size.x / 2, -size.y / 2, size.x, size.y);
		graphics.popTransform();
	}
	
}
