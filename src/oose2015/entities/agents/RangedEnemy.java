package oose2015.entities.agents;

import oose2015.Assets;
import oose2015.Settings;
import oose2015.World;
import oose2015.entities.projectiles.Projectile;
import oose2015.utilities.VectorUtility;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;

public class RangedEnemy extends Enemy{
    Sound arrowSound;
    
    float attackRadius 			= Settings.ENEMY_RANGED_RADIUS;

    float nextAttackTime;
    float attackDelay 			= Settings.ENEMY_ATTACK_DELAY;

	public RangedEnemy(Vector2f position, int level) {
		super(position, level);
		// TODO Auto-generated constructor stub
        arrowSound = Assets.SOUND_ARROW_SHOOT;
	}

    /**
     * Updates enemy state and controls enemy action.
     */
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
            	chasePlayer(target,dt, isAttacking);
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
            
            if(dist < attackRadius){
      			isAttacking = true;
                //attack
              	if(isShot)
                    isShot = false;
    
              	if(nextAttackTime < World.TIME) {
                		rotation = calculateAngleToTarget(target);
                		rangedAttack();
              	}
            } else
            	isAttacking = false;
            
        }
    }
	
    protected void rangedAttack(){
        nextAttackTime = World.TIME + attackDelay;
    	new Projectile(this, damage);
    	arrowSound.play();
    }
	
}
