package managers;

import states.Playstate;
import states.Scorestate;
import states.Storestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;



public class MyInputProcessor implements InputProcessor {
		
		

	
	   @Override
	   public boolean keyDown(int keycode) {
		   
		   int state = GameStateManager.ACTIVESTATE;
		   //if(keycode == Keys.HOME)
	        if(keycode == Keys.BACK)
	        {
	        	if(state == GameStateManager.PLAY) Playstate.isPaused = !Playstate.isPaused;
	        	if(state == GameStateManager.MENU)
	        	{
	        		Gdx.app.exit();  	
	        		//keyDown(Keys.HOME);
	        		//Application.
	        	}
	        	if(state == GameStateManager.SCORE)
	        	{
	        		AnimationManager.SideAnimation = true;
	        		Scorestate.screenDir = -1;
	        	}
	        	if(state == GameStateManager.STORE)
	        	{
	        		AnimationManager.SideAnimation = true;
	        		Storestate.screenDir = 1;
	        	}
	        	
	        }
	        return false;
	   }

	   @Override
	   public boolean keyUp (int keycode) {
	      return false;
	   }

	   @Override
	   public boolean keyTyped (char character) {
	      return false;
	   }

	   @Override
	   public boolean touchDown (int x, int y, int pointer, int button) {
	      return false;
	   }

	   // fire an event to Playstate when the user releases the touchpad
	   @Override
	   public boolean touchUp (int x, int y, int pointer, int button) {
		  Playstate.touchUp(x, y);
	      return true;
	   }

	   @Override
	   public boolean touchDragged (int x, int y, int pointer) {
	      return false;
	   }

	   @Override
	   public boolean mouseMoved (int x, int y) {
	      return false;
	   }

	   @Override
	   public boolean scrolled (int amount) {
	      return false;
	   }
	}
