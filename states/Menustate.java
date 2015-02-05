package states;

import managers.GameStateManager;
import managers.RectangleManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.audio.Sound;

//Class by Óttar Guðmundsson
//Written 30.10.2014
//Creates a new state when user is viewing the menu
public class Menustate extends Gamestate {
	   
	 public RectTex Menu;
	 public RectTex Play;
	 public RectTex Score;
	 public RectTex Tutorial;
	 public RectTex Store;
	 
	 private int downSpeed;
	 public boolean intro;
	// private RectangleAdd RectangleManager;
	 private RectangleManager R_Man;
	 private int speedAdd;
	 private Sound gameSound;
	   
	//Constructor
	//See abskrakt class Gamestate(GameStateManager gsm);
	public Menustate(GameStateManager gsm)
	{	
		super(gsm);
	}
	//See abstrakt class Gamestate init();
	public void init(RectangleManager RectMan)
	{
		
		gsm.introEnd = false;
		intro = false;
			
		R_Man = RectMan;
		R_Man.backgroundSpeed = 600;
		downSpeed = R_Man.backgroundSpeed;	
		Menu = R_Man.Menu;
		Play = R_Man.EnterPlay;
		Score = R_Man.EnterScore;
		Tutorial = R_Man.EnterTut;
		Store = R_Man.EnterStore;
		R_Man._r = R_Man._rOrg;
		R_Man._g = R_Man._gOrg;
		R_Man._b = R_Man._bOrg;
		R_Man._w = 0;
		speedAdd = 1;
		gameSound = Gdx.audio.newSound(Gdx.files.internal("startup.wav"));
		
	}
	//See abstrakt class Gamestate update(float dt);
	public void update(float dt)
	{
		if(intro)
		{
			R_Man.backgroundSpeed +=speedAdd;
			downSpeed = R_Man.backgroundSpeed;
			Menu.y -= downSpeed*dt;
			Play.y -= downSpeed*dt;
			Score.y -= downSpeed*dt;
			Tutorial.y -= downSpeed*dt;
			Store.y -= downSpeed*dt;
			if(Menu.y <= -(Menu.height + 64)) setGame();
			speedAdd +=1;
		}
		
	}
	//See abstrakt class Gamestate draw(SpriteBatch b);
	public void draw(SpriteBatch batch)
	{
		  batch.draw(Menu.tex, Menu.x, Menu.y);
	      batch.draw(Play.tex, Play.x, Play.y);
	      batch.draw(Score.tex, Score.x, Score.y);
	      batch.draw(Tutorial.tex, Tutorial.x, Tutorial.y);
	      batch.draw(Store.tex, Store.x, Store.y);
	}
	//See abstrakt class Gamestate justTouched(x,y);
	public void justTouched(float x, float y)
	{
		if(intro) return;
		if(buttonClick(Play,x,y)) 
		{
			gsm.introStart = true; 
			intro = true;
			gameSound.play(R_Man.Volume);
		}
		if(buttonClick(Score,x,y))gsm.setState(GameStateManager.SCORE);
		if(buttonClick(Tutorial,x,y))gsm.setState(GameStateManager.TUTORIAL);
		if(buttonClick(Store,x,y))gsm.setState(GameStateManager.STORE);
	}
	//Tells if user just pressed a corresponding rectangle
	//Takes in Rectangle Rekt that and x and y coordinates of world position
	public boolean buttonClick(RectTex rekt, float x, float y) {
		if (x < (rekt.x + rekt.width) && x > rekt.x && y > rekt.y && y < (rekt.y + rekt.height)) return true;
		return false;
	}
	//calls the main game loop to stop rendering the background with introend and tells the state machine manager to set the next state to playing state since the intro is finished
	private void setGame()
	{
		gsm.setState(GameStateManager.PLAY);
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
