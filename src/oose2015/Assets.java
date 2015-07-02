package oose2015;

import org.newdawn.slick.PackedSpriteSheet;
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
    public static Sound SOUND_WEAPON_SWING;
    public static Sound SOUND_KILL;
    
    public static Sound SOUND_FIRST_BOSS_ROAR;

	public static PackedSpriteSheet SPRITE_SHEET;
	
	public Assets(){
		loadSpriteSheet();
		loadSounds();
	}

	private void loadSpriteSheet() {
		try {
			SPRITE_SHEET = new PackedSpriteSheet("assets\\spritesheet.def");
		} catch (SlickException e) {
			System.out.println("Error in file path when loading spritesheet");
		}
	}

	private void loadSounds() {
		String sound_dir = "assets\\sounds\\";

		try {
			SOUND_BOW_DRAW = new Sound(sound_dir + "BowDrawCreak.wav");
			SOUND_ARROW_SHOOT = new Sound(sound_dir + "ArrowShootSound.wav");
			SOUND_WEAPON_SWING = new Sound(sound_dir + "WeaponSwing.wav");
			SOUND_KILL = new Sound(sound_dir + "KillSound.wav");

			//Boss Sounds
			SOUND_FIRST_BOSS_ROAR = new Sound(sound_dir + "FirstBossRoar.wav");
		} catch (SlickException e) {
			System.out.println("Error in file path when loading sound");
		}
	}
}
