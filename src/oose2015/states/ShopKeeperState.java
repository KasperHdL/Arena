package oose2015.states;

import oose2015.Main;
import oose2015.Settings;
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
 * Sets up shop menu state.
 * <p/>
 */

public class ShopKeeperState implements GameState {

    public int allReadyTime = -1;
    public int readyTimeLength = 1000;

    StateBasedGame stateBasedGame;
    
    ShopKeeperMenu playerMenu;

    //TEMPORARY probably
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

    /**
     * Creates shop menu
     */
    public void createMenu(){
        int sizeX = Main.SCREEN_WIDTH/4;

        playerMenu = new ShopKeeperMenu(new Vector2f(Main.SCREEN_WIDTH/2 - sizeX/2,0),sizeX);

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

            playerMenu.render(graphics);
    }

    /**
     * Update menu shop state.
     */
    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int dt) throws SlickException {
        boolean allReady = true;

        playerMenu.update();

        if(!playerMenu.isReady)
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
    public void controllerButtonPressed(int i, int btnIndex) {
    }

    @Override
    public void controllerButtonReleased(int i, int btnIndex) {

    }

    @Override
    public void keyPressed(int key, char c) {
        if(key == Settings.UP_KEY)
            playerMenu.handleInput(Button.Up);
        else if(key == Settings.DOWN_KEY)
            playerMenu.handleInput(Button.Down);
        else if(key == Input.KEY_SPACE)
            playerMenu.handleInput(Button.Select);
    }

    @Override
    public void keyReleased(int i, char c) {

    }

    @Override
    public void mouseWheelMoved(int i) {

    }

    @Override
    public void mouseClicked(int i, int i1, int i2, int i3) {
        playerMenu.handleInput(Button.Select);
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
