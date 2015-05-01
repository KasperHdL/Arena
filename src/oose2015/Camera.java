package oose2015;

import oose2015.entities.Entity;
import oose2015.entities.tiles.Tile;
import org.newdawn.slick.geom.Polygon;
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

public class Camera {

    public Vector2f position;
    public Vector2f halfViewSize;

    private float halfTileSize;

    public Camera(){
        halfViewSize = new Vector2f(Main.SCREEN_WIDTH/2,Main.SCREEN_HEIGHT/2);
        halfTileSize = Tile.TILE_SIZE/2;
    }

    public void update(float dt){
        Polygon polygon = new Polygon();
        for (int i = 0; i < World.PLAYERS.size(); i++) {
            Vector2f pos = World.PLAYERS.get(i).position;
            polygon.addPoint(pos.x,pos.y);
        }
        position = new Vector2f(polygon.getCenterX(),polygon.getCenterY());

    }

    public boolean entityWithinView(Entity entity){
        return  entity.position.x + entity.size.x/2 > position.x - halfViewSize.x &&
                entity.position.x - entity.size.x/2 < position.x + halfViewSize.x &&
                entity.position.y + entity.size.x/2 > position.y - halfViewSize.y &&
                entity.position.y - entity.size.x/2 < position.y + halfViewSize.y;
    }
    
    public boolean tileWithinView(Tile tile){
        return  tile.position.x + halfTileSize > position.x - halfViewSize.x &&
                tile.position.x - halfTileSize < position.x + halfViewSize.x &&
                tile.position.y + halfTileSize > position.y - halfViewSize.y &&
                tile.position.y - halfTileSize < position.y + halfViewSize.y;
    }

}
