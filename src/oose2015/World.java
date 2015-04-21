package oose2015;

import oose2015.entities.Enemy;
import oose2015.entities.Player;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

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

public class World {
    public static boolean DEBUG_MODE = true;

    GameContainer gameContainer;
    StateBasedGame stateBasedGame;

    EntityHandler entityHandler;

    public static Random RANDOM;

    public static int time = 0;

    private int nextWave = 0;
    private int waveDelay = 5000;
    private int waveCount = 0;

    public World(GameContainer gameContainer, StateBasedGame stateBasedGame){
        this.gameContainer = gameContainer;
        this.stateBasedGame = stateBasedGame;
        RANDOM = new Random();

        entityHandler = new EntityHandler();

        Player p = new Player(new Vector2f(Main.SCREEN_WIDTH/2,Main.SCREEN_HEIGHT/2), Input.KEY_UP, Input.KEY_DOWN, Input.KEY_LEFT, Input.KEY_RIGHT, Input.KEY_SPACE, Input.KEY_LCONTROL);
        gameContainer.getInput().addKeyListener(p);



    }

    public void update(float dt){
        time += dt;
        float delta = dt/100;
        //System.out.println("time: " + time + " dt: " + dt + " delta " + delta);

        //TEMPORARY
            boolean allDead = true;
            for (int i = 0; i < EntityHandler.enemies.size(); i++) {
                if(EntityHandler.enemies.get(i).isAlive){
                    allDead = false;
                    break;
                }
            }
            if(allDead && nextWave == 0){
                nextWave = time + waveDelay;
            }

            if(allDead && nextWave < time){
                nextWave = 0;
                spawnWave();
            }


        entityHandler.update(delta);
        entityHandler.updatePhysics(delta);
    }

    public void spawnWave(){
        waveCount++;
        for (int i = 0; i < waveCount * 2; i++) {
            new Enemy(new Vector2f(World.RANDOM.nextInt(Main.SCREEN_WIDTH),World.RANDOM.nextInt(Main.SCREEN_HEIGHT)),RANDOM.nextInt(waveCount)+1);
        }
    }

    public void render(Graphics graphics){
        entityHandler.render(graphics);
    }
}
