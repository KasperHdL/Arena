package oose2015;

import oose2015.artifacts.Artifact;
import oose2015.entities.Entity;
import oose2015.entities.tiles.Tile;
import oose2015.particles.Particle;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by @Kasper on 01/05/2015
 * <p/>
 * Description:
 * Camera Class. Controls position of view.
 * <p/>
 * Usage:
 * graphics.scale(camera.scale, camera.scale);
 * graphics.translate(	-camera.position.x + (camera.halfViewSize.x * (1/camera.scale)), 
        				-camera.position.y + (camera.halfViewSize.y * (1/camera.scale)));
 */
public class Camera {

    public Vector2f position;
    public Vector2f halfViewSize;
    public float scale;


    //target
    public Vector2f targetPosition;
    public float targetScale;

    //shake

    public int shakeEnd;
    public int shakeLength;
    public Vector2f shake;
    private float oscSpeed = 1;


    private float halfTileSize;

    public Camera(){
        halfViewSize = new Vector2f(Main.SCREEN_WIDTH/2,Main.SCREEN_HEIGHT/2);
        halfTileSize = Tile.TILE_SIZE/2;
        scale = 1f;

        position = new Vector2f(1,1);
        targetPosition = new Vector2f(0,0);
    }

    /**
     * Updates camera position and zoom.
     * @param dt - delta time
     */
    public void update(float dt){

        //easing
        Vector2f d = targetPosition.sub(position).scale(5f * dt);

        position.add(d);

        //shake
        addShake();

        targetPosition = World.PLAYER.position.copy();


    }

    /**
     * Add screen-shake to position
     */
    private void addShake(){
        if(shakeEnd > World.TIME){
            float t = (float)(shakeEnd - World.TIME)/shakeLength;

            Vector2f s = new Vector2f(  t * (float)Math.cos(World.TIME * oscSpeed) * shake.x,
                    t * (float)Math.sin(World.TIME * oscSpeed) * shake.y);

            position.add(s);
        }
    }

    public Vector2f screenPointToWorldPoint(Vector2f input){
        input = input.copy().add(new Vector2f(-Main.SCREEN_WIDTH/2,-Main.SCREEN_HEIGHT/2));
        input.scale(scale);
        input.add(position);
        return input;
    }

    /**
     * Initiates screen shake
     * @param shake - direction and magnitude of shake
     * @param time - length of shake time
     * @param oscillationSpeed - oscillation-speed of screen shake
     */
    public void shakeScreen(Vector2f shake,int time,float oscillationSpeed){
        this.shake = shake;
        shakeLength = time;
        oscSpeed = oscillationSpeed;
        shakeEnd = World.TIME + shakeLength;
    }

    /**
     * Returns true if entity is within camera-view
     * @param entity Entity checked
     * @return boolean
     */
    public boolean entityWithinView(Entity entity){
        return entityWithinView(entity,scale);
    }

    /**
     * Returns true if entity is within camera view.
     * @param entity Entity checked
     * @param scale - zoom scale (the larger the closer)
     * @return boolean
     */
    public boolean entityWithinView(Entity entity,float scale){
        float x = (1/scale) * halfViewSize.x;
        float y = (1/scale) * halfViewSize.y;
        return  entity.position.x + entity.size.x/2 > position.x - x &&
                entity.position.x - entity.size.x/2 < position.x + x &&
                entity.position.y + entity.size.y/2 > position.y - y &&
                entity.position.y - entity.size.y/2 < position.y + y;
    }
    
    /**
     * Return true if tile is within view
     * @param tile Tile checked
     * @return boolean
     */
    public boolean tileWithinView(Tile tile){
        float x = (1/scale) * halfViewSize.x;
        float y = (1/scale) * halfViewSize.y;
        return  tile.position.x + halfTileSize > position.x - x &&
                tile.position.x - halfTileSize < position.x + x &&
                tile.position.y + halfTileSize > position.y - y &&
                tile.position.y - halfTileSize < position.y + y;
    }

    /**
     * Return true if particle is within view
     * @param particle Particle being checked
     * @return boolean
     */
    public boolean particleWithinView(Particle particle){
        float x = (1/scale) * halfViewSize.x;
        float y = (1/scale) * halfViewSize.y;
        return  particle.position.x + particle.renderSize > position.x - x &&
                particle.position.x - particle.renderSize < position.x + x &&
                particle.position.y + particle.renderSize > position.y - y &&
                particle.position.y - particle.renderSize < position.y + y;
    }

    /**
     * Return true if particle within view
     * @param artifact Artifact being checked
     * @return boolean
     */
    public boolean artifactWithinView(Artifact artifact){
        float x = (1/scale) * halfViewSize.x;
        float y = (1/scale) * halfViewSize.y;
        return  artifact.position.x + artifact.renderSize > position.x - x &&
                artifact.position.x - artifact.renderSize < position.x + x &&
                artifact.position.y + artifact.renderSize > position.y - y &&
                artifact.position.y - artifact.renderSize < position.y + y;
    }

}
