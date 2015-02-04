package states;

import managers.GameStateManager;
import managers.RectangleManager;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//Class by Óttar Guðmundsson
//Written 25.11.2014
//Creates a new state when store is viewed. There is no activity here
//This is only to show what this app might be capable off
public class Storestate extends Gamestate {

	   private RectTex Back;
	   private RectTex Store;
	   private RectangleManager R_Man;
	   
	   private int scoreOne;
	   private int scoreTwo;
	   private int scoreThr;
	   
	   private String dispCurrency;
	   
	   private BitmapFont font;
	   
	   
	//Constructor
	//See abskrakt class Gamestate(GameStateManager gsm);
	public Storestate(GameStateManager gsm)
	{	
		super(gsm);
	}
	//See abstrakt class Gamestate init();
	public void init(RectangleManager RectMan)
	{
		R_Man = RectMan;

		Store = R_Man.Store;
		Back = R_Man.Back;
		
		font = R_Man.font;
		
		//dispCurrency = Integer.toString(R_Man.currency);
	      
	}
	//See abstrakt class Gamestate update(float dt);
	public void update(float dt)
	{
		
	}
	//See abstrakt class Gamestate draw(SpriteBatch b);
	public void draw(SpriteBatch batch)
	{
		batch.draw(Store.tex, Store.x, Store.y);
		font.draw(batch, "Your currency : " + dispCurrency, 20, 650);
		
		//Note : These are just placeholder to show what the user might buy. The strings are just made for fun.
		font.draw(batch, "Extra sound pack :", 20, 550);
		font.draw(batch, "5.000", 350, 550);
		font.draw(batch, "More blocks :", 20, 500);
		font.draw(batch, "20.000", 350, 500);
		font.draw(batch, "Slow motion mode :", 20, 450);
		font.draw(batch, "25.000", 350, 450);
		font.draw(batch, "Buggy mode :", 20, 400);
		font.draw(batch, "35.000", 350, 400);
		font.draw(batch, "Unlock credit list", 20, 350);
		font.draw(batch, "40.000", 350, 350);
		font.draw(batch, "Golden carrot :", 20, 300);
		font.draw(batch, "50.000", 350, 300);
		font.draw(batch, "Unlock the whole game for $0.99.", 20, 200);
		font.draw(batch, "Coming soon.", 140, 150);
		
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
