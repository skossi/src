package managers;

import states.Gamestate;
import states.Menustate;
import states.Playstate;
import states.Scorestate;
import states.Tutorialstate;
import states.Loststate;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//Class by �ttar Gu�mundsson
//Written 30.10.2014
//Manages the states that are available in the app. Is responsible for passing deltas and batches to active states
public class GameStateManager{
	private Gamestate gameState;
	
	public static final int MENU = 0;
	public static final int PLAY = 1;
	public static final int SCORE = 2;
	public static final int TUTORIAL = 3;
	public static final int LOST = 4;
	
	public boolean introStart;
	public boolean introEnd;
	//public RectangleAdd RectMan = new RectangleAdd();
	private RectangleManager RectMana;
	
	
	//initation of the gamestate manager. Called when a new manager is created and sets to starting state
	public GameStateManager(RectangleManager rsm)
	{
		RectMana = rsm;
		if(rsm.firstTime)setState(MENU);
		else setState(TUTORIAL);
		
	}
	//Takes in parameter static final int state which stands for each 
	public void setState(int state)
	{
		if(gameState != null) gameState.dispose();
		if(state == MENU) gameState = new Menustate(this);
		if(state == PLAY) gameState = new Playstate(this);
		if(state == SCORE) gameState = new Scorestate(this);
		if(state == TUTORIAL) gameState = new Tutorialstate(this);
		if(state == LOST) gameState = new Loststate(this);
		
		gameState.init(RectMana);
	}
	//update each state with parameter float dt which is passed from the main game screen
	public void update(float dt)
	{
		gameState.update(dt);
	}

	//render each state with parameter Spritebatch b which is created and passed from the main game screen
	public void draw(SpriteBatch b)
	{
		gameState.draw(b);
	}

	//event listener if user presses the screen in each state. Takes the parameters of orthogonic position of the screen that is passed from the main screen
	public void justTouched(float x, float y)
	{
		gameState.justTouched(x,y);
	}

	//event listener if user is pressing the screen in each state. Takes the parameters of orthogonic position of the screen that is passed from the main screen
	public void isTouched(float x, float y)
	{
		gameState.isTouched(x,y);
	}
}
