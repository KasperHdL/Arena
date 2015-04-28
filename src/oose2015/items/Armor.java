package oose2015.items;

public class Armor extends Item{

    public float speedReduction, damageReduction;

    public Armor(int level){
        this.level = level;
        speedReduction = level * 0.04f;
        damageReduction = level * 0.02f;
    }

    public float getSpeedModifier(){
        return 1f - speedReduction;
    }
    public float getDamageModifier(){
        return 1f - damageReduction;
    }

}
