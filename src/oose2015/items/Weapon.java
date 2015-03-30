package oose2015.items;

public class Weapon extends Item{
	public float damage, attackRadius;

    /**
     * Constructor for weapon
     * @param damage damage of the weapon
     * @param attackRadius radius of the weapon
     */
	public Weapon(float damage, float attackRadius){
		this.attackRadius = attackRadius;
		this.damage = damage;
	}
}
