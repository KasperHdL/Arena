package oose2015.entities;

import oose2015.EntityHandler;
import oose2015.VectorUtil;
import oose2015.items.Armor;
import oose2015.items.Weapon;
import oose2015.states.GamePlayState;
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

    public static boolean debug = true;

    public Weapon weapon;
    public Armor armor;

    private float nextAttackTime;
    private boolean attacking;

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
        EntityHandler.players.add(this);

        curHealth = 100;
        maxHealth = curHealth;

        size = new Vector2f(50.0f, 50.0f);

        this.position = position;

        maxVelocity = 15f;

        speedForce = 6f;
        mass = 1f;

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

        weapon = new Weapon(1f, 50f, 300f);

        attacking = false;
        nextAttackTime = 0f;
    }
    
    @Override
    protected void attack(){
        nextAttackTime = GamePlayState.time + weapon.attackDelay;

    	for(Enemy enemy : EntityHandler.enemies){
    		float dist = VectorUtil.getDistanceToEntity(this, enemy);
    		if(dist < weapon.attackRadius){
    			enemy.takeDamage(weapon.damage);
    		}
    	}
    }
    
    @Override
    protected void move(float dt){

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

        input.normalise();

    	input.scale(speedForce / mass);

    	super.move(dt,input);
    }
    
    @Override
    public void update(float dt){
        if(isAlive)
    	    move(dt);

        if(attackKeyDown && nextAttackTime < GamePlayState.time){
            attacking = true;
            attack();
        }else if(nextAttackTime - weapon.attackDelay/2 < GamePlayState.time) //mini hack.. should be fixed with animation implementation
            attacking = false;
    }

    @Override
    public void render(Graphics graphics){
        graphics.pushTransform();
        graphics.translate(position.x, position.y);
        graphics.rotate(0, 0, rotation);


        graphics.setColor(Color.red);

        if(attacking)
            graphics.fillOval(-(weapon.attackRadius + size.x) / 2, -(weapon.attackRadius + size.y) / 2, weapon.attackRadius + size.x, weapon.attackRadius + size.y);
        else if(debug)
            graphics.drawOval(-(weapon.attackRadius + size.x) / 2, -(weapon.attackRadius + size.y) / 2, weapon.attackRadius + size.x, weapon.attackRadius + size.y);


        if(isAlive)
            graphics.setColor(Color.blue);
        else
            graphics.setColor(Color.red);

        graphics.fillOval(-size.x / 2, -size.x / 2, size.x, size.y);


        graphics.popTransform();


        if(debug && isAlive) {
            graphics.setColor(Color.white);
            graphics.drawString(curHealth + " / " + maxHealth, position.x + 10, position.y + 10);
        }
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
    
    @Override
    public float getDamage(){return weapon.damage;}
}
