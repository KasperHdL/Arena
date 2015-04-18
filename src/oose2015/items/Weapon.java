package oose2015.items;

public class Weapon extends Item{
	public float damage, attackRadius, attackDelay = 200f;


    /**
     * Constructor for weapon
     * @param damage damage of the weapon
     * @param attackRadius radius of the weapon
     */
	public Weapon(float damage, float attackRadius, float attackDelay){
        if(damage < 0)
            throw new IllegalArgumentException("illegal damage: " + damage + " is less than 0");
        if(attackRadius < 0)
            throw new IllegalArgumentException("illegal attackRadius: " + attackRadius + " is less than 0");

        this.attackDelay = attackDelay;
		this.attackRadius = attackRadius;
		this.damage = damage;
	}
}
