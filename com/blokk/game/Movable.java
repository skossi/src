package com.blokk.game;

import states.Playstate;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

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
	public boolean isBeingThrusted;
	public boolean isPower;
	public String power;
	
	/**	
	*Constructs ablock, i.e. Movable
	* @param isMovable A boolean which decides if the block is movable by the user
	*/
	public Movable(boolean isMovable) {
		timeBlacked = Long.MAX_VALUE;
		if (isMovable) {
			typeOne = false;
			typeTwo = true;
		}
		else {
			typeOne = null;
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
		
		if(System.currentTimeMillis() - timeThrusted > 2000 && isBeingThrusted){
			isBeingThrusted = false;
			speed = -600;
			//breytum týpum svo þetta fari ekki að haga sér illa
			//semsagt skjótast upp aftur og aftur
//			typeTwo = randomizeType();
			
		}
			
		y += speed*dy;
		
		return;
	}
	//This function checks whether a movable was unmovable and whether its time
	//for it to become movable
	public boolean movableCheck() {
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
	
	/**
 	* A function for randomizing the column where the Movable block should be spawned
 	*/
	private int randomizeSlot() {
		return MathUtils.random(0, 6);
	}
}