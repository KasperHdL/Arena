package oose2015.entities;

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



    }


    @Override
    public void render(Graphics graphics){
        graphics.setColor(Color.red);
        graphics.fillOval(position.x - size.x/2, position.y - size.y/2, size.x, size.y);

    }
}
