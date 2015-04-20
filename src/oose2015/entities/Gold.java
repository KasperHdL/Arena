package oose2015.entities;

import oose2015.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import java.util.Random;


/**
 * Created by @Kasper on 18/04/2015
 * <p/>
 * Description:
 * ---
 * <p/>
 * Usage:
 * ---
 */

public class Gold extends Entity {

    public int value;

    public Gold(Vector2f position, int value){
        this.position = position;
        this.value = value;

        this.size = new Vector2f(10f,10f);


        this.rotation = World.RANDOM.nextFloat() * 360;

        float s = World.RANDOM.nextFloat() * 3 + 2;
        this.scale = new Vector2f(s,s);

        isSolid = false;
    }

    @Override
    public void update(float dt){

    }

    @Override
    public void render(Graphics graphics){
        graphics.pushTransform();
        graphics.translate(position.x, position.y);
        graphics.rotate(0, 0, rotation);

        graphics.setColor(Color.yellow);
        graphics.fillOval(-size.x / 2, -size.x / 2, size.x, size.y);

        graphics.popTransform();
    }
}