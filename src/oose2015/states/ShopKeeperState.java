package oose2015.states;

import oose2015.Main;
import oose2015.gui.ShopKeeperMenu;
import oose2015.World;
import org.newdawn.slick.*;
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
            playerMenus[i] = new ShopKeeperMenu(new Vector2f(i*sizeX,0),sizeX,i);
        }
    }

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

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int dt) throws SlickException {
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
                allReadyTime = Main.TIME + readyTimeLength;
            }else if(allReadyTime < Main.TIME){
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
        for (int j = 0; j < World.PLAYERS.size(); j++) {
           if(World.PLAYERS.get(j).controllerIndex == i){
               playerMenus[j].handleInput(Button.Up);
           }
        }
    }

    @Override
    public void controllerUpReleased(int i) {

    }

    @Override
    public void controllerDownPressed(int i) {
        for (int j = 0; j < World.PLAYERS.size(); j++) {
            if(World.PLAYERS.get(j).controllerIndex == i){
                playerMenus[j].handleInput(Button.Down);
            }
        }
    }

    @Override
    public void controllerDownReleased(int i) {

    }

    @Override
    public void controllerButtonPressed(int i, int btnIndex) {
        for (int j = 0; j < World.PLAYERS.size(); j++) {
            if(World.PLAYERS.get(j).controllerIndex == i && (btnIndex == 1 || btnIndex == 3)){
                playerMenus[j].handleInput(Button.Select);
            }
        }
    }

    @Override
    public void controllerButtonReleased(int i, int btnIndex) {

    }

    @Override
    public void keyPressed(int key, char c) {

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
