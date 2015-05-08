package oose2015.entities;

import oose2015.entities.Entity;
import oose2015.entities.tiles.Tile;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by @Kasper on 01/05/2015
 * <p/>
 * Description:
 * Child class of entity class.
 * Creates impassable wall entity.
 * <p/>
 */

public class Wall extends Entity {

    private Color color;
    private Color lineColor;

    public Wall(Vector2f position){
        this.position = position;
        size = new Vector2f(Tile.TILE_SIZE,Tile.TILE_SIZE);
        isMovable = false;

        color = Color.gray;
        lineColor = Color.black;
    }

    @Override
    public void render(Graphics graphics){
        graphics.pushTransform();
        graphics.translate(position.x, position.y);

        graphics.setColor(color);
        graphics.fillRect(-size.x / 2, -size.y / 2, size.x, size.y);

        graphics.setColor(lineColor);

        graphics.drawRect(-size.x / 2, -size.y / 2, size.x, size.y);
        graphics.drawLine(-size.x / 2, -size.y / 2, size.x / 2, size.y / 2);
        graphics.drawLine(size.x/2,-size.y/2,-size.x/2,size.y/2);

        graphics.popTransform();
    }

	@Override
	public void update(float dt) {
	}

	@Override
	public void collides(Entity other) {
	}


}
