package states;

import managers.AudioManager;
import managers.GameStateManager;
import managers.RectangleManager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.audio.Sound;

import entities.RectTex;

//Class by Óttar Guðmundsson
//Written 30.10.2014
//Creates a new state when user is viewing the menu
public class Menustate extends Gamestate {
	 
	
	 private RectangleManager R_Man;
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
		
		R_Man = RectMan;

		MenuArray[0] = R_Man.ButtonM.Menu;
		MenuArray[1] = R_Man.ButtonM.EnterPlay;
		MenuArray[2] = R_Man.ButtonM.EnterScore;
		MenuArray[3] = R_Man.ButtonM.EnterTut;
		MenuArray[4] = R_Man.ButtonM.EnterStore;
		//R_Man._r = R_Man._rOrg;
		//R_Man._g = R_Man._gOrg;
		//R_Man._b = R_Man._bOrg;
		
		//R_Man.horizontalSpeed = 600;
		//if(R_Man.moveFromSides) speedAdd = 65;
		//else speedAdd = 1;
		moveMenu = false;
		
		
	}
	//See abstrakt class Gamestate update(float dt);
	public void update(float dt)
	{
		if(moveMenu)
		{
			if(toPlay)
			{
				R_Man.horizontalSpeed += R_Man.speedAdd;
				R_Man.MenuYOffset += R_Man.horizontalSpeed*dt*stateDir;
				//for(int i = 0; i < MenuArray.length;i++)MenuArray[i].y += R_Man.horizontalSpeed*dt*stateDir;		
				R_Man.speedAdd++;
				if(R_Man.MenuYOffset <= -800) setPlayState(GameStateManager.PLAY);
				if(R_Man.MenuYOffset >= 800) setPlayState(GameStateManager.TUTORIAL);
				//if(MenuArray[0].y <= -(MenuArray[0].height + 64)) setPlayState(GameStateManager.PLAY);
				//if(MenuArray[4].y >= 800 + MenuArray[4].height) setPlayState(GameStateManager.TUTORIAL);	
			}	
			else
			{
				R_Man.verticalSpeed += R_Man.speedAdd;
				R_Man.MenuXOffset += R_Man.verticalSpeed*dt*stateDir;
				R_Man.speedAdd++;
				if(R_Man.MenuXOffset >= 480) gsm.setState(GameStateManager.SCORE);
				if(R_Man.MenuXOffset <= -480) gsm.setState(GameStateManager.STORE);
			}
		}
		if(R_Man.moveFromSides)EaseMenuBack(dt);
		if(R_Man.isMenuDown)
		{
			if(R_Man._r < R_Man._rOrg) R_Man._r += 6*dt;
			if(R_Man._g < R_Man._gOrg) R_Man._g += 6*dt;
			if(R_Man._b < R_Man._bOrg) R_Man._b += 6*dt;
			
			R_Man.horizontalSpeed -= R_Man.speedAdd;
			R_Man.MenuYOffset += R_Man.horizontalSpeed*dt;
			R_Man.speedAdd -= 2;
			if(R_Man.MenuYOffset >= 0)
			{
				//R_Man.resetMenu();
				R_Man.MenuYOffset = 0;
				R_Man.horizontalSpeed = 600;
				gsm.introEnd = false;
				R_Man.isMenuDown = false;
			}
		}
		
	}
	//Moves the menu back to its original position when user switches to Menu state.
	//Will be moved to the animation class when it has been written.
	private void EaseMenuBack(float dt)
	{
		R_Man.verticalSpeed -= R_Man.speedAdd;
		R_Man.MenuXOffset += R_Man.verticalSpeed*dt*R_Man.sideDir; //**sinnum r_man.dir
		R_Man.speedAdd--;
		if(R_Man.MenuXOffset*R_Man.sideDir*-1 <= 0) R_Man.moveFromSides = false;
	}
	
	//See abstrakt class Gamestate draw(SpriteBatch b);
	public void draw(SpriteBatch batch)
	{
		for(int i = 0; i < MenuArray.length; i++)batch.draw(MenuArray[i].tex, MenuArray[i].x+R_Man.MenuXOffset, MenuArray[i].y+R_Man.MenuYOffset);    
	}
	
	private void TransitionToState(boolean aActionState, int aDirection)
	{
		moveMenu = true;
		toPlay = aActionState;
		stateDir = aDirection;
		R_Man.horizontalSpeed = 600;
		R_Man.verticalSpeed = 600;
		R_Man.speedAdd = 1;
	}
	//See abstrakt class Gamestate justTouched(x,y);
	public void justTouched(float x, float y)
	{
		if(moveMenu || R_Man.moveFromSides ) return; //|| !R_Man.isMenuDown
		//Play
		if(buttonClick(MenuArray[1],x,y)) 
		{
			TransitionToState(true,-1);
			gsm.introStart = true; 
			R_Man.PlaySoundEffect(AudioManager.START);
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
		if (x < (rekt.x + rekt.width) && x > rekt.x && y > rekt.y && y < (rekt.y + rekt.height)) return true;
		return false;
	}
	//calls the main game loop to stop rendering the background with introend and tells the state machine manager to set the next state to playing state since the intro is finished
	private void setPlayState(int aState)
	{
		gsm.setState(aState);
		gsm.introEnd = true;
		gsm.introStart = false;
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
