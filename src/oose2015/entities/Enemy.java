	package oose2015.entities;

import oose2015.EntityHandler;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;


/**
 * Created by @Kasper on 26/03/2015
 * <p/>
 * Description:
 * ---
 * <p/>
 * Usage:
 * ---
 */

public class Enemy extends Agent {
	public static boolean debug = true;


    float goldDrop;
    boolean isIdle;
    
    float engageRadius = 100f;
    float disengageRadius = 150f;
    float attackRadius = 10f;

    boolean isChasing = false;
     
	
    public Entity target;

    /**
     * Constructor for Enemy
     * @param position spawn position
     */
    public Enemy(Vector2f position){
        System.out.println("Enemy created");

        curHealth = 10;
        maxHealth = curHealth;

        size = new Vector2f(25.0f, 25.0f);

        this.position = position;

        maxVelocity = 2f;

        speedForce = .2f;
        mass = 100f;
        friction = .99f;
        inertia = .60f;

      
    }

    @Override
    public void update(int dt){
        if(isChasing){
            //get closest player
            Player player = getClosestPlayer();

            if(player != target){
                target = player;
            }

            Vector2f delta = target.position.copy().sub(position);
            float dist = delta.length();

            if(dist < attackRadius){
                //attack
            }else if(dist > disengageRadius){
                //disengage
                target = null;
                isChasing = false;
            }else{
                //move
                delta.normalise();
                delta.scale(speedForce/mass);
                acceleration.add(delta);
                super.move(dt);
            }
        }else{
            //get closest player
            Player player = getClosestPlayer();

            Vector2f delta = player.position.copy().sub(position);
            float dist = delta.length();
            //check distance
            if(dist < engageRadius){
                isChasing = true;
                target = player;
            }else {
                //TODO move a bit around randomly when not chasing any one
            }
        }
    }


    @Override
    public void render(Graphics graphics){
        graphics.pushTransform();
        graphics.setColor(Color.red);
        graphics.translate(position.x,position.y);

        graphics.rotate(0,0,rotation);
        graphics.fillOval(-size.x / 2,-size.y / 2, size.x, size.y);

        if(debug){
            if(isChasing){
                graphics.drawOval(-attackRadius,-attackRadius, attackRadius * 2, attackRadius * 2);
                graphics.setColor(Color.blue);
                graphics.drawOval(-disengageRadius,-disengageRadius, disengageRadius * 2, disengageRadius * 2);
            }else{
                graphics.drawOval(-engageRadius,-engageRadius, engageRadius * 2, engageRadius * 2);
            }
        }
        graphics.popTransform();
    }


    private Player getClosestPlayer(){
        Vector2f delta = EntityHandler.players.get(0).position.copy();
        float minDistance = delta.distance(position);
        int minIndex = 0;
        for (int i = 1; i < EntityHandler.players.size(); i++) {
            delta = EntityHandler.players.get(i).position.copy().sub(position);
            float dist = delta.length();

            if(minDistance > dist){
                minDistance = dist;
                minIndex = i;
            }
        }

        return EntityHandler.players.get(minIndex);
    }
}
