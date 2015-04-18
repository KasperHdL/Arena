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
     * @param input send Input from the main class
     */
    public EntityHandler(Input input){
        entities = new ArrayList<Entity>(50);
        enemies = new ArrayList<Enemy>();
        players = new ArrayList<Player>();

        Player p = new Player(new Vector2f(10,10), Input.KEY_UP, Input.KEY_DOWN, Input.KEY_LEFT, Input.KEY_RIGHT, Input.KEY_SPACE);
        input.addKeyListener(p);

        new Enemy(new Vector2f(0,0));
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

                Vector2f coll = CollisionUtil.collides(entity,other);
                //TODO change to based mass based! (awesome)
                entity.position.add(coll);
            }
        }
    }

    public void render(Graphics graphics){
        for(Entity entity : entities){
            entity.render(graphics);
        }
    }

}
