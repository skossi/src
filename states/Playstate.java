package states;

import java.util.Random;
import com.blokk.game.Utils;

import managers.AudioManager;
import managers.GameStateManager;
import managers.RectangleManager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import entities.Movable;
import entities.RectTex;
import entities.UI;
 

//Class by �ttar, Hlynur and �orsteinn. 
//
//Written 30.10.2014
//Creates a new state when user is playing.
//This is the main game loop that is in charge of the game.
//This class might be refactored by using the resource manager but currently that is not a priority
public class Playstate extends Gamestate{

	   private Movable[][] Movables;
	   private long lastDropTime;
	   private int rows;
	   private int columns;
	   private int superSpeed;
	   public static boolean isPaused;
	   private int size;
	   private float selectedX;
	   private float selectedY;
	   private int takeoffSpeed;
	   private int[] swapScores;
	   private String[] drawSwapScores;
	   private int steps;
	   private int startingRows;
	   private RectangleManager Man;
	   private UI UI;
	   private Movable selectedM;
	   private long lastWave;
	   private int loseCondition;
	   private int loseConditionOffset;
	   private int losePos;
	   private boolean canPlay;
	   private float loseSpeed;
	   private float defaultSpeed;
	   private long actionTime;
	   private int actions;
	   private int IDs;
	   private boolean startAnimation;
	   private int scoreBoardPos;
	   private int score;
	   private int introSpeed;
	   
	   // public for global access
	   public static boolean isSelected;
	   public static double difficulty;
	   private int musicThreshold;
	   
	   //chosen background
	   
	   private boolean isTesting;

	   
	//Constructor
	//See abskrakt class Gamestate(GameStateManager gsm);
	public Playstate(GameStateManager gsm)
	{	
		super(gsm);
	}
	//See abstrakt class Gamestate init();
	public void init(RectangleManager RectMan)
	{
		actions = 0;
		actionTime = System.currentTimeMillis()-500;
		Man = RectMan;
		isPaused = false;
		Man.AnimationM.isMenuDown = true;
		size = 68;
		steps = size; //pixel perfect updating
		columns = 7;
		rows = 13;
		takeoffSpeed = 300;
		startingRows = 3;
		IDs = 1;
		Movables = new Movable[columns][rows];
		lastWave = 0;
		UI = new UI(0, 800, 480, size);
		difficulty = 1.0;
		defaultSpeed = 600;
		loseCondition = 840;
		losePos = 1;
		swapScores = new int[4];
		drawSwapScores = new String[]{"0","0","0","0"};
		canPlay = false;
		startAnimation = true;
		introSpeed = 300;
		loseSpeed = 20;
		superSpeed = 2000;
		score = 0;
		prepareMatrix();
		
		isTesting = false;
		
		Man.AudioM.raiseThemeMusic();
		musicThreshold = 0;
		Man._r = Man._rOrg;
		Man._g = Man._gOrg;
		Man._b = Man._bOrg;
	}
	/**
   * Creates a new cube on a timed interval. It���s type is randomed. This method is a temporary solution for spawning cubes in debugging mode
   *
   * @return            a new cube of some sort is created and placed in the grid
   */
	   private void spawnMovable(int col, float speed) {
			  Movable m1;

			  m1 = new Movable(true);
		      m1.col = col;
			  m1.type = createType(m1.typeOne, m1.typeTwo);
			  
			  if (Movables[m1.col][rows-1] != null) return;
			  
		      int available_row = 0;
		      for (int i = 0; i < rows; i++) {
		    	  if (Movables[m1.col][i] == null) {
		    		  available_row = i;
		    		  break;
		    	  }
		      }
		      
		     
		      m1.row = available_row;
		      m1.x = (size+1)*m1.col;
		      m1.y = 800;
		      m1.speed = -speed;
		      m1.width = size;
		      m1.height = size;
		      m1.isBeingThrusted = false;
		      m1.justSpawned = true;
		      m1.ID = 0;
			  
			  Movables[m1.col][available_row] = m1;
			  giveLegalType(m1);
			  
		      lastDropTime = System.currentTimeMillis();
		      
		   }
   
   // Use: spawnWave(speed);
   // After: A new wave of blocks has been spawned with velocity speed
   private void spawnWave(float speed) {
	   for(int j = 0; j < columns; j++){
		   spawnMovable(j, speed);
	   }
   }
	 /**
   * Prepares the Movables matrix by adding a row of immovable blocks below the screen for collision purposes    
   * 
   * @return            one row of immovable blocks are created below the screen
   */
   public void prepareMatrix() {
	   for (int i = 0; i < columns; i++) {
		   Movable movable = new Movable(false);
		   movable.type = createType(movable.typeOne, movable.typeTwo); 
	       movable.row = 0;
	       movable.y = -size;
	       movable.speed = 0;
	       movable.width = size;
	       movable.height = size;
	       movable.isBeingThrusted = false;
	       movable.justSpawned = false;
	       movable.x = (size+1)*i;
	       movable.isPower = false;
	       movable.ID = -1;
		   Movables[i][0] = movable;
		   
	   }
   }
   
   // Use: killBlock(m1);
   // After: Called when a block is deleted by thrusting it up above the loseCondition line
   //       Deletes the movable, adds scores and plays sound
   public void killBlock(Movable m1){
	   if (m1.y > loseCondition && m1.speed > 0){ 
		   addScore(Movables[m1.col][m1.row].typeOne, Movables[m1.col][m1.row].typeTwo,1);

		   Movables[m1.col][m1.row] = null;
		   // ensure that there is no gap between blocks inside the array at any time
		   int lowestEmptyIndex = -1;
		   for (int i=0; i < rows; i++) {
			   // find the first empty row in the same column as m1
			   if (Movables[m1.col][i] == null && lowestEmptyIndex == -1) {
				   lowestEmptyIndex = i;
				   continue;
			   }
			   // this case only happens if there is a block with a higher index than 
			   // the first empty one => there is a gap in the array
			   else if (Movables[m1.col][i] != null && lowestEmptyIndex > -1) {
				   Movable temp = new Movable(Movables[m1.col][i]);
				   temp.row = lowestEmptyIndex;
				   Movables[m1.col][lowestEmptyIndex] = temp;
				   Movables[m1.col][i] = null;
			   }
		   }
		   Man.playSoundEffect(AudioManager.COLLECT);
	   }	   
   }
   /**
   * Gives a created cube its texture depend on his boolean tree structure    
   * 
   * @param typeOne file Boolean which decides if it is a movable cube or black block
   * @param typeTwo boolean that decides what kind of color the cube is
   * @return            returns Texture corresponding to it���s boolean structure
   */   
	private Texture createType(Boolean typeOne, boolean typeTwo) 
	{
			if (typeOne == null)
			{
				if(!typeTwo)
					return Man.TextureM.black;
				else
					return Man.TextureM.blinkBlack;
			}
			return (typeOne ? (typeTwo ? Man.TextureM.square : Man.TextureM.circle) 
					: (typeTwo ? Man.TextureM.triangle : Man.TextureM.ex));
	}
	
	// Use: printMovables();
	// After: All blocks have been translated to integers from 1-4 and written to the console
	private void printMovables() {
		for (int j=rows-1; j > 0; j--) {
			String log = "";
			for (int i=0; i < columns; i++) {
				    log += translateType(Movables[i][j]);
			}
				System.out.println(log);
			}
	    }
				
	// Use: int type = translateType(m1);
	// After: Returns value 1 if m1 has types True/True, 
	//        value 2 if m1 has types True/False,
	//        value 3 if m1 has types False/True and
	//        value 4 if m1 has types False/False
    private int translateType(Movable m) {
    	if (m == null) return 0;
    	
    	Boolean typeOne = m.typeOne;
    	boolean typeTwo = m.typeTwo;
    	if (typeOne == null) return -1;
    	
    	else if (typeOne && typeTwo) return 1;
    	else if (typeOne && !typeTwo) return 3;
    	else if (!typeOne && typeTwo) return 2;
    	else if (!typeOne && !typeTwo) return 4;
    	
    	return 0;
    }
    
    // Use: beginAction()
    // After: A wave of blocks has been spawned
    public void beginAction() {
 	   spawnWave(2500); // test, var � 2000
    }

    
	//See abstrakt class Gamestate update(float dt);
	public void update(float dt)
	{
		
		if(isTesting) return;
		if (isPaused) {
//			printMovables();
//			isTesting = true;
			System.out.println("Entering delay");
			delayMovableTimers(dt);
			return;
		}
		if(canPlay)
		{
			if(actions < startingRows){
				if(System.currentTimeMillis() - actionTime > 1000) { // testing, var 1000
					beginAction();
					actionTime = System.currentTimeMillis();
					actions++;
				}
				lastWave = System.currentTimeMillis();
			}
			if(System.currentTimeMillis() - lastWave > 15000*difficulty)
			{
				lastWave = System.currentTimeMillis();
				spawnWave((float)((1+(1-difficulty))*defaultSpeed));
				if(difficulty > 0.45)
				{
					difficulty -= 0.03;
					musicThreshold++;
					//TODO:This is just a placeholder. Will be fixed
					if(musicThreshold == 4)
					{
						musicThreshold = 0;
						Man.AudioM.raiseThemeMusic();
					}	
				}
			} 
			else if (actions == startingRows && System.currentTimeMillis() - lastDropTime > 900*difficulty) spawnMovable(MathUtils.random(0, 6), (float)((1+(1-difficulty))*defaultSpeed));
		}
		else if (startAnimation) playIntro(dt,-1);
		else blackMovableAnimation(dt);
		for (int i = 0; i < steps; i++) computeSubStep(dt/steps);
	}

	private void playIntro(float dt, int dir)
	{
		introSpeed += dir;
		
		if(UI.y > 800-size) UI.y += introSpeed * dt * dir;
		else UI.y = 800-size;
		if(dir < 0)
		{
			scoreBoardPos = (int)UI.y+5;
			loseCondition += introSpeed * dt * dir;
			
			if(loseCondition < 670)
			{
				loseCondition = 670;//720-size;
				canPlay = true;
				startAnimation = false;
				//introSpeed = 200;
			}	
		}	
	}
	
	// Use: blackMovableAnimation(dt);
	// After: Starts the losing animation when game is lost. 
	//        Blacks every Movable on the screen as they fall down
	private void blackMovableAnimation(float dt)
	{
		playIntro(dt,1);
		if(Man._r >= 0) Man._r -= dt*4;
		if(Man._g >= 0) Man._g -= dt*4;
		if(Man._b >= 0) Man._b -= dt*4;
		for(int i = 0; i < rows; i++) 
		{
			for (int j = 0; j < columns; j++) 
			{
				Movable m = Movables[j][i];
				if(m != null)
				{
					if(m.typeOne != null)
					{
						m.typeOne = null;
						m.typeTwo = false;
						return;
					}
					else
					{
						m.y -= loseSpeed*dt;
						loseSpeed += 2;
						if(m.row == 11 && m.y + size*4 <= 0)gameOver();
					}
				}
			}
		}
		
	}
	
	private void checkLoseOffset(boolean aMethod)
	{
		if(aMethod)
		{
			losePos *= -1;
			loseConditionOffset = 0;
		}
		else
		{
			loseConditionOffset -= 8;
			if(loseConditionOffset < 0) loseConditionOffset = 0;
			else loseConditionOffset *= losePos;
		}
	}
	
	//See abstract class Gamestate draw(SpriteBatch b);
	public void draw(SpriteBatch batch)
	{
		//TODO: this should not be here!!!! just for demo atm
		checkLoseOffset(true);
		
		//batch.draw(Background, 0, 0);
	      for(int i = 0; i < columns; i++) {
	    	  for (int j = 0; j < rows; j++) {
	    		  Movable m = Movables[i][j];
	    		  if (m != null)
	    		  {		
	    			//TODO: this should also not be here!!!! just for demo atm
	    			  if(m.row > loseConditionOffset) loseConditionOffset = m.row;
	    			  batch.draw(createType(m.typeOne,m.typeTwo), m.x, m.y); 
		    		  if(m.isPower)
		    		  {
		    		  }
	    			  
	    		  }
	    	  }
	      }
	      
	      checkLoseOffset(false);
	      
	      batch.draw(Man.TextureM.redline, 0, loseCondition+loseConditionOffset);
	      
	      if(isSelected && selectedM != null) {
	    	  if (!selectedM.justSpawned) 
	    		  batch.draw(Man.TextureM.selected, selectedM.x, selectedM.y);
	      }
	      if(isPaused && !isTesting)
	      {
	    	  batch.draw(Man.TextureM.pauseBlock,0,0,480,800);
	    	  batch.draw(Man.ButtonM.PauseResume.tex, Man.ButtonM.PauseResume.x, Man.ButtonM.PauseResume.y);
	    	  batch.draw(Man.ButtonM.PauseRestart.tex, Man.ButtonM.PauseRestart.x, Man.ButtonM.PauseRestart.y);
	    	  batch.draw(Man.ButtonM.PauseQuit.tex, Man.ButtonM.PauseQuit.x, Man.ButtonM.PauseQuit.y);
	      }
	      batch.draw(Man.TextureM.ui_bg, UI.x, UI.y, UI.width, UI.height);
	      if(!isPaused)batch.draw(Man.TextureM.ui_pauseOn,UI.x,UI.y+5,64,64);
	      else batch.draw(Man.TextureM.ui_pauseOff,UI.x,UI.y+5,64,64);
	     
	      if(Man.isMuted) batch.draw(Man.TextureM.ui_soundOff,416,UI.y+10,64,64);
	      else batch.draw(Man.TextureM.ui_soundOn,416,UI.y+10,64,64);
	      
	      //Man.drawScoreBoard(batch, 100, scoreBoardPos, drawSwapScores, false, -1, Man.fontWhite);
	      Man.fontWhite.draw(batch, Integer.toString(score), 100, scoreBoardPos);
	}
	
	//See abstrakt class Gamestate justTouched(x,y);
	public void justTouched(float x, float y)
	{
		
		int barPress = UI.isTouched(x, y);
		if(barPress == 1) 
		{
			isPaused =! isPaused;
			Man.playSoundEffect(AudioManager.PAUSE);
		}
		if(barPress == 4)
		{
			Man.soundMute();
			Man.playSoundEffect(AudioManager.MUTE);
		}
		
		isSelected = true;
		selectedM = locateMovable(x, y);
		
		if (selectedM != null) {
			selectedX = selectedM.x + size/2;
			selectedY = selectedM.y + size/2;
		}
		
		if(isPaused)
		{
			if(buttonClick(Man.ButtonM.PauseResume,x,y)) isPaused = false;
			if(buttonClick(Man.ButtonM.PauseRestart,x,y)) RestartGame();
			if(buttonClick(Man.ButtonM.PauseQuit,x,y))
			{
				isPaused = false;
				Man.AudioM.resetThemeMusic();
				gsm.setState(GameStateManager.MENU);		
			}
		}
	}
	
	// Use: RestartGame();
	// After: All starting variables have been reset so the game can be played from start
	private void RestartGame()
	{
		for(int i = 0; i < columns; i++)
		{
			for(int j = 0; j < rows ; j++)
			{
				Movables[i][j] = null;
			}
		}
		difficulty = 1.0;
		for(int k = 0; k < swapScores.length;k++)
		{
			swapScores[k] = 0;
			drawSwapScores[k] = "0";
		}
		isPaused = false;
		prepareMatrix();
		actions = 0;
		actionTime = System.currentTimeMillis()-500;
		Man.AudioM.resetThemeMusic();
		Man.AudioM.raiseThemeMusic();
	}
	
	//See abstrakt class Gamestate isTouched(x,y);
	public void isTouched(float x, float y)
	{		
		if(isPaused) return;
	    if (isSelected) findMovable(x, y);
	    
	    if (selectedM != null) {
			selectedX = selectedM.x + size/2;
			selectedY = selectedM.y + size/2;
		}
	}
	
	public void delayMovableTimers(float dt)
	{
		System.out.println(dt);
		System.out.println("Inside delay");
		for(Movable[] row : Movables)
			for(Movable m1 : row)
			{
				if(m1==null || m1.row ==0) continue;
				if(m1.timeBlacked!=Long.MAX_VALUE)
				{
					m1.timeBlacked=m1.timeBlacked+(long) (3000*dt);
					System.out.println("Delaying black");
				}
				if(m1.isBeingThrusted)
				{
					m1.timeThrusted=m1.timeThrusted+(long)(3000*dt);
					System.out.println("Delaying thrust");
				}
			}
	}
	 /**
   *Breaks the entities update into smaller steps so it wont render out of bounds.
   * @param dy is the delta time of each frame rendered
   */
	public void computeSubStep(float dy) {
		for(int j = 0; j < rows; j++) {
    	  for (int i = 0; i < columns; i++) {
    		  Movable m1 = Movables[i][j];
    		  if(m1 == null || m1.row == 0) continue;

    		  if (m1.speed != 0) {
				  if (Movables[m1.col][m1.row-1] != null && m1 != Movables[m1.col][m1.row-1] && 
						  m1.intersects(Movables[m1.col][m1.row-1])) {
					  //assume here that m1 and m2 are some colliding blocks
					  Movable m2 = Movables[m1.col][m1.row-1];
					  m1.speed = Math.max(m1.speed, m2.speed);
					  m1.timeThrusted = Math.max(m2.timeThrusted, m1.timeThrusted);
					  m1.isBeingThrusted = m2.isBeingThrusted;
					  m1.ID = m2.ID;
					  m1.justSpawned = false;
					  if (m1.y < m2.y+size) m1.y = m2.y+size;
					  //if(m1.row == 11) endGameAnimation();
					  if(m1.row == 11 && m1.speed == 0) endGameAnimation();
//					  if(m1.speed>0){
//	    				  m1.isBeingThrusted = true;
//	    				  m1.timeThrusted = m2.timeThrusted;
//	    			  }
					  IDs++;
	    			  handleMatches(m1, IDs);
				  }
    		  }
    		  // this happens when block can be unblacked/afsvertist
    		  if(m1.movableCheck()){
    			  giveLegalType(m1);
    		  }
        	  m1.update(dy);
        	  killBlock(m1);
    	  }
      }
	}
	
	// Use: giveLegalType(m1);
	// After: m1 has been given a type so he doesn't match with nearby blocks  
	public void giveLegalType(Movable m1){
		Random ran = new Random();
		  int x = ran.nextInt(4);
		  for(int i = 0; i<4; i++){
			  int[] tryType = Utils.integerType(((x+i)%4)+1);
			  boolean tryOne = Utils.toBoolean(tryType[0]);
			  boolean tryTwo = Utils.toBoolean(tryType[1]);
			  m1.setType(tryOne, tryTwo);
			  boolean thrustCase = false;
			  int[] resultsHorizontal = findHorizontalMatches(m1, thrustCase);
			  int[] resultsVertical = findVerticalMatches(m1, thrustCase);
			  if(resultsHorizontal[0] == 0 && resultsVertical[0] == 0)
				  break;
		  }    
	}
	
	
	// Use: int[] shootCoordinates = findHorizontalMatches(m1);
	// After: Returns an array of size 3 if 3 or more blocks of same type are "matched"
	//        in the same row. The first index returns 1 if there is a match, else 0. 
	//        The second index returns in what column the first match occurs.
	//        The third index returns how many blocks are matched.

	public int[] findHorizontalMatches(Movable m1, boolean thrustCase) {
		   int count = 0;
		   int row = m1.row;
		   int index = -1;
		   int ID = m1.ID;
		   for(int j = 0; j < columns; j++){
			   for(int i = j;  i< columns; i++){
				   if( isSameType(Movables[i][row], m1)){
					   if(thrustCase){
						   if(Movables[i][row].ID == ID ){
							   count++;   
						   }      
					   } else
						   count++;
					   
				   } else{
					   break;
				   }
			   }
			   if(count >= 3){
				   index = j;
				   break;
			   }
			   count = 0;
		   }
		   int[] result = new int[3];
		   if(count==0) {
			   return result;
			   }
		   else {
			   result[0] = 1;
			   result[1] = index;
			   result[2] = count;
			   return result;
			   }
	}
	
	// Use: int[] shootCoordinates = findVerticalMatches(m1);
	// After: Returns an array of size 3 if 3 or more blocks of same type are "matched"
	//        in the same column. The first index returns 1 if there is a match, else 0. 
	//        The second index returns in what column the first match occurs.
	//        The third index returns how many blocks are matched.
	public int[] findVerticalMatches(Movable m1, boolean thrustCase) {
		int count = 0;
		int index = -1;
		int col = m1.col;
		int ID = m1.ID;
		for(int j = 0; j < rows; j++){
		   for(int i = j;  i< rows; i++){
			   if( isSameType(Movables[col][i], m1)){
				   if (thrustCase) {
					   if(Movables[col][i].ID == ID ){
						   count++;   
					   }
				   }
				   else 
					   count++;
			   } else{
				   break;
			   }
		   }
		   if(count >= 3){
			   index = j;
			   break;
		   }
		   count = 0;
	    }
		int[] result = new int[3];
	    if(count==0) {
		    return result;
		    }
	    else {
	 	    result[0] = 1;
	 	    result[1] = index;
		    result[2] = count;
		    return result;
		    }
	}
	
//	public void setUnmatchedType(){
		//viljum ad handlematches gefi bara boolean hvort thad se column eda row match
		//kollum svo a thau foll her:
		//gongum i gegnum typurnar, gefum kubb lit og gerum svo handlematches
		//ef thad matchast eitthvad tha profum vid naesta lit
//	}
	
	/**
   *Checks to see if the player did indeed move a block to a valid position and to find if he added *three or more together
   * @param m1 A moved Movable block by the user
   */
	public void handleMatches(Movable m1, int thrustID)
	{
	   boolean thrustCase = true;
	   if (m1 == null || m1.typeOne == null) return;
	   checkRowMatches(m1, thrustCase, thrustID);
	   checkColMatches(m1, thrustCase, thrustID);
	   return;
	}
	   
   /**
   *Checks out if anyone is linked to the moved Movable in the Column
   * @param m1 A moved Movable block by the user
   */ 

   public void checkColMatches(Movable m1, boolean thrustCase, int thrustID){
	   int col = m1.col;
	   int[] shootCoordinates = findVerticalMatches(m1, thrustCase);
	   int index = shootCoordinates[1];
	   int count = shootCoordinates[2];
	   if(count > 2){
		Movable m3 = Movables[col][index];	   
		m3.typeOne = null;
		m3.typeTwo = false;
		m3.timeBlacked = System.currentTimeMillis();
   
	   //TODO: skoda
	   if(m1.ID != -1) shootByID(m1.ID, superSpeed);
	   else shootRows(m1.col, 1, index, takeoffSpeed, false, thrustID);
	   Man.playSoundEffect(AudioManager.MATCH);
	   }
   }
	   
/**
   *Checks out if anyone is linked to the moved Movable in the Row. This one is bugged and needs *refactoring
   * @param m1 A moved Movable block by the user
   */
   public void checkRowMatches(Movable m1, boolean thrustCase, int thrustID){
	   int row = m1.row;
	   int[] shootCoordinates = findHorizontalMatches(m1, thrustCase);
	   int index = shootCoordinates[1];
	   int count = shootCoordinates[2];
	   if(count > 2){
		   for(int j = index; j < index+count; j++){ 
			   Movable m3 = Movables[j][row];
			   if(m3.isPower)
			   {
				   //TODO: Resevered for powerupSpace
			   }
			   
				m3.typeOne = null;
				m3.typeTwo = false;
				m3.timeBlacked = System.currentTimeMillis();
		   }
		   //TODO: skoda
		   if(m1.ID != -1) shootByID(m1.ID, superSpeed);
		   else shootRows(index, count, row, takeoffSpeed, false, thrustID);
		   Man.playSoundEffect(AudioManager.MATCH);
	   }
   }

   // Use: addScore(typeOne, typeTwo, pts);
   // After: The score has been incremented by amount pts
	private void addScore(Boolean typeOne, boolean typeTwo, int aScore) 
	{
		score++;
		if(typeOne == null) return;
		int swapToAdd = typeOne ? (typeTwo ? 0 : 2) : (typeTwo ? 1 : 3);
		swapScores[swapToAdd] += aScore;
		drawSwapScores[swapToAdd] = Integer.toString(swapScores[swapToAdd]);
	}
	
   // Use: shootRows(blockIndex, blockCount, blockRow, isBeingThrusted);
   // After: Thrusts the blocks from row blockRow from index blockIndex to index
   //        blockIndex+blockCount and all blocks above that.
   //        The variable isBeingThrusted is not being used currently.
   public void shootRows(int index, int count, int row, int speed, boolean isBeingThrusted, int ID){
	   isSelected = false;
	   if(isBeingThrusted){
		   //TODO: Resevered for thrustage
		   //Stop reserving space. We must save space for powerups!
	   }
	   for(int j = index; j < index+count; j++){
		   for (int i = row; i < rows; i++){
			   Movable m1 = Movables[j][i];
			   if(m1 != null) {
				   if(m1.justSpawned || m1.ID != -1) continue;
				   m1.speed = speed;
			       m1.timeThrusted = System.currentTimeMillis();   
			       m1.isBeingThrusted = true; 
			       m1.ID = ID;
			   }
		   }
	   }
   }
   
   // Use: shootByID(ID, superSpeed);
   // After: Every block with id "ID" will be thrusted upwards with speed "superSpeed"
   public void shootByID(int ID, int superSpeed){
	   for(int i=0; i<columns; i++){
		   for(int j=0; j<rows; j++){
			   Movable m1 = Movables[i][j];
			   if(m1 != null) {
				   if (m1.ID == ID) m1.speed = superSpeed;
			   }
		   }
	   }
   }
	   
  /**
   *Finds out if the moved block was indeed the same color is the one moved in it���s direction
   * @param m1 A moved Movable block by the user
   * @param typeOne lets the method know what kind of cube it is.
   * @param typeOne lets the method know what kind of cube it is.
   * @return true if a corresponding block is matched
   */
   public boolean isSameType(Movable m1, Movable m2){
	   if(m1 == null || m2 == null || m1.typeOne == null || m2.typeOne == null){return false;}
	   return m1.typeOne == m2.typeOne && m1.typeTwo == m2.typeTwo;
   }
	   
   /**
   *Passes the x,y coordinates of the screen on to it���s chiled methods to find out if the player is  *indeed clicking at a cube.
   * @param x X-coordinates of the screen
   * @param y Y-coordinates of the screen
   */
   public Movable locateMovable(float x, float y) {
	   int col = (int)(x/size);
	   
	   Movable selected = null;

	   if (col < 0 || col > columns-1) return selected;
	   
	   for (int i = 0; i < rows; i++) {
		   if (Movables[col][i] == null || Movables[col][i].typeOne == null) continue;
		   if (y > Movables[col][i].y && y < (Movables[col][i].y + size)) {
			   return Movables[col][i];
		   }
	   }
	   return selected;
   }
   
   // Use: findMovable(x, y);
   // After: Finds the block that player when pressing down and handles all swipe gestures
   //        by the user. 
   public void findMovable(float x, float y) {
	   if(selectedM == null) return;
	   int col = selectedM.col;
	   int row = selectedM.row;
	   if (row < 0 || row > rows-1 || col < 0 || col > columns-1) return;	   
	   if (selectedM.justSpawned) return;
		   if (y > selectedY + size/2) {
			   handleSwap(col, row, 1);
			   return;
		   }
		   
		   if (y < selectedY - size/2) {
			   handleSwap(col, row, -1);
			   return;
		   }
   }
   
   // Use: handleSwap(col, row, direction);
   // After: If the conditions for a swap have happened, two blocks have swapped positions
   //        and possible matches are checked for
   public void handleSwap(int col, int row, int direction){
	   if (Movables[col][row+direction] == null) return;
	   Movable targetM = Movables[col][row+direction];
	   //TODO: see if checking for same speed has any meaning
	   if (targetM == null || targetM.typeOne == null || targetM.speed != selectedM.speed) return;
	   swapTypes(selectedM, targetM);
	   IDs++;
	   //DOUBLE SWAP CASES, DOUBLESWAP, SUPERSWAP
	   if (row < row + direction) {
		   handleMatches(Movables[col][row], IDs);
		   handleMatches(Movables[col][row+direction], IDs);
	   }
	   else {
		   handleMatches(Movables[col][row+direction], IDs);
		   handleMatches(Movables[col][row], IDs);
	   }
	   handleMatches(Movables[col][row], IDs);
	   selectedY += direction*size;
   }
	   
   /**
   *Swaps to different Movables if the move of the player was legit
   * @param m1 A moved Movable
   * @param m2 Movable that is going to be swapped
   */

   public void swapTypes(Movable m1, Movable m2) {
	   Boolean tempOne = m1.typeOne;
	   boolean tempTwo = m1.typeTwo;
	   m1.typeOne = m2.typeOne;
	   m1.typeTwo = m2.typeTwo;
	   m2.typeOne = tempOne;
	   m2.typeTwo = tempTwo;
	   
	   selectedM = m2;	   
   }  
	 //See abstrakt class Gamestate dispose();
	public void dispose()
	{

	}
	
	// Use: buttonClick(rekt, x, y);
	// After: Returns true if the button rekt at position x,y is being pressed.
	public boolean buttonClick(RectTex rekt, float x, float y) {
		if (x < (rekt.x + rekt.width) && x > rekt.x && y > rekt.y && y < (rekt.y + rekt.height)) return true;
		return false;
	}
	
	private void endGameAnimation()
	{
		canPlay = false;
		UI.y += 1;
	}
	
	//Saves current score and sets the state to Lost. Called when game is lost
	private void gameOver()
	{
		Man.ScoreM.checkScore(swapScores,score);
		gsm.setState(GameStateManager.LOST);
	}	
}
