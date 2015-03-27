package oose2015.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by @Kasper on 26/03/2015
 * <p/>
 * Description:
 * ---
 * <p/>
 * Usage:
 * ---
 */

public class Player extends Agent implements KeyListener{

    public float speedForce;
    public int upKey, leftKey, rightKey, downKey;
    public boolean upKeyDown, leftKeyDown, rightKeyDown, downKeyDown;
    
    public Vector2f input;

    public Player(Vector2f position, int upKey, int downKey, int leftKey, int rightKey){
        System.out.println("Player created");

        curHealth = 100;
        maxHealth = curHealth;

        size = new Vector2f(5.0f, 5.0f);
        scale = new Vector2f(1f,1f);

        this.position = position;
        velocity = new Vector2f(0,0);
        acceleration = new Vector2f(0,0);

        speedForce = 5;
        mass = 1f;
        friction = 0.9f;

        this.upKey = upKey;
        this.downKey = downKey;
        this.leftKey = leftKey;
        this.rightKey = rightKey;

        upKeyDown = false;
        downKeyDown = false;
        leftKeyDown = false;
        rightKeyDown = false;
    }
    
    @Override
    protected void attack(){
    	for(Enemy enemy : EntityHandler.enemies){
    		Vector2f delta = position.sub(enemy.position);
    		if(delta.length() < weapon.attackRadius){
    			enemy.takeDamage(weapon.damage);
    		}
    	}
    }
    
    @Override
    protected void move(int dt){

    	input = new Vector2f(0,0);
    	
    	if(upKeyDown)
    		input.y = 1;

    	if(downKeyDown)
    		input.y = -1;
    	
    	if(leftKeyDown)
    		input.x = -1;
    	
    	if(rightKeyDown)
    		input.x = 1;

    	input.scale(speedForce/mass * dt);
    	acceleration.add(input);
    	
    	super.move(dt);
    }
    
    @Override
    public void update(int dt){
    	move(dt);
    }

    @Override
    public void render(Graphics graphics){
        graphics.setColor(Color.red);
        graphics.fillOval(position.x, position.y, size.x, size.y);
    }

	@Override
	public void inputEnded() {

	}

	@Override
	public void inputStarted() {
	}

	@Override
	public boolean isAcceptingInput() {
		return true;
	}

	@Override
	public void setInput(Input arg0) {

	}

	@Override
	public void keyPressed(int key, char c) {

		if(upKey == key)
			upKeyDown = true;
		
		if(downKey == key)
			downKeyDown = true;
		
		if(leftKey == key)
			leftKeyDown = true;
		
		if(rightKey == key)
			rightKeyDown = true;
	}

	@Override
	public void keyReleased(int key, char c) {
        if(upKey == key)
            upKeyDown = false;

        if(downKey == key)
            downKeyDown = false;

        if(leftKey == key)
            leftKeyDown = false;

        if(rightKey == key)
            rightKeyDown = false;
	}
    
    
}
