package states;

import java.util.Random;

import managers.GameStateManager;
import managers.RectangleManager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.blokk.game.Movable;
import com.blokk.game.UI;
 

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
	   public static boolean isPaused;
	   private int size;
	   private float selectedX;
	   private float selectedY;
	   private int[] swapScores;
	   private String[] drawSwapScores;
	   private int steps;
	   private int startingRows;
	   private RectangleManager R_Man;
	   private UI UI;
	   private BitmapFont font;
	   private Movable selectedM;
	   private long lastWave;
	   private int loseCondition;
	   private float defaultSpeed;
	   private long actionTime;
	   private int actions;
	   // public static for global access
	   public static boolean isSelected;
	   public static double difficulty;
//	   private int[] warning;
	   
	   //chosen background
	   private Texture Background;
	   
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
		R_Man = RectMan;
		size = 68;
		steps = size; //pixel perfect updating
		columns = 7;
		rows = 13;
		startingRows = 3;
//		warning = new int[rows];
		Movables = new Movable[columns][rows];
		lastWave = 0;
		UI = new UI(0, 800-size, 480, size);
		font = R_Man.font;
		difficulty = 1.0;
		defaultSpeed = 600;
		loseCondition = 720-size;
		swapScores = new int[4];
		drawSwapScores = new String[]{"0","0","0","0"};
		prepareMatrix();
		double back = Math.random();
		if(back < 0.5) Background = R_Man.back_1;
		else Background = R_Man.back_2;
		
		isTesting = false;
	}
	/**
   * Creates a new cube on a timed interval. It�s type is randomed. This method is a temporary solution for spawning cubes in debugging mode
   *
   * @return            a new cube of some sort is created and placed in the grid
   */
   private void spawnMovable(int col, float speed) {
		  Movable movable;

		  movable = new Movable(true);
	      movable.col = col;
		  movable.type = createType(movable.typeOne, movable.typeTwo);
		  
		  if (Movables[movable.col][rows-1] != null) return;
		  
	      int available_row = 0;
	      for (int i = 0; i < rows; i++) {
	    	  if (Movables[movable.col][i] == null) {
	    		  available_row = i;
	    		  break;
	    	  }
	      }
	      
	      double givePower = Math.random();
	      if(givePower < 0.15)
	      {
	    	  movable.isPower = true;
	    	  double whichPower = Math.random();
	    	  if(whichPower < 0.3)
	    	  {
	    		  movable.power = "50";
	    		  movable.typePowerOne = true;
	    		  movable.typePowerTwo = false;
	    	  }
	    	  else
	    	  {
	    		  movable.power = "2x";
	    		  movable.typePowerOne = true;
	    		  movable.typePowerTwo = true;
	    	  }
	      }
	      else movable.isPower = false;
	      movable.row = available_row;
	      movable.x = (size+1)*movable.col;
	      movable.y = 800;
	      movable.speed = -speed;
	      movable.width = size;
	      movable.height = size;
	      movable.isBeingThrusted = false;
	      
//	      warning[movable.col] +=1;
		  Movables[movable.col][available_row] = movable;
		  
		  
		  // tilraunastarfsemi
//		  while (checkRowMatches(movable) == true) {
//			  movable.typeOne = movable.randomizeType();
//			  movable.typeOne = movable.randomizeType();
//			  Movables[movable.col][available_row] = movable;
//		  }
		  
	      lastDropTime = TimeUtils.nanoTime();
	      
	   }
   //Spawns a new wave of blocks on a fixed interval
   private void spawnWave(float speed) {
	   for(int j = 0; j < columns; j++){
		   spawnMovable(j, speed);
//		   warning[j] +=1;
	   }
//	   dangerColumn();
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
	       movable.x = (size+1)*i;
	       movable.isPower = false;
		   Movables[i][0] = movable;
		   
	   }
   }
   
   
   //Called when a block is deleted by thrusting it up above the loseCondition line
   //Deletes the movable, adds scores and plays sound
   public void killBlock(Movable m1){
	   if (m1.y > loseCondition && m1.speed > 0){ 
		   addScore(Movables[m1.col][m1.row].typeOne, Movables[m1.col][m1.row].typeTwo,1);
//		   warning[m1.col] -= 1;
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
		   
//		   dangerColumn();
		   R_Man.destSound.play(R_Man.Volume);
	   }	   
   }
//   //Colors the background red according to the highest column
//   private void dangerColumn()
//   {
//	   int i = whichColumn(warning, rows)-11;//threshold so the background wont turn red just yet
//	   if(i >= 0)R_Man._w = i/5f;
//	   else R_Man._w = 0;
//   }
   //Returns how big the largest column is
//   public static int whichColumn(int[] warning, int rows)
//   {
//	   int ret = -1;
//	   for(int i = 0; i < rows; i++){
//		   if(warning[i] > ret) ret = warning[i];
//	   }
//	   return ret;
//   }
   /**
   * Gives a created cube its texture depend on his boolean tree structure    
   * 
   * @param typeOne file Boolean which decides if it is a movable cube or black block
   * @param typeTwo boolean that decides what kind of color the cube is
   * @return            returns Texture corresponding to it�s boolean structure
   */   
	private Texture createType(Boolean typeOne, boolean typeTwo) 
	{
			if (typeOne == null) return R_Man.black;
			return (typeOne ? (typeTwo ? R_Man.square : R_Man.circle) : (typeTwo ? R_Man.triangle : R_Man.ex));
	}
	private Texture drawPower(Boolean typeOne, boolean typeTwo) 
	{
			if (typeOne == null) return R_Man.black;
			return (typeOne ? (typeTwo ? R_Man.Power_Multi : R_Man.Power_50) : (typeTwo ? R_Man.triangle : R_Man.ex));
	}
	
	private void printMovables() {
		for (int j=rows-1; j > 0; j--) {
			String log = "";
			for (int i=0; i < columns; i++) {
				    log += translateType(Movables[i][j]);
			}
				System.out.println(log);
			}
	    }
				
	
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
    
    public void beginAction() {
 	   spawnWave(2500); // test, var � 2000
    }
    
	//See abstrakt class Gamestate update(float dt);
	public void update(float dt)
	{
		if(actions < startingRows){
			if(System.currentTimeMillis() - actionTime > 1000) { // testing, var 1000
				beginAction();
				actionTime = System.currentTimeMillis();
				actions++;
			}
			lastWave = System.currentTimeMillis();
		}
		if(isTesting) return;
		if (isPaused) {
			System.out.println("csvCat");
			printMovables();
			isTesting = true;
			return;
		}
		if(System.currentTimeMillis() - lastWave > 15000*difficulty){
			lastWave = System.currentTimeMillis();
			spawnWave((float)((1+(1-difficulty))*defaultSpeed));
			if(difficulty > 0.45) difficulty -= 0.03;
			
		} else if (actions == startingRows && TimeUtils.nanoTime() - lastDropTime > 900000000*difficulty) spawnMovable(MathUtils.random(0, 6), (float)((1+(1-difficulty))*defaultSpeed));
		
		for (int i = 0; i < steps; i++) computeSubStep(dt/steps);
	}
	//See abstract class Gamestate draw(SpriteBatch b);
	public void draw(SpriteBatch batch)
	{
		batch.draw(Background, 0, 0);
	      for(int i = 0; i < columns; i++) {
	    	  for (int j = 0; j < rows; j++) {
	    		  Movable m = Movables[i][j];
	    		  if (m != null)
	    		  {		batch.draw(createType(m.typeOne,m.typeTwo), m.x, m.y); 
		    		  if(m.isPower)
		    		  {
		    			  //The function to draw images did not work. We need to change this
		    			  if(m.power == "2x") batch.draw(R_Man.Power_Multi, m.x, m.y);
		    			  if(m.power == "50") batch.draw(R_Man.Power_50, m.x, m.y);
		    			  //batch.draw(drawPower(m.typePowerOne,m.typePowerTwo), m.x, m.y);
		    			  font.draw(batch, m.power, m.x+size/4, m.y+size-15);//just seeing how it looks
		    		  }
	    			  
	    		  }
	    	  }
	      }
	      
	      batch.draw(R_Man.redline, 0, loseCondition);
	      if(isSelected && selectedM != null) {
	    	  if (selectedM.speed == 0) 
	    		  batch.draw(R_Man.selected, selectedX-size/2, selectedY-size/2);
	      }
	      if(isPaused && !isTesting)batch.draw(R_Man.pauseBlock,0,0,480,800);
	      batch.draw(R_Man.ui_bg, UI.x, UI.y, UI.width, UI.height);
	      if(!isPaused)batch.draw(R_Man.ui_pauseOn,UI.x,UI.y+5,64,64);
	      else batch.draw(R_Man.ui_pauseOff,UI.x,UI.y+5,64,64);
	     
	      if(R_Man.isMuted) batch.draw(R_Man.ui_soundOff,416,UI.y+10,64,64);
	      else batch.draw(R_Man.ui_soundOn,416,UI.y+10,64,64);
	      
	      batch.draw(R_Man.ScoreBoard,100,UI.y+15);
	      //Draw all the numbers of swapped blocks here
	      for(int i = 0; i < 4; i++)
	      {
	    	  if(drawSwapScores[i].length() == 3)font.draw(batch, drawSwapScores[i], 110+size*i, 790); 
	    	  else if(drawSwapScores[i].length() == 2)font.draw(batch, drawSwapScores[i], 120+size*i, 790);
	    	  else font.draw(batch, drawSwapScores[i], 130+size*i, 790); 	  
	      }

	}
	//See abstrakt class Gamestate justTouched(x,y);
	public void justTouched(float x, float y)
	{
		
		int barPress = UI.isTouched(x, y);
		if(barPress == 1) 
		{
			isPaused =! isPaused;
			R_Man.pauseSound.play(R_Man.Volume);
		}
		if(barPress == 4)
		{
			R_Man.soundMute();
			R_Man.muteSound.play();
		}
		
		int row = (int)(y/size);
		int column = (int)(x/size);
		  
		selectedX = column*(size+1) + size/2;
		selectedY = row*(size+1) + size/2;
		
		isSelected = true;
		selectedM = locateMovable(x, y);
	}
	//See abstrakt class Gamestate isTouched(x,y);
	public void isTouched(float x, float y)
	{		
		if(isPaused) return;
	    if (isSelected) findMovable(x, y);
	}
	
	 /**
   *Breaks the entities update into smaller steps so it wont render out of bounds.
   * @param dy is the delta time of each frame rendered
   */
	public void computeSubStep(float dy) {
		for(Movable[] row : Movables) {
    	  for (Movable m1 : row) {
    		  if(m1 == null || m1.row == 0) continue;
    		  if (m1.speed < 0 && 
				  System.currentTimeMillis() - m1.timeThrusted > 1000) {
				  if (Movables[m1.col][m1.row-1] != null && m1 != Movables[m1.col][m1.row-1] && m1.intersects(Movables[m1.col][m1.row-1])) {
					  Movable m2 = Movables[m1.col][m1.row-1];
					  m1.speed = m2.speed;
					  if (m1.y < m2.y+size) m1.y = m2.y+size;
//				      dangerColumn();
					  if(m1.row == 11) gameLost();
					  if(m1.speed>0){
	    				  m1.isBeingThrusted = true;
	    				  m1.timeThrusted = m2.timeThrusted;
	    			  }
	    			  handleMatches(m1);
				  }
    		  }
    		  // this happens when block can be unblacked
    		  if(m1.movableCheck()){
    			  Random ran = new Random();
    			  int x = ran.nextInt(4);
    			  for(int i = 0; i<4; i++){
    				  int[] tryType = integerType(((x+i)%4)+1);
    				  System.out.println(((x+i)%4)+1);
    				  boolean tryOne = toBoolean(tryType[0]);
    				  boolean tryTwo = toBoolean(tryType[1]);
    				  m1.setType(tryOne, tryTwo);
    				  int[] matchResults = findHorizontalMatches(m1);
    				  if(matchResults[0] == 0)
    					  break;
    			  }    				  
    			  
    			  
    		  }
        	  m1.update(dy);
        	  killBlock(m1);
    	  }
      }
	}
	
	public int[] integerType(int i){
		int[] result = new int[2];
		switch(i){
		case 1: 
			return result;
		case 2:
			result[1] = 1;
			return result;
		case 3: 
			result[0] = 1;
			return result;
		case 4:
			result[0] = 1;
			result[1] = 1;
			return result;
		}
		return result;
	}

	public boolean toBoolean(int i){
		return i == 1;
	}
	
	public int[] findHorizontalMatches(Movable m1) {
		   Boolean typeOne = m1.typeOne;
		   boolean typeTwo = m1.typeTwo;
		   int count = 0;
		   int row = m1.row;
		   int index = -1;
		   for(int j = 0; j < columns; j++){
			   for(int i = j;  i< columns; i++){
				   if( isSameType(Movables[i][row], m1)){
					   if(Movables[i][row].speed == 0){
						   count++;   
					   }   
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
	
	
	public void setUnmatchedType(){
		//viljum ad handlematches gefi bara boolean hvort thad se column eda row match
		//kollum svo a thau foll her:
		//gongum i gegnum typurnar, gefum kubb lit og gerum svo handlematches
		//ef thad matchast eitthvad tha profum vid naesta lit
		
	}
	
	/**
   *Checks to see if the player did indeed move a block to a valid position and to find if he added *three or more together
   * @param m1 A moved Movable block by the user
   */
	public void handleMatches(Movable m1)
	{
	   if (m1.typeOne == null) return;
	   checkRowMatches(m1);
	   checkColMatches(m1);
	   return;
	}
	   
   /**
   *Checks out if anyone is linked to the moved Movable in the Column
   * @param m1 A moved Movable block by the user
   */ 
   public void checkColMatches(Movable m1){
	   Boolean typeOne = m1.typeOne;
	   boolean typeTwo = m1.typeTwo;
	   int count = 0;
	   int col = m1.col;
	   int index = -1;
	   for(int j = 0; j < rows; j++){
		   for(int i = j;  i< rows; i++){
			   if( isSameType(Movables[col][i], m1)){
				   if(Movables[col][i].speed == 0){
					   count++;   
				   }   
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
	   if(count > 2){
		   int scoreToAdd = count;
		   int multiplier = 1;
		   for(int j = index; j < index+count; j++){ 
			   if(Movables[col][j].isPower)
			   {
				   if(Movables[col][j].power == "50") scoreToAdd += 50;
				   if(Movables[col][j].power == "2x") multiplier += 1;
				   Movables[col][j].isPower = false;
				   Movables[col][j].power = "";
			   }
			   
				Movables[col][j].typeOne = null;
				Movables[col][j].timeBlacked = System.currentTimeMillis();
		   }
		   scoreToAdd *= multiplier;
		   addScore(typeOne, typeTwo, scoreToAdd);
		   //TODO: Lata fallid gera miklu minna
		   //af hverju er shootrows herna inni
		   shootRows(m1.col, 1, index, false);
		   R_Man.shootSound.play(R_Man.Volume);
	   }
   }
	   
/**
   *Checks out if anyone is linked to the moved Movable in the Row. This one is bugged and needs *refactoring
   * @param m1 A moved Movable block by the user
   */
   public void checkRowMatches(Movable m1){
	   int row = m1.row;
	   Boolean typeOne = m1.typeOne;
	   boolean typeTwo = m1.typeTwo;
//	   int count = 0;
//	   int index = -1;
//	   for(int j = 0; j < columns; j++){
//		   for(int i = j;  i< columns; i++){
//			   if( isSameType(Movables[i][row], m1)){
//				   if(Movables[i][row].speed == 0){
//					   count++;   
//				   }   
//			   } else{
//				   break;
//			   }
//		   }
//		   if(count >= 3){
//			   index = j;
//			   break;
//		   }
//		   count = 0;
//	   }
	   int[] shootCoordinates = findHorizontalMatches(m1);
	   int index = shootCoordinates[1];
	   int count = shootCoordinates[2];
	   if(count > 2){
		   int scoreToAdd = count;
		   int multiplier = 1;
		   for(int j = index; j < index+count; j++){ 
			   if(Movables[j][row].isPower)
			   {
				   if(Movables[j][row].power == "50") scoreToAdd += 50;
				   if(Movables[j][row].power == "2x") multiplier += 1;
				   Movables[j][row].isPower = false;
				   Movables[j][row].power = "";
			   }
			   
				Movables[j][row].typeOne = null;
				Movables[j][row].timeBlacked = System.currentTimeMillis();
		   }
		   scoreToAdd *= multiplier;
		   addScore(typeOne, typeTwo, scoreToAdd);
		   shootRows(index, count, row, false);
		   R_Man.shootSound.play(R_Man.Volume);
	   }
   }

   //This should be fixed. 
	private void addScore(Boolean typeOne, boolean typeTwo, int aScore) 
	{
		if(typeOne == null) return;
		int swapToAdd = typeOne ? (typeTwo ? 0 : 2) : (typeTwo ? 1 : 3);
		swapScores[swapToAdd] += aScore;
		drawSwapScores[swapToAdd] = Integer.toString(swapScores[swapToAdd]);
	}
   //Gives the 3 or more blocks combined speed that thrusts them up.
   //Takes in parameters index and row that represents its columns and rows
   //The parameter isBeingThrusted is used for debugging tools
   public void shootRows(int index, int count, int row, boolean isBeingThrusted){
	   
	   isSelected = false;
	   if(isBeingThrusted){
		   //Vantar hér lógík til að skjóta platforminu alla leið upp
	   }
	   for(int j = index; j < index+count; j++){
		   for (int i = row; i < rows; i++){
			   if(Movables[j][i] != null) {
				   if(Movables[j][i].speed < 0) continue;
				   Movables[j][i].speed = 700;
			       Movables[j][i].timeThrusted = System.currentTimeMillis();   
			       Movables[j][i].isBeingThrusted = true; 
			   }
		   }
	   }
   }
	   
  /**
   *Finds out if the moved block was indeed the same color is the one moved in it�s direction
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
   *Passes the x,y coordinates of the screen on to it�s chiled methods to find out if the player is  *indeed clicking at a cube.
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
   //Finds the block that player when pressing down.
   //Takes in parameters world co ordinates x and y
   public void findMovable(float x, float y) {
	   int col = (int)(selectedX/size);
	   int row = (int)(selectedY/size)+1;
	   
	   if (row < 0 || row > rows-1 || col < 0 || col > columns-1) return;
	   
	   if(selectedM != null) {
		 if (selectedM.speed != 0) return;
		   if (y > selectedY + size) {
			   if (Movables[col][row+1] == null) return;
			   Movable targetM = new Movable(Movables[col][row+1]);
			   if (targetM.typeOne == null || targetM.speed != selectedM.speed) return;
			   swapMovables(targetM, selectedM.y, col, row, 1);
			   handleMatches(Movables[col][row]);
			   handleMatches(Movables[col][row+1]);
			   selectedY += size;
			   return;
		   }
		   
		   if (y < selectedY - size) {
			   if (Movables[col][row-1] == null) return;
			   Movable targetM = new Movable(Movables[col][row-1]);
			   if (targetM.typeOne == null || targetM.speed != selectedM.speed) return;
			   swapMovables(targetM, selectedM.y, col, row, -1);
			   handleMatches(Movables[col][row]);
			   handleMatches(Movables[col][row-1]);
			   selectedY -= size;
			   return;
		   }
		   
	   }
   }
	   
   /**
   *Swaps to different Movables if the move of the player was legit
   * @param m1 A moved Movable
   * @param m2 Movable that is going to be swapped
   * @param col the column of the moved cube
   * @param row the row of the moved cube
   * @param add integer that decides if we are swapping upwards or downwards
   */
   public void swapMovables(Movable m2, float y, int col, int row, int add) {
	   Movable temp1 = new Movable(selectedM);
	   Movable temp2 = new Movable(m2);
	   
	   Movables[col][row] = temp2;
	   Movables[col][row+add] = temp1;
	   
	   Movables[col][row].row = row;
	   Movables[col][row+add].row = row+add;
	   
	   Movables[col][row].y = y;
	   Movables[col][row+add].y = y+size*add;
	   
	   selectedM = Movables[col][row+add];
   }
	 //See abstrakt class Gamestate dispose();
	public void dispose()
	{
		
	}
	//Saves current score and sets the state to Lost. Called when game is lost
	public void gameLost()
	{
		R_Man.checkScore(swapScores);
		gsm.setState(GameStateManager.LOST);
		R_Man.resetMenu();
	}	
}
