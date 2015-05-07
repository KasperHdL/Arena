package oose2015.gui;

import oose2015.Main;
import oose2015.entities.agents.Player;
import oose2015.gui.elements.Slider;
import oose2015.gui.elements.TextBox;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by @Kasper on 04/05/2015
 * <p/>
 * Description:
 * Creates player interface.
 * <p/>
 */
public class PlayerUI {

    private Slider healthSlider;

    private TextBox levelBox;
    private TextBox goldBox;

    private Player player;
    private Color playerColor;

    private Vector2f position;
    private Vector2f size;

    /**
     * Constructor for player interface. 
     * @param posX - position in x dimension
     * @param sizeX - Size in x dimension
     */
    public PlayerUI(int posX, int sizeX){
        this.position 	= new Vector2f(posX, Main.SCREEN_HEIGHT - 50);
        this.size 		= new Vector2f(sizeX,50);

        healthSlider 	= new Slider(new Vector2f(5,22),new Vector2f(size.x-10,25),Color.red,Color.gray);
        levelBox 		= new TextBox("Level " + 0,new Vector2f(5,2), TextBox.Align.LEFT_ALIGN);
        goldBox 		= new TextBox(999 + " gold",new Vector2f(sizeX-5,2), TextBox.Align.RIGHT_ALIGN);

        levelBox.color 	= Color.black;
        goldBox.color 	= Color.black;
    }

    /**
     * Sets player values for UI elements.
     * @param player
     * @param color - player colour
     */
    public void setPlayer(Player player,Color color){
        this.player = player;
        playerColor = player.color;

        goldBox.text = player.gold + " gold";
        levelBox.text = "level " + player.level;

        healthSlider.setValue(player.curHealth);
        healthSlider.setMaxValue(player.maxHealth);
    }

    /**
     * Render UI graphics.
     * @param graphics
     */
    public void render(Graphics graphics){
        if(player == null)return;
        graphics.pushTransform();
        graphics.translate(position.x, position.y);

        graphics.setColor(playerColor);
        graphics.fillRect(0, 0, size.x, size.y);

        goldBox.render(graphics);
        levelBox.render(graphics);
        healthSlider.render(graphics);

        graphics.popTransform();
    }

    /**
     * Updates gold count.
     */
    public void updateGold(){
        goldBox.text = player.gold + " gold";
    }

    /**
     * Update level count
     */
    public void updateLevel(){
        levelBox.text = "level " + player.level;
    }

    /**
     * Update health count.
     */
    public void updateHealth(){
        healthSlider.updateValue(player.curHealth,player.maxHealth);
    }
}
