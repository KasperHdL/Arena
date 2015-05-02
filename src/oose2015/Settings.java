package oose2015;

import oose2015.entities.agents.Agent;

import org.newdawn.slick.geom.Vector2f;

public class Settings {

	//Player Variables
		//general
	    public static final float PLAYER_HEALTH = 100;
	
	    public static final float PLAYER_MAXVELOCITY = 15f;
	
	    public static final float PLAYER_SPEEDFORCE = 6f;
	    public static final float PLAYER_MASS = 1f;
	
		//controls
	    
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
	
	    public static final int	ATTACKBUTTON = 5,
							    RANGEDBUTTON = 6,
							    LEFTSTICKX = 1,
							    LEFTSTICKY = 0,
								RIGHTSTICKX = 3,
								RIGHTSTICKY = 2;
	    
	    //deadzones
	    public static final float 	LEFTDEADX = 0.1f,
				    				LEFTDEADY = 0.1f,
				    				RIGHTDEADX = 0.6f,
				    				RIGHTDEADY = 0.6f;

	
		//player bowdraw Variables
		public static final float MINDRAWSPEED = 700;
	
		public static final float MAXDRAWSPEED = 1500;
	
		public static final float MAXDRAWGRAPHICSIZE = 10;
	
		public static final float DRAWGRAPHIC = 0;
	
		//melee variables
		public static final float PLAYER_STARTARC = -20,
				 				  PLAYER_ENDARC = 80;
		
	//Enemy Variables
		public static final float ENEMY_DAMAGE = 1f;
	    public static final float ENEMY_HEALTH = 10;
		
		public static final int ENEMY_GOLDDROPPERLVL = 5;
		public static final int ENEMY_EXPDROPPERLVL = 10;
		public static final float ENEMY_MASSPERLVL = 1;
		public static final float ENEMY_SPEEDFORCE = 8f;
		public static final float ENEMY_MAXVELOCITY = 12f;
		
		public static final float ENEMY_ENGAGERADIUS = 200f;
		public static final float ENEMY_DISENGAGERADIUS = 500f;
		public static final float ENEMY_MELEERADIUS = 20f;
		public static final float ENEMY_RANGEDRADIUS = 200f;

		public static final float attackDelay = 200f;
		
	//Projectile Variables
		public static final float BASEPROJECTILESPEED = 10; //Base speed of projectiles (before drawtime modifier), this is located in the player script
		
		public static final float PROJECTILE_FLYTIME = 1500;
	
		public static final float PROJECTILE_FRICTION = 0.99f;
		public static final float PROJECTILE_INERTIA = 0.999f;
	
}
