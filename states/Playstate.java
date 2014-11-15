package states;

import managers.GameStateManager;

import managers.RectangleManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.TimeUtils;
import com.blokk.game.Movable;
import com.blokk.game.UI;

//Class by �ttar Gu�mundsson
//Written 30.10.2014
//Creates a new state when user is playing
public class Playstate extends Gamestate{

	   private Movable[][] Movables;
	   private long lastDropTime;
	   private int rows;
	   private int columns;
	   private boolean isPaused;
	   private int size;
	   private float selectedX;
	   private float selectedY;
	   private Texture square;
	   private Texture triangle;
	   private Texture circle;
	   private Texture ex;
	   private Texture black;
	   private Texture selected;
	   private Texture ui_bg;
	   private int steps;
	   private RectangleManager RectMana;

	   private Texture ui_bg;
	   private UI UI;
	   private BitmapFont font;
	   // public static so we can access it from the input processor
	   public static boolean isSelected;
	//Constructor
	//See abskrakt class Gamestate(GameStateManager gsm);
	public Playstate(GameStateManager gsm)
	{	
		super(gsm);
	}
	//See abstrakt class Gamestate init();
	public void init(RectangleManager RectMan)
	{
		RectMana = RectMan;
	      size = 64;
	      steps = size; //pixel perfect updating
	      columns = 7;
	      rows = 13;
	      Movables = new Movable[columns][rows];
		  square = new Texture(Gdx.files.internal("square.png"));
		  triangle = new Texture(Gdx.files.internal("triangle.png"));
		  circle = new Texture(Gdx.files.internal("circle.png"));
		  ex = new Texture(Gdx.files.internal("ex.png"));
		  black = new Texture(Gdx.files.internal("black.png"));
		  selected = new Texture(Gdx.files.internal("selected.png"));
		  ui_bg = new Texture(Gdx.files.internal("ui_bg.png"));
		  UI = new UI(0, 0, 480, 64);
		  font = new BitmapFont();
	      font.setColor(Color.BLACK);
	      font.setScale(2,2);
		  prepareMatrix();
	}
	/**
   * Creates a new cube on a timed interval. It�s type is randomed. This method is a temporary solution for spawning cubes in debugging mode
   *
   * @return            a new cube of some sort is created and placed in the grid
   */
   private void spawnMovable() {
		  Movable movable;
		  double randomize = Math.random();
		  if (randomize < 1) {
			  movable = new Movable(true);
			  movable.type = createType(movable.typeOne, movable.typeTwo);
		  }
		  else {
			  movable = new Movable(false);
			  movable.type = createType(movable.typeOne, movable.typeTwo);
		  }
		  
		  if (Movables[movable.col][rows-1] != null) return;
		  
	      int available_row = 0;
	      for (int i = 0; i < rows; i++) {
	    	  if (Movables[movable.col][i] == null) {
	    		  available_row = i;
	    		  break;
	    	  }
	      }
	      movable.row = available_row;
	      movable.x = (size+1)*movable.col;
	      movable.y = 800;
	      movable.speed = -600;
	      movable.width = size;
	      movable.height = size;
	      movable.isBeingThrusted = false;
		  Movables[movable.col][available_row] = movable;
	      lastDropTime = TimeUtils.nanoTime();
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
	       movable.y = 1;
	       movable.speed = 0;
	       movable.width = size;
	       movable.height = size;
	       movable.isBeingThrusted = false;
	       movable.x = (size+1)*i;
		   Movables[i][0] = movable;
	   }
   }
   
   public void handleOutOfBounds(Movable m1){
	   if (m1.y > 800){
		   Movables[m1.col][m1.row] = null;
	   }
   }
   /**
   * Gives a created cube its texture depend on his boolean tree structure    
   * 
   * @param typeOne file Boolean which decides if it is a movable cube or black block
   * @param typeTwo boolean that decides what kind of color the cube is
   * @return            returns Texture corresponding to it�s boolean structure
   */   
	private Texture createType(Boolean typeOne, boolean typeTwo) 
	{
			if (typeOne == null) return black;
			return (typeOne ? (typeTwo ? square : circle) : (typeTwo ? triangle : ex));
	}
	//See abstrakt class Gamestate update(float dt);
	public void update(float dt)
	{
		if (isPaused) return;
		if (TimeUtils.nanoTime() - lastDropTime > 900000000) spawnMovable();
		for (int i = 0; i < steps; i++) computeSubStep(dt/steps);
	}
	//See abstract class Gamestate draw(SpriteBatch b);
	public void draw(SpriteBatch batch)
	{
	      for(int i = 0; i < columns; i++) {
	    	  for (int j = 0; j < rows; j++) {
	    		  Movable m = Movables[i][j];
	    		  if (m != null) batch.draw(createType(m.typeOne,m.typeTwo), m.x, m.y); // afhverju ekki m.type h�r?
	    		  //else if (m != null && m.speed == 0) batch.draw(createType(m.typeOne,m.typeTwo), i*65, j*65);
	    	  }
	      }
	      if(isSelected)batch.draw(selected, selectedX-size/2, selectedY-size/2);
	      batch.draw(ui_bg, UI.x, UI.y, UI.width, UI.height);
		font.draw(batch, "1 2 3 4 5 6 7 8 9", 120, 50);

	}
	//See abstrakt class Gamestate justTouched(x,y);
	public void justTouched(float x, float y)
	{
		selectedX = x;
		selectedY =y;
		  
		int row = (int)(selectedY/size);
		int column = (int)(selectedX/size);
		  
		selectedX = column*65 + size/2;
		selectedY = row*65 + size/2;
		
		isSelected = true;
	}
	//See abstrakt class Gamestate isTouched(x,y);
	public void isTouched(float x, float y)
	{
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
					  if(m1.speed>0){
	    				  m1.isBeingThrusted = true;
	    				  m1.timeThrusted = m2.timeThrusted;
	    			  }
	    			  handleMatches(m1);
				  }
    		  }
        	  m1.update(dy);
        	  handleOutOfBounds(m1);
    	  }
      }
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
			   if( isSameType(Movables[col][i], typeOne, typeTwo)){
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
	   if(count > 1){
		   for(int j = index; j < index+count; j++){
			   Movables[col][j].type = circle;
		   }
	   }
	   return;
   }
	   
/**
   *Checks out if anyone is linked to the moved Movable in the Row. This one is bugged and needs *refactoring
   * @param m1 A moved Movable block by the user
   */
   public void checkRowMatches(Movable m1){
	   //Stundum kemur villa thegar kubbur dettur nidur a milli tveggja kubba
	   //thad sem gerist er ad kubburinn er merktur sem ad hann matchist adur en hann
	   //er buinn ad detta nidur
	   Boolean typeOne = m1.typeOne;
	   boolean typeTwo = m1.typeTwo;
	   int count = 0;
	   int row = m1.row;
	   int index = -1;
	   for(int j = 0; j < columns; j++){
		   for(int i = j;  i< columns; i++){
			   if( isSameType(Movables[i][row], typeOne, typeTwo)){
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
	   if(count > 2){
//		   for(int j = index; j < index+count; j++){
//			   Movables[j][row].type = circle;
//		   }
		   shootRows(index, count, row, false);
	   }
	   return;
   }
	   

public void shootRows(int index, int count, int row, boolean isBeingThrusted){
	   System.out.println("Shooting!");
	   isSelected = false;
	   if(isBeingThrusted){
		   //Vantar hér lógík til að skjóta platforminu alla leið upp
	   }
	   for(int j = index; j < index+count; j++){
		   for (int i = row; i < rows; i++){
			   if(Movables[j][i] != null) {
				   if(Movables[j][i].speed < 0) continue;
				   Movables[j][i].speed = 400;
			       Movables[j][i].timeThrusted = System.currentTimeMillis();   
			       Movables[j][i].isBeingThrusted = true;
				   System.out.println("blokk: " + Movables[j][i].row + ", " + Movables[j][i].col + ", " + Movables[j][i].speed);   
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
   public boolean isSameType(Movable m1, Boolean typeOne, boolean typeTwo){
	   if(m1 == null){return false;}
	   return m1.typeOne == typeOne && m1.typeTwo == typeTwo;
   }
	   
   /**
   *Passes the x,y coordinates of the screen on to it�s chiled methods to find out if the player is  *indeed clicking at a cube.
   * @param x X-coordinates of the screen
   * @param y Y-coordinates of the screen
   */
   public void findMovable(float x, float y) {
	   int row = (int)(selectedY/size)+1;
	   int col = (int)(selectedX/size);
	   
	   if (row < 0 || row > rows-1 || col < 0 || col > columns-1) return;
	   
	   if (Movables[col][row] != null && Movables[col][row].typeOne != null) {
		   Movable selectedM = new Movable(Movables[col][row]);
		   
		   if (row < 11 && Movables[col][row+1] != null) {
			   Movable targetM = new Movable(Movables[col][row+1]);
			   
			   if (y > selectedY + size && targetM != null && targetM.typeOne != null && targetM.speed == 0) {
				   selectedY += size;
				   swapMovables(selectedM, targetM, selectedM.y, col, row, 1);
				   handleMatches(Movables[col][row]);
				   handleMatches(Movables[col][row+1]);
			   }
		   }
		   
		   if (row > 0 && Movables[col][row-1] != null) {
			   Movable targetM = new Movable(Movables[col][row-1]);
			   
			   if (y < selectedY - size && targetM != null && targetM.typeOne != null && targetM.speed == 0) {
				   selectedY -= size;
				   swapMovables(selectedM, targetM, selectedM.y, col, row, -1);
				   handleMatches(Movables[col][row]);
				   handleMatches(Movables[col][row-1]);
			   }
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
   public void swapMovables(Movable m1, Movable m2, float y, int col, int row, int add) {
	   Movable temp1 = new Movable(m1);
	   Movable temp2 = new Movable(m2);
	   
	   Movables[col][row] = temp2;
	   Movables[col][row+add] = temp1;
	   
	   Movables[col][row].row = row;
	   Movables[col][row+add].row = row+add;
	   
	   Movables[col][row].y = y;
	   Movables[col][row+add].y = y+size*add;
   }
	 //See abstrakt class Gamestate dispose();
	public void dispose()
	{
	
	}
}
