package oose2015.entities.agents;

import oose2015.entities.Entity;
import oose2015.entities.drops.Gold;
import oose2015.entities.projectiles.Projectile;
import oose2015.utilities.CollisionUtility;
import oose2015.EntityHandler;
import oose2015.utilities.VectorUtility;
import oose2015.World;
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

public class KeyboardPlayer extends Agent implements KeyListener{

    public int gold;
    public int exp;
    private int lastLevelExp;
    private int nextLevelExp;

    public Weapon weapon;
    public Armor armor;
    
    private float nextAttackTime;
    private boolean attacking;

    //controls
    public int      upKey,
                    leftKey,
                    rightKey,
                    downKey,
                    attackKey,
                    rangedKey,
                    enterKey;

    private boolean  upKeyDown = false,
                    leftKeyDown = false,
                    rightKeyDown= false,
                    downKeyDown = false,
                    attackKeyDown = false,
                    rangedKeyDown = false,
                    enterKeyDown = false;

    
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
    public KeyboardPlayer(Vector2f position, int upKey, int downKey, int leftKey, int rightKey, int attackKey, int rangedKey,int enterKey){
        World.KEYBOARDPLAYERS.add(this);
        
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

        this.upKey = upKey;
        this.downKey = downKey;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.attackKey = attackKey;
        this.rangedKey = rangedKey;
        this.enterKey = enterKey;

        weapon = new Weapon(1);

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
	public void keyPressed(int key, char c) {

		if(upKey == key)
            upKeyDown = true;
		
		else if(downKey == key)
			downKeyDown = true;

        else if(leftKey == key)
			leftKeyDown = true;

        else if(rightKey == key)
			rightKeyDown = true;

        else if(attackKey == key)
            attackKeyDown = true;
        
        else if(rangedKey == key) {
        	rangedAttack();
        	rangedKeyDown = true;
            if(nextAttackTime < World.TIME){
                attacking = true;
                rangedAttack();
            } else {
            	attacking = false;
            }
        }

        else if(enterKey == key){
            checkExits();
            enterKeyDown = true;
        }

	}

	@Override
	public void keyReleased(int key, char c) {
        if(upKey == key)
            upKeyDown = false;

        else if(downKey == key)
            downKeyDown = false;

        else if(leftKey == key)
            leftKeyDown = false;

        else if(rightKey == key)
            rightKeyDown = false;

        else if(attackKey == key)
            attackKeyDown = false;

        else if(rangedKey == key)
        	rangedKeyDown = false;

        else if(enterKey == key)
            enterKeyDown = false;
	}
    
    @Override
    public float getDamage(){return weapon.damage;}
}
