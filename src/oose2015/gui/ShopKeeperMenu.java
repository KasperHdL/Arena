package oose2015.gui;

import oose2015.Main;
import oose2015.gui.Element;
import oose2015.gui.active.ActiveElement;
import oose2015.gui.active.Button;
import oose2015.gui.active.ItemElement;
import oose2015.states.ShopKeeperState;
import org.newdawn.slick.Color;
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

public class ShopKeeperMenu {

    public boolean isReady = false;

    private Vector2f position;

    public int playerIndex;

    private int active = 0;

    private ActiveElement[] activeElements;
    private Element[] elements;

    public ShopKeeperMenu(Vector2f position,int sizeX){
        this.position = position;
        activeElements = new ActiveElement[9];

        //Create List
        int startY = Main.SCREEN_HEIGHT - (activeElements.length) * 30;
        for (int i = 0; i < activeElements.length-1; i++) {
            ItemElement l = new ItemElement(this,new Vector2f(0,startY + i*29),new Vector2f(sizeX,25));
            l.price = i * 10;
            l.level = i;
            activeElements[i] = l;
        }

        //create Done button
        activeElements[activeElements.length-1] = new Button(this,new Vector2f(0,Main.SCREEN_HEIGHT - 30),new Vector2f(sizeX,25),"Done?");

        moveActive(0);


    }

    public void update(){

    }

    public void render(Graphics graphics){
        graphics.pushTransform();
        graphics.translate(position.x,position.y);
        for (int i = 0; i < activeElements.length; i++) {
            activeElements[i].render(graphics);
        }

        graphics.popTransform();
    }

    public void handleInput(ShopKeeperState.Button btn){
        switch(btn){
            case Up:{
                moveActive(-1);
            }break;
            case Down:{
                moveActive(1);
            }break;
            case Select:{
                selectActive();
            }break;
        }
    }

    public void moveActive(int amount){
        if(active + amount >= 0 && active + amount < activeElements.length){

            activeElements[active].movedAway();
            active += amount;
            activeElements[active].movedOver();
        }else{
            activeElements[active].blink(Color.red);
        }
    }

    public void selectActive(){
        //select item
        activeElements[active].select();
    }

}
