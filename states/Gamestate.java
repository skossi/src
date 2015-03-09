package states;
import managers.GameStateManager;
import managers.RectangleManager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//Class by Óttar Guðmundsson
//Written 30.10.2014
//Abstrakt class for new state when new state is created
public abstract class Gamestate {
	
	protected GameStateManager gsm;
	
	//Constructor
	//Takes in the main game state machine gsm and calls  the initiation of each state when it is set.
	protected Gamestate(GameStateManager gsm)
	{
		this.gsm = gsm;
		
	}
	//The initation of each state machine. Each state holds it owns initiation parameters to create needed entites
	public abstract void init(RectangleManager RectMana);
	//update each state with parameter float dt which is passed from the main game screen
	public abstract void update(float dt);
	//render each state with parameter Spritebatch b which is created and passed from the main game screen
	public abstract void draw(SpriteBatch b);
	//event listener if user presses the screen in each state. Takes the parameters of orthogonic position of the screen that is passed from the main screen
	public abstract void justTouched(float x, float y);
	//event listener if user is pressing the screen in each state. Takes the parameters of orthogonic position of the screen that is passed from the main screen
	public abstract void isTouched(float x, float y);
	//called each time a state is exited and a new state is called. Disposes batches and parameters which arent used.
	public abstract void dispose();
	
}
