package oose2015.entities.tiles;

import oose2015.Assets;
import oose2015.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by @Kasper on 01/05/2015
 * <p/>
 * Description:
 * Creates background tile for level.
 * Child class of Tile. 
 * <p/>
 */
public class Floor extends Tile {

    private Color color;

    /**
     * Floor constructor. Sets floor variables.
     * @param position - position of floor tile
     * @param color - colour of floor tile.
     */
    public Floor(Vector2f position,Color color){
        this.position = position;
        size = new Vector2f(Tile.TILE_SIZE,Tile.TILE_SIZE);

        this.color = color.darker(World.RANDOM.nextFloat() * 0.4f);
        image = Assets.SPRITE_SHEET.getSprite("dot.png");
    }

    /**
     * Renders floor tile graphics.
     */
    @Override
    public void render() {
        image.draw(position.x - size.x / 2, position.y - size.y / 2, size.x, size.y, color);
    }
}
