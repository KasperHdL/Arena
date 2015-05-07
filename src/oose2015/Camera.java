package oose2015;

import oose2015.artifacts.Artifact;
import oose2015.entities.Entity;
import oose2015.entities.tiles.Tile;
import oose2015.particles.Particle;
import oose2015.states.GamePlayState;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by @Kasper on 01/05/2015
 * <p/>
 * Description:
 * Camera Class. Controls position of view.
 * <p/>
 * Usage:
 * ---
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
     * @param dt
     */
    public void update(float dt){
        if(World.PLAYERS.size() == 1){
            Vector2f d = World.PLAYERS.get(0).position.copy().sub(position);
            position.add(d.scale(0.1f));
            addShake();
            return;
        }

        //easing
        Vector2f d = targetPosition.sub(position);

        position.add(d.scale(0.1f));
        scale += (targetScale - scale) * 0.1f;

        //shake
        addShake();



        targetPosition = getCenterOfPlayers();

        //find the player the longest away from center and scale until within view
        float dist = position.copy().sub(World.PLAYERS.get(0).position).length();
        int index = 0;
        for (int i = 0; i < World.PLAYERS.size(); i++) {
            Vector2f delta = position.copy().sub(World.PLAYERS.get(i).position);
            if (dist < delta.length()) {
                index = i;
                dist = delta.length();
            }
        }

        if(entityWithinView(World.PLAYERS.get(index))){
            //check if need to get closer
            while(entityWithinView(World.PLAYERS.get(index),targetScale)){
                if(targetScale > 1f)break;
                targetScale += .01f;
            }
            targetScale -= .1f;

        }else{
            while(!entityWithinView(World.PLAYERS.get(index),targetScale))
                targetScale -= .01f;

            targetScale -= .1f;
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
            x += World.PLAYERS.get(i).position.x;
            y += World.PLAYERS.get(i).position.y;
        }

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
     * @param entity
     * @return
     */
    public boolean entityWithinView(Entity entity){
        return entityWithinView(entity,scale);
    }

    /**
     * Returns true if entity is within camera view.
     * @param entity
     * @param scale - zoom scale (the larger the closer)
     * @return
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
     * @param tile
     * @return
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
     * @param particle
     * @return
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
     * @param artifact
     * @return
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
