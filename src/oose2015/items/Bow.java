package oose2015.items;

/**
 * @author itai.yavin
 * <p/>
 * Description:
 * Child class of weapon.
 * Creates bow item for player.
 * <p/>
 */
//TODO: WORK I PROGRESS
public class Bow extends Weapon{

	public Bow(int level) {
		super(level,1);
		if (level < 0)
            throw new IllegalArgumentException("illegal level: " + level + " is less than 0");
		// TODO Auto-generated constructor stub
		ranged = true;
		melee = false;
	}
}
