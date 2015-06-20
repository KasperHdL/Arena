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

        if(level < 10)
            speedReduction = level * 0.03f;
        else if(level <= 20)
            speedReduction = .30f + (level - 10) * 0.02f;
        else if(level > 20)
            speedReduction = 0.50f;

        if(level < 10)
            damageReduction = level * 0.02f;
        else if(level <= 20)
            damageReduction = .20f + (level - 10) * 0.01f;
        else if(level > 20)
            damageReduction = 0.30f;
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
