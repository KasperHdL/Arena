package oose2015;

import oose2015.entities.DungeonExit;
import oose2015.entities.agents.Enemy;
import oose2015.entities.agents.Player;

import oose2015.entities.tiles.Floor;
import oose2015.entities.tiles.Tile;
import oose2015.entities.Wall;
import oose2015.gui.PlayerUI;
import oose2015.gui.elements.TextBox;
import oose2015.utilities.CollisionUtility;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by @Kasper on 18/04/2015
 * <p/>
 * Description:
 * Holds game world and objects
 * <p/>
 */

public class World {
    public static boolean DEBUG_MODE = false;
    
    public static ArrayList<Player> PLAYERS; //for reference
    public static ArrayList<Enemy> ENEMIES; //for reference
    public static ArrayList<DungeonExit> EXITS; //for reference

    public static Camera camera;

    GameContainer gameContainer;
    static StateBasedGame stateBasedGame;

    public static PlayerUI[] playerUIs;

    EntityHandler entityHandler;

    public static Random RANDOM;

    public static int TIME = 0;

    private int nextWave = 0;
    private int waveDelay = 1500;
    private int waveCount = 0;

    private TextBox dungeonExitText;
    private int numPlayersOnExit = 0;
    public static int deadPlayers = 0;
    private boolean playerOnExit = false;
    private boolean justExitedShop = false;
    private int dungeonExitTime = -1;
    private int dungeonExitLength = 2000;

    public World(GameContainer gameContainer, StateBasedGame stateBasedGame){

        camera = new Camera();

        PLAYERS = new ArrayList<Player>();
        ENEMIES = new ArrayList<Enemy>(20);
        EXITS = new ArrayList<DungeonExit>(1);

        playerUIs = new PlayerUI[4];
        int sizeX = Main.SCREEN_WIDTH/4;
        for (int i = 0; i < playerUIs.length; i++) {
            playerUIs[i] = new PlayerUI(i*sizeX,sizeX);
        }

        this.gameContainer = gameContainer;
        World.stateBasedGame = stateBasedGame;
        RANDOM = new Random();

        entityHandler = new EntityHandler(camera);

        dungeonExitText = new TextBox("dungeon exit", new Vector2f(Main.SCREEN_WIDTH/2,(Main.SCREEN_HEIGHT*3)/4), TextBox.Align.CENTER);

        new DungeonExit(new Vector2f(0,0));

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if(i == 0 || j == 0 || i == 99 || j == 99)
                    new Wall(new Vector2f((i-50) * Tile.TILE_SIZE,(j-50) * Tile.TILE_SIZE));
                else
                    new Floor(new Vector2f((i-50) * Tile.TILE_SIZE,(j-50) * Tile.TILE_SIZE),Color.green);

            }

        }
      //  for(int i = 0;i<120;i++)
       //     ParticleFactory.createDeathSplatter(new Vector2f(new Vector2f(i*10).scale(100)),Color.cyan);

    }

    /**
     * Calls the entity handler render function
     * @param graphics Graphics reference
     */
    public void render(Graphics graphics){
        entityHandler.render(graphics);
    }

    /**
     * Renders game interface
     * @param graphics Graphics reference
     */
    public void renderInterface(Graphics graphics){

        for (int i = 0; i < playerUIs.length; i++) {
            playerUIs[i].render(graphics);
        }

        //dungeon exiting
        if(!justExitedShop && playerOnExit){

            if(dungeonExitTime == -1) {
                //waiting for other players
                dungeonExitText.text = "Waiting for " + ((PLAYERS.size()-deadPlayers) - numPlayersOnExit) + " players";
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

    /**
     * Updates world state
     * @param dt - delta time
     */
    public void update(float dt){
        TIME += dt;
        float delta = dt/1000;
        //System.out.println("time: " + TIME + " dt: " + dt + " delta " + delta + " FPS: " + gameContainer.getFPS());

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
            }else if(allDead && nextWave < TIME){
                nextWave = 0;
                spawnWave();
            }



        checkExits();
        entityHandler.update(delta);
        entityHandler.updatePhysics(delta);
    }

    /**
     * Creates new wave of enemy objects
     */
    public void spawnWave(){
        waveCount++;
        for (int i = 0; i < waveCount * 2 * PLAYERS.size(); i++) {
            new Enemy(new Vector2f(RANDOM.nextFloat()*360).scale(RANDOM.nextFloat()*(Tile.TILE_SIZE) + 10*Tile.TILE_SIZE),RANDOM.nextInt(3)+RANDOM.nextInt(waveCount)+2, RANDOM.nextBoolean());
        }
    }

    /**
     * Checks whether all players are located on exit
     */
    public void checkExits(){

        for (int i = 0; i < EXITS.size(); i++) {
            numPlayersOnExit = 0;
            for (int j = 0; j < PLAYERS.size(); j++) {
                if(!PLAYERS.get(j).isAlive)
                    continue;

                if (CollisionUtility.checkCollision(PLAYERS.get(j), EXITS.get(i)))
                    numPlayersOnExit++;
            }

            playerOnExit = numPlayersOnExit != 0;


            if(justExitedShop && !playerOnExit)
                    justExitedShop = false;
            else if(numPlayersOnExit != PLAYERS.size() - deadPlayers)
                dungeonExitTime = -1;
            else if(dungeonExitTime == -1)
                dungeonExitTime = TIME + dungeonExitLength;
        }
    }

    /**
     * Creates player object
     */
    public void createPlayer(Vector2f v, Color color, int controllerInput){
        new Player(v, color, controllerInput,gameContainer.getInput());
    		
    }
}
