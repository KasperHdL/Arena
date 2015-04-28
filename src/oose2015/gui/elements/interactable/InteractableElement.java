package oose2015.gui.elements.interactable;

import oose2015.Main;
import oose2015.gui.ShopKeeperMenu;
import oose2015.states.ShopKeeperState;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import oose2015.gui.Element;

/**
 * Created by @Kasper on 26/04/2015
 * <p/>
 * Description:
 * ---
 * <p/>
 * Usage:
 * ---
 */

public class InteractableElement extends Element {
    protected ShopKeeperMenu menu;

    public boolean isOver;
    public Color overColor;

    //blink
    protected int stopBlink = 0;
    protected int blinkLength = 100;
    protected Color blinkColor;



    public InteractableElement(ShopKeeperMenu menu, Vector2f position, Vector2f size) {
        this.menu = menu;
        this.position = position;
        this.size = size;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.pushTransform();
        graphics.translate(position.x, position.y);

        //graphic
        graphics.setColor(Color.white);
        graphics.drawRect(0,0, size.x, size.y);

        if(stopBlink > Main.TIME)
            graphics.setColor(blinkColor);
        else if(isOver)
            graphics.setColor(overColor);
        else
            graphics.setColor(color);

        graphics.fillRect(1, 1, size.x - 1, size.y - 1);
        graphics.popTransform();
    }


    public void blink(Color color){
        stopBlink = Main.TIME + blinkLength;
        blinkColor = color;
    }

    public void select(){}

    public void movedOver(){isOver = true;}
    public void movedAway(){isOver = false;}
}
