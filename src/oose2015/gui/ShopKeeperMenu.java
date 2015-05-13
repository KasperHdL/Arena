package oose2015.gui;

import oose2015.Main;
import oose2015.World;
import oose2015.entities.agents.Player;
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

/**
 * Created by @Kasper on 22/04/2015
 * <p/>
 * Description:
 * Creates interactable player shop for upgrading of weapons and armor.
 * <p/>
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


    Color color ;
    Color overColor;
    Color boughtColor;
    Color boughtOverColor ;

    /**
     * ShopKeeperMenu Constructor.
     * Creates new shop box-menu for individual player.
     * @param position - position on screen
     * @param sizeX - size of box
     */
    public ShopKeeperMenu(Vector2f position,int sizeX){
        this.position = position;
        interactables = new InteractableElement[9];
        player = World.PLAYER;

         color = player.color;
         overColor = color.brighter(.2f);
         boughtColor = color.darker(.4f);
         boughtOverColor = boughtColor.brighter(.2f);

        //Create List
        int startY = Main.SCREEN_HEIGHT - (interactables.length) * 30;
        for (int i = 0; i < interactables.length-1; i++) {
            ItemElement l;



            if(i < 2)
                l = new ItemElement(this,new Vector2f(0,startY + i*29),new Vector2f(sizeX,25),new Weapon(player.weapon.level+1,0));
            else if(i < 4)
                l = new ItemElement(this,new Vector2f(0,startY + i*29),new Vector2f(sizeX,25),new Weapon(player.weapon.level+1,1));
            else if(i < 6)
                l = new ItemElement(this,new Vector2f(0,startY + i*29),new Vector2f(sizeX,25),new Weapon(player.weapon.level+1,2));
            else
                l = new ItemElement(this,new Vector2f(0,startY + i*29),new Vector2f(sizeX,25),new Armor(player.armor.level+1));

            l.color = color;
            l.overColor = overColor;
            l.boughtColor = boughtColor;
            l.boughtOverColor = boughtOverColor;

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

    /**
     * render shop graphics
     * @param graphics
     */
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

    /**
     * Handles player input for shop.
     * @param btn
     */
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

    /**
     * Moves chosen shop element.
     * @param amount
     */
    public void moveActive(int amount){
        if(active + amount >= 0 && active + amount < interactables.length){

            interactables[active].movedAway();
            active += amount;
            interactables[active].movedOver();
        }else{
            interactables[active].blink(Color.red);
        }
    }

    /**
     * Select active element in shop.
     */
    public void selectActive(){
        //select item
        if(interactables[active] instanceof ItemElement){
            ItemElement ie = (ItemElement) interactables[active];
            //if(ie.hasBeenBought)return;
            if(player.gold < ie.price){
                goldBox.blinkText(Color.red);
                return;
            }
            ie.select();
            if(ie.hasBeenBought){
                if(ie.item instanceof Weapon){
                    player.weapon = (Weapon) ie.item;
                }else{
                    player.armor = (Armor)ie.item;

                }
                player.gold -= ie.price;


                for (int i = 0; i < interactables.length-1; i++) {
                    ItemElement l = (ItemElement)interactables[i];

                    if(i < 2)
                        l = new ItemElement(this,l.position,l.size,new Weapon(player.weapon.level+1,0));
                    else if(i < 4)
                        l = new ItemElement(this,l.position,l.size,new Weapon(player.weapon.level+1,1));
                    else if(i < 6)
                        l = new ItemElement(this,l.position,l.size,new Weapon(player.weapon.level+1,2));
                    else
                        l = new ItemElement(this,l.position,l.size,new Armor(player.armor.level+1));

                    l.color = color;
                    l.overColor = overColor;
                    l.boughtColor = boughtColor;
                    l.boughtOverColor = boughtOverColor;

                    interactables[i] = l;
                }

                interactables[active].movedOver();
                ie.hasBeenBought = false;
            }
        }else
            interactables[active].select();
    }



}
