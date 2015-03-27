package oose2015.entities;

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

    public float health, speedForce;
    public int upKey, leftKey, rightKey, downKey;
    public boolean upKeyDown, leftKeyDown, rightKeyDown, downKeyDown;
    
    public Vector2f input;

    public Player(Vector2f position){
        this.position = position;
        System.out.println("Player created");
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
	public void inputEnded() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputStarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAcceptingInput() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setInput(Input arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(int key, char arg1) {
		// TODO Auto-generated method stub
		
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
	public void keyReleased(int key, char arg1) {
		// TODO Auto-generated method stub
		if(leftKey == key)
			leftKeyDown = false;
	}
    
    
}
