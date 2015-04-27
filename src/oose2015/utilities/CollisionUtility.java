package oose2015.utilities;

import oose2015.entities.Entity;
import oose2015.entities.MovableEntity;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by @Kasper on 18/04/2015
 * <p/>
 * Description:
 * ---
 * <p/>
 * Usage:
 * ---
 */

public class CollisionUtility {

    /**
     * checks if two entities is colliding CURRENTLY ONLY CIRCLES
     * @param entity Entity
     * @param other Entity
     * @return boolean if is colliding
     */
    public static boolean checkCollision(Entity entity, Entity other){
        Vector2f delta = other.position.copy().sub(entity.position);
        float dist = delta.length() - entity.size.x/2 - other.size.x/2;
        return dist <= 0;
    }

    /**
     * handles collision between two entities CURRENTLY ONLY CIRCLES
     * @param entity Entity
     * @param other Entity
     */
    public static void handleCollision(Entity entity, Entity other){

        Vector2f delta = other.position.copy().sub(entity.position);
        float dist = delta.length() - entity.size.x/2 - other.size.x/2;
        if(dist > 0) {
            return;
        }

        //send collision message
        entity.collides(other);
        other.collides(entity);

        if(!entity.isSolid || !other.isSolid) return;


        delta.normalise();
        delta.scale(dist);

        if(entity instanceof MovableEntity && other instanceof MovableEntity){
            pushBasedOnMass(delta,(MovableEntity)entity,(MovableEntity)other);
        }else{
            entity.position.add(delta);
        }
    }



    private static void pushBasedOnMass(Vector2f delta, MovableEntity entity, MovableEntity other){
        float totalMass = entity.mass + other.mass;
        float eRatio = (other.mass/totalMass);
        float oRatio = (entity.mass/totalMass);

        Vector2f vec = delta.copy().scale(eRatio);
        entity.position.add(vec);

        vec = delta.copy().scale(-oRatio);
        other.position.add(vec);

    }
}