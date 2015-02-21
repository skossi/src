package states;

import managers.GameStateManager;
import managers.RectangleManager;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import entities.RectTex;
//import com.badlogic.gdx.audio.Sound;
//Class by Óttar Guðmundsson
//Written 30.10.2014
//Creates a new state when score is viewed
public class Scorestate extends Gamestate {

	   private RectTex Back;
	   private RectTex Score;
	   private RectangleManager R_Man;
	   
	   private String[] drawBestScore;
	   private String[] drawBestSquare;
	   private String[] drawBestTriangle;
	   private String[] drawBestCircle;
	   private String[] drawBestEx;
	   
	   private boolean Animation;
	   private int xOffset;
	   private int screenDir;
	   
	   
	//Constructor
	//See abskrakt class Gamestate(GameStateManager gsm);
	public Scorestate(GameStateManager gsm)
	{	
		super(gsm);
	}
	//See abstrakt class Gamestate init();
	public void init(RectangleManager RectMan)
	{
		R_Man = RectMan;

		Score = R_Man.Score;
		Back = R_Man.BackScore;
		
		drawBestScore = new String[4];
		drawBestSquare = new String[4];
		drawBestTriangle = new String[4];
		drawBestCircle = new String[4];
		drawBestEx = new String[4];
		for(int i = 0; i < 4; i++)
		{
			drawBestScore[i] = Integer.toString(R_Man.sumRecordHolder[i]);
			drawBestSquare[i] = Integer.toString(R_Man.squareRecordHolder[i]);
			drawBestTriangle[i] = Integer.toString(R_Man.triangleRecordHolder[i]);
			drawBestCircle[i] = Integer.toString(R_Man.circleRecordHolder[i]);
			drawBestEx[i] = Integer.toString(R_Man.exRecordHolder[i]);
		}
		Animation = true;
		xOffset = -480;
		screenDir = 1;
	      
	}
	//See abstrakt class Gamestate update(float dt);
	public void update(float dt)
	{
		if(Animation)
		{
			if(xOffset < 0 && screenDir > 0)
			{
				R_Man.verticalSpeed -= R_Man.speedAdd;
				xOffset += R_Man.verticalSpeed*dt*screenDir;
				R_Man.speedAdd--;
				if(xOffset >= 0) 
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
				if(xOffset < -480)
				{
					R_Man.moveFromSides = true;
					R_Man.sideDir = -1;
					R_Man.MenuXOffset = 480;
					gsm.setState(GameStateManager.MENU);
				}
			}
		}
	}
	//See abstrakt class Gamestate draw(SpriteBatch b);
	public void draw(SpriteBatch batch)
	{
		batch.draw(Score.tex, Score.x+xOffset, Score.y+10);
		
		R_Man.fontBlack.draw(batch,"Your best score is :", 115+xOffset*1.5f, 695);
		R_Man.drawScoreBoard(batch, 100+xOffset*1.5f, 560, drawBestScore, false, -1,R_Man.fontBlack);
		
		R_Man.fontBlack.draw(batch,"Your best individual score is :", 50+xOffset*2f, 550);
		
		R_Man.fontBlack.draw(batch,"Red Run : ", 10+xOffset*3f, 480);
		R_Man.drawScoreBoard(batch, 180+xOffset*3f, 435, drawBestSquare, true, 0,R_Man.fontWhite);
		
		R_Man.fontBlack.draw(batch,"Green Run : ", 10+xOffset*3.5f, 380);
		R_Man.drawScoreBoard(batch, 180+xOffset*3.5f, 335, drawBestTriangle, true, 1,R_Man.fontWhite);

		R_Man.fontBlack.draw(batch,"Yellow Run : ", 10+xOffset*4f, 280);
		R_Man.drawScoreBoard(batch, 180+xOffset*4f, 235, drawBestCircle, true, 2,R_Man.fontWhite);

		R_Man.fontBlack.draw(batch,"Blue Run : ", 10+xOffset*4.5f, 180);
		R_Man.drawScoreBoard(batch, 180+xOffset*4.5f, 135, drawBestEx, true, 3,R_Man.fontWhite);
		
		batch.draw(Back.tex, Back.x+xOffset, Back.y);
		
		
	}
	
	//See abstrakt class Gamestate justTouched(x,y);
	public void justTouched(float x, float y)
	{
		if(buttonClick(Back,x,y))
		{
			screenDir = -1;
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
