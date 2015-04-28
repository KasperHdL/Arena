package oose2015.gui;

import oose2015.Main;
import oose2015.World;
import oose2015.entities.Player;
import oose2015.gui.elements.TextBox;
import oose2015.gui.elements.interactable.InteractableElement;
import oose2015.gui.elements.interactable.Button;
import oose2015.gui.elements.interactable.ItemElement;
import oose2015.items.Armor;
import oose2015.items.Weapon;
import oose2015.states.ShopKeeperState;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import javax.xml.soap.Text;

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
    public Player player;

    private int active = 0;

    private TextBox goldBox;

    private InteractableElement[] interactables;
    private Element[] elements;

    public ShopKeeperMenu(Vector2f position,int sizeX){
        this.position = position;
        interactables = new InteractableElement[9];

        player = World.PLAYERS.get(playerIndex);
        //Create List
        int startY = Main.SCREEN_HEIGHT - (interactables.length) * 30;
        for (int i = 0; i < interactables.length-1; i++) {
            ItemElement l;
            if(i< interactables.length-3)
                l = new ItemElement(this,new Vector2f(0,startY + i*29),new Vector2f(sizeX,25),new Weapon(player.weapon.level+1));
            else
                l = new ItemElement(this,new Vector2f(0,startY + i*29),new Vector2f(sizeX,25),new Armor(player.armor.level+1));

            interactables[i] = l;
        }

        //create Done button
        interactables[interactables.length-1] = new Button(this,new Vector2f(0,Main.SCREEN_HEIGHT - 30),new Vector2f(sizeX,25),"Done?");

        elements = new Element[1];
        goldBox = new TextBox("gold: " + player.gold,new Vector2f(5,startY - 20), TextBox.Align.LEFT_ALIGN);
        goldBox.color = Color.white;

        elements[0] = goldBox;

        moveActive(0);


    }

    public void update(){

    }

    public void render(Graphics graphics){
        graphics.pushTransform();
        graphics.translate(position.x,position.y);
        for (int i = 0; i < interactables.length; i++) {
            interactables[i].render(graphics);
        }
        for (int i = 0; i < elements.length; i++) {
            elements[i].render(graphics);
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
                goldBox.text = "gold: " + player.gold;
            }break;
        }
    }

    public void moveActive(int amount){
        if(active + amount >= 0 && active + amount < interactables.length){

            interactables[active].movedAway();
            active += amount;
            interactables[active].movedOver();
        }else{
            interactables[active].blink(Color.red);
        }
    }

    public void selectActive(){
        //select item
        if(interactables[active] instanceof ItemElement){
            ItemElement ie = (ItemElement) interactables[active];
            if(ie.hasBeenBought)return;
            if(player.gold < ie.price){
                goldBox.blinkText(Color.red);
                return;
            }
            ie.select();
            if(ie.hasBeenBought){
                if(ie.item instanceof Weapon){
                    Weapon w = (Weapon) ie.item;
                    player.weapon = w;
                }else{
                    Armor a = (Armor) ie.item;
                    player.armor = a;

                }
                player.gold -= ie.price;
            }
        }else
            interactables[active].select();
    }

}
