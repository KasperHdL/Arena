package oose2015.entities.drops;

import oose2015.World;
import oose2015.entities.Entity;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by @Kasper on 18/04/2015
 * <p/>
 * Description:
 * Child class to Entity. Creates and renders gold object.
 * <p/>
 */

public class Gold extends Entity {

    public int value;

    //Constructor
    public Gold(Vector2f position, int value){
        this.position = position;
        this.value = value;

        float s = World.RANDOM.nextFloat() * 2 + value;
        this.size = new Vector2f(s,s);

        isMovable = false;
    }

    /**
     * Renders object graphics.
     */
    @Override
    public void render(Graphics graphics){
        graphics.pushTransform();
        graphics.translate(position.x, position.y);
        graphics.rotate(0, 0, rotation);

        graphics.setColor(Color.yellow);
        graphics.fillOval(-size.x / 2, -size.x / 2, size.x, size.y);

        graphics.popTransform();
    }

	@Override
	public void update(float dt) {
	}

	@Override
	public void collides(Entity other) {
	}
}
