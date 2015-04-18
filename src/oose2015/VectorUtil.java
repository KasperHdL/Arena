package oose2015;

import oose2015.entities.Agent;
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

public class VectorUtil {


    public static float getDistanceToAgent(Agent agent, Agent other){
        Vector2f delta = other.position.copy().sub(agent.position);
        return delta.length() - agent.size.x/2 - other.size.x/2;
    }
}
