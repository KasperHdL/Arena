package oose2015.states;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import oose2015.Main;
import oose2015.World;
import oose2015.entities.Player;

import oose2015.gui.elements.TextBox;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import javax.xml.soap.Text;

/**
 * Created by @Kasper on 26/03/2015
 * <p/>
 * Description:
 * ---
 * <p/>
 * Usage:
 * ---
 */

public class MainMenuState implements GameState{
	public int[] controllerIndex = new int[] {-1,-1,-1,-1};
    public TextBox[] controllerBox;
	
    StateBasedGame stateBasedGame;

    int sizeX = Main.SCREEN_WIDTH/4;

    
    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.stateBasedGame = stateBasedGame;

        controllerBox = new TextBox[4];
        for (int i = 0; i < controllerBox.length; i++) {
            controllerBox[i] = new TextBox("Press Start on the Controller", new Vector2f(sizeX * i + sizeX/2,Main.SCREEN_HEIGHT-20), TextBox.Align.CENTER);
            controllerBox[i].blinkTextLength = 1500;
        }

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setColor(Color.white);
        graphics.drawString("Press Select to start the game",10,100);

        for (int i = 0; i < controllerIndex.length; i++) {
            controllerBox[i].render(graphics);
        }

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int dt) throws SlickException {

    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    }

    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    	
    }

    private void startGame(){
        stateBasedGame.enterState(1);
        for(int j = 0; j < controllerIndex.length; j++){
            if(controllerIndex[j] != -1){
                GamePlayState g = (GamePlayState)stateBasedGame.getState(1);
                g.world.createPlayer(new Vector2f(Main.SCREEN_WIDTH/2,Main.SCREEN_HEIGHT/2), controllerIndex[j]);
            }
        }
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
    public void controllerButtonPressed(int conIndex, int btnIndex) {
        System.out.println("con: " + conIndex + ", btn: " + btnIndex);

        //select
        if(btnIndex == 7){
            boolean noControllers = true;
            for(int i = controllerIndex.length - 1; i >= 0; i--)
                if(controllerIndex[i] != -1){
                    noControllers = false;
                    break;
                }
            if(noControllers)
                addController(conIndex);
            startGame();
        }

        //start == 8
        if(btnIndex == 8){
            addController(conIndex);
    	}
    }

    public void addController(int conIndex){
        int emptyIndex = -1;
        for(int i = controllerIndex.length - 1; i >= 0; i--){
            if(controllerIndex[i] == -1){
                emptyIndex = i;
            } else if(controllerIndex[i] == conIndex) {
                controllerIndex[i] = -1;
                emptyIndex = -1;
                controllerBox[i].blinkText("Player " + (i+1) + " is disconnected", Color.red);
                controllerBox[i].text = "Press Start on the Controller";
                break;
            }
        }
        if(emptyIndex != -1){
            controllerIndex[emptyIndex] = conIndex;
            controllerBox[emptyIndex].text = "Player " + (emptyIndex + 1) + " is connected";
        }
    }

    @Override
    public void controllerButtonReleased(int i, int i1) {

    }

    @Override
    public void keyPressed(int i, char c) {
        if(i == Input.KEY_SPACE){
            System.out.println("You really should not start the game this way ... \nhope you connected a controller!!");
            startGame();
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
