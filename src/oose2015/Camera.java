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
    public float scale;

    public Vector2f targetPosition;
    public float targetScale;

    private float halfTileSize;

    public Camera(){
        halfViewSize = new Vector2f(Main.SCREEN_WIDTH/2,Main.SCREEN_HEIGHT/2);
        halfTileSize = Tile.TILE_SIZE/2;
        scale = 1f;

        position = new Vector2f(1,1);
        targetPosition = new Vector2f(0,0);
    }

    public void update(float dt){
        if(World.PLAYERS.size() == 1){
            Vector2f d = World.PLAYERS.get(0).position.copy().sub(position);
            position.add(d.scale(0.1f));
            return;
        }

        //easing
        Vector2f d = targetPosition.sub(position);
        position.add(d.scale(0.1f));

        scale += (targetScale - scale) * 0.1f;



        Polygon polygon = new Polygon();
        for (int i = 0; i < World.PLAYERS.size(); i++) {
            Vector2f pos = World.PLAYERS.get(i).position;
            polygon.addPoint(pos.x,pos.y);
        }
        targetPosition = new Vector2f(polygon.getCenterX(),polygon.getCenterY());


        //find the player the longest away from center and scale until within view
        float dist = position.copy().sub(World.PLAYERS.get(0).position).length();
        int index = 0;
        for (int i = 0; i < World.PLAYERS.size(); i++) {
            Vector2f delta = position.copy().sub(World.PLAYERS.get(i).position);
            if (dist < delta.length()) {
                index = i;
                dist = delta.length();
            }
        }

        if(entityWithinView(World.PLAYERS.get(index))){
            //check if need to get closer
            while(entityWithinView(World.PLAYERS.get(index),targetScale)){
                if(targetScale > 1f)break;
                targetScale += .01f;
            }
            targetScale -= .1f;

        }else{
            while(!entityWithinView(World.PLAYERS.get(index),targetScale))
                targetScale -= .01f;

            targetScale -= .1f;
        }




    }

    public boolean entityWithinView(Entity entity){
        return entityWithinView(entity,scale);
    }

    public boolean entityWithinView(Entity entity,float scale){
        float x = (1/scale) * halfViewSize.x;
        float y = (1/scale) * halfViewSize.y;
        return  entity.position.x + entity.size.x/2 > position.x - x &&
                entity.position.x - entity.size.x/2 < position.x + x &&
                entity.position.y + entity.size.x/2 > position.y - y &&
                entity.position.y - entity.size.x/2 < position.y + y;
    }
    
    public boolean tileWithinView(Tile tile){
        float x = (1/scale) * halfViewSize.x;
        float y = (1/scale) * halfViewSize.y;
        return  tile.position.x + halfTileSize > position.x - x &&
                tile.position.x - halfTileSize < position.x + x &&
                tile.position.y + halfTileSize > position.y - y &&
                tile.position.y - halfTileSize < position.y + y;
    }

}
