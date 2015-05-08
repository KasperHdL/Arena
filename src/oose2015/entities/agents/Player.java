package oose2015.entities.agents;

import oose2015.ParticleFactory;
import oose2015.entities.Entity;
import oose2015.entities.drops.Gold;
import oose2015.entities.projectiles.Projectile;
import oose2015.Assets;
import oose2015.EntityHandler;
import oose2015.Settings;
import oose2015.gui.PlayerUI;
import oose2015.utilities.VectorUtility;
import oose2015.World;
import oose2015.items.Armor;
import oose2015.items.Weapon;

import org.newdawn.slick.Color;
import org.newdawn.slick.ControllerListener;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by @Kasper on 26/03/2015
 * <p/>
 * Description:
 * Child class of agent. Creates controllable player object.
 * <p/>
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

	//bow
    boolean startedBowDraw = false;
	float startTime;
	float releaseTime;
	
	public float 	drawTime,
				 	minDrawTime = Settings.MIN_DRAW_SPEED,
				 	maxDrawTime = Settings.MAX_DRAW_SPEED,
				 	maxDrawGraphicSize = Settings.MAX_DRAW_GRAPHIC_SIZE,
				 	drawGraphic = 0,
				 	sweetSpot = Settings.SWEETSPOT,
				 	sweetSpotRange = Settings.SWEETSPOT_RANGE;
    
	//melee
	public float startArc = Settings.PLAYER_ARC_START,
				 endArc = Settings.PLAYER_ARC_END;
	
    //controls
    public int		controllerIndex,
                    attackButton = Settings.ATTACK_BUTTON,
                    rangedButton = Settings.RANGED_BUTTON,
                    leftStickX = Settings.LEFT_STICK_X,
                    leftStickY = Settings.LEFT_STICK_Y,
    				rightStickX = Settings.RIGHT_STICK_X,
    				rightStickY = Settings.RIGHT_STICK_Y;

    //deadzones
    public float 	leftDeadX = Settings.LEFT_DEAD_X,
    				leftDeadY = Settings.LEFT_DEAD_Y,
    				rightDeadX = Settings.RIGHT_DEAD_X,
    				rightDeadY = Settings.RIGHT_DEAD_Y;
    
    //sound variables
    public Sound bowDrawSound;
    public Sound arrowShootSound;
    

    private boolean attackKeyDown = false,
                    rangedKeyDown = false;

	private Input input;
	
    public Color color;

    public PlayerUI playerUI;

    /**
     * Constructor for player [use Input.KEY_X as key arguments]
     * @param position Position
     * @param controllerIndex index for the controller
     * @param input reference to input
     */
    public Player(Vector2f position,Color color, int controllerIndex, Input input){
        name = "Player";

        playerUI = World.playerUIs[World.PLAYERS.size()];
        World.PLAYERS.add(this);

        this.input = input;
        this.color = color;
        input.addControllerListener(this);

        this.position = position;
        size = new Vector2f(50.0f, 50.0f);


        curHealth       =   Settings.PLAYER_HEALTH;
        maxHealth       =   curHealth;

        maxVelocity 	= 	Settings.PLAYER_MAX_VELOCITY;
        speedForce 		= 	Settings.PLAYER_SPEED_FORCE;
        mass 			= 	Settings.PLAYER_MASS;
        
        bowDrawSound 	= Assets.SOUND_BOW_DRAW;
        arrowShootSound = Assets.SOUND_ARROW_SHOOT;
        
        gold 			= 0;
        exp	 			= 0;

        level 			= 1;
        lastLevelExp 	= 0;
        nextLevelExp 	= (level*level)*100;

        this.controllerIndex = controllerIndex;

        weapon 			= new Weapon(1,0);
        armor 			= new Armor(1);

        drawAttack 		= false;
        nextAttackTime 	= 0f;

        playerUI.setPlayer(this,color);
    }
    
    /**
     * Melee attack in given arc.
     */
    @Override
    protected void attack(){
        nextAttackTime = World.TIME + weapon.attackDelay;

        for (int i = World.ENEMIES.size()-1; i >= 0; i--) {
            Enemy enemy = World.ENEMIES.get(i);
    		float dist = VectorUtility.getDistanceToEntity(this, enemy);
    		if(dist < weapon.attackRadius){
    			float enemyAngle = calculateAngleToTarget(enemy);

                float startAngle = startArc + rotation;
                float endAngle = endArc + rotation;

                boolean attacksEnemy;

                if(startAngle > 360 || endAngle < 0){

                    if(endAngle < 0)endAngle += 360;
                    if(startAngle > 360) startAngle -= 360;

                    attacksEnemy = (enemyAngle > endAngle && enemyAngle < 361) || (enemyAngle > -1 && enemyAngle < startAngle);
                }else
                    attacksEnemy = (enemyAngle > endAngle && enemyAngle < startAngle);

                if(attacksEnemy && enemy.takeDamage(this,weapon.damage))
                    addExp(enemy.expDrop);


            }
    	}
    }
    
    /**
     * Calculates speed and damage of ranged attack, and creates new projectile.
     */
    protected void rangedAttack(){
        boolean hitSweetSpot = false;

    	if(drawTime < minDrawTime)
    		return;
    	else if(drawTime > sweetSpot-sweetSpotRange && drawTime < sweetSpot)
    		hitSweetSpot = true;
    	else if(drawTime > maxDrawTime)
    		drawTime = maxDrawTime;
    	float projectileSpeed = Settings.PROJECTILE_SPEED_FORCE *(drawTime/maxDrawTime);
    	float damage = weapon.damage*(drawTime/maxDrawTime);
        if(hitSweetSpot)
            damage *= 2f;
    	
        nextAttackTime = World.TIME + weapon.attackDelay;
        drawGraphic = 0;

        //System.out.println("Time: " + World.TIME + " attackDelay: " + nextAttackTime);
        	new Projectile(this, damage,projectileSpeed);
    }

    /**
     * Adds experience to the player.
     * @param value amount of experience
     */
    public void addExp(int value){
        exp += value;


        if(exp >= nextLevelExp){
            level++;
            playerUI.updateLevel();

            lastLevelExp = nextLevelExp;
            nextLevelExp = (level*level)*100;

            World.camera.shakeScreen(new Vector2f(World.RANDOM.nextFloat()*20,World.RANDOM.nextFloat()*20),1000,3f);
            ParticleFactory.createLevelUpRing(position);

        }else
            World.camera.shakeScreen(new Vector2f(World.RANDOM.nextFloat()*15,World.RANDOM.nextFloat()*15),200,1f);

    }

    /**
     * Moves player object.
     */
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
        axis.scale((armor.getSpeedModifier() * speedForce));

        if(axis.length() > .5f)
            ParticleFactory.createSmokeTrail(position.copy(),new Vector2f(0,size.y/2-20).add(velocity.getTheta()),velocity.copy().scale(-1f));

    	super.move(dt, axis);
    }
    
    /**
     * Updates player state.
     * Checks for button press booleans, and initiates actions if conditions are true. 
     */
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

    /**
     * Renders player graphics.
     */
    @Override
    public void render(Graphics graphics){
        graphics.pushTransform();
        graphics.translate(position.x, position.y);
        graphics.rotate(0, 0, rotation);

        graphics.setColor(Color.red);

        float halfRad = weapon.attackRadius + size.x / 2;
        if(drawAttack){
        	graphics.fillArc(-halfRad, -halfRad, halfRad * 2,halfRad * 2, endArc, startArc);
        }
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
                else if(t > 1) t = 1;
                graphics.drawLine(size.x / 2, 0, (1 - t) * (size.x / 2), 0);
                
                float timeDrawn = World.TIME - startTime;
                if(timeDrawn >= maxDrawTime - maxDrawGraphicSize*100){
                	if(timeDrawn > sweetSpot-sweetSpotRange && timeDrawn < sweetSpot)
                		graphics.setColor(Color.red);
                	else
                		graphics.setColor(Color.white);
                	graphics.drawOval(-(size.x+drawGraphic)/2, -(size.x+drawGraphic)/2, size.x+drawGraphic, size.y+drawGraphic);
                	if(drawGraphic < maxDrawGraphicSize && (timeDrawn) % 100 == 0)
                		drawGraphic++;
                	else if(drawGraphic >= maxDrawGraphicSize && (timeDrawn) % 100 == 0)
                		drawGraphic = 7;
                }
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

    /**
     * Checks for collision.
     * If collision with gold object is true, then adds to gold amount of player.
     */
    @Override
    public void collides(Entity other){
        //if is colliding with gold then collect
        if(other instanceof Gold){
            Gold g = (Gold) other;
            gold += g.value;
            playerUI.updateGold();

            EntityHandler.entities.remove(other);
        }
    }

    /**
     * Controls damage taken by player.
     * Creates screenshake and bloodsplatter particles upon damage taken.
     * Kills player if player health is 0 or below.
     */
    @Override
    public boolean takeDamage(Agent attacker, float damage){
        if(!isAlive) return false;

        curHealth -= (damage * armor.getDamageModifier());
        playerUI.updateHealth();
        if(curHealth <= 0){
            World.camera.shakeScreen(new Vector2f(World.RANDOM.nextFloat()*100,World.RANDOM.nextFloat()*100),200,.5f);
            ParticleFactory.createDeathSplatter(position.copy(), color);
            World.deadPlayers++;
            die();
            return true;
        }else{
            World.camera.shakeScreen(new Vector2f(World.RANDOM.nextFloat()*20,World.RANDOM.nextFloat()*20),100,2f);
            ParticleFactory.createBloodSplatter(position.copy(),new Vector2f(calculateAngleToTarget(attacker) ), color);
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
    
	/**
	 * Returns weapon damage value.
	 */
    @Override
    public float getDamage(){return weapon.damage;}

    /**
     * Checks for button press. Sets button booleans true upon button press.
     * Plays sound on ranged attack.
     */
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

	/**
	 * Checks for button release. Sets button booleans to false upon button release.
	 * Stops ranged sound.
	 */
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
	}

	@Override
	public void controllerDownReleased(int controllerIn) {
	}

	@Override
	public void controllerLeftPressed(int controllerIn) {
	}

	@Override
	public void controllerLeftReleased(int controllerIn) {
	}

	@Override
	public void controllerRightPressed(int controllerIn) {
	}

	@Override
	public void controllerRightReleased(int controllerIn) {
	}

	@Override
	public void controllerUpPressed(int controllerIn) {
	}

	@Override
	public void controllerUpReleased(int controllerIn) {
	}
}
