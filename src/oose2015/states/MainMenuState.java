package oose2015.states;

import oose2015.Assets;
import oose2015.Main;
import oose2015.Settings;
import oose2015.gui.elements.TextBox;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by @Kasper on 26/03/2015
 * <p/>
 * Description:
 * Sets up main menu state.
 * <p/>
 */

public class MainMenuState implements GameState{


    public Color[] colors = {
            new Color(255,116,56),
            new Color(0,155,155),
            new Color(189,122,246),
            new Color(255,105,180),
            new Color(139,69,19),
            new Color(175,175,175)
    };

    public TextBox instructionBox;
    public int colorIndex = 0;
	
    StateBasedGame stateBasedGame;

    int sizeX = Main.SCREEN_WIDTH/4;

    
    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.stateBasedGame = stateBasedGame;

        instructionBox = new TextBox("Press Start to start the game", new Vector2f(Main.SCREEN_WIDTH/2,100), TextBox.Align.CENTER);

        new Assets();

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setColor(Color.white);
        instructionBox.render(graphics);


        for (int j = 0; j < colors.length; j++) {
            int miniX = (sizeX/colors.length);
            graphics.setColor(colors[j]);
            graphics.fillRect((Main.SCREEN_WIDTH/2) - (sizeX/2) + miniX*j, Main.SCREEN_HEIGHT - 70, miniX, 20);
        }
        graphics.setColor(colors[colorIndex]);
        graphics.fillRect((Main.SCREEN_WIDTH/2) - (sizeX/2), Main.SCREEN_HEIGHT - 50, sizeX, 20);


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

    /**
     * Starts game.
     */
    private void startGame(){
        stateBasedGame.enterState(1);
        GamePlayState g = (GamePlayState)stateBasedGame.getState(1);
        g.world.createPlayer(new Vector2f(0,0),colors[colorIndex]);
    }

    /**
     * Change colour of player.
     * @param goLeft
     */
    public void changeColor(boolean goLeft){
        int c = colorIndex + (goLeft ? -1:1);

        if(c < 0)c = colors.length - 1;
        else if(c >= colors.length) c = 0;

        colorIndex = c;
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
    }

    @Override
    public void controllerButtonReleased(int i, int i1) {

    }

    @Override
    public void keyPressed(int i, char c) {
        if(i == Input.KEY_LEFT || i == Settings.LEFT_KEY){
            changeColor(true);
        }else if(i == Input.KEY_RIGHT || i == Settings.RIGHT_KEY){
            changeColor(false);
        }else if(i == Input.KEY_SPACE){
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
