package oose2015.items;

public class Weapon extends Item{
	public float damage, attackRadius, attackDelay;
	public boolean ranged = true,
				   melee = true;

    public Weapon(int level){
        if (level < 0)
            throw new IllegalArgumentException("illegal level: " + level + " is less than 0");

        this.level = level;
        this.damage = level;
        this.attackRadius = level * 20;
        this.attackDelay = level * 2000;
    }
    /**
     * Constructor for weapon
     * @param damage damage of the weapon
     * @param attackRadius radius of the weapon
     */
    public Weapon(int level, float damage, float attackRadius, float attackDelay) {
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
    }
}
