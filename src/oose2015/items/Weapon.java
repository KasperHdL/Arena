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

        this.level 				= level;
        if(type == 0){
            melee = true;
            this.damage			= level * 3;
            
            if(level < 4)
            	attackDelay		= level * 200;
            else
            	attackDelay		= 4 * 200;
            
            if(level < 3)
            	this.attackRadius = level * 20;
            else
            	this.attackRadius = 5 * 30;
        }else if(type == 1){
            ranged = true;
            this.damage			= 2*level;
            attackDelay			= 2;
            this.attackRadius 	= level * 20;
        }else if(type == 2){
            magic = true;
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


        if(type == 0)
            melee = true;
        else if(type == 1)
            ranged = true;
        else if(type == 2)
            magic = true;
    }
}
