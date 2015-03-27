package oose2015.entities;

import items.Weapon;
import oose2015.EntityHandler;

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

	public Weapon weapon;
    public float health, speedForce;
    public int upKey, leftKey, rightKey, downKey;
    public boolean upKeyDown, leftKeyDown, rightKeyDown, downKeyDown;
    
    public Vector2f input;

    public Player(Vector2f position, int upKey, int downKey, int leftKey, int rightKey){
        this.position = position;
        System.out.println("Player created");
        health = 100;
        speedForce = 5;
        size = new Vector2f(5.0f, 5.0f);
    }
    
    @Override
    protected void move(int dt){

    	input.x = 0;
    	input.y = 0;
    	
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
    protected void attack(){
    	for(int i = 0; i < EntityHandler.enemies.size(); i++){
    		if(this.position - EntityHandler.enemies[i] > weapon.attackRange){
    			EntityHandler.enemies[i].takeDamage(weapon.damage);
    		}
    	}
    }
    
    @Override
    public void update(int dt){
    	move(dt);
    }

    @Override
    public void render(Graphics graphics){
        graphics.fillOval(position.x, position.y, size.x, size.y);
    }

	@Override
	public void inputEnded() {

	}

	@Override
	public void inputStarted() {
		// TODO Auto-generated method stub
		
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
