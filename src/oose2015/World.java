package oose2015;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

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
    GameContainer gameContainer;
    StateBasedGame stateBasedGame;

    EntityHandler entityHandler;

    public static int time = 0;

    public World(GameContainer gameContainer, StateBasedGame stateBasedGame){
        this.gameContainer = gameContainer;
        this.stateBasedGame = stateBasedGame;

        entityHandler = new EntityHandler(gameContainer.getInput());
    }

    public void update(float dt){
        time += dt;
        float delta = dt/100;
        //System.out.println("time: " + time + " dt: " + dt + " delta " + delta);

        entityHandler.update(delta);
        entityHandler.updatePhysics(delta);
    }

    public void render(Graphics graphics){
        entityHandler.render(graphics);
    }
}
