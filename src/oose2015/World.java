package oose2015;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import oose2015.entities.DungeonExit;
import oose2015.entities.Enemy;
import oose2015.entities.KeyboardPlayer;
import oose2015.entities.Player;

import oose2015.gui.elements.TextBox;
import oose2015.utilities.CollisionUtility;
import org.newdawn.slick.Color;
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

    private int numPlayersOnExits = 0;

    GameContainer gameContainer;
    static StateBasedGame stateBasedGame;

    EntityHandler entityHandler;

    public static Random RANDOM;

    public static int TIME = 0;

    private int nextWave = 0;
    private int waveDelay = 1500;
    private int waveCount = 0;

    private TextBox dungeonExitText;
    private int playersOnExit = 0;
    private boolean playerOnExit = false;
    private boolean justExitedShop = false;
    private int dungeonExitTime = -1;
    private int dungeonExitLength = 2000;

    public World(GameContainer gameContainer, StateBasedGame stateBasedGame){

        PLAYERS = new ArrayList<Player>(4);
        ENEMIES = new ArrayList<Enemy>(20);
        EXITS = new ArrayList<DungeonExit>(1);

        this.gameContainer = gameContainer;
        World.stateBasedGame = stateBasedGame;
        RANDOM = new Random();

        entityHandler = new EntityHandler();

        dungeonExitText = new TextBox("dungeon exit", new Vector2f(Main.SCREEN_WIDTH/2,(Main.SCREEN_HEIGHT*3)/4), TextBox.Align.CENTER);

        new DungeonExit(new Vector2f(100,10));
    }

    public void render(Graphics graphics){
        entityHandler.render(graphics);

    }

    public void renderInterface(Graphics graphics){

        //dungeon exiting
        if(!justExitedShop && playerOnExit){

            if(dungeonExitTime == -1) {
                //waiting for other players
                dungeonExitText.text = "Waiting for " + (PLAYERS.size() - playersOnExit) + " players";
                dungeonExitText.render(graphics);
            }else if(dungeonExitTime < TIME){
                //exit dungeon
                justExitedShop = true;
                dungeonExitTime = -1;
                stateBasedGame.enterState(2);
            }else{
                //display timer
                String time = "" + ((float)(dungeonExitTime - TIME)/1000);
                dungeonExitText.text = "Exiting level in " + time.substring(0,3) + " seconds";
                dungeonExitText.render(graphics);
            }

        }
    }

    public void update(float dt){
        TIME += dt;
        float delta = dt/100;
        //System.out.println("time: " + time + " dt: " + dt + " delta " + delta);

        //TEMPORARY wave spawn
            boolean allDead = true;
            for (int i = 0; i < ENEMIES.size(); i++) {
                if(ENEMIES.get(i).isAlive){
                    allDead = false;
                    break;
                }
            }
            if(allDead && nextWave == 0){
                nextWave = TIME + waveDelay;
            }

            if(allDead && nextWave < TIME){
                nextWave = 0;
                spawnWave();
            }


        checkExits();
        entityHandler.update(delta);
        entityHandler.updatePhysics(delta);
    }

    public void spawnWave(){
        waveCount++;
        for (int i = 0; i < waveCount * 2; i++) {
            new Enemy(new Vector2f(RANDOM.nextInt(Main.SCREEN_WIDTH),RANDOM.nextInt(Main.SCREEN_HEIGHT)),RANDOM.nextInt(2)+RANDOM.nextInt(waveCount)+1, true);
        }
    }

    public void checkExits(){

        for (int i = 0; i < EXITS.size(); i++) {
            playersOnExit = 0;
            for (int j = 0; j < PLAYERS.size(); j++) {
                if (CollisionUtility.checkCollision(PLAYERS.get(j), EXITS.get(i)))
                    playersOnExit++;
            }

            playerOnExit = playersOnExit != 0;

            if(justExitedShop && !playerOnExit)
                    justExitedShop = false;
            else if(playersOnExit != PLAYERS.size())
                dungeonExitTime = -1;
            else if(dungeonExitTime == -1)
                dungeonExitTime = TIME + dungeonExitLength;
        }
    }

    /**
     * OVERLOARD FOR KEYBOARDPLAYER
     * @param player Player
     */
    public static void enteredExit(KeyboardPlayer player){
    }
    
    public void createPlayer(Vector2f v, Color color, int controllerInput){
    		new Player(v, color, controllerInput,gameContainer.getInput());
    		
    }
}
