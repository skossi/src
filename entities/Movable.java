package entities;

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
	public long timeShuffled1;
	public long timeShuffled2;
	public long timeShuffled3;
	public long delayBlacked;
	public boolean isBeingThrusted;
	public boolean isPowerDown;
	public boolean isBeingSwapped;
	public boolean justSpawned;
	public int ID;
	public double gravity = 0.1;
	
	public boolean spawnParticles;
	public ParticleEmitter particleEmit;
	
	/**	
	*Constructs ablock, i.e. Movable
	* @param isMovable A boolean which decides if the block is movable by the user
	*/
	public Movable(boolean isMovable) {
		timeBlacked = Long.MAX_VALUE;
		if (isMovable) {
			typeOne = randomizeType();//false;
			typeTwo = randomizeType();//true;
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
		ID = m.ID;
		width = m.width;
		height = m.height;
		isMovable = m.isMovable;
		justSpawned = m.justSpawned;
		timeThrusted = m.timeThrusted;
		timeShuffled1 = m.timeShuffled1;
		timeShuffled2 = m.timeShuffled2;
		timeShuffled3 = m.timeShuffled3;
		delayBlacked = m.delayBlacked;
		isBeingThrusted = m.isBeingThrusted;
		timeBlacked = Long.MAX_VALUE;
		isPowerDown = m.isPowerDown;
	}
	
	/**
 	*Takes the delta time so we can update the entity to correspond to the input of the gameloop
 	* @param dy is the delta time of each frame rendered
 	*/
	public void update(float dy) {	
		
		// UNCOMMENT THIS TO REMOVE GRAVITY
//		if(System.currentTimeMillis() - timeThrusted > 3500 && isBeingThrusted){
//			isBeingThrusted = false;
//			speed = -600;
//		}
		
		// no need to update the invisible bottom row or static blocks
		if (row == 0) return;
		
		
		if (speed < 0 && isBeingThrusted) isBeingThrusted = false;
		if (speed != 0) speed -= gravity;
		
		
<<<<<<< HEAD
		if (!justSpawned) {
			if (speed < 0) 
			{
				isBeingThrusted = false;
			}
			if (speed != 0) speed -= gravity;
		}
=======
>>>>>>> 649c8bf0bfc3f1888a7610fa2a9fcddfb444fffd
			
		y += speed*dy;
		
		if(spawnParticles)
		{
			particleEmit.x = (int)x;
			particleEmit.y = (int)y;
			particleEmit.update(dy,speed);
		}
		
			
		
		return;  //Why is this here?
	}
	
	public void upgradeEmitter()
	{
		particleEmit.upgrade();
	}
	
	//Should happen when swap blocked lands again.
	public void removeEmitter()
	{
		spawnParticles = false;
		particleEmit = null;
		
	}
	
	//This function checks whether a movable was unmovable and whether its time
	//for it to become movable
	public boolean movableCheck() {
		//the upper if statements magic number is meant to be 3000 lower
		//than the one in the lower if statement
		if(System.currentTimeMillis() - timeBlacked > 12000*Playstate.difficulty)
		{
			if(System.currentTimeMillis()%1000<500)
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