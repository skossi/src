package com.blokk.game;
import states.Playstate;


import com.badlogic.gdx.graphics.Texture;

/**
 * @author     Ottar og �orsteinn. Edit by Hlynur
 * @version     1.0a                 Alpha
 * @since       2014-10-10        
 */
public class Movable {
	public Boolean typeOne;
	public boolean typeTwo;
	public int col;
	public int row;
	public Texture type;
	public float speed;
	public float x;
	public float y;
	public float width;
	public float height;
	public boolean isMovable;
	public long timeThrusted;
	public long timeBlacked;
	//private long blinkInterval=1000;
	public boolean isBeingThrusted;
	public boolean isPower;
	public String power;
	public boolean typePowerOne;
	public boolean typePowerTwo;
	
	/**	
	*Constructs ablock, i.e. Movable
	* @param isMovable A boolean which decides if the block is movable by the user
	*/
	public Movable(boolean isMovable) {
		timeBlacked = Long.MAX_VALUE;
		if (isMovable) {
			typeOne = randomizeType();
			typeTwo = randomizeType();
		}
		else {
			typeOne = null;
			typeTwo = false;
		}
	}
	
	/**
	*Constructs a block, i.e. Movable 
	* @param Movable m A Movable block which is cloned 
	*/
	public Movable(Movable m) {
		typeOne = m.typeOne;
		typeTwo = m.typeTwo;
		col = m.col;
		row = m.row;
		type = m.type;
		speed = m.speed;
		x = m.x;
		y = m.y;
		width = m.width;
		height = m.height;
		isMovable = m.isMovable;
		timeThrusted = m.timeThrusted;
		isBeingThrusted = m.isBeingThrusted;
		timeBlacked = Long.MAX_VALUE;
		isPower = m.isPower;
		power = m.power;
	}
	
	/**
 	*Takes the delta time so we can update the entity to correspond to the input of the gameloop
 	* @param dy is the delta time of each frame rendered
 	*/
	public void update(float dy) {	
		/*
		if (Playstate.isPaused)
		{
			this.timeThrusted+=3*dy;
			this.timeBlacked+=3*dy;
		}*/
		if(System.currentTimeMillis() - timeThrusted > 2000 && isBeingThrusted){
			speed = -200;
			//breytum t��pum svo ��etta fari ekki a�� haga s��r illa
			//semsagt skj��tast upp aftur og aftur
//			typeTwo = randomizeType();
			
		}
			
		y += speed*dy;
		
		return;
	}
	
	
	//This function checks whether a movable was unmovable and whether its time
	//for it to become movable
	public boolean movableCheck() {
		//the upper if statements magic number is meant to be 3000 lower
		//than the one in the lower if statement
		if(System.currentTimeMillis() - timeBlacked > 12000*Playstate.difficulty)
		{
			//if(System.currentTimeMillis%blinkInterval<blinkInterval/2)
			if(System.currentTimeMillis()%1000<500)
				//this.blinkInterval*=0.75;
				this.typeTwo=true;
			else
				this.typeTwo=false;
		}
		if(System.currentTimeMillis() - timeBlacked > 15000*Playstate.difficulty){
			timeBlacked = Long.MAX_VALUE;
			return true;
		}
		return false;
	}
	
	public void setType (Boolean type1, boolean type2) {
		typeOne = type1;
		typeTwo = type2;
		
		return;
	}
	
	public void setInvertType(Boolean type1, boolean type2) {
		if (type1 == null) {
			typeOne = randomizeType();
			typeTwo = randomizeType();
		}
		else if (Math.random() < 0.5) {
			typeOne = !type1;
			typeTwo = randomizeType();
		}
		else {
			typeTwo = !type2;
			typeOne = randomizeType();
		}
	}
	
	/**
 	*  Returns true if a block is intersecting with another Movable block or the ground
 	* @param Movable m is the Movable block being checked for collision
 	*/
	public boolean intersects(Movable m) {
		if (x < (m.x + m.width) && (x + width) > m.x && (y + height) > m.y && y < (m.y + m.height)) return true;
		return false;
	}
	
	/**
 	*  A function for randoming the types of the Movable blocks, each being a combination of two booleans
 	*/
	public boolean randomizeType() {
		boolean type = false;
		
		if (Math.random() < 0.5) type = true;
		
		return type;
	}
}