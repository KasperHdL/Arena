package oose2015.entities.tiles;

import oose2015.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by @Kasper on 01/05/2015
 * <p/>
 * Description:
 * ---
 * <p/>
 * Usage:
 * ---
 */

public class Floor extends Tile {

    private Color color;

    public Floor(Vector2f position,Color color){
        this.position = position;
        size = new Vector2f(Tile.TILE_SIZE,Tile.TILE_SIZE);

        this.color = color.darker(World.RANDOM.nextFloat()* 0.4f);
    }

    @Override
    public void render(Graphics graphics) {

        graphics.pushTransform();
        graphics.translate(position.x, position.y);

        graphics.setColor(color);
        graphics.fillRect(-size.x / 2, -size.y / 2, size.x, size.y);

        graphics.popTransform();
    }
}
