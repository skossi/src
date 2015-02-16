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

	   private String[] drawCurrency;
	   
	   private boolean Animation;
	   
	   private int xOffset;
	   private int screenDir;
	   
	   
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
		
		drawCurrency = new String[4];
		for(int i = 0; i < 4; i++)
		{
			drawCurrency[i] = Integer.toString(R_Man.currencyInt[i]);
		}
		
		Animation = true;
		xOffset = 480;
		screenDir = -1;
		
		Store = R_Man.Store;
		Back = R_Man.BackStore;
	      
	}
	
	//See abstrakt class Gamestate update(float dt);
	public void update(float dt)
	{
		if(Animation)
		{
			if(xOffset > 0 && screenDir < 0)
			{
				R_Man.verticalSpeed -= R_Man.speedAdd;//speedMin;
				xOffset += R_Man.verticalSpeed*dt*screenDir;
				R_Man.speedAdd--;
				if(xOffset <= 0) 
				{
					xOffset = 0;
					Animation = false;	
				}
			}
			else
			{
				R_Man.verticalSpeed += R_Man.speedAdd;
				xOffset += R_Man.verticalSpeed*dt*screenDir;
				R_Man.speedAdd++;
				if(xOffset >= 480)
				{
					R_Man.moveFromSides = true;
					R_Man.sideDir = 1;
					R_Man.MenuXOffset = -480;
					gsm.setState(GameStateManager.MENU);
				}
			}
		}
	}
	//See abstrakt class Gamestate draw(SpriteBatch b);
	public void draw(SpriteBatch batch)
	{
		batch.draw(Store.tex, Store.x+xOffset, Store.y);
		R_Man.fontBlack.draw(batch, "Your currency : ", 10+xOffset, 650);
		R_Man.drawScoreBoard(batch, 210+xOffset, 590, drawCurrency,false, -1, R_Man.fontBlack);
		
		batch.draw(Back.tex, Back.x+xOffset, Back.y);
	}
	
	//See abstrakt class Gamestate justTouched(x,y);
	public void justTouched(float x, float y)
	{
		if(buttonClick(Back,x,y))
		{
			screenDir = 1;
			Animation = true;
		}
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
