package managers;

import states.Playstate;
import states.Loststate;
import states.Menustate;
import states.Scorestate;
import states.Storestate;
import managers.RectangleManager;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class MyInputProcessor implements InputProcessor {
	   @Override
	   public boolean keyDown(int keycode) {
	        if(keycode == Keys.BACK){
	        	  Playstate.isPaused = !Playstate.isPaused;
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
		  Playstate.isSelected = false;
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
