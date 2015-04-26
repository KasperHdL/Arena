package oose2015;
import java.util.logging.Level;
import java.util.logging.Logger;

import oose2015.states.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame
{

	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 720;


	public Main(String gamename){
		super(gamename);
	}

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
		gameContainer.setTargetFrameRate(120);
		addState(new ShopKeeperState());
		addState(new MainMenuState());
		addState(new GamePlayState());

    }

	public static void main(String[] args)
	{
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Main("Simple Slick Game"));
			appgc.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	protected void preUpdateState(GameContainer container, int delta){

	}
}