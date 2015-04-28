package oose2015;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import oose2015.entities.DungeonExit;
import oose2015.entities.Enemy;
import oose2015.entities.KeyboardPlayer;
import oose2015.entities.Player;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
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

    //ARRAYLIST FOR KEYBOARDPLAYERS
    public static ArrayList<KeyboardPlayer> KEYBOARDPLAYERS;
    
    public static ArrayList<Player> PLAYERS; //for reference
    public static ArrayList<Enemy> ENEMIES; //for reference
    public static ArrayList<DungeonExit> EXITS; //for reference

    GameContainer gameContainer;
    static StateBasedGame stateBasedGame;

    EntityHandler entityHandler;

    public static Random RANDOM;

    public static int TIME = 0;

    private int nextWave = 0;
    private int waveDelay = 1500;
    private int waveCount = 0;

    public World(GameContainer gameContainer, StateBasedGame stateBasedGame){

        PLAYERS = new ArrayList<Player>(4);
        ENEMIES = new ArrayList<Enemy>(20);
        EXITS = new ArrayList<DungeonExit>(1);

        this.gameContainer = gameContainer;
        World.stateBasedGame = stateBasedGame;
        RANDOM = new Random();

        entityHandler = new EntityHandler();

        new DungeonExit(new Vector2f(100,10));
    }

    public void render(Graphics graphics){
        entityHandler.render(graphics);
    }

    public void update(float dt){
        TIME += dt;
        float delta = dt/100;
        //System.out.println("time: " + time + " dt: " + dt + " delta " + delta);

        //TEMPORARY
            boolean allDead = true;
            for (int i = 0; i < ENEMIES.size(); i++) {
                if(ENEMIES.get(i).isAlive){
                    allDead = false;
                    break;
                }
            }
            if(allDead && nextWave == 0){
                waveDelay *= 1.2f;
                nextWave = TIME + waveDelay;
            }

            if(allDead && nextWave < TIME){
                nextWave = 0;
                spawnWave();
            }


        entityHandler.update(delta);
        entityHandler.updatePhysics(delta);
    }

    public void spawnWave(){
        waveCount++;
        for (int i = 0; i < waveCount * 2; i++) {
            new Enemy(new Vector2f(RANDOM.nextInt(Main.SCREEN_WIDTH),RANDOM.nextInt(Main.SCREEN_HEIGHT)),RANDOM.nextInt(2)+RANDOM.nextInt(waveCount)+1);
        }
    }

    public static void enteredExit(Player player){
        System.out.println(player + " exited");
        stateBasedGame.enterState(2);
    }

    /**
     * OVERLOARD FOR KEYBOARDPLAYER
     * @param player Player
     */
    public static void enteredExit(KeyboardPlayer player){
        System.out.println(player + " exited");
    }
    
    public void createPlayer(Vector2f v, int controllerInput){
    		Player p = new Player(v, controllerInput,gameContainer.getInput());
    		
    }
}
