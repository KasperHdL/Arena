package oose2015.entities;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import oose2015.CollisionUtility;
import oose2015.EntityHandler;
import oose2015.VectorUtility;
import oose2015.World;
import oose2015.items.Armor;
import oose2015.items.Weapon;

import org.newdawn.slick.Color;
import org.newdawn.slick.ControllerListener;
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

public class Player extends Agent implements ControllerListener{

    public int gold;
    public int exp;
    private int lastLevelExp;
    private int nextLevelExp;

    public Weapon weapon;
    public Armor armor;
    
    private float nextAttackTime;
    private boolean attacking;

    //controls
    public int		controllerIndex,
    				upButton,
                    leftButton,
                    rightButton,
                    downButton,
                    attackButton = 1,
                    rangedButton = 2,
                    enterButton = 6;
    /*
    L1 = 5
    R1 = 6
    Triangle = 4
    Cross = 1
    Circle = 2
    Square = 3
    Left stick = 9
    Right stick = 10
    Select = 7
    Start = 8
    */
    

    private boolean  upKeyDown = false,
                    leftKeyDown = false,
                    rightKeyDown= false,
                    downKeyDown = false,
                    attackKeyDown = false,
                    rangedKeyDown = false,
                    enterKeyDown = false;

	Controller[] ca = ControllerEnvironment.getDefaultEnvironment().getControllers();
	Component[] components = ca[controllerIndex].getComponents();
    
    private Vector2f input;


    /**
     * Constructor for player [use Input.KEY_X as key arguments]
     * @param position Position
     * @param upKey up key
     * @param downKey down key
     * @param leftKey left key
     * @param rightKey right key
     * @param attackKey attack key
     */
    public Player(Vector2f position, int controllerIndex){
    	System.out.println("Player created");
        World.PLAYERS.add(this);
        
        name = "Player";

        curHealth = 100;
        maxHealth = curHealth;

        size = new Vector2f(50.0f, 50.0f);

        this.position = position;

        maxVelocity = 15f;

        speedForce = 6f;
        mass = 1f;

        gold = 0;
        exp = 0;

        level = 1;
        lastLevelExp = 0;
        nextLevelExp = (level*level)*100;

        this.controllerIndex = controllerIndex;

        weapon = new Weapon(1f, 50f, 300f);

        attacking = false;
        nextAttackTime = 0f;
    }
    
    @Override
    protected void attack(){
        nextAttackTime = World.TIME + weapon.attackDelay;

    	for(Enemy enemy : World.ENEMIES){
    		float dist = VectorUtility.getDistanceToEntity(this, enemy);
    		if(dist < weapon.attackRadius){
    			if(enemy.takeDamage(weapon.damage)){
                    //enemy killed
                    addExp(enemy.expDrop);
                }
    		}
    	}
    }
    
    protected void rangedAttack(){
        nextAttackTime = World.TIME + weapon.attackDelay;
    	new Projectile(this, weapon.attackRadius, weapon.damage);
    }

    private void checkExits(){
        for (int i = 0; i < World.EXITS.size(); i++) {
            if(CollisionUtility.checkCollision(this,World.EXITS.get(i))){
                World.enteredExit(this);
            }
        }
    }

    public void addExp(int value){
        exp += value;

        if(exp >= nextLevelExp){
            level++;

            lastLevelExp = nextLevelExp;
            nextLevelExp = (level*level)*100;
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

        if(attackKeyDown && nextAttackTime < World.TIME){
            attacking = true;
            attack();
        }else if(nextAttackTime - weapon.attackDelay/2 < World.TIME) //mini hack.. should be fixed with animation implementation
            attacking = false;
    }

    @Override
    public void render(Graphics graphics){
        graphics.pushTransform();
        graphics.translate(position.x, position.y);
        graphics.rotate(0, 0, rotation);


        graphics.setColor(Color.red);

        float halfRad = weapon.attackRadius + size.x / 2;
        if(attacking)
            graphics.fillOval(-halfRad, -halfRad, halfRad * 2,halfRad * 2);
        else if(World.DEBUG_MODE)
            graphics.drawOval(-halfRad, -halfRad, halfRad * 2, halfRad * 2);


        if(isAlive)
            graphics.setColor(Color.blue);
        else
            graphics.setColor(Color.red);

        graphics.fillOval(-size.x / 2, -size.x / 2, size.x, size.y);


        graphics.popTransform();


        if(World.DEBUG_MODE && isAlive) {
            graphics.setColor(Color.white);
            graphics.drawString(curHealth + " / " + maxHealth, position.x + 10, position.y + 10);
            graphics.drawString("gold: " + gold, position.x + 10, position.y - 10);
            graphics.drawString("exp: " + exp + " / " + nextLevelExp, position.x + 10, position.y - 30);
            graphics.drawString("level " + level, position.x + 10, position.y - 50);
        }
    }

    @Override
    public void collides(Entity other){
        //if is colliding with gold then collect
        if(other instanceof Gold){
            Gold g = (Gold) other;
            gold += g.value;

            EntityHandler.entities.remove(other);
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
    public float getDamage(){return weapon.damage;}

	@Override
	public void controllerButtonPressed(int controllerIn, int button) {
		// TODO Auto-generated method stub
		System.out.println(components[2].getPollData());

		if(controllerIn != controllerIndex)
			return;
		
		if(attackButton == button)
            attackKeyDown = true;
        
        else if(rangedButton == button) {
        	rangedAttack();
        	rangedKeyDown = true;
            if(nextAttackTime < World.TIME){
                attacking = true;
                rangedAttack();
            } else {
            	attacking = false;
            }
        }

        else if(enterButton == button){
            checkExits();
            enterKeyDown = true;
        }
	}

	@Override
	public void controllerButtonReleased(int controllerIn, int button) {
		// TODO Auto-generated method stub
		if(controllerIn != controllerIndex)
			return;

        if(attackButton == button)
            attackKeyDown = false;

        else if(rangedButton == button)
        	rangedKeyDown = false;

        else if(enterButton == button)
            enterKeyDown = false;
	}

	@Override
	public void controllerDownPressed(int controllerIn) {
		// TODO Auto-generated method stub
		if(controllerIn != controllerIndex)
			return;
            downKeyDown = true;
	}

	@Override
	public void controllerDownReleased(int controllerIn) {
		// TODO Auto-generated method stub
		if(controllerIn != controllerIndex)
			return;
            downKeyDown = false;
	}

	@Override
	public void controllerLeftPressed(int controllerIn) {
		// TODO Auto-generated method stub
		if(controllerIn != controllerIndex)
			return;
            leftKeyDown = true;
	}

	@Override
	public void controllerLeftReleased(int controllerIn) {
		// TODO Auto-generated method stub
		if(controllerIn != controllerIndex)
			return;
            leftKeyDown = false;
	}

	@Override
	public void controllerRightPressed(int controllerIn) {
		// TODO Auto-generated method stub
		if(controllerIn != controllerIndex)
			return;
            rightKeyDown = true;
	}

	@Override
	public void controllerRightReleased(int controllerIn) {
		// TODO Auto-generated method stub
		if(controllerIn != controllerIndex)
			return;
            rightKeyDown = false;
	}

	@Override
	public void controllerUpPressed(int controllerIn) {
		// TODO Auto-generated method stub
		if(controllerIn != controllerIndex)
			return;
            upKeyDown = true;
	}

	@Override
	public void controllerUpReleased(int controllerIn) {
		// TODO Auto-generated method stub
		if(controllerIn != controllerIndex)
			return;
            upKeyDown = false;
	}
}
