package oose2015;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 * Assets class loads assets.
 * @author itai.yavin
 *
 */
public class Assets {
    public static Sound SOUND_BOW_DRAW;
    public static Sound SOUND_ARROW_SHOOT;
	
	public Assets(){
        //load sound clips
        String user_dir = System.getProperty("user.dir");
        String sound_dir = user_dir + "\\Assets\\Sounds\\";
        
	    try {	
	    	SOUND_BOW_DRAW 		= new Sound(sound_dir + "BowDrawCreak.wav");
	    	SOUND_ARROW_SHOOT 	= new Sound(sound_dir + "ArrowShootSound.wav"); 	
		} catch (SlickException e) {
			System.out.println("Error with file path");
		}
	}
}
