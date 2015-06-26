package oose2015;

import oose2015.input.ControllerScheme;

import java.util.ArrayList;

/**
 * Settings class holds variable values for other class variables
 * @author itai.yavin
 *
 */

public class Settings {

	public static final String PATH = "settings.txt";

	//input
	public static final int NUM_ACTIONS = 11;
	public static final boolean DEBUG_CONTROLLER = false;
	//Player Variables
		//general
	    public static final float 	PLAYER_HEALTH = 100;
	    public static final float 	PLAYER_SPEED_FORCE = 30000f;
	    public static final float 	PLAYER_MAX_VELOCITY = 500f;
	    public static final float 	PLAYER_MASS = 1f;
	public static final int ATTACK_BUTTON = 6,
			LEFT_STICK_X = 1,
			LEFT_STICK_Y = 0,
			RIGHT_STICK_X = 3,
			RIGHT_STICK_Y = 2;
	/*L1 = 5
		R1 = 6
        Triangle = 4
        Cross = 1
        Circle = 2
        Square = 3
        Left stick = 9
        Right stick = 10
        Select = 7
        Start = 8*/
	//deadzones
	public static final float LEFT_DEAD_X = 0.1f,
			LEFT_DEAD_Y = 0.1f,
			RIGHT_DEAD_X = 0.6f,
			RIGHT_DEAD_Y = 0.6f;

		//controls
		//player bowdraw Variables
		public static final float 	MIN_DRAW_SPEED = 100,
									MAX_DRAW_SPEED = 1500;
		public static final float 	MAX_DRAW_GRAPHIC_SIZE = 10;
		public static final float   SWEETSPOT = 1337;
		public static final float   SWEETSPOT_RANGE = 100;
		//melee variables
		public static final float 	PLAYER_ARC_START = 50,
				 				  	PLAYER_ARC_END = -50;
		//Enemy Variables
		public static final float 	ENEMY_DAMAGE = 1f,
									ENEMY_HEALTH = 10;
		public static final int 	ENEMY_GOLD_DROP_PER_LVL = 5,
									ENEMY_EXP_DROP_PER_LVL = 10;
		public static final float 	ENEMY_MASS_PER_LVL = .05f;
		public static final float 	ENEMY_SPEED_FORCE = 20000f,
									ENEMY_MAX_VELOCITY = 400f;
		public static final float 	ENEMY_ENGAGE_RADIUS = 200f,
									ENEMY_DISENGAGE_RADIUS = 500f,
									ENEMY_MELEE_RADIUS = 20f,
									ENEMY_RANGED_RADIUS = 200f,
									ENEMY_ATTACK_DELAY = 800;
		public static final float 	CHARGE_DELAY = 3000;
		public static final float 	MIN_CHARGE_DISTANCE = 50;
		public static final float 	MAX_CHARGE_DISTANCE = 200;
		public static final float	CHARGE_TIME = 200;
		public static final int		CHARGE_SCALAR = 2;
		//Projectile Variables
		public static final float 	PROJECTILE_SPEED_FORCE = 30000f; //Base speed of projectiles (before drawtime modifier), this is located in the player script
		public static final float 	PROJECTILE_MAX_SPEED = 1000f;
		public static final float 	PROJECTILE_FLY_TIME = 2000;
		public static final float 	PROJECTILE_FRICTION = .9f,
									PROJECTILE_INERTIA = .85f;
	public static ArrayList<ControllerScheme> CONTROLLER_SCHEMES = new ArrayList<ControllerScheme>();
		//Shake Variables




}
