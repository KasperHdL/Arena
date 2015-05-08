package oose2015.states;

import oose2015.Assets;
import oose2015.Main;
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
	public int[] controllerIndex = {-1,-1,-1,-1};
    public int[] playerColors = {-1,-1,-1,-1};

    public Color[] colors = {
            new Color(255,116,56),
            new Color(0,155,155),
            new Color(189,122,246),
            new Color(255,105,180),
            new Color(139,69,19),
            new Color(125,125,125)
    };

    public TextBox instructionBox;
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

        instructionBox = new TextBox("Press Select to start the game", new Vector2f(Main.SCREEN_WIDTH/2,100), TextBox.Align.CENTER);
        for (int i = 0; i < controllerBox.length; i++) {
            controllerBox[i] = new TextBox("Press Start on the Controller", new Vector2f(sizeX * i + sizeX/2,Main.SCREEN_HEIGHT-20), TextBox.Align.CENTER);
            controllerBox[i].blinkTextLength = 1500;
        }
        
        new Assets();



    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setColor(Color.white);
        instructionBox.render(graphics);

        for (int i = 0; i < controllerIndex.length; i++) {
            if(controllerIndex[i] != -1) {
                for (int j = 0; j < colors.length; j++) {
                    int miniX = (sizeX/colors.length);
                    graphics.setColor(colors[j]);
                    graphics.fillRect(sizeX * i + miniX*j, Main.SCREEN_HEIGHT - 70, miniX, 20);
                }
                graphics.setColor(colors[playerColors[i]]);
                graphics.fillRect(sizeX * i, Main.SCREEN_HEIGHT - 50, sizeX, 20);
            }
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

    /**
     * Starts game.
     */
    private void startGame(){
        stateBasedGame.enterState(1);
        for(int j = 0; j < controllerIndex.length; j++){
            if(controllerIndex[j] != -1){
                GamePlayState g = (GamePlayState)stateBasedGame.getState(1);
                g.world.createPlayer(new Vector2f(Main.SCREEN_WIDTH/2,Main.SCREEN_HEIGHT/2),colors[playerColors[j]],controllerIndex[j]);
            }
        }
    }

    /**
     * Change colour of player.
     * @param index - playerColour array index
     * @param goLeft
     */
    public void changeColor(int index, boolean goLeft){
        boolean foundColor = false;
        int c = playerColors[index] + (goLeft ? -1:1);
        while(!foundColor){
            if(c < 0)c = colors.length - 1;
            else if(c >= colors.length) c = 0;

            boolean otherPlayerHasColor = false;

            for (int i = 0; i < playerColors.length; i++) {
                if(c == playerColors[i]){
                    c += (goLeft ? -1:1);
                    otherPlayerHasColor = true;
                    break;
                }
            }
            if(!otherPlayerHasColor){
                foundColor = true;
            }

        }
        playerColors[index] = c;
    }
    
    @Override
    public void controllerLeftPressed(int i) {
        for (int j = 0; j < controllerIndex.length; j++) {
            if(i == controllerIndex[j]){
                changeColor(j, true);
            }
        }
    }

    @Override
    public void controllerLeftReleased(int i) {

    }

    @Override
    public void controllerRightPressed(int i) {
        for (int j = 0; j < controllerIndex.length; j++) {
            if(i == controllerIndex[j]){
                changeColor(j, false);
            }
        }
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

    /**
     * Upon controller button press will:
     * if already active: set controller as inactive
     * if inactive: set controller as active.
     */
    @Override
    public void controllerButtonPressed(int conIndex, int btnIndex) {
        //System.out.println("con: " + conIndex + ", btn: " + btnIndex);

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

    /**
     * Adds controller and saves its index.
     * @param conIndex - Controller index.
     */
    public void addController(int conIndex){
        int emptyIndex = -1;
        for(int i = controllerIndex.length - 1; i >= 0; i--){
            if(controllerIndex[i] == -1){
                emptyIndex = i;
            } else if(controllerIndex[i] == conIndex) {
                controllerIndex[i] = -1;
                emptyIndex = -1;
                playerColors[i] = -1;
                controllerBox[i].blinkText("Player " + (i+1) + " is disconnected", Color.red);
                controllerBox[i].text = "Press Start on the Controller";
                break;
            }
        }
        if(emptyIndex != -1){
            controllerIndex[emptyIndex] = conIndex;
            controllerBox[emptyIndex].stopBlinkText();
            controllerBox[emptyIndex].text = "Player " + (emptyIndex + 1) + " is connected";
            changeColor(emptyIndex,false);
        }
    }

    @Override
    public void controllerButtonReleased(int i, int i1) {

    }

    @Override
    public void keyPressed(int i, char c) {
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
