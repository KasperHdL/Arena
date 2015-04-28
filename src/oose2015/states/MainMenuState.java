package oose2015.states;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import oose2015.Main;
import oose2015.World;
import oose2015.entities.Player;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

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
	
    StateBasedGame stateBasedGame;
    

    
    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.stateBasedGame = stateBasedGame;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawString("Press Space to Play",10,100);
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
    public void controllerButtonPressed(int controllerIn, int buttonIn) {
    	//start == 8

    	if(buttonIn == 8){
    		int emptyIndex = -1;
    		for(int i = 0; i < controllerIndex.length; i++){
    			if(controllerIndex[i] == -1){
    				emptyIndex = i;
    			} else if(controllerIndex[i] == controllerIn) {
    				controllerIndex[i] = -1;
    				emptyIndex = -1;
        			System.out.println("Controller " + controllerIn + " is disconnected");
    				break;
    			}
    		}
    		if(emptyIndex != -1){
    			controllerIndex[emptyIndex] = controllerIn;
    			System.out.println("Controller " + controllerIn + " is connected");
    		}
    		/*for(int x = 0; x < controllerIndex.length; x++){
    			System.out.println("ControllerIndex: " + controllerIndex[x]);
    		}*/
    	}
    }

    @Override
    public void controllerButtonReleased(int i, int i1) {

    }

    @Override
    public void keyPressed(int i, char c) {
        if(i == Input.KEY_SPACE){
            stateBasedGame.enterState(1);
            for(int j = 0; j < controllerIndex.length; j++){
        		if(controllerIndex[j] != -1){
        			GamePlayState g = (GamePlayState)stateBasedGame.getState(1);
        			g.world.createPlayer(new Vector2f(Main.SCREEN_WIDTH/2,Main.SCREEN_HEIGHT/2), controllerIndex[j]);
        		}
        	}
            
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
