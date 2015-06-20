package oose2015.entities.agents;

import org.newdawn.slick.geom.Vector2f;

public class FirstBoss extends BossEnemy{
	
	FirstBossHand 	rightHand,
					leftHand;
	
	public FirstBoss(Vector2f position, int level) {
		super(position, level);
		// TODO Auto-generated constructor stub
		name = "Ogre";
	}

	/**
	 * @param targetPosition
	 * rapidly moves boss towards target position
	 * If collided with target at destination, target needs to take damage
	 */
	protected void jump(Vector2f targetPosition){
		
	}
	
	/**
	 * @param minionAmount
	 * Spawns minions corresponding to integer size.
	 */
	protected void spawnMinions(int minionAmount){
		
	}
	
	/**
	 * @param hand
	 * @param targetPosition
	 * moves hand to target position
	 * during move if collided with target player
	 */
	protected void punch(FirstBossHand hand, Vector2f targetPosition){
		
	}
	
}
