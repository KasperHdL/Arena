package oose2015.items;


/**
 * @author itai.yavin
 * <p/>
 * Child of Item class.
 * Creates weapon specification for players.
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

        this.level = level;
        this.damage = level;
        this.attackRadius = level * 20;
        this.attackDelay = level * 2;
        if(type == 0)
            melee = true;
        else if(type == 1)
            ranged = true;
        else if(type == 2)
            magic = true;
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
