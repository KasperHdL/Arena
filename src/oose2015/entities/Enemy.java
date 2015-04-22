	package oose2015.entities;

import oose2015.EntityHandler;
import oose2015.VectorUtility;
import oose2015.World;

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
    float damage = 1f;

    int goldDrop;
    int expDrop;

    float engageRadius = 100f;
    float disengageRadius = 150f;
    float attackRadius = 10f;

    float nextAttackTime = 0f;
    float attackDelay = 200f;

    boolean isChasing = false;
     
	
    public Agent target;

    /**
     * Constructor for Enemy
     * @param position spawn position
     */
    public Enemy(Vector2f position,int level){
        System.out.println("Enemy created");
        World.ENEMIES.add(this);

        curHealth = 10;
        maxHealth = curHealth;

        this.level = level;
        this.position = position;

        size = new Vector2f(level * 5f + 5f,level * 5f + 5f );


        maxVelocity = 12f;

        speedForce = 8f;
        mass = level + 1f;
        goldDrop = level * 5;
        expDrop = level * 10;

      
    }

    @Override
    public void update(float dt){
        if(isAlive){
            Player player = getClosestPlayer();
            if(isChasing){

                if(player != target)
                    target = player;

                if(target != null)
                    chasePlayer(target,dt);
            }else if(player != null){

                float dist = VectorUtility.getDistanceToEntity(this, player);


                //check distance
                if(dist < engageRadius){
                    isChasing = true;
                    target = player;
                }else {
                    //idle
                    //TODO move a bit around randomly when not chasing any one
                }
            }
        }else{
            //dead
        }
    }

    private void chasePlayer(Agent agent, float dt){
        Vector2f delta = target.position.copy().sub(position);
        float dist = VectorUtility.getDistanceToEntity(this, agent);

        if(dist < attackRadius){
            //attack
            if(nextAttackTime < World.TIME) {
                agent.takeDamage(damage);
                nextAttackTime = World.TIME + attackDelay;
            }
        }else if(dist > disengageRadius){
            //disengage
            target = null;
            isChasing = false;
        }else{
            //move
            move(delta,dt);
        }
    }

    protected void move(Vector2f input, float dt){
        input.normalise();
        input.scale(speedForce / mass);

        rotation = (float)acceleration.getTheta();

        super.move(dt,input);
    }


    @Override
    public void render(Graphics graphics){
        graphics.pushTransform();
        graphics.translate(position.x, position.y);
        graphics.rotate(0, 0, rotation);

        if(isAlive){
            graphics.setColor(Color.pink);

            graphics.fillOval(-size.x / 2, -size.y / 2, size.x, size.y);
            graphics.setColor(Color.black);
            graphics.drawLine(0,0,size.x/2,0);

            float halfRadius;
            float dist = Float.MAX_VALUE;

            if(target != null)
                dist = VectorUtility.getDistanceToEntity(this, target);

            if(World.DEBUG_MODE){

                if(isChasing){

                    //disengage radius
                    halfRadius = disengageRadius + size.x/2;
                    graphics.setColor(Color.blue);
                    graphics.drawOval(-halfRadius, -halfRadius, halfRadius * 2, halfRadius * 2);
                }else{
                    graphics.setColor(Color.pink);
                    //engage radius
                    halfRadius = engageRadius + size.x/2;
                    graphics.drawOval(-halfRadius, -halfRadius, halfRadius * 2, halfRadius * 2);
                }
            }

            //attack radius
            halfRadius = attackRadius + size.x/2;
            if(nextAttackTime - 50 < World.TIME && dist < attackRadius) {
                graphics.setColor(Color.red);
                graphics.fillOval(-halfRadius, -halfRadius, halfRadius*2, halfRadius*2);
            }else if(World.DEBUG_MODE)
                graphics.drawOval(-halfRadius, -halfRadius, halfRadius * 2, halfRadius * 2);

        }else{

            graphics.setColor(new Color(200,0,0,127));

            graphics.fillOval(-size.x / 2, -size.y / 2, size.x, size.y);

        }




        graphics.popTransform();

        if(World.DEBUG_MODE && isAlive) {
            graphics.setColor(Color.white);
            graphics.drawString(curHealth + " / " + maxHealth, position.x + 10, position.y + 10);
            graphics.drawString("level " + level, position.x + 10, position.y - 10);
        }
    }


    private Player getClosestPlayer(){
        Vector2f delta = World.PLAYERS.get(0).position.copy().sub(position);

        float minDistance = delta.distance(position);
        int minIndex = 0;

        for (int i = 1; i < World.PLAYERS.size(); i++) {
            Player p = World.PLAYERS.get(i);
            if(!p.isAlive)continue;
            delta = p.position.copy().sub(position);
            float dist = delta.length();

            if(minDistance > dist){
                minDistance = dist;
                minIndex = i;
            }
        }

        return World.PLAYERS.get(minIndex);
    }

    @Override
    public float getDamage(){return damage;}

    @Override
    public void die(){
        new Gold(position,goldDrop);
        super.die();
    }

}
