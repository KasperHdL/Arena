package oose2015.particles;

import oose2015.artifacts.Artifact;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by @Kasper on 05/05/2015
 * <p/>
 * Description:
 * ---
 * <p/>
 * Usage:
 * ---
 */

public class BloodParticle extends Particle {
    public BloodParticle(Vector2f position,int size,float rotation,Vector2f velocity,float angularVelocity,int time, Color color) {
        super(position, size, rotation, velocity, angularVelocity, time, false, color);
    }

    @Override
    protected void destroy() {
        //create blood splatter

        new Artifact(position,size,rotation,color);
        super.destroy();
    }
}
