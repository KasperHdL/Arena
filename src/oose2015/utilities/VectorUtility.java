package oose2015.utilities;

import oose2015.entities.Entity;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by @Kasper on 18/04/2015
 * <p/>
 * Description:
 * Handles vector calculations.
 * <p/>
 */

public class VectorUtility {


    /**
     * Gets the distance from one Entity to another. NOT DISTANCE FROM CENTER TO CENTER but from circle edge to circle edge!
     * @param entity Entity
     * @param other other Entity
     * @return distance between Entities
     */
    public static float getDistanceToEntity(Entity entity, Entity other){
        Vector2f delta = other.position.copy().sub(entity.position);
        return delta.length() - entity.size.x/2 - other.size.x/2;
    }
}
