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
 * ---
 * <p/>
 * Usage:
 * ---
 */

public class PlayerUI {

    private Slider healthSlider;

    private TextBox levelBox;
    private TextBox goldBox;

    private Player player;
    private Color playerColor;

    private Vector2f position;
    private Vector2f size;

    public PlayerUI(int posX, int sizeX){
        this.position = new Vector2f(posX, Main.SCREEN_HEIGHT - 50);
        this.size = new Vector2f(sizeX,50);

        healthSlider = new Slider(new Vector2f(5,22),new Vector2f(size.x-10,25),Color.red,Color.gray);
        levelBox = new TextBox("Level " + 0,new Vector2f(5,2), TextBox.Align.LEFT_ALIGN);
        goldBox = new TextBox(999 + " gold",new Vector2f(sizeX-5,2), TextBox.Align.RIGHT_ALIGN);

        levelBox.color = Color.black;
        goldBox.color = Color.black;
    }

    public void setPlayer(Player player,Color color){
        this.player = player;
        playerColor = player.color;

        goldBox.text = player.gold + " gold";
        levelBox.text = "level " + player.level;

        healthSlider.setValue(player.curHealth);
        healthSlider.setMaxValue(player.maxHealth);
    }

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

    public void updateGold(){
        goldBox.text = player.gold + " gold";
    }

    public void updateLevel(){
        levelBox.text = "level " + player.level;
    }

    public void updateHealth(){
        healthSlider.updateValue(player.curHealth,player.maxHealth);
    }
}
