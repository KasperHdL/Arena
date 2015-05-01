package oose2015;

import oose2015.entities.Entity;
import oose2015.entities.tiles.Tile;
import org.newdawn.slick.geom.Vector2f;

import java.util.Vector;

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

    public Camera(){
        halfViewSize = new Vector2f(Main.SCREEN_WIDTH/2,Main.SCREEN_HEIGHT/2);
    }

    public void update(float dt){
        position = World.PLAYERS.get(0).position;
    }

    public boolean entityWithinView(Entity entity){
        return  entity.position.x > position.x - halfViewSize.x &&
                entity.position.x < position.x + halfViewSize.x &&
                entity.position.y > position.y - halfViewSize.y &&
                entity.position.y < position.y + halfViewSize.y;
    }
    
    public boolean tileWithinView(Tile tile){
        return  tile.position.x > position.x - halfViewSize.x &&
                tile.position.x < position.x + halfViewSize.x &&
                tile.position.y > position.y - halfViewSize.y &&
                tile.position.y < position.y + halfViewSize.y;
    }

}
