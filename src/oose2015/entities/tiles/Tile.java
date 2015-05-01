package oose2015.entities.tiles;

import oose2015.EntityHandler;
import oose2015.entities.Entity;
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
