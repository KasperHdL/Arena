package oose2015.entities;

import oose2015.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;


/**
 * Created by @Kasper on 21/04/2015
 * <p/>
 * Description:
 * ---
 * <p/>
 * Usage:
 * ---
 */

public class DungeonExit extends Entity {

    public boolean isVisible = true;

    public DungeonExit(Vector2f position){
        World.EXITS.add(this);
        this.position = position;

        this.size = new Vector2f(20f,20f);

        isSolid = false;
    }

    @Override
    public void render(Graphics graphics) {
        if (isVisible) {
            graphics.pushTransform();
            graphics.translate(position.x, position.y);
            graphics.rotate(0, 0, rotation);

            graphics.setColor(Color.magenta);
            graphics.fillOval(-size.x / 2, -size.x / 2, size.x, size.y);
            graphics.setColor(Color.white);
            graphics.drawString("exit", 0, 0);

            graphics.popTransform();

        }
    }
}