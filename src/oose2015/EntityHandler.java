package oose2015;

import oose2015.entities.Enemy;
import oose2015.entities.Entity;
import oose2015.entities.Player;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

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

    public static ArrayList<Player> players; //for reference
    public static ArrayList<Enemy> enemies; //for reference

    public static ArrayList<Entity> entities;//contains every entity player, enemy and all others

    /**
     * Constructor for the EntityHandler
     */
    public EntityHandler(){
        entities = new ArrayList<Entity>(50);
        enemies = new ArrayList<Enemy>();
        players = new ArrayList<Player>();
    }

    public void update(float dt){
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
        for(Entity entity : entities){
            entity.render(graphics);
        }
    }

}
