package oose2015.entities.agents;

import oose2015.EntityHandler;
import oose2015.entities.Entity;
import oose2015.entities.drops.Gold;
import oose2015.entities.projectiles.Projectile;
import oose2015.utilities.VectorUtility;
import oose2015.Assets;
import oose2015.Settings;
import oose2015.World;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;


/**
 * Created by @Kasper on 26/03/2015
 * <p/>
 * Description:
 * Enemy class creates agent that targets players. Child class of agent.
 * <p/>
 */

public class Enemy extends Agent {
    float damage = Settings.ENEMY_DAMAGE;
    
    public int goldDrop;
    public int expDrop;

    float engageRadius 			= Settings.ENEMY_ENGAGE_RADIUS;
    float disengageRadius 		= Settings.ENEMY_DISENGAGE_RADIUS;

    public boolean 	isChasing 	= false,
    				isShot 		= false,
    				isAttacking = false;
    
    Vector2f chargePoint;
	
    public Agent target;
    public Player shooter;
    
    /**
     * Constructor for Enemy
     * @param position spawn position
     */
    public Enemy(Vector2f position, int level/*, boolean isMelee*/){
        World.ENEMIES.add(this);

        curHealth 		= Settings.ENEMY_HEALTH;
        maxHealth 		= curHealth;

        this.level 		= level;
        this.position 	= position;
        
        size 			= new Vector2f(level * 5f + 15f,level * 5f + 15f );


        maxVelocity 	= Settings.ENEMY_MAX_VELOCITY;

        speedForce 		= Settings.ENEMY_SPEED_FORCE;
        mass 			= level + Settings.ENEMY_MASS_PER_LVL;
        goldDrop 		= level * Settings.ENEMY_GOLD_DROP_PER_LVL;
        expDrop 		= level * Settings.ENEMY_EXP_DROP_PER_LVL;
      
    }

    /**
     * Updates enemy state and controls enemy action.
     */
    @Override
    public void update(float dt){
        if(isAlive){
            Player player;
            if(isShot){
                player = shooter;
                isChasing = true;
            }else {
                player = getClosestPlayer();
            }

            if(player == null)
                return;

            if(isChasing){
                if(player != target)
                    target = player;
                if(!isAttacking)
                	chasePlayer(target,dt);
            }else{
                float dist = VectorUtility.getDistanceToEntity(this, player);
            }
        }
    }

    /**
     * Checks distance to player, if player is within distance enemy will chase.
     * @param agent Agent to chase
     * @param dt - delta time
     */
    protected void chasePlayer(Agent agent, float dt){
        Vector2f delta = target.position.copy().sub(position);
        float dist = VectorUtility.getDistanceToEntity(this, agent);
    	
        if(dist > disengageRadius && !isShot){
            //disengage
            target = null;
            isChasing = false;
        }else{
            //move
            move(dt,delta);
        }
    }
    //Overload
    protected void chasePlayer(Agent agent, float dt, boolean isAttacking){
        Vector2f delta = target.position.copy().sub(position);
        float dist = VectorUtility.getDistanceToEntity(this, agent);
    	
        if(dist > disengageRadius && !isShot){
            //disengage
            target = null;
            isChasing = false;
        }else if(!isAttacking){
            //move
            move(dt,delta);
        }
    }
    
    
    /**
     * Moves enemy and sets enemy rotation.
     * @param dt - delta time
     * @param input Vector passed to super class at that point added to the acceleration of the entity
     */
    @Override
    protected void move(float dt,Vector2f input){
        input.normalise();
        input.scale(speedForce);

        rotation = (float)acceleration.getTheta();

        super.move(dt, input);
    }

    /**
     * Renders enemy graphics.
     */
    @Override
    public void render(Graphics graphics){
        graphics.pushTransform();
        graphics.translate(position.x, position.y);
        graphics.rotate(0, 0, rotation);

        if(isAlive){
            graphics.setColor(Color.red);

            graphics.fillOval(-size.x / 2, -size.y / 2, size.x, size.y);
            graphics.setColor(Color.black);
            graphics.drawLine(0,0,size.x/2,0);


            float halfRadius;
            float dist = Float.MAX_VALUE;

            if(target != null)
                dist = VectorUtility.getDistanceToEntity(this, target);

            if(World.DEBUG_MODE){

                if(isChasing){

                    //disengage radius
                    halfRadius = disengageRadius + size.x/2;
                    graphics.setColor(Color.blue);
                    graphics.drawOval(-halfRadius, -halfRadius, halfRadius * 2, halfRadius * 2);
                }else{
                    graphics.setColor(Color.pink);
                    //engage radius
                    halfRadius = engageRadius + size.x/2;
                    graphics.drawOval(-halfRadius, -halfRadius, halfRadius * 2, halfRadius * 2);
                }
            }

        }

        graphics.popTransform();

        if(World.DEBUG_MODE && isAlive) {
            graphics.setColor(Color.white);
            graphics.drawString(curHealth + " / " + maxHealth, position.x + 10, position.y + 10);
            graphics.drawString("level " + level, position.x + 10, position.y - 10);
        }
    }


    /**
     * Calculates the closest living player
     * @return - closest player object
     */
    protected Player getClosestPlayer(){
        Vector2f delta;

        float minDistance = Float.MAX_VALUE;
        int minIndex = -1;

        for (int i = 0; i < World.PLAYERS.size(); i++) {
            Player p = World.PLAYERS.get(i);
            if(!p.isAlive)continue;
            delta = p.position.copy().sub(position);
            float dist = delta.length();

            if(minDistance > dist){
                minDistance = dist;
                minIndex = i;
            }
        }

        if(minIndex == -1){
            //all players dead
            return null;
        }

        return World.PLAYERS.get(minIndex);
    }

    @Override
    public float getDamage(){return damage;}

    @Override
    public void die(){
        new Gold(position,goldDrop);
        super.die();

        EntityHandler.entities.remove(this);
        World.ENEMIES.remove(this);
    }

	@Override
	public void collides(Entity other) {
	}

	@Override
	protected void attack() {
	}

}
