package oose2015.entities.agents;

import oose2015.Settings;
import oose2015.World;
import oose2015.entities.Entity;
import oose2015.entities.agents.Player;
import oose2015.utilities.VectorUtility;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class MeeleeEnemy extends Enemy{
	
    float attackRadius 			= Settings.ENEMY_MELEE_RADIUS;

    float nextAttackTime;
    float attackDelay 			= Settings.ENEMY_ATTACK_DELAY;
    float nextChargeTime 		= 0;
    float chargeDelay 			= Settings.CHARGE_DELAY;
    float minChargeDistance 	= Settings.MIN_CHARGE_DISTANCE;
    float maxChargeDistance 	= Settings.MAX_CHARGE_DISTANCE;
    float chargeTime 			= Settings.CHARGE_TIME;
    float chargeEndTime 		= 0;
    int chargeScalar 			= Settings.CHARGE_SCALAR;

    public boolean isCharging 	= false;
    
	public MeeleeEnemy(Vector2f position, int level) {
		super(position, level);
		// TODO Auto-generated constructor stub
	}
	
    @Override
    public void update(float dt){
        if(isAlive){
        	float dist;
            Player player;
            if(isShot){
                player = shooter;
                isChasing = true;
            }else {
                player = getClosestPlayer();
            }

            if(player == null)
                return;
            else
                dist = VectorUtility.getDistanceToEntity(this, player);

            if(isChasing){
                if(player != target)
                    target = player;
                if(!isAttacking)
                	chasePlayer(target,dt);
            }else{

                //check distance
                if(dist < engageRadius){
                    isChasing = true;
                    target = player;
                }else {
                    //idle
                    //TODO move a bit around randomly when not chasing any one
                }
            }
	            
	        if(nextChargeTime < World.TIME && !isCharging && dist > minChargeDistance && dist < maxChargeDistance){
				speedForce = speedForce*chargeScalar;
				chargeEndTime = chargeTime + World.TIME;
				nextChargeTime = chargeDelay + World.TIME;
			    isCharging = true;
	        } else if(isCharging && chargeEndTime <= World.TIME){
	        	speedForce = Settings.ENEMY_SPEED_FORCE;
	        	isCharging = false;
	        }
	          
	        if(dist < attackRadius){
	        	isAttacking = true;
	        	//attack
	        	if(isShot)
	        		isShot = false;
	  
	        	if(nextAttackTime < World.TIME) {
	        		target.takeDamage(this, getDamage());
	        		if(!isCharging) {
                        nextAttackTime = World.TIME + attackDelay;
                    }
	            }	            
	        } else
		        	isAttacking = false;
        }
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

            //attack radius
            halfRadius = attackRadius + size.x/2;
            if(nextAttackTime - 50 < World.TIME && dist < attackRadius) {
                graphics.setColor(Color.red);
                graphics.fillOval(-halfRadius, -halfRadius, halfRadius*2, halfRadius*2);
            }else if(World.DEBUG_MODE)
                graphics.drawOval(-halfRadius, -halfRadius, halfRadius * 2, halfRadius * 2);


        }

        graphics.popTransform();

        if(World.DEBUG_MODE && isAlive) {
            graphics.setColor(Color.white);
            graphics.drawString(curHealth + " / " + maxHealth, position.x + 10, position.y + 10);
            graphics.drawString("level " + level, position.x + 10, position.y - 10);
        }
    }
    
	@Override
	public void collides(Entity other) {
		Player player;
		if(other instanceof Player){
			player = (Player) other;
			if(isCharging){
				player.takeDamage(this, damage);
			}
		}
	}
	
}
