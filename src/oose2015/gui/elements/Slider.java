package oose2015.gui.elements;

import oose2015.gui.Element;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import javax.xml.soap.Text;

/**
 * Created by @Kasper on 04/05/2015
 * <p/>
 * Description:
 * ---
 * <p/>
 * Usage:
 * ---
 */

public class Slider extends Element {

    private float maxValue;
    public float value;

    private Color bgColor;

    private TextBox textBox;

    public Slider(Vector2f position, Vector2f size,Color color, Color bgColor){
        this.position = position;
        this.size = size;
        this.color = color;
        this.bgColor = bgColor;

        maxValue = 1;
        value = maxValue;
        textBox = new TextBox("100 %",new Vector2f(size.x/2,3), TextBox.Align.CENTER);
        textBox.color = Color.black;

    }

    @Override
    public void render(Graphics graphics){
        graphics.pushTransform();
        graphics.translate(position.x, position.y);

        graphics.setColor(bgColor);
        graphics.fillRect(0,0, size.x, size.y);

        graphics.setColor(color);
        graphics.fillRect(0,0,size.x*(value/maxValue),size.y);


        textBox.render(graphics);

        graphics.popTransform();
    }

    public void updateValue(float value,float maxValue){
        this.value = value;
        this.maxValue = maxValue;
        textBox.text = ((float)Math.round(value*10)/10) + " / " + ((float)Math.round(maxValue*10)/10);
    }
    public void setValue(float value){
        this.value = value;
        textBox.text = ((float)Math.round(value*10)/10) + " / " + ((float)Math.round(maxValue*10)/10);
    }
    public void setMaxValue(float maxValue){
        this.maxValue = maxValue;
        textBox.text = ((float)Math.round(value*10)/10) + " / " + ((float)Math.round(maxValue*10)/10);
    }

    public void blinkText(String text, Color textColor){
        textBox.blinkText(text,textColor);
    }
}
