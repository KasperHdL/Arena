package oose2015;

import oose2015.particles.BloodParticle;
import oose2015.particles.Particle;
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

public class ParticleFactory {

    public static void createBloodSplatter(Vector2f position, Vector2f dir,Color color){
        for (int i = 0; i < 5; i++) {
            Color c = new Color(color).darker(World.RANDOM.nextFloat()*0.1f+.1f);
            new BloodParticle(
                    position.copy(),//position
                    5 + World.RANDOM.nextInt(5),//size
                    World.RANDOM.nextFloat()*360,//rotation
                    dir.copy().add((World.RANDOM.nextFloat() * 90) - 45).scale(World.RANDOM.nextFloat()*15),//velocity
                    World.RANDOM.nextFloat()*2000,//angular velocity
                    400,//time alive
                    c//color
            );
        }
    }

    public static void createDeathSplatter(Vector2f position, Color color){
        for (int i = 0; i < 5; i++) {
            Color c = new Color(color).darker(World.RANDOM.nextFloat()*0.3f+.1f);
            new BloodParticle(
                    position.copy(),//position
                    10 + World.RANDOM.nextInt(5),//size
                    World.RANDOM.nextFloat()*360,//rotation
                    new Vector2f(World.RANDOM.nextFloat() * 360).scale(World.RANDOM.nextFloat() * 20),//velocity
                    World.RANDOM.nextFloat()*2000,//angular velocity
                    400,//time alive
                    c//color
            );
        }
    }

    public static void createSmokeTrail(Vector2f position,Vector2f area,Vector2f dir){
        for (int i = 0; i < World.RANDOM.nextInt(2)+1; i++) {
            Color c = new Color(Color.darkGray).darker(World.RANDOM.nextFloat()*0.2f+.1f);

            new Particle(
                    position.copy().add(area.scale((World.RANDOM.nextFloat() * 2) - 1)),//position
                    World.RANDOM.nextInt(5)+5,//size
                    World.RANDOM.nextFloat()*360,//rotation
                    dir.copy().scale(World.RANDOM.nextFloat() + 1),//velocity
                    World.RANDOM.nextFloat()*2000,//angular velocity
                    300,//time Alive
                    true,//fade out
                    c//color
            );
        }
    }

    public static void createProjectileTrail(Vector2f position,Vector2f area,Vector2f dir){
        for (int i = 0; i < World.RANDOM.nextInt(2)+1; i++) {
            Color c = new Color(Color.lightGray).darker(World.RANDOM.nextFloat()*0.2f+.1f);
            new Particle(
                    position.copy().add(area.scale((World.RANDOM.nextFloat() * 2) - 1)),//position
                    World.RANDOM.nextInt(5)+1,//size
                    World.RANDOM.nextFloat()*360,//rotation
                    dir.copy().scale(World.RANDOM.nextFloat()+1),//velocity
                    World.RANDOM.nextFloat()*2000,//angular velocity
                    300,//time Alive
                    true,//fade out
                    c//color
            );
        }
    }

    public static void createLevelUpRing(Vector2f position){
        for (int i = 0; i < 120; i++) {
            Color c = new Color(Color.yellow).brighter(World.RANDOM.nextFloat()*0.2f+.1f);
            new Particle(
                    position.copy(),//position
                    World.RANDOM.nextInt(10)+15,//size
                    World.RANDOM.nextFloat()*360,//rotation
                    new Vector2f(i*3).scale(World.RANDOM.nextFloat()*10+20),//velocity
                    World.RANDOM.nextFloat()*2000,//angular velocity
                    500,//time Alive
                    true,//fade out
                    c//color
            );
        }
    }
}
