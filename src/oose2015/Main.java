package oose2015;

import oose2015.input.InputHandler;
import oose2015.states.*;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main
 * @author itai.yavin
 *
 */
public class Main extends StateBasedGame
{
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 720;
	public static int TIME = 0;


	public Main(String gamename){
		super(gamename);
	}

	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Main("Simple Slick Game"));
			appgc.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
		gameContainer.setTargetFrameRate(60);
		gameContainer.setSmoothDeltas(true);
		addState(new MainMenuState());
		addState(new GamePlayState());
		addState(new ShopKeeperState());
		addState(new OptionState());
		addState(new CalibrationState());

    }

	@Override
	protected void preUpdateState(GameContainer container, int delta){
		TIME += delta;
		InputHandler.update();
	}
}