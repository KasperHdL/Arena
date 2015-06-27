package oose2015.states;

import oose2015.World;
import oose2015.input.InputHandler;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by @Kasper on 26/03/2015
 * <p/>
 * Description:
 * Sets up gameplay state.
 * <p/>
 */

public class GamePlayState extends CustomGameState {

    public World world;
    StateBasedGame stateBasedGame;

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.stateBasedGame = stateBasedGame;
        world = new World(gameContainer, stateBasedGame);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {

        world.render(graphics);
        world.renderInterface(graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int dt) throws SlickException {
        world.update(dt);

        if (InputHandler.getKeyDown(Input.KEY_D)) {
            //toggle debug mode
            World.DEBUG_MODE = !World.DEBUG_MODE;
        }
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        for (int i = 0; i < World.PLAYERS.size(); i++)
            World.PLAYERS.get(i).playerUI.updateGold();
    }

    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }


}
