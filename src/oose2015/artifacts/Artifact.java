package oose2015.artifacts;

import oose2015.EntityHandler;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by @Kasper on 05/05/2015
 * <p/>
 * Description:
 * Background element, that is only rendered.
 * <p/>
 */

public class Artifact {
    public Vector2f position;
    public Vector2f size;
    public float rotation;

    public float renderSize;

    public Color color;

    public Artifact(Vector2f position, Vector2f size, float rotation, Color color){
        EntityHandler.artifacts.add(this);
        this.position = position;
        this.size = size;
        this.rotation = rotation;

        this.color = color;

        renderSize = size.length();
    }

    /**
     * Renders artifact
     * @param graphics
     */
    public void render(Graphics graphics){
        graphics.pushTransform();
        graphics.translate(position.x, position.y);
        graphics.rotate(0, 0, rotation);

        graphics.setColor(color);
        graphics.fillRect(-size.x/2,-size.y/2,size.x,size.y);

        graphics.popTransform();
    }
}
