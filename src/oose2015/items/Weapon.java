package oose2015.items;

public class Weapon extends Item{
	public float damage, attackRadius;

    /**
     * Constructor for weapon
     * @param damage damage of the weapon
     * @param attackRadius radius of the weapon
     */
	public Weapon(float damage, float attackRadius){
        if(damage < 0)
            throw new IllegalArgumentException("illegal damage: " + damage + " is less than 0");
        if(attackRadius < 0)
            throw new IllegalArgumentException("illegal attackRadius: " + attackRadius + " is less than 0");

		this.attackRadius = attackRadius;
		this.damage = damage;
	}
}
