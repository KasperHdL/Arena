package oose2015;

import oose2015.artifacts.Artifact;
import oose2015.entities.Entity;
import oose2015.entities.tiles.Tile;
import oose2015.particles.Particle;
import oose2015.utilities.CollisionUtility;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;

/**
 * Created by @Kasper on 26/03/2015
 * <p/>
 * Description:
 * EntityHandler update and renders all entities, currently also spawns entities(which might change in the future)
 * <p/>
 * Usage:
 * ---
 */

public class EntityHandler {

    public static ArrayList<Entity> entities;//contains every entity player, enemy and all others
    public static ArrayList<Tile> tiles;
    public static ArrayList<Particle> particles;
    public static ArrayList<Artifact> artifacts;


    private Camera camera;

    /**
     * Constructor for the EntityHandler
     */
    public EntityHandler(Camera camera){
        this.camera = camera;
        entities = new ArrayList<Entity>(50);
        tiles = new ArrayList<Tile>(10000);
        particles = new ArrayList<Particle>(10000);
        artifacts = new ArrayList<Artifact>(10000);
    }

    /**
     * Updates objects
     * @param dt
     */
    public void update(float dt){
        camera.update(dt);

        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            entity.update(dt);
        }
        for (int i = 0; i < particles.size(); i++) {
            Particle particle = particles.get(i);
            particle.update(dt);
        }
    }

    /**
     * Runs collision for all entities
     * @param dt
     */
    public void updatePhysics(float dt){

        //Collision
        for (int i = 0; i < entities.size(); i++) {

            Entity entity = entities.get(i);
            for (int j = i+1; j < entities.size(); j++) {
                Entity other = entities.get(j);

                CollisionUtility.handleCollision(entity, other);
            }
        }
    }

    /**
     * Renders graphics of objects
     * @param graphics
     */
    public void render(Graphics graphics){
        graphics.pushTransform();
        graphics.scale(camera.scale,camera.scale);
        graphics.translate(	-camera.position.x + (camera.halfViewSize.x * (1 / camera.scale)), 
        					-camera.position.y + (camera.halfViewSize.y * (1/camera.scale)));
        for(Tile tile : tiles){
            if(camera.tileWithinView(tile))
                tile.render(graphics);
        }
        for(Artifact artifact: artifacts){
            if(camera.artifactWithinView(artifact))
                artifact.render(graphics);
        }

        for(Particle particle : particles){
            if(World.camera.particleWithinView(particle))
                particle.render(graphics);
        }

        for(Entity entity : entities){
            if(camera.entityWithinView(entity))
                entity.render(graphics);
        }




        graphics.popTransform();
    }

}
