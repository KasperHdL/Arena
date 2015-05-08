package oose2015;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.opengl.Texture;

/**
 * Assets class loads assets.
 * @author itai.yavin
 *
 */
public class Assets {
    public static Sound SOUND_BOW_DRAW;
    public static Sound SOUND_ARROW_SHOOT;
    public static Sound SOUND_WEAPON_SWING;
    public static Sound SOUND_KILL;
	
	public Assets(){
        //load sound clips
        String sound_dir = "assets\\sounds\\";
        
	    try {	
	    	SOUND_BOW_DRAW 		= new Sound(sound_dir + "BowDrawCreak.wav");
	    	SOUND_ARROW_SHOOT 	= new Sound(sound_dir + "ArrowShootSound.wav");
	    	SOUND_WEAPON_SWING	= new Sound(sound_dir + "WeaponSwing.wav");
	    	SOUND_KILL			= new Sound(sound_dir + "KillSound.wav");
		} catch (SlickException e) {
			System.out.println("Error with file path");
		}

	}
}
