package oose2015.entities.agents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import oose2015.entities.Entity;
import oose2015.entities.drops.Gold;
import oose2015.entities.projectiles.Projectile;
import oose2015.EntityHandler;
import oose2015.utilities.VectorUtility;
import oose2015.World;
import oose2015.items.Armor;
import oose2015.items.Weapon;

import org.newdawn.slick.Color;
import org.newdawn.slick.ControllerListener;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
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
    private boolean drawAttack;

	//bow variables
    boolean startedBowDraw = false;
	float startTime;
	float releaseTime;
	public float drawTime;
    
    //controls
    public int		controllerIndex,
                    attackButton = 5,
                    rangedButton = 6,
                    leftStickX = 1,
                    leftStickY = 0,
    				rightStickX = 3,
    				rightStickY = 2;

    //deadzones
    public float 	leftDeadX = 0.1f,
    				leftDeadY = 0.1f,
    				rightDeadX = 0.6f,
    				rightDeadY = 0.6f;
    
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
    
    //sound variables
    public File file;
    public Sound bowDrawSound;
    public Sound arrowShootSound;
    

    private boolean upKeyDown = false,
                    leftKeyDown = false,
                    rightKeyDown= false,
                    downKeyDown = false,
                    attackKeyDown = false,
                    rangedKeyDown = false;

	private Input input;

    public Color color;


    /**
     * Constructor for player [use Input.KEY_X as key arguments]
     * @param position Position
     * @param controllerIndex index for the controller
     * @param input reference to input
     */
    public Player(Vector2f position,Color color, int controllerIndex, Input input){
        World.PLAYERS.add(this);
        this.input = input;
        this.color = color;
        input.addControllerListener(this);
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

        weapon = new Weapon(1);
        armor = new Armor(1);

        drawAttack = false;
        nextAttackTime = 0f;
        
        //load sound clips
        String s = System.getProperty("user.dir");
        String bowDrawPath = s+"\\Assets\\Sounds\\BowDrawCreak.wav";
        String arrowShootPath = s+"\\Assets\\Sounds\\ArrowShootSound.wav";
	    try {
			bowDrawSound = new Sound(bowDrawPath);
			arrowShootSound = new Sound(arrowShootPath);
		} catch (SlickException e) {
			System.out.println("Error with file path");
		}
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
    	if(drawTime < 700)
    		drawTime = 700;
    	else if(drawTime > 1500)
    		drawTime = 1500;
    	float projectileSpeed = 10*(drawTime/1000);
    	float damage = weapon.damage*(drawTime/1000);
    	
        nextAttackTime = World.TIME + weapon.attackDelay;

        //System.out.println("Time: " + World.TIME + " attackDelay: " + nextAttackTime);
        new Projectile(this, weapon.attackRadius, damage, projectileSpeed);
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
    	float x = input.getAxisValue(controllerIndex, rightStickX), 
    		  y = input.getAxisValue(controllerIndex, rightStickY);
    
    	if(Math.abs(x) < rightDeadX && Math.abs(y) < rightDeadY){
    		x = 0;
    		y = 0;
    	}
    	
    	Vector2f axis = new Vector2f(x,y);
    	
    	if(axis.length() != 0)
    		rotation = (float)axis.getTheta();
    	
    	x = input.getAxisValue(controllerIndex, leftStickX);
    	y = input.getAxisValue(controllerIndex, leftStickY);

    	if(Math.abs(x) < leftDeadX)
    		x = 0;
    	if(Math.abs(y) < leftDeadY)
    		y = 0;	
    	
    	axis = new Vector2f(x,y);
    	axis.scale((armor.getSpeedModifier() * speedForce) / mass);

    	super.move(dt, axis);
    }
    
    @Override
    public void update(float dt){
        if(isAlive) {
            move(dt);

            if(rangedKeyDown && nextAttackTime < World.TIME){
                rangedAttack();
                rangedKeyDown = false;
                drawTime = 0;
                startedBowDraw = false;
            	arrowShootSound.play();
            } else if(rangedKeyDown && nextAttackTime > World.TIME)
            	rangedKeyDown = false;
            	
            if (attackKeyDown && nextAttackTime < World.TIME) {
                drawAttack = true;
                attack();
            } else if (nextAttackTime - weapon.attackDelay / 2 < World.TIME) //mini hack.. should be fixed with animation implementation
                drawAttack = false;
        }
    }

    @Override
    public void render(Graphics graphics){
        graphics.pushTransform();
        graphics.translate(position.x, position.y);
        graphics.rotate(0, 0, rotation);


        graphics.setColor(Color.red);

        float halfRad = weapon.attackRadius + size.x / 2;
        if(drawAttack)
            graphics.fillOval(-halfRad, -halfRad, halfRad * 2,halfRad * 2);
        else if(World.DEBUG_MODE)
            graphics.drawOval(-halfRad, -halfRad, halfRad * 2, halfRad * 2);


        if(isAlive)
            graphics.setColor(color);
        else
            graphics.setColor(new Color(color.r,color.g,color.b,.15f));

        graphics.fillOval(-size.x / 2, -size.x / 2, size.x, size.y);

        graphics.setColor(Color.white);
        if(isAlive) {
            graphics.drawLine(0, 0, size.x / 2, 0);
            if (startedBowDraw) {
                graphics.setColor(Color.red);
                float t = (World.TIME - startTime)/1500;
                if(t < 0) t = 0;
                else if( t> 1) t = 1;
                graphics.drawLine(size.x / 2, 0, (1 - t) * (size.x / 2), 0);
            }
        }
        
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
    public boolean takeDamage(float damage){
        if(!isAlive) return false;

        curHealth -= (damage * armor.getDamageModifier());
        if(curHealth <= 0){
            die();
            return true;
        }
        return false;
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
		if(controllerIn != controllerIndex)
			return;
		
		if(attackButton == button && weapon.melee)
            attackKeyDown = true;
        
        else if(rangedButton == button && weapon.ranged && nextAttackTime < World.TIME){
        	startTime = World.TIME;
        	bowDrawSound.play();
            startedBowDraw = true;
        	//rangedKeyDown = true;
        }

	}

	@Override
	public void controllerButtonReleased(int controllerIn, int button) {
		if(controllerIn != controllerIndex)
			return;

        if(attackButton == button && weapon.melee)
            attackKeyDown = false;

        else if(rangedButton == button && weapon.ranged && startedBowDraw){
        	releaseTime = World.TIME;
        	drawTime = releaseTime - startTime;
            startedBowDraw = false;
        	rangedKeyDown = true;
        	bowDrawSound.stop();
        }

	}

	@Override
	public void controllerDownPressed(int controllerIn) {
		if(controllerIn != controllerIndex)
			return;
        downKeyDown = true;
	}

	@Override
	public void controllerDownReleased(int controllerIn) {
		if(controllerIn != controllerIndex)
			return;
        downKeyDown = false;
	}

	@Override
	public void controllerLeftPressed(int controllerIn) {
		if(controllerIn != controllerIndex)
			return;
        leftKeyDown = true;
	}

	@Override
	public void controllerLeftReleased(int controllerIn) {
		if(controllerIn != controllerIndex)
			return;
        leftKeyDown = false;
	}

	@Override
	public void controllerRightPressed(int controllerIn) {
		if(controllerIn != controllerIndex)
			return;
        rightKeyDown = true;
	}

	@Override
	public void controllerRightReleased(int controllerIn) {
		if(controllerIn != controllerIndex)
			return;
        rightKeyDown = false;
	}

	@Override
	public void controllerUpPressed(int controllerIn) {
		if(controllerIn != controllerIndex)
			return;
        upKeyDown = true;
	}

	@Override
	public void controllerUpReleased(int controllerIn) {
		if(controllerIn != controllerIndex)
			return;
        upKeyDown = false;
	}
}
