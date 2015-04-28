package oose2015.states;

import oose2015.EntityHandler;
import oose2015.Main;
import oose2015.World;
import oose2015.entities.Player;
import org.newdawn.slick.*;
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

public class GamePlayState implements GameState {

    StateBasedGame stateBasedGame;

    public World world;

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

        //Super simple camera for single player -- TEMPORARY @hack
        graphics.pushTransform();
        Vector2f p = World.PLAYERS.get(0).position;
        Vector2f a = World.PLAYERS.get(0).acceleration.copy();
        a.scale(2);
        graphics.translate(-p.x + a.x + Main.SCREEN_WIDTH/2,-p.y + a.y + Main.SCREEN_HEIGHT/2);
        world.render(graphics);
        graphics.popTransform();
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int dt) throws SlickException {
        world.update(dt);
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
    public void controllerButtonPressed(int i, int i1) {

    }

    @Override
    public void controllerButtonReleased(int i, int i1) {

    }

    @Override
    public void keyPressed(int i, char c) {
        if(i == Input.KEY_D){
            //toggle debug mode
            World.DEBUG_MODE = !World.DEBUG_MODE;
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
