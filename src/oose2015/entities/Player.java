package oose2015.entities;

import oose2015.EntityHandler;
import oose2015.items.Armor;
import oose2015.items.Weapon;
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

    public Weapon weapon;
    public Armor armor;

    public float speedForce;
    public int upKey, leftKey, rightKey, downKey,attackKey;
    public boolean upKeyDown, leftKeyDown, rightKeyDown, downKeyDown, attackKeyDown;
    
    public Vector2f input;


    /**
     * Constructor for player [use Input.KEY_X as key arguments]
     * @param position Position
     * @param upKey up key
     * @param downKey down key
     * @param leftKey left key
     * @param rightKey right key
     * @param attackKey attack key
     */
    public Player(Vector2f position, int upKey, int downKey, int leftKey, int rightKey, int attackKey){
        System.out.println("Player created");

        curHealth = 100;
        maxHealth = curHealth;

        size = new Vector2f(50.0f, 50.0f);

        this.position = position;

        maxVelocity = 2f;

        speedForce = .2f;
        mass = 100f;
        friction = .99f;
        inertia = .60f;

        this.upKey = upKey;
        this.downKey = downKey;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.attackKey = attackKey;

        upKeyDown = false;
        downKeyDown = false;
        leftKeyDown = false;
        rightKeyDown = false;
        attackKeyDown = false;

        weapon = new Weapon(1f, 50f);
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
    		input.y = -1;
    	else if(downKeyDown)
    		input.y = 1;
        else
            input.y = 0;
    	
    	if(leftKeyDown)
    		input.x = -1;
    	else if(rightKeyDown)
    		input.x = 1;
        else
            input.x = 0;

    	input.scale(speedForce/mass);
    	acceleration.add(input);
    	
    	super.move(dt);
    }
    
    @Override
    public void update(int dt){
    	move(dt);
    }

    @Override
    public void render(Graphics graphics){
        graphics.setColor(Color.blue);
        graphics.fillOval(position.x - size.x/2, position.y- size.x/2, size.x, size.y);

        if(attackKeyDown)
            graphics.drawOval(position.x- (weapon.attackRadius + size.x)/2, position.y- (weapon.attackRadius + size.y)/2, weapon.attackRadius + size.x, weapon.attackRadius + size.y);
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

        if(attackKey == key) {
            attack();
            attackKeyDown = true;
        }
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

        if(attackKey == key)
            attackKeyDown = false;
	}
    
    
}
