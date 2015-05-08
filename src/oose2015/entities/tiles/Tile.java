package oose2015.entities.tiles;

import oose2015.EntityHandler;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by @Kasper on 01/05/2015
 * <p/>
 * Description:
 * Tile base class.
 * Tiles are background elements.
 * <p/>
 */
public class Tile {

    public final static int TILE_SIZE = 32;

    public Vector2f size;
    public Vector2f position;

    public Tile(){
        EntityHandler.tiles.add(this);
    }

    public void render(Graphics graphics){

    }

}
