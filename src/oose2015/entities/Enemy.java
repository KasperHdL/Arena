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

    public Enemy(Vector2f position){
        System.out.println("Player created");


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
        graphics.fillOval(position.x, position.y, size.x, size.y);

    }
}
