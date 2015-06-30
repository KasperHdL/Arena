package oose2015.entities.agents;

import oose2015.Assets;
import oose2015.World;
import oose2015.utilities.VectorUtility;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;

public class FirstBoss extends BossEnemy{

	protected MoveState movement = MoveState.RIGHTHAND;
	FirstBossHand 	rightHand,
					leftHand,
					previousHand;
	Sound roar = Assets.SOUND_FIRST_BOSS_ROAR;
	Vector2f currentHandPosition = new Vector2f();
	boolean hasInitiallyRoared = false;
	boolean isRoaring = false,
			isJumping = false,
			isPunching = false;
	boolean rightHandMoved = false;
	boolean leftHandMoved = false;
	boolean reachedHand = false;
	float attackRange;
	float[] playerDamages;
	
	public FirstBoss(Vector2f position, int level) {
		super(position, level);
		name = "Ogre";
        size = new Vector2f(100f, 100f);
        rightHand = new FirstBossHand(new Vector2f(position.x, position.y-size.y/2), this);
        leftHand = new FirstBossHand(new Vector2f(position.x, position.y+size.y/2), this);
	}

	@Override
	public void update(float dt) {
		System.out.println(movement);
		if (target != null) {
			rightHand.update(dt);
			leftHand.update(dt);
		}

		if (getClosestPlayer().position.distance(position) < engageRadius && target == null) {
			target = getClosestPlayer();
			rightHand.getTarget(target);
			leftHand.getTarget(target);
		}

		if (target != null) {
			float dist = VectorUtility.getDistanceToEntity(this, target);

			if (!hasInitiallyRoared) {
				roar();
				hasInitiallyRoared = true;
			}

			switch (movement) {
				case RIGHTHAND:
					if (!rightHand.gotPosition)
						rightHand.calculateNextPosition();
					break;
				case LEFTHAND:
					if (!leftHand.gotPosition)
						leftHand.calculateNextPosition();
					break;
				case BODY:
					moveToHand(previousHand, dt);
					float distanceFromHand = VectorUtility.getDistanceToEntity(this, previousHand);
					if (distanceFromHand < 10)
						if (previousHand == rightHand)
							movement = MoveState.LEFTHAND;
						else
							movement = MoveState.RIGHTHAND;
					break;
			}

		}
	}
	
	@Override
	public void render(Graphics graphics) {
        graphics.pushTransform();
        graphics.translate(position.x, position.y);
        graphics.rotate(0, 0, rotation);

        if(isAlive){
        	graphics.setColor(Color.black);
        	graphics.fillOval(-size.x/2, -size.y/2, size.x, size.y);
        	graphics.setColor(Color.red);
        	graphics.drawLine(0,0,size.x/2,0);

            graphics.popTransform();
        }
	}

	protected void moveToHand(FirstBossHand hand, float dt){
		Vector2f delta = hand.position.copy().sub(position);

		move(dt, delta);
	}
	
    @Override
    protected void move(float dt,Vector2f input){
        input.normalise();
        input.scale(speedForce);

        rotation = (float)acceleration.getTheta();

        super.move(dt, input);
    }

	protected void roar(){
    	roar.play();
    	World.camera.shakeScreen(new Vector2f(World.RANDOM.nextFloat()*30,World.RANDOM.nextFloat()*30),2000,4f);
    }

	/**
	 * @param targetPosition rapidly moves boss towards target position
	 *                       If collided with target at destination, target needs to take damage
	 */
	protected void jump(Vector2f targetPosition) {

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
	protected void punch(FirstBossHand hand, Vector2f targetPosition) {

	}

	protected enum MoveState {
		RIGHTHAND,
		LEFTHAND,
		BODY
	}
	
}
