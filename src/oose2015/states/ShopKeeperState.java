package oose2015.states;

import oose2015.Main;
import oose2015.World;
import oose2015.gui.ShopKeeperMenu;
import oose2015.input.Action;
import oose2015.input.InputHandler;
import oose2015.input.InputWrapper;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;

/**
 * Created by @Kasper on 22/04/2015
 * <p/>
 * Description:
 * Sets up shop menu state.
 * <p/>
 */

public class ShopKeeperState extends CustomGameState {

    public int allReadyTime = -1;
    public int readyTimeLength = 1000;

    StateBasedGame stateBasedGame;
    
    ShopKeeperMenu[] playerMenus;

    ArrayList<InputWrapper> inputWrappers;

    @Override
    public int getID() {
        return 2;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.stateBasedGame = stateBasedGame;

    }

    /**
     * Render menu graphics.
     */
    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setColor(Color.white);
        if(allReadyTime == -1){
            graphics.drawString("Everyone needs to be ready",200,10);
        }else{
            if(allReadyTime < Main.TIME){
                graphics.drawString("Everyone is ready",200,10);
            }else{

                String time = "" + ((float)(allReadyTime - Main.TIME)/1000);
                graphics.drawString("Entering battle in " + time.substring(0,3) + " seconds",200,10);
            }
        }

        for (int i = 0; i < playerMenus.length; i++) {
            playerMenus[i].render(graphics);
        }
    }

    /**
     * Update menu shop state.
     */
    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int dt) throws SlickException {
        boolean allReady = true;

        for (int i = 0; i < playerMenus.length; i++) {
            if (inputWrappers.get(i).getActionDown(Action.Up))
                playerMenus[i].handleInput(ShopKeeperMenu.input.Up);
            if (inputWrappers.get(i).getActionDown(Action.Down))
                playerMenus[i].handleInput(ShopKeeperMenu.input.Down);
            if (inputWrappers.get(i).getActionDown(Action.Select))
                playerMenus[i].handleInput(ShopKeeperMenu.input.Select);

            playerMenus[i].update();
            if(!playerMenus[i].isReady)
                allReady = false;
        }

        if(!allReady)
            allReadyTime = -1;
        else{
            if(allReadyTime == -1){
                allReadyTime = Main.TIME + readyTimeLength;
            }else if(allReadyTime < Main.TIME){
                stateBasedGame.enterState(1);
            }
        }
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        inputWrappers = InputHandler.getArrayList();
        createMenu();
    }

    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }


    /**
     * Creates shop menu
     */
    public void createMenu() {
        playerMenus = new ShopKeeperMenu[World.PLAYERS.size()];
        int sizeX = Main.SCREEN_WIDTH / 4;
        for (int i = 0; i < playerMenus.length; i++) {
            playerMenus[i] = new ShopKeeperMenu(new Vector2f(i * sizeX, 0), sizeX, i);
        }
    }

}
