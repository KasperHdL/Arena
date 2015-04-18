	package oose2015.entities;

import oose2015.EntityHandler;

import oose2015.VectorUtil;
import oose2015.states.GamePlayState;
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

    float damage = 1f;

    float goldDrop;

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
    public Enemy(Vector2f position){
        System.out.println("Enemy created");

        curHealth = 10;
        maxHealth = curHealth;

        size = new Vector2f(25.0f, 25.0f);

        this.position = position;

        maxVelocity = 2f;

        speedForce = .1f;
        mass = 100f;
        friction = .99f;
        inertia = .60f;

      
    }

    @Override
    public void update(int dt){
        if(isAlive){
            Player player = getClosestPlayer();
            if(isChasing){

                if(player != target)
                    target = player;

                if(target != null)
                    chasePlayer(target,dt);
            }else if(player != null){

                float dist = VectorUtil.getDistanceToEntity(this, player);


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

    private void chasePlayer(Agent agent, int dt){
        Vector2f delta = target.position.copy().sub(position);
        float dist = VectorUtil.getDistanceToEntity(this, agent);

        if(dist < attackRadius){
            //attack
            if(nextAttackTime < GamePlayState.time) {
                agent.takeDamage(damage);
                nextAttackTime = GamePlayState.time + attackDelay;
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

    protected void move(Vector2f delta, int dt){
        delta.normalise();
        delta.scale(speedForce / mass);
        acceleration.add(delta);

        rotation = (float)acceleration.getTheta();

        super.move(dt);
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

            if(debug){
                float halfRadius;

                if(isChasing){
                    float dist = Float.MAX_VALUE;

                    if(target != null)
                        dist = VectorUtil.getDistanceToEntity(this, target);

                    //attack radius
                    halfRadius = attackRadius + size.x/2;
                    if(nextAttackTime - 50 < GamePlayState.time && dist < attackRadius) {
                        graphics.setColor(Color.red);
                        graphics.fillOval(-halfRadius, -halfRadius, halfRadius*2, halfRadius*2);
                    }else
                        graphics.drawOval(-halfRadius, -halfRadius, halfRadius * 2, halfRadius * 2);

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
        }else{

            graphics.setColor(new Color(200,0,0,127));

            graphics.fillOval(-size.x / 2, -size.y / 2, size.x, size.y);

        }




        graphics.popTransform();

        if(debug && isAlive) {
            graphics.setColor(Color.white);
            graphics.drawString(curHealth + " / " + maxHealth, position.x + 10, position.y + 10);
        }
    }


    private Player getClosestPlayer(){
        Vector2f delta = EntityHandler.players.get(0).position.copy().sub(position);

        float minDistance = delta.distance(position);
        int minIndex = 0;

        for (int i = 1; i < EntityHandler.players.size(); i++) {
            Player p = EntityHandler.players.get(i);
            if(!p.isAlive)continue;
            delta = p.position.copy().sub(position);
            float dist = delta.length();

            if(minDistance > dist){
                minDistance = dist;
                minIndex = i;
            }
        }

        return EntityHandler.players.get(minIndex);
    }

    @Override
    public float getDamage(){return damage;}

}
