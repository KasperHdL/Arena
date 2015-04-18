package oose2015;

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

public class CollisionUtil {

    /**
     * checks if two Entities is colliding, CURRENTLY ONLY CIRCLES! return zero vector if not colliding
     * @param entity Entity
     * @param other Entity
     * @return Vector2f that tell how much the entity has to be moved in order NOT to collide anymore
     */
    public static Vector2f collides(Entity entity, Entity other){

        Vector2f delta = other.position.copy().sub(entity.position);
        float dist = delta.length() - entity.size.x/2 - other.size.x/2;
        if(dist > 0) {
            return new Vector2f();
        }
        entity.collides(other);
        other.collides(entity);

        if(!entity.isSolid || !other.isSolid) return new Vector2f();

        delta.normalise();
        return delta.scale(dist);
    }
}
