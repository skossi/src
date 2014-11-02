package com.blokk.game;

import managers.GameStateManager;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;



public class BlokkGame implements ApplicationListener {
	

   private SpriteBatch batch;
   private OrthographicCamera camera;
   private float dy;
   
   private GameStateManager gsm;

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

   @Override
   public void create() {
      camera = new OrthographicCamera();
      camera.setToOrtho(false, 480, 800);
      batch = new SpriteBatch();
      gsm = new GameStateManager();
      
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
   }
   
   @Override
   public void render() {
      Gdx.gl.glClearColor(0.43f, 0.5f, 0.2f, 1);
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

   public void spawnBackground()
   {
	 if (TimeUtils.nanoTime() - lastDropTime > 900000000 && !gsm.introStart) spawnMovable();
	 		for (int i = 0; i < steps; i++) computeSubStep(dy/steps);
	 	
	   
	   for(int i = 0; i < columns; i++) {
	    	  for (int j = 0; j < rows; j++) {
	    		  Movable m = Movables[i][j];
	    		  if (m != null && !(m.speed == 0)) batch.draw(createType(m.typeOne,m.typeTwo), m.x, m.y); // afhverju ekki m.type hér?
	    		  else if (m != null && m.speed == 0) batch.draw(createType(m.typeOne,m.typeTwo), i*65, j*65);
	    	  }
	      }
   }
   
	private Texture createType(Boolean typeOne, boolean typeTwo) 
	{
			if (typeOne == null) return black;
			return (typeOne ? (typeTwo ? square : circle) : (typeTwo ? triangle : ex));
	}
	
	public void computeSubStep(float dt) {
		for(Movable[] rows : Movables) {
	    	  for (Movable m1 : rows) {
	    		  if(m1 == null) continue;
	        	  m1.update(dt);
	        	  if(m1.y <= -size) m1 = null;//m1.y = 850;
	    	  }
	      }
	  }
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