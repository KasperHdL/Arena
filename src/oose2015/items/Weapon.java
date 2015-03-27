package oose2015.items;

public class Weapon extends Item{
	public float damage, attackRadius;
	
	public Weapon(float damage, float attackRadius){
		this.attackRadius = attackRadius;
		this.damage = damage;
	}
}
