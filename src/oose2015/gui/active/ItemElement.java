package oose2015.gui.active;

import oose2015.gui.ShopKeeperMenu;
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

public class ItemElement extends ActiveElement {

    public int price;
    public int level;

    public boolean isSelected;
    public boolean hasBeenBought;

    public ItemElement(ShopKeeperMenu menu,Vector2f position, Vector2f size) {
        super(menu,position, size);

        color = Color.gray;
        overColor = Color.lightGray;

    }

    @Override
    public void render(Graphics graphics){
        graphics.pushTransform();
        graphics.translate(position.x, position.y);

        //graphic
        graphics.setColor(Color.white);
        graphics.drawRect(0, 0, size.x, size.y);


        if(stopBlink > ShopKeeperState.TIME)
            graphics.setColor(blinkColor);
        else if(hasBeenBought && isOver)
            graphics.setColor(Color.gray);
        else if(hasBeenBought)
            graphics.setColor(Color.darkGray);
        else if(isOver)
            graphics.setColor(overColor);
        else
            graphics.setColor(color);

        graphics.fillRect(1, 1, size.x - 1, size.y - 1);

        if(isSelected && !isOver)
            isSelected = false;


        //text
        graphics.translate(0,3);
        graphics.setColor(Color.white);
        Font f = graphics.getFont();

        if(hasBeenBought) {

            int iconWidth = f.getWidth("ICON");
            graphics.drawString("ICON", 5, 0);

            graphics.drawString("you bought this!", iconWidth + 15, 0);
        }else if(isSelected){

            int iconWidth = f.getWidth("ICON");
            graphics.drawString("ICON", 5, 0);

            int levelWidth = f.getWidth("buy this item?");
            graphics.drawString("buy this item?", iconWidth + 10, 0);

            graphics.drawString(price + " g", levelWidth + iconWidth + 25, 0);
        }else {

            int iconWidth = f.getWidth("ICON");
            graphics.drawString("ICON", 5, 0);

            int levelWidth = f.getWidth("lvl " + level);
            graphics.drawString("lvl " + level, iconWidth + 10, 0);

            graphics.drawString(price + " g", levelWidth + iconWidth + 25, 0);


        }
        graphics.popTransform();
    }

    @Override
    public void select(){
        if(isSelected){
            //buy
            hasBeenBought = true;
        }else{
            isSelected = true;
        }
    }

    @Override
    public void movedOver(){
        super.movedOver();
    }

    @Override
    public void movedAway(){
        super.movedAway();
    }


}
