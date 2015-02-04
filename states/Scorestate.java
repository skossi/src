package states;

import managers.GameStateManager;
import managers.RectangleManager;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.audio.Sound;
//Class by Óttar Guðmundsson
//Written 30.10.2014
//Creates a new state when score is viewed
public class Scorestate extends Gamestate {

	   private RectTex Back;
	   private RectTex Score;
	   private RectangleManager R_Man;
	   
	   private BitmapFont font;
	   private String[] drawBestScore;
	   private String[] drawBestSquare;
	   private String[] drawBestTriangle;
	   private String[] drawBestCircle;
	   private String[] drawBestEx;
	   private int size;
	   
	   
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
		Back = R_Man.Back;
		
		font = R_Man.font;
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
		size = 68;
	      
	}
	//See abstrakt class Gamestate update(float dt);
	public void update(float dt)
	{
		
	}
	//See abstrakt class Gamestate draw(SpriteBatch b);
	public void draw(SpriteBatch batch)
	{
		batch.draw(Score.tex, Score.x, Score.y+10);
		
		font.draw(batch,"Your best score is :", 115, 695);
		batch.draw(R_Man.ScoreBoard, 100, 560);
		for(int i = 0; i < 4; i++)
	    {
			if(drawBestScore[i].length() == 3)font.draw(batch, drawBestScore[i], 110+size*i, 620); 
			else if(drawBestScore[i].length() == 2)font.draw(batch, drawBestScore[i], 120+size*i, 620);
			else font.draw(batch, drawBestScore[i], 130+size*i, 620); 	  
	    }
		font.draw(batch,"Your best individual score is :", 50, 550);
		
		//We can put this is somekind of a function to draw each thing but for the
		//UT Messan we will just make this work. Hardcoding ftw
		font.draw(batch,"Pink Run : ", 10, 480);
		batch.draw(R_Man.ScoreBoard, 180, 420);
		for(int i = 0; i < 4; i++)
	    {
			if(drawBestSquare[i].length() == 3)font.draw(batch, drawBestSquare[i], 190+size*i, 480); 
			else if(drawBestSquare[i].length() == 2)font.draw(batch, drawBestSquare[i], 200+size*i, 480);
			else font.draw(batch, drawBestSquare[i], 210+size*i, 480); 	  
	    }
		font.draw(batch,"Green Run : ", 10, 380);
		batch.draw(R_Man.ScoreBoard, 180, 320);
		for(int i = 0; i < 4; i++)
	    {
			if(drawBestTriangle[i].length() == 3)font.draw(batch, drawBestTriangle[i], 190+size*i, 380); 
			else if(drawBestTriangle[i].length() == 2)font.draw(batch, drawBestTriangle[i], 200+size*i, 380);
			else font.draw(batch, drawBestTriangle[i], 210+size*i, 380); 	  
	    }
		font.draw(batch,"Brown Run : ", 10, 280);
		batch.draw(R_Man.ScoreBoard, 180, 220);
		for(int i = 0; i < 4; i++)
	    {
			if(drawBestCircle[i].length() == 3)font.draw(batch, drawBestCircle[i], 190+size*i, 280); 
			else if(drawBestCircle[i].length() == 2)font.draw(batch, drawBestCircle[i], 200+size*i, 280);
			else font.draw(batch, drawBestCircle[i], 210+size*i, 280); 	  
	    }
		font.draw(batch,"Blue Run : ", 10, 180);
		batch.draw(R_Man.ScoreBoard, 180, 120);
		for(int i = 0; i < 4; i++)
	    {
			if(drawBestEx[i].length() == 3)font.draw(batch, drawBestEx[i], 190+size*i, 180); 
			else if(drawBestEx[i].length() == 2)font.draw(batch, drawBestEx[i], 200+size*i, 180);
			else font.draw(batch, drawBestEx[i], 210+size*i, 180); 	  
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
