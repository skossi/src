package states;

import managers.GameStateManager;
import managers.RectangleManager;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//import com.badlogic.gdx.audio.Sound;
//Class by Óttar Guðmundsson
//Written 14.11.2014
//Creates a new state when game is lost
public class Loststate extends Gamestate{

	private RectTex GameOver;
	private RectTex Back;
	private RectangleManager R_Man;
	
	private String[] scoreMade;
			
	private String[] drawBestScore;
	
	private BitmapFont font;
	private String better;
	private String worse;
	private int size;
	
	//Constructor
	//See abskrakt class Gamestate(GameStateManager gsm);
	public Loststate(GameStateManager gsm)
	{	
		super(gsm);
	}
	//See abstrakt class Gamestate init();
	public void init(RectangleManager RectMan)
	{
		R_Man = RectMan;
		size = 68;
		GameOver = R_Man.Over;
		Back = R_Man.Back;
		scoreMade = new String[4];
		drawBestScore = new String[4];
		for(int i = 0; i < 4; i++)
		{
			scoreMade[i] = Integer.toString(R_Man.currentScore[i]);
			drawBestScore[i] = Integer.toString(R_Man.sumRecordHolder[i]);
		}
		
		font = R_Man.font;
		better = R_Man.betterString;
		worse = R_Man.worseString;
		
	}
	//See abstrakt class Gamestate update(float dt);
	public void update(float dt)
	{
		if(R_Man._r < 1) R_Man._r += dt;
		if(R_Man._g < 0.7) R_Man._g += dt;
		if(R_Man._w > 0) R_Man._w -= 3*dt;
	}
	//See abstrakt class Gamestate draw(SpriteBatch b);
	public void draw(SpriteBatch batch)
	{
		batch.draw(GameOver.tex, GameOver.x, GameOver.y);
		if(R_Man.grats)font.draw(batch,better , 60, 675);
		else font.draw(batch,worse , 45, 675);
		font.draw(batch,"Your score was :", 135, 625);
		batch.draw(R_Man.ScoreBoard, 100, 490);
		for(int i = 0; i < 4; i++)
	    {
			if(scoreMade[i].length() == 3)font.draw(batch, scoreMade[i], 110+size*i, 550); 
    		else if(scoreMade[i].length() == 2)font.draw(batch, scoreMade[i], 120+size*i, 550);
    		else font.draw(batch, scoreMade[i], 130+size*i, 550); 	  
	    }
		font.draw(batch,"Your best score is :", 115, 470);
		batch.draw(R_Man.ScoreBoard, 100, 335);
		for(int i = 0; i < 4; i++)
	    {
			if(drawBestScore[i].length() == 3)font.draw(batch, drawBestScore[i], 110+size*i, 395); 
			else if(drawBestScore[i].length() == 2)font.draw(batch, drawBestScore[i], 120+size*i, 395);
			else font.draw(batch, drawBestScore[i], 130+size*i, 395); 	  
	    }
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
