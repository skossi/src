package states;

import managers.AudioManager;
import managers.GameStateManager;
import managers.RectangleManager;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.audio.Sound;

import entities.RectTex;

//Class by Óttar Guðmundsson
//Written 30.10.2014
//Creates a new state when user is viewing the menu
public class Menustate extends Gamestate {
	 
	 private RectangleManager Man;
	 public RectTex Menu;
	 public RectTex Play;
	 public RectTex Score;
	 public RectTex Tutorial;
	 public RectTex Store;
	 
	 private RectTex[] MenuArray;
	 
	 private boolean toPlay;
	 private int stateDir;
	 private boolean moveMenu;
	   
	//Constructor
	//See abskrakt class Gamestate(GameStateManager gsm);
	public Menustate(GameStateManager gsm)
	{	
		super(gsm);
	}
	//See abstrakt class Gamestate init();
	public void init(RectangleManager RectMan)
	{
		MenuArray = new RectTex[5];
		
		Man = RectMan;

		MenuArray[0] = Man.ButtonM.Menu;
		MenuArray[1] = Man.ButtonM.EnterPlay;
		MenuArray[2] = Man.ButtonM.EnterScore;
		MenuArray[3] = Man.ButtonM.EnterTut;
		MenuArray[4] = Man.ButtonM.EnterStore;
		moveMenu = false;
		
	}
	//See abstrakt class Gamestate update(float dt);
	public void update(float dt)
	{
		if(moveMenu)
		{
			if(toPlay)
			{
				//Horizontal is true
				Man.AnimationM.MenuAnimateMethod(false, 800, stateDir, GameStateManager.PLAY, GameStateManager.TUTORIAL,dt);
			}	
			else
			{
				Man.AnimationM.MenuAnimateMethod(true, 480, stateDir, GameStateManager.STORE, GameStateManager.SCORE,dt);
			}
		}
		if(Man.AnimationM.moveFromSides)Man.AnimationM.EaseMenuBack(dt);
		if(Man.AnimationM.isMenuDown)
		{
			if(Man._r < Man._rOrg) Man._r += 4*dt;
			if(Man._g < Man._gOrg) Man._g += 4*dt;
			if(Man._b < Man._bOrg) Man._b += 4*dt;
			Man.AnimationM.MenuDown(dt);
		}
		
	}

	
	//See abstrakt class Gamestate draw(SpriteBatch b);
	public void draw(SpriteBatch batch)
	{
		for(int i = 0; i < MenuArray.length; i++)
		{
			batch.draw
			(MenuArray[i].tex, 
			MenuArray[i].x+Man.AnimationM.MenuXOffset, 
			MenuArray[i].y+Man.AnimationM.MenuYOffset);   
		}
	}
	
	//Sets the direction of the transition.
	private void TransitionToState(boolean aActionState, int aDirection)
	{
		moveMenu = true;
		toPlay = aActionState;
		stateDir = aDirection;
	}
	//See abstrakt class Gamestate justTouched(x,y);
	public void justTouched(float x, float y)
	{
		if(moveMenu || Man.AnimationM.moveFromSides ) return; //|| !Man.isMenuDown
		//Play
		if(buttonClick(MenuArray[1],x,y)) 
		{
			TransitionToState(true,-1);
			gsm.introStart = true; 
			Man.playSoundEffect(AudioManager.START);
		}
		//Tutorial
		if(buttonClick(MenuArray[3],x,y))
		{
			//TODO:Implement what the tutorial state will hold.
			//Dont delete the next two lines here below, they are transition to the tutorial state.
			//TransitionToState(true,1);
			//gsm.introStart = true; 
		}
		//Scores
		if(buttonClick(MenuArray[2],x,y))
		{
			TransitionToState(false,1);
		}
		//Store
		if(buttonClick(MenuArray[4],x,y))
		{
			TransitionToState(false,-1);
		}
	}
	//Tells if user just pressed a corresponding rectangle
	//Takes in Rectangle Rekt that and x and y coordinates of world position
	public boolean buttonClick(RectTex rekt, float x, float y) {
		if (x < (rekt.x + rekt.width) && x > rekt.x && y > rekt.y && y < (rekt.y + rekt.height))
		{
			//rekt.y -= 10;
			return true;
		}
		return false;
	}
	
	//See abstrakt class Gamestate isTouched(x,y);
	public void isTouched(float x, float y)
	{
		
	}
	//See abstrakt class Gamestate dispose();
	public void dispose()
	{
	
	}
}
