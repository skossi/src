package com.blokk.game;

import managers.GameStateManager;
import managers.RectangleManager;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;


/**
 * @author     Ottar og Þorsteinn. Edit by Hlynur
 * @version     1.0a                 Alpha
 * @since       2014-10-10        
 */
public class BlokkGame implements ApplicationListener {

   private SpriteBatch batch;
   private OrthographicCamera camera;
   private float dy;
   
   private GameStateManager gsm;
   private RectangleManager rsm;
   
   private float _r;
   private float _b;
   private float _g;

   //BACKGROUND
     private Movable[][] Movables;
	 private long lastDropTime;
	 private int rows;
	 private int columns;
	 private int size;
	 private Texture square;
	 private Texture triangle;
	 private Texture circle;
	 private Texture ex;
	 private Texture black;
	 private int steps;

   /**
   * Starts the gameloop by opening components from badlogic pack and sets the orthogonal projection of the camera.
   *
   */	 
   @Override
   public void create() {
      camera = new OrthographicCamera();
      camera.setToOrtho(false, 480, 800);
      batch = new SpriteBatch();
      rsm = new RectangleManager();
      gsm = new GameStateManager(rsm);
      
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
	  lastDropTime = TimeUtils.nanoTime();
	  
	  _r = 1f;//0.43f;
	  _g = 178/255f;//0.5f;
	  _b = 0;//0.2f;
   }
   
   /**
   * Clears the window and draws all entities. Sends coordinates to methods to find out where player *is touching
   *
   */
   @Override
   public void render() {
      Gdx.gl.glClearColor(_r, _g, _b, 1);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
      
      dy = Gdx.graphics.getDeltaTime()/3;
      gsm.update(dy);
      
      camera.update();

      batch.setProjectionMatrix(camera.combined);
      
      batch.begin();
      if(!gsm.introEnd) spawnBackground();
      gsm.draw(batch);
      batch.end();
      
      if (Gdx.input.justTouched()) {
    	  
    	  Vector3 touchPosOld = new Vector3();
    	  touchPosOld.set(Gdx.input.getX(),Gdx.input.getY(),0);
    	  camera.unproject(touchPosOld);
    	  gsm.justTouched(touchPosOld.x, touchPosOld.y);
      }
      
      if(Gdx.input.isTouched()) {
         Vector3 touchPos = new Vector3();
         touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
         camera.unproject(touchPos);
         gsm.isTouched(touchPos.x, touchPos.y);
      }
   }

   /**
    * Creates an endless moving background for all states except Playstate
    *
    */
   public void spawnBackground()
   {
	 if (TimeUtils.nanoTime() - lastDropTime > 900000000 && !gsm.introStart) spawnMovable();
	 		for (int i = 0; i < steps; i++) computeSubStep(dy/steps);

	 if(gsm.introStart)
	 {
		 if(_r > 0) _r -= 0.6*dy;
		 if(_g > 0) _g -= 0.25*dy;
		// if(_g > 0) _g -= 0.4*dy;
	 }
	   
	   for(int i = 0; i < columns; i++) {
	    	  for (int j = 0; j < rows; j++) {
	    		  Movable m = Movables[i][j];
	    		  if (m != null) batch.draw(createType(m.typeOne,m.typeTwo), m.x, m.y); // afhverju ekki m.type hér?
	    	  }
	      }
   }
   
   /**
    * Gives a created cube it’s texture depend on his boolean tree structure
    *
    * @param typeOne file Boolean which decides if it is a movable cube or black block
    * @param typeTwo boolean that decides what kind of color the cube is
    * @return            returns Texture corresponding to it’s boolean structure
    */
	private Texture createType(Boolean typeOne, boolean typeTwo) 
	{
			if (typeOne == null) return black;
			return (typeOne ? (typeTwo ? square : circle) : (typeTwo ? triangle : ex));
	}
	
	/**
	*Breaks the entities update into smaller steps so it wont render out of bounds.
	* @param dy is the delta time of each frame rendered
	*/ 
	public void computeSubStep(float dt) {
		for(Movable[] rows : Movables) {
	    	  for (Movable m1 : rows) {
	    		  if(m1 == null) continue;
	        	  m1.update(dt);
	        	  if(m1.y <= -size) m1 = null;//m1.y = 850;
	    	  }
	      }
	  }
	
	/**
	* Creates a new cube on a timed interval. It’s type is randomed. This method is a temporaty solution for spawning cubes in debugging mode
	*
	* @return            a new cube of some sort is created and placed in the grid
	*/
	private void spawnMovable() {
		  
		  Movable movable;
		  movable = new Movable(true);
		  movable.type = createType(movable.typeOne, movable.typeTwo);
		  
	      int available_row = 0;
	      for (int i = 0; i < rows; i++) {
	    	  if (Movables[movable.col][i] == null) {
	    		  available_row = i;
	    		  break;
	    	  }
	      }
	      Movables[movable.col][available_row] = movable;
	      movable.row = available_row;
	      movable.x = (size+1)*movable.col;
	      movable.y = 800;
	      movable.speed = -600;
	      movable.width = size;
	      movable.height = size;
	      lastDropTime = TimeUtils.nanoTime();
	   }
   
   @Override
   public void dispose() {
      // dispose of all the native resources
      batch.dispose();
   }

   @Override
   public void resize(int width, int height) {
   }

   @Override
   public void pause() {
   }

   @Override
   public void resume() {
   }
}