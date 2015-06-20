package oose2015.items;


/**
 * @author itai.yavin
 * <p/>
 * Child of Item class.
 * Creates weapon specification for players.
 * Controls how the player attacks
 * <P/>
 */
public class Weapon extends Item{
	public float damage, attackRadius, attackDelay, rotation;
	public boolean  ranged = false,
				    melee = false,
                    magic = false;

	/**
	 * Overloaded constructor for weapon class.
	 * @param level
	 * @param type
	 */
    public Weapon(int level,int type){
        if (level < 0)
            throw new IllegalArgumentException("illegal level: " + level + " is less than 0");

        ranged = false;
        melee = false;
        magic = false;

        this.level 				= level;
        if(type == 0){
            melee = true;
            this.damage			=level*3 + 5;
            
            if(level < 10)
            	attackDelay		= level * 15 + 100;
            else if(level<20)
            	attackDelay		= 250 + level * 10;
            else
                attackDelay     = 350;
            
            if(level < 3)
            	this.attackRadius = level * 10 + 20;
            else
            	this.attackRadius = 60;
        }else if(type == 1){
            ranged = true;
            this.damage			= 8*level+5;
            attackDelay			= 2;
        }else if(type == 2){
            magic = true;
            damage = 0;

        }
    }
    /**
     * Constructor for weapon
     * @param damage - damage of the weapon
     * @param attackRadius - radius of the weapon
     */
    public Weapon(int level, float damage, float attackRadius, float attackDelay,int type) {
        if (level < 0)
            throw new IllegalArgumentException("illegal level: " + level + " is less than 0");
        if (damage < 0)
            throw new IllegalArgumentException("illegal damage: " + damage + " is less than 0");
        if (attackRadius < 0)
            throw new IllegalArgumentException("illegal attackRadius: " + attackRadius + " is less than 0");

        this.level = level;
        this.attackDelay = attackDelay;
        this.attackRadius = attackRadius;
        this.damage = damage;


        ranged = false;
        melee = false;
        magic = false;
        if(type == 0)
            melee = true;
        else if(type == 1)
            ranged = true;
        else if(type == 2)
            magic = true;
    }
}
