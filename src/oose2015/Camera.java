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
        Vector2f d = targetPosition.sub(position);

        position.add(d.scale(0.2f*dt));
        scale += (targetScale - scale) * 0.2f * dt;

        //shake
        addShake();

        targetPosition = getCenterOfPlayers();

        //find the player the longest away from center and scale until within view
        float dist = -1;
        int index = -1;
        for (int i = 0; i < World.PLAYERS.size(); i++) {
            if(!World.PLAYERS.get(i).isAlive)continue;
            Vector2f delta = position.copy().sub(World.PLAYERS.get(i).position);
            if (dist < delta.length()) {
                index = i;
                dist = delta.length();
            }
        }
        if(index == -1){
            //no players found
            targetScale = 1;
            return;
        }

        //check if it can get closer
        while(entityWithinView(World.PLAYERS.get(index),targetScale)) {
            if (targetScale > .98f) break;
            targetScale += .02f;
        }


        while(!entityWithinView(World.PLAYERS.get(index),targetScale + .15f)) {
            if (targetScale <= 0.001f) break;
            targetScale -= .02f;
        }




    }

    /**
     * Finds centre position between players.
     * @return - Vector2f centre of player.
     */
    private Vector2f getCenterOfPlayers(){
        float x = 0,y = 0;
        int length = World.PLAYERS.size();

        for (int i = 0; i < length; i++) {
            if(!World.PLAYERS.get(i).isAlive)
                continue;

            x += World.PLAYERS.get(i).position.x;
            y += World.PLAYERS.get(i).position.y;
        }
        length = length-World.deadPlayers;
        return new Vector2f(x/length,y/length);
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
