package oose2015.states;

import oose2015.Main;
import oose2015.gui.ShopKeeperMenu;
import oose2015.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by @Kasper on 22/04/2015
 * <p/>
 * Description:
 * ---
 * <p/>
 * Usage:
 * ---
 */

public class ShopKeeperState implements GameState {

    public static int TIME = 0;

    public int allReadyTime = -1;
    public int readyTimeLength = 1000;

    StateBasedGame stateBasedGame;
    
    ShopKeeperMenu[] playerMenus;

    //TEMPORARY prolly
    public enum Button{
        Up,
        Down,
        Select
    }


    @Override
    public int getID() {
        return 2;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.stateBasedGame = stateBasedGame;

    }

    public void createMenu(){
        playerMenus = new ShopKeeperMenu[World.PLAYERS.size()];
        int sizeX = Main.SCREEN_WIDTH/4;
        for (int i = 0; i < playerMenus.length; i++) {
            playerMenus[i] = new ShopKeeperMenu(new Vector2f(i*sizeX,0),sizeX);
        }
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {

        if(allReadyTime == -1){
            graphics.drawString("Everyone needs to be ready",200,10);
        }else{
            if(allReadyTime < TIME){
                graphics.drawString("Everyone is ready",200,10);
            }else{
                graphics.drawString("Entering battle in " + ((allReadyTime - TIME)/1000),200,10);
            }
        }

        for (int i = 0; i < playerMenus.length; i++) {
            playerMenus[i].render(graphics);
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int dt) throws SlickException {
        TIME += dt;
        boolean allReady = true;
        for (int i = 0; i < playerMenus.length; i++) {
            playerMenus[i].update();
            if(!playerMenus[i].isReady)
                allReady = false;
        }

        if(!allReady)
            allReadyTime = -1;
        else{
            if(allReadyTime == -1){
                allReadyTime = TIME + readyTimeLength;
            }else if(allReadyTime < TIME){
                stateBasedGame.enterState(1);
            }
        }
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        createMenu();
    }

    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void controllerLeftPressed(int i) {

    }

    @Override
    public void controllerLeftReleased(int i) {

    }

    @Override
    public void controllerRightPressed(int i) {

    }

    @Override
    public void controllerRightReleased(int i) {

    }

    @Override
    public void controllerUpPressed(int i) {

    }

    @Override
    public void controllerUpReleased(int i) {

    }

    @Override
    public void controllerDownPressed(int i) {

    }

    @Override
    public void controllerDownReleased(int i) {

    }

    @Override
    public void controllerButtonPressed(int i, int i1) {

    }

    @Override
    public void controllerButtonReleased(int i, int i1) {

    }

    @Override
    public void keyPressed(int key, char c) {
        if(Input.KEY_UP == key){
            playerMenus[0].handleInput(Button.Up);
        }else if(Input.KEY_DOWN == key){
            playerMenus[0].handleInput(Button.Down);
        }else if(Input.KEY_RIGHT == key){
            playerMenus[0].handleInput(Button.Select);
        }

        else if(Input.KEY_W == key){
            playerMenus[1].handleInput(Button.Up);
        }else if(Input.KEY_S == key){
            playerMenus[1].handleInput(Button.Down);
        }else if(Input.KEY_D == key){
            playerMenus[1].handleInput(Button.Select);
        }
    }

    @Override
    public void keyReleased(int i, char c) {

    }

    @Override
    public void mouseWheelMoved(int i) {

    }

    @Override
    public void mouseClicked(int i, int i1, int i2, int i3) {

    }

    @Override
    public void mousePressed(int i, int i1, int i2) {

    }

    @Override
    public void mouseReleased(int i, int i1, int i2) {

    }

    @Override
    public void mouseMoved(int i, int i1, int i2, int i3) {

    }

    @Override
    public void mouseDragged(int i, int i1, int i2, int i3) {

    }

    @Override
    public void setInput(Input input) {

    }

    @Override
    public boolean isAcceptingInput() {
        return true;
    }

    @Override
    public void inputEnded() {

    }

    @Override
    public void inputStarted() {

    }
}
