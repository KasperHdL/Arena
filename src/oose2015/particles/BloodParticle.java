package oose2015.particles;

import oose2015.artifacts.Artifact;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by @Kasper on 05/05/2015
 * <p/>
 * Description:
 * Child of Particle class.
 * Creates blood particle effect.
 * <p/>
 */
public class BloodParticle extends Particle {
   
	/**
	 * Blood particle constructor
	 * @param position
	 * @param size
	 * @param rotation
	 * @param velocity
	 * @param angularVelocity
	 * @param time
	 * @param color
	 */
	public BloodParticle(Vector2f position,int size,float rotation,Vector2f velocity,float angularVelocity,int time, Color color) {
        super(position, size, rotation, velocity, angularVelocity, time, true, color);
    }

	/**
	 * Destroys particle object and creates artifact in its place.
	 */
    @Override
    protected void destroy() {
        //create blood splatter

        super.destroy();
    }
}
