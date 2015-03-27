package oose2015;

import oose2015.entities.Enemy;
import oose2015.entities.Entity;
import oose2015.entities.Player;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;

/**
 * Created by @Kasper on 26/03/2015
 * <p/>
 * Description:
 * ---
 * <p/>
 * Usage:
 * ---
 */

public class EntityHandler {

    public static ArrayList<Player> players;
    public static ArrayList<Enemy> enemies;


    public EntityHandler(){
        enemies = new ArrayList<Enemy>(20);
        players = new ArrayList<Player>(4);

        Player p = new Player(new Vector2f(0,0),50,51,52,53);
        players.add(p);

        enemies.add(new Enemy(new Vector2f(0,0)));


    }

    public void update(int dt){
        for (Entity player : players) {
            player.update(dt);
        }
        for (Entity entity : enemies) {
            entity.update(dt);
        }
    }

    public void render(Graphics graphics){
        for (Entity entity : enemies) {
            entity.render(graphics);
        }

        for (Entity player : players) {
            player.render(graphics);
        }
    }

}
