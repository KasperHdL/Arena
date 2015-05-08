package oose2015.gui.elements;

import oose2015.Main;
import oose2015.gui.Element;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by @Kasper on 28/04/2015
 * <p/>
 * Description:
 * Child class of Element.
 * Creates a text box element on screen.
 * <p/>
 */

public class TextBox extends Element {

	/**
	 * Textbox alignment.
	 */
   public enum Align{
       LEFT_ALIGN,
       CENTER,
       RIGHT_ALIGN
   }


    //blink
    protected 	int stopTextBlink 	= 0;
    public 		int blinkTextLength = 500;

    protected Color blinkTextColor;
    protected String blinkText;

    public Align alignment;
    public String text;
    
    /**
     * TextBox constructor, sets textbox variables.
     * @param text - String text.
     * @param position - position of textbox
     * @param alignment - position relative to centre
     */
    public TextBox(String text, Vector2f position, Align alignment){
        this.text 		= text;
        this.position 	= position;
        this.alignment 	= alignment;
        color			= Color.white;
    }

    /**
     * Render textbox graphics.
     */
    @Override
    public void render(Graphics graphics){
        graphics.pushTransform();
        graphics.translate(position.x, position.y);

        String txt = text;
        if(stopTextBlink > Main.TIME) {
            graphics.setColor(blinkTextColor);
            txt = blinkText;
        }else
            graphics.setColor(color);

        switch (alignment){
            case LEFT_ALIGN: {
                graphics.drawString(txt, 0, 0);

            }break;
            case CENTER: {
                int width = graphics.getFont().getWidth(txt);
                graphics.drawString(txt, -width / 2, 0);

            }break;
            case RIGHT_ALIGN: {
                int width = graphics.getFont().getWidth(txt);
                graphics.drawString(txt, -width, 0);

            }break;
        }

        graphics.popTransform();
    }

    /**
     * blinkText overload uses current text string value for textbox.
     * Set temporary colour for textbox
     * @param textColor
     */
    public void blinkText(Color textColor){
        blinkText(text,textColor);
    }

    /**
     * Set temporary text in colour for textbox
     * @param text - String text
     * @param textColor - colour of text
     */
    public void blinkText(String text,Color textColor){
        stopTextBlink = Main.TIME + blinkTextLength;
        blinkTextColor = textColor;
        blinkText = text;
    }

    /**
     * Stop text blink.
     */
    public void stopBlinkText(){
        stopTextBlink = 0;
    }
}
