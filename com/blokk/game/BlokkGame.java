package com.blokk.game;

import managers.AudioManager;
import managers.GameStateManager;
import managers.RectangleManager;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import entities.Movable;
import entities.RectTex;


/**
 * @author     Ottar og �orsteinn. Edit by Hlynur
 * @version     1.0a                 Alpha
 * @since       2014-10-10        
 */
public class BlokkGame implements ApplicationListener {

   private SpriteBatch batch;
   private OrthographicCamera camera;
   private float dy;
   
   private GameStateManager gsm;
   private RectangleManager R_Man;

   //BACKGROUND
   private Movable[][] Movables;
   private long lastDropTime;
   private int rows;
   private int columns;
   private int size;
   private int steps;
   //TODO : Implemtent warning if nominal update is too big?
  // private int _CONST_NOMINALUPDATE = 16;
   
   /**
   * Starts the gameloop by opening components from badlogic pack and sets the orthogonal projection of the camera.
   *
   */	 
   @Override
   public void create() {
      camera = new OrthographicCamera();
      camera.setToOrtho(false, 480, 800);
      batch = new SpriteBatch();
      
      R_Man = new RectangleManager();
      
      gsm = new GameStateManager(R_Man);
      R_Man.gsm = gsm;
      R_Man.assignAnimation(gsm);

      size = 68;
      steps = size; //pixel perfect updating
      columns = 7;
      rows = 13;
      Movables = new Movable[columns][rows];
	  lastDropTime = 0;//TimeUtils.nanoTime();
	  spawnMovable();
   }
   
   /**
   * Clears the window and draws all entities. Sends coordinates to methods to find out where player *is touching
   *
   */
   @Override
   public void render() {
	  Gdx.gl.glClearColor(R_Man._r, R_Man._g, R_Man._b, 1);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
   
      dy = Gdx.graphics.getDeltaTime()/3;
      
      //TODO : Use this : if(dy >= 200) dy = _CONST_NOMINALUPDATE/3;
      
      gsm.update(dy);
      
      camera.update();

      batch.setProjectionMatrix(camera.combined);
      
      batch.begin();
      if(!gsm.introEnd) spawnBackground();	  
      
      gsm.draw(batch);
      
      if(R_Man.isMuted)R_Man.drawButton(batch, R_Man.ButtonM.SoundOff, 0, 0, false);
      else R_Man.drawButton(batch, R_Man.ButtonM.SoundOn, 0, 0, false);
      
      batch.end();
      
      if (Gdx.input.justTouched()) 
      {  
    	  Vector3 touchPosOld = new Vector3();
    	  touchPosOld.set(Gdx.input.getX(),Gdx.input.getY(),0);
    	  camera.unproject(touchPosOld);
    	  gsm.justTouched(touchPosOld.x, touchPosOld.y);
    	  
		if(buttonClick(R_Man.ButtonM.SoundOn,touchPosOld.x,touchPosOld.y) || buttonClick(R_Man.ButtonM.SoundOff,touchPosOld.x,touchPosOld.y))
		{
			R_Man.soundMute();
			R_Man.playSoundEffect(AudioManager.MUTE);
		}  	  
      }
      
      if(Gdx.input.isTouched()) 
      {
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
   
   private boolean buttonClick(RectTex rekt, float x, float y) {
		if (x < (rekt.x + rekt.width) && x > rekt.x && y > rekt.y && y < (rekt.y + rekt.height))
		{
			rekt.pressedEffect();
			return true;
		}
		return false;
	}
   
   private void killBackground()
   {
	   for(int i = 0; i < columns; i++) 
	   {
			for (int j = 0; j < rows; j++) 
			{
				Movable m = Movables[i][j];
				if (m != null && !m.isDead)
				{
					m.isDead = true;
					m.deathFrame = 0;
				}
			}
		}
   }
   
   public void spawnBackground()
   {
		if (TimeUtils.nanoTime() - lastDropTime > 900000000/1.1 && !gsm.introStart) spawnMovable();
		if(gsm.introStart)killBackground();
		for (int i = 0; i < steps; i++) computeSubStep(dy/steps);
		for(int i = 0; i < columns; i++) 
		{
			for (int j = 0; j < rows; j++) 
			{
				Movable m = Movables[i][j];
				if (m != null)
				{
					if(m.y < 0-size)Movables[m.col][m.row] = null;
					if (m.isDead) 
				    {
				    	batch.draw(R_Man.TextureM.kill[m.deathFrame], m.x, m.y);
				    	m.deathFrame++;
						if(m.deathFrame > 7)Movables[m.col][m.row] = null;
				    }
					else batch.draw(createType(m.typeOne,m.typeTwo), m.x, m.y); 
				}
			}
		}
   }
   
   /**
    * Gives a created cube it�s texture depend on his boolean tree structure
    *
    * @param typeOne file Boolean which decides if it is a movable cube or black block
    * @param typeTwo boolean that decides what kind of color the cube is
    * @return            returns Texture corresponding to it�s boolean structure
    */
   private Texture createType(Boolean typeOne, boolean typeTwo) 
	{
			if (typeOne == null)
			{
				if(!typeTwo)
					return R_Man.TextureM.black;
				else
					return R_Man.TextureM.blinkBlack;
			}
			return (typeOne ? (typeTwo ? R_Man.TextureM.square : R_Man.TextureM.circle) 
					: (typeTwo ? R_Man.TextureM.triangle : R_Man.TextureM.ex));
	}
	
	/**
	*Breaks the entities update into smaller steps so it wont render out of bounds.
	* @param dy is the delta time of each frame rendered
	*/ 
	public void computeSubStep(float dt) {
		for(Movable[] rows : Movables) {
	    	  for (Movable m1 : rows) {
	    		  if(m1 == null) continue;
	    		  
	    		 // if(gsm.introStart)m1.speed = -R_Man.AnimationM.verticalSpeed;
	    		 // else 
	    		  m1.speed = -600;
	        	  m1.update(dt);
	        	  if(m1.y <= -size) m1 = null;//m1.y = 850;
	    	  }
	      }
	  }
	
	/**
	* Creates a new cube on a timed interval. It�s type is randomed. This method is a temporaty solution for spawning cubes in debugging mode
	*
	* @return            a new cube of some sort is created and placed in the grid
	*/
	private void spawnMovable() 
	{	  
		  Movable movable;
		  movable = new Movable(true);
		  movable.col = MathUtils.random(0, 6);
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
	      movable.x = (float) (size+1)*movable.col; 
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