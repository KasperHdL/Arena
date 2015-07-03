package oose2015.artifacts;

import oose2015.Assets;
import oose2015.EntityHandler;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
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
    public Image image;

    public Artifact(Vector2f position, Vector2f size, float rotation, Color color){
        EntityHandler.artifacts.add(this);
        this.position = position;
        this.size = size;
        this.rotation = rotation;

        this.color = color;

        image = Assets.SPRITE_SHEET.getSprite("dot.png");

        renderSize = size.length();
    }

    /**
     * Renders artifact
     */
    public void render() {
        image.setRotation(rotation);
        image.draw(position.x - size.x / 2, position.y - size.y / 2, size.x, size.y, color);
    }
}
