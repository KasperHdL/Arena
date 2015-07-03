package oose2015.entities.agents;

import oose2015.settings.Settings;
import oose2015.entities.Entity;
import oose2015.entities.agents.FirstBoss.MoveState;
import oose2015.utilities.VectorUtility;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class FirstBossHand extends Agent{

	private Agent target;
	private Vector2f nextPosition = new Vector2f(1,0);
	float distance;
	
	protected float armReach = 100;
	
	protected boolean gotPosition = false;
	protected FirstBoss body;
	
	public FirstBossHand(Vector2f position, FirstBoss body){
        size = new Vector2f(50f, 50f);
        this.position = position;
        
        maxVelocity 	= Settings.ENEMY_MAX_VELOCITY;

        speedForce 		= Settings.ENEMY_SPEED_FORCE;
        mass 			= level + Settings.ENEMY_MASS_PER_LVL;
        
        this.body = body;
    }
	
	@Override
	public float getDamage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void attack() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		if(gotPosition){
			distance = VectorUtility.getDistanceToPoint(this, nextPosition);
			moveToPosition(dt);
			body.currentHandPosition = position;
			if(distance <= target.size.x/2){
				body.previousHand = this;
				body.movement = MoveState.BODY;
				gotPosition = false;
			}
		}
	}

	@Override
	public void render(Graphics graphics) {
		// TODO Auto-generated method stub
        graphics.pushTransform();
        graphics.translate(position.x, position.y);
        graphics.rotate(0, 0, rotation);
        
    	graphics.setColor(Color.black);
    	graphics.fillOval(-size.x/2, -size.y/2, size.x, size.y);
    	graphics.setColor(Color.red);
    	
        graphics.popTransform();
        
        graphics.pushTransform();
        graphics.translate(nextPosition.x, nextPosition.y);
        graphics.rotate(0, 0, rotation);
        
    	graphics.setColor(Color.blue);
    	graphics.fillOval(-size.x/2, -size.y/2, size.x, size.y);
    	graphics.setColor(Color.red);
    	
        graphics.popTransform();
	}

	@Override
	public void collides(Entity other) {
		// TODO Auto-generated method stub
		
	}
	
	private void moveToPosition(float dt){
		Vector2f delta = new Vector2f();
		delta = nextPosition.copy().sub(position);
		move(dt, delta);
	}
	
	/**
	 * Calculates next position using current target.
	 */
	protected void calculateNextPosition(){
		if(target != null){
			rotation = calculateAngleToTarget(target);
			nextPosition.add(rotation);
			nextPosition.normalise();
			nextPosition.scale(armReach);
			nextPosition.add(position);
		
			gotPosition = true;
		}
	}
	
	protected void getTarget(Agent target){
		this.target = target;
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
	
}
