package oose2015.entities;

import oose2015.EntityHandler;
import oose2015.VectorUtility;
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

public class Player extends Agent implements KeyListener{

    public int gold;
    public int exp;
    private int lastLevelExp;
    private int nextLevelExp;

    public Weapon weapon;
    public Armor armor;
    
    private float nextAttackTime;
    private boolean attacking;
    public int upKey, leftKey, rightKey, downKey,attackKey,rangedKey;
    public boolean upKeyDown, leftKeyDown, rightKeyDown, downKeyDown, attackKeyDown, rangedKeyDown;

    
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
    public Player(Vector2f position, int upKey, int downKey, int leftKey, int rightKey, int attackKey, int rangedKey){
        System.out.println("Player created");
        EntityHandler.players.add(this);
        
        name = "player";

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
        
        upKeyDown = false;
        downKeyDown = false;
        leftKeyDown = false;
        rightKeyDown = false;
        attackKeyDown = false;
        rangedKeyDown = false;

        weapon = new Weapon(1f, 50f, 300f);

        attacking = false;
        nextAttackTime = 0f;
    }
    
    @Override
    protected void attack(){
        nextAttackTime = World.time + weapon.attackDelay;

    	for(Enemy enemy : EntityHandler.enemies){
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
    	new Projectile(this, weapon.attackRadius);
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

        if(attackKeyDown && nextAttackTime < World.time){
            attacking = true;
            attack();
        }else if(nextAttackTime - weapon.attackDelay/2 < World.time) //mini hack.. should be fixed with animation implementation
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
            graphics.drawOval(-halfRad, -halfRad, halfRad * 2,halfRad * 2);


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
		
		if(downKey == key)
			downKeyDown = true;
		
		if(leftKey == key)
			leftKeyDown = true;
		
		if(rightKey == key)
			rightKeyDown = true;

        if(attackKey == key) {
            attackKeyDown = true;
        }
        
        if(rangedKey == key) {
        	rangedAttack();
        	rangedKeyDown = true;
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
        
        if(rangedKey == key)
        	rangedKeyDown = false;
	}
    
    @Override
    public float getDamage(){return weapon.damage;}
}
