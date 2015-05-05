package oose2015.gui.elements.interactable;

import oose2015.Main;
import oose2015.World;
import oose2015.gui.ShopKeeperMenu;
import oose2015.gui.elements.TextBox;
import oose2015.items.Armor;
import oose2015.items.Item;
import oose2015.items.Weapon;
import oose2015.states.ShopKeeperState;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by @Kasper on 22/04/2015
 * <p/>
 * Description:
 * ---
 * <p/>
 * Usage:
 * ---
 */

public class ItemElement extends InteractableElement {

    public int price;
    public int level;
    public Item item;

    //temp until icons
    private String type;

    private TextBox leftBox;
    private TextBox middleBox;
    private TextBox middleOverlay;
    private TextBox rightBox;

    public Color boughtColor;
    public Color boughtOverColor;

    public boolean isSelected;
    public boolean hasBeenBought;

    public ItemElement(ShopKeeperMenu menu,Vector2f position, Vector2f size,Item item) {
        super(menu,position, size);
        this.item = item;
        level = item.level;
        price = level * 10 + World.RANDOM.nextInt(10) - 5;

        //temp until icon
        if(item instanceof Weapon){
          Weapon w = (Weapon) item;
            if(w.melee)
                type = "sword";
            else if(w.ranged)
                type = "bow";
            else if(w.magic)
                type = "nothing yet..";
        } else if(item instanceof Armor)
            type = "armor";

        leftBox = new TextBox(type,new Vector2f(5,0), TextBox.Align.LEFT_ALIGN);
        middleBox = new TextBox("level " + level, new Vector2f(size.x/2,0), TextBox.Align.CENTER);
        middleOverlay = new TextBox("buy this item?", new Vector2f(size.x/2,0), TextBox.Align.CENTER);
        rightBox = new TextBox(price + " g",new Vector2f(size.x - 5,0), TextBox.Align.RIGHT_ALIGN);

        color = Color.gray;
        overColor = Color.lightGray;
        boughtColor = Color.darkGray;
        boughtOverColor = Color.gray;

    }

    @Override
    public void render(Graphics graphics){
        graphics.pushTransform();
        graphics.translate(position.x, position.y);

        //graphic
        graphics.setColor(Color.white);
        graphics.drawRect(0, 0, size.x, size.y);


        if(stopBlink > Main.TIME)
            graphics.setColor(blinkColor);
        else if(hasBeenBought){
            if(isOver)
                graphics.setColor(boughtOverColor);
            else
                graphics.setColor(boughtColor);
        }else{
            if(isOver)
                graphics.setColor(overColor);
            else
                graphics.setColor(color);
        }

        graphics.fillRect(1, 1, size.x - 1, size.y - 1);

        if(isSelected && !isOver)
            isSelected = false;


        //text
        graphics.translate(0, 3);
        graphics.setColor(Color.white);

        leftBox.render(graphics);
        if(hasBeenBought || isSelected) {
            middleOverlay.render(graphics);
        }else
            middleBox.render(graphics);
        if(!hasBeenBought)
            rightBox.render(graphics);

        graphics.popTransform();
    }

    @Override
    public void select(){
        if(hasBeenBought)return;

        if(isSelected){
            //buy
            hasBeenBought = true;
            middleOverlay.text = "you bought this item!";

        }else{
            isSelected = true;
            middleOverlay.text = "buy this item?";
        }
    }

}
