package oose2015.items;

/**
 * @author itai.yavin
 * <p/>
 * Description:
 * Child of item class.
 * Armor class item.
 * <p/>
 */
public class Armor extends Item{

    public float speedReduction, damageReduction;

    /**
     * Armor constructor
     * Sets armor variables
     * @param level - level of armor
     */
    public Armor(int level){
        this.level = level;
        speedReduction = level * 0.04f;
        damageReduction = level * 0.02f;
    }

    /**
     * Modifies speedReduction variable
     * @return
     */
    public float getSpeedModifier(){
        return 1f - speedReduction;
    }
    
    /**
     * Modifies damageReduction variable
     * @return
     */
    public float getDamageModifier(){
        return 1f - damageReduction;
    }

}
