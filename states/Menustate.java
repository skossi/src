package states;

import managers.GameStateManager;
import managers.RectangleManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.audio.Sound;

//Class by Óttar Guðmundsson
//Written 30.10.2014
//Creates a new state when user is viewing the menu
public class Menustate extends Gamestate {
	   
	 public RectTex Menu;
	 public RectTex Play;
	 public RectTex Score;
	 public RectTex Tutorial;
	 
	 private int downSpeed;
	   
	 private int width;
	 private int height;
	 private int numButtons;
	 private int[] buttonPos;
	 private RectTex[] buttons;
	 public boolean intro;
	// private RectangleAdd RectangleManager;
	 private RectangleManager RectMana;
	   
	//Constructor
	//See abskrakt class Gamestate(GameStateManager gsm);
	public Menustate(GameStateManager gsm)
	{	
		super(gsm);
	}
	//See abstrakt class Gamestate init();
	public void init(RectangleManager RectMan)
	{
		downSpeed = 600;		
		RectMana = RectMan;
		Menu = RectMana.Menu;
		Play = RectMana.EnterPlay;
		Score = RectMana.EnterScore;
		Tutorial = RectMana.EnterTut;
	}
	//See abstrakt class Gamestate update(float dt);
	public void update(float dt)
	{
		if(intro)
		{
			Menu.y += -downSpeed*dt;
			Play.y += -downSpeed*dt;
			Score.y += -downSpeed*dt;
			Tutorial.y += -downSpeed*dt;
			if(Menu.y <= -(Menu.height + 64)) setGame();
		}
		
	}
	//See abstrakt class Gamestate draw(SpriteBatch b);
	public void draw(SpriteBatch batch)
	{
		  batch.draw(Menu.tex, Menu.x, Menu.y);
	      batch.draw(Play.tex, Play.x, Play.y);
	      batch.draw(Score.tex, Score.x, Score.y);
	      batch.draw(Tutorial.tex, Tutorial.x, Tutorial.y);
	}
	//See abstrakt class Gamestate justTouched(x,y);
	public void justTouched(float x, float y)
	{
		if(intro) return;
		if(buttonClick(Play,x,y)) {gsm.introStart = true; intro = true;}
		if(buttonClick(Score,x,y))gsm.setState(GameStateManager.SCORE);
		if(buttonClick(Tutorial,x,y))gsm.setState(GameStateManager.TUTORIAL);
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
