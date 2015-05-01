package oose2015;

import oose2015.entities.Entity;
import oose2015.entities.tiles.Tile;
import oose2015.utilities.CollisionUtility;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;

/**
 * Created by @Kasper on 26/03/2015
 * <p/>
 * Description:
 * EntityHandler update and renders all entities, currently also spawns entities(which might change in the future)
 * <p/>
 * Usage:
 * ---
 */

public class EntityHandler {

    public static ArrayList<Entity> entities;//contains every entity player, enemy and all others
    public static ArrayList<Tile> tiles;

    private Camera camera;

    /**
     * Constructor for the EntityHandler
     */
    public EntityHandler(Camera camera){
        this.camera = camera;
        entities = new ArrayList<Entity>(50);
        tiles = new ArrayList<Tile>(10000);
    }

    public void update(float dt){
        camera.update(dt);

        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            entity.update(dt);
        }
    }

    public void updatePhysics(float dt){

        //Collision
        for (int i = 0; i < entities.size(); i++) {

            Entity entity = entities.get(i);
            for (int j = i+1; j < entities.size(); j++) {
                Entity other = entities.get(j);

                CollisionUtility.handleCollision(entity, other);
            }
        }
    }

    public void render(Graphics graphics){
        graphics.pushTransform();
        graphics.translate(-camera.position.x + camera.halfViewSize.x,-camera.position.y + camera.halfViewSize.y);
        for(Tile tile : tiles){
            if(camera.tileWithinView(tile))
                tile.render(graphics);
        }

        for(Entity entity : entities){
            if(camera.entityWithinView(entity))
                entity.render(graphics);
        }



        graphics.popTransform();
    }

}
