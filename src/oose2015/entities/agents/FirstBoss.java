package oose2015.entities.agents;

import oose2015.Assets;
import oose2015.World;
import oose2015.entities.Entity;
import oose2015.entities.Wall;
import oose2015.settings.Settings;
import oose2015.utilities.CollisionUtility;
import oose2015.utilities.VectorUtility;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;

public class FirstBoss extends BossEnemy{
	protected enum MoveState {
		RIGHTHAND,
		LEFTHAND,
		BODY,
		PUNCHING,
		CHARGING,
		STUNNED
	}
	
	protected MoveState movement = MoveState.RIGHTHAND;
	
	FirstBossHand 	rightHand,
					leftHand,
					previousHand,
					punchingHand;
	
	Sound roar = Assets.SOUND_FIRST_BOSS_ROAR;
	Vector2f currentHandPosition = new Vector2f();
	Vector2f punchPosition = new Vector2f();
	
	boolean hasInitiallyRoared = false;
	boolean isRoaring = false,
			isCharging = false,
			gotChargeRot = false;
	boolean rightHandMoved = false;
	boolean leftHandMoved = false;
	boolean reachedHand = false;
	
	private float maxChargeSpeed;
	private float chargeRotation = 0;
	private float baseSpeed;
	private float punchCooldown;
	private float nextPunchTime;
	private float chargeCooldown;
	private float nextChargeTime;
	
	private float stunDuration;
	private float stunEnd;
	
	protected float attackRange;
	protected float punchSpeed;
	protected float[] playerDamages;
	
	public FirstBoss(Vector2f position, int level) {
		super(position, level);
		name = "Ogre";
        size = new Vector2f(100f, 100f);
        rightHand = new FirstBossHand(new Vector2f(position.x, position.y-size.y/2), this);
        leftHand = new FirstBossHand(new Vector2f(position.x, position.y+size.y/2), this);
		
        moveRot = false;
        
        baseSpeed = speedForce;
        punchSpeed = rightHand.speedForce * 5;
        attackRange = rightHand.armReach + size.x/2;
        engageRadius = attackRange + 100;
        punchCooldown = 5000;
        chargeCooldown = 10000;
        stunDuration = 2000;
        
        maxChargeSpeed = 50000f;
	}

	@Override
	public void update(float dt) {
		if (getClosestPlayer().position.distance(position) < engageRadius && target == null) {
			target = getClosestPlayer();
			rightHand.getTarget(target);
			leftHand.getTarget(target);
			nextPunchTime = World.TIME + punchCooldown;
			nextChargeTime = World.TIME + chargeCooldown;
		}

		if (target != null) {
			float dist = VectorUtility.getDistanceToEntity(this, target);

			findTarget();
			
			if(!isCharging)
				rotation = calculateAngleToTarget(target);
			
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
					if(!CollisionUtility.checkCollision(previousHand, this))
						moveToHand(previousHand, dt);
					
					if(dist <= attackRange && nextPunchTime < World.TIME){
						movement = MoveState.PUNCHING;
						punchPosition = target.position;
						
						if(VectorUtility.getDistanceToEntity(rightHand, target) < VectorUtility.getDistanceToEntity(leftHand, target)){
							punchingHand = rightHand;
							previousHand = rightHand;
						}else {
							punchingHand = leftHand;
							previousHand = leftHand;
						}
						punchingHand.punchHit = false;
						
						break;
					}
					
					if(nextChargeTime < World.TIME){
						movement = MoveState.CHARGING;
						
						break;
					}
					
					float distanceFromHand = VectorUtility.getDistanceToEntity(this, previousHand);
					if (distanceFromHand < 10)
						if(VectorUtility.getDistanceToEntity(this, target) > attackRange){
							if (previousHand == rightHand)
								movement = MoveState.LEFTHAND;
							else
								movement = MoveState.RIGHTHAND;
						}
					break;
					
				case PUNCHING:
					if(CollisionUtility.checkCollision(punchingHand, target)){
						World.camera.shakeScreen(new Vector2f(World.RANDOM.nextFloat()*5,World.RANDOM.nextFloat()*5),500,4f);
						movement = MoveState.BODY;
						nextPunchTime = punchCooldown + World.TIME;
						punchingHand.speedForce = Settings.ENEMY_SPEED_FORCE;
						break;
					}
					if(VectorUtility.getDistanceToEntity(this, punchingHand) < punchingHand.armReach)
						punch(punchingHand, punchPosition, dt);
					else{
						movement = MoveState.BODY;
						punchingHand.speedForce = Settings.ENEMY_SPEED_FORCE;
						nextPunchTime = punchCooldown + World.TIME;
					}
					break;
				
				case CHARGING:
					if(!gotChargeRot){
						chargeRotation = calculateAngleToTarget(target);
						gotChargeRot = true;
					}
					
					charge(chargeRotation, dt);
					break;
					
				case STUNNED:
					if(stunEnd < World.TIME)
						movement = MoveState.BODY;
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

	protected void roar(){
    	//roar.play();
    	World.camera.shakeScreen(new Vector2f(World.RANDOM.nextFloat()*30,World.RANDOM.nextFloat()*30),2000,4f);
    }

	/**
	 * @param targetPosition rapidly moves boss towards target position
	 * If collided with target at destination, target needs to take damage
	 */
	protected void charge(float rot, float dt) {
		Vector2f rightHandVector = new Vector2f(size.x/2+rightHand.size.x/2,0);
		Vector2f leftHandVector = new Vector2f(size.x/2+rightHand.size.x/2,0);
		
		Vector2f chargeVector = new Vector2f(1, 0);
		isCharging = true;
		chargeVector.add(rot);
		
		rightHandVector.add(rot);
		rightHandVector.add(135);
		
		leftHandVector.add(rot);
		leftHandVector.add(225);
		
		rightHand.position = rightHandVector.add(position);
		leftHand.position = leftHandVector.add(position);
		
		if(speedForce < maxChargeSpeed)
			speedForce = speedForce * 1.1f;
		else
			speedForce = maxChargeSpeed;
		
		move(dt, chargeVector);
	}
	
	/**
	 * @param hand
	 * @param targetPosition
	 * moves hand to target position
	 * during move if collided with target player
	 */
	protected void punch(FirstBossHand hand, Vector2f targetPosition, float dt) {
		hand.moveTowardsPosition(targetPosition, dt);
		hand.speedForce = punchSpeed;
	}
	
	@Override
	public void collides(Entity other) {
		if(isCharging)
			if(other instanceof Player){
				((Player) other).takeDamage(this, damage);
				World.camera.shakeScreen(new Vector2f(World.RANDOM.nextFloat()*10,World.RANDOM.nextFloat()*10),500,4f);
				movement = MoveState.BODY;
				nextChargeTime = World.TIME + chargeCooldown;
				speedForce = baseSpeed;
				isCharging = false;
				gotChargeRot = false;
			} else if(other instanceof Wall){
				World.camera.shakeScreen(new Vector2f(World.RANDOM.nextFloat()*30,World.RANDOM.nextFloat()*30),2000,4f);
				stunEnd = World.TIME + stunDuration;
				movement = MoveState.STUNNED;
				nextChargeTime = World.TIME + chargeCooldown;
				speedForce = baseSpeed;
				isCharging = false;
				gotChargeRot = false;
			}
	}
	
	private void findTarget() {
		for(int i = 0; i < World.PLAYERS.size(); i++)
			if(VectorUtility.getDistanceToEntity(this, World.PLAYERS.get(i)) < engageRadius)
				if(World.PLAYERS.get(i).curHealth < target.curHealth)
					target = World.PLAYERS.get(i);
	}
}
