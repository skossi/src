package states;

import managers.GameStateManager;
import managers.RectangleManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//import com.badlogic.gdx.audio.Sound;
//Class by Óttar Guðmundsson
//Written 14.11.2014
//Creates a new state when game is lost
public class Loststate extends Gamestate{

	private RectTex GameOver;
	private RectTex Back;
	private RectangleManager RectMana;
	private boolean higher;
	private int scoreOne;
	private int scoreTwo;
	private int scoreThr;
	
	private String scoreMade;
	
	private BitmapFont font;
	private String better;
	private String worse;
	
	//Constructor
	//See abskrakt class Gamestate(GameStateManager gsm);
	public Loststate(GameStateManager gsm)
	{	
		super(gsm);
	}
	//See abstrakt class Gamestate init();
	public void init(RectangleManager RectMan)
	{
		RectMana = RectMan;
		GameOver = RectMana.Over;
		Back = RectMana.Back;
		
		scoreMade = Integer.toString(RectMana.currScore);
		higher = RectMana.grats;
		scoreOne = RectMana.one;
		scoreTwo = RectMana.two;
		scoreThr = RectMana.thr;
		
		font = RectMana.font;
		better = RectMana.betterString;
		worse = RectMana.worseString;
		
		
	}
	//See abstrakt class Gamestate update(float dt);
	public void update(float dt)
	{
		if(RectMana._r < 1) RectMana._r += dt;
		if(RectMana._g < 0.7) RectMana._g += dt;
		if(RectMana._w > 0) RectMana._w -= 3*dt;
	}
	//See abstrakt class Gamestate draw(SpriteBatch b);
	public void draw(SpriteBatch batch)
	{
		batch.draw(GameOver.tex, GameOver.x, GameOver.y);
		if(higher)font.draw(batch,better , 60, 625);
		else font.draw(batch,worse , 45, 625);
		font.draw(batch,"Your score was : " + scoreMade , 105, 575);
		
		font.draw(batch, "1st : " + scoreOne, 175, 500);
		font.draw(batch, "2nd : " + scoreTwo, 175, 400);
		font.draw(batch, "3rd : " + scoreThr, 175, 300);
		
		batch.draw(Back.tex, Back.x, Back.y);
	}
	
	//See abstrakt class Gamestate justTouched(x,y);
	public void justTouched(float x, float y)
	{
		if(buttonClick(Back,x,y)) gsm.setState(GameStateManager.MENU);
	}
	//Tells if user just pressed a corresponding rectangle
	//Takes in Rectangle Rekt that and x and y coordinates of world position
	public boolean buttonClick(RectTex rekt, float x, float y) {
		if (x < (rekt.x + rekt.width) && x > rekt.x && y > rekt.y && y < (rekt.y + rekt.height)) return true;
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
