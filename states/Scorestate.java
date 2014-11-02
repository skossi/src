package states;

import managers.GameStateManager;

import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
//Class by Óttar Guðmundsson
//Written 30.10.2014
//Creates a new state when score is viewed
public class Scorestate extends Gamestate {

	   private Rectangle Back;
	   private Rectangle ScoreLogo;
	   private Texture BackTex;
	   private Texture ScoreLogoTex;
	   
	//Constructor
	//See abskrakt class Gamestate(GameStateManager gsm);
	public Scorestate(GameStateManager gsm)
	{	
		super(gsm);
	}
	//See abstrakt class Gamestate init();
	public void init()
	{
		/*
		 * DÆMI UM VISTUN Á GILDUM - þurfum að skoða shared preferences
		Preferences prefs = Gdx.app.getPreferences("My Preferences");
		
		String name = prefs.getString("name", "No name stored");

		prefs.putBoolean("soundOn", true);
		prefs.putInteger("highscore", 10);
		
		prefs.flush(); // kalla á þegar við ætlum að update-a
		*/
		
		  
		  ScoreLogoTex = new Texture(Gdx.files.internal("logo_score.png"));
		  
		  ScoreLogo = new Rectangle();
		  ScoreLogo.width = ScoreLogoTex.getWidth();
		  ScoreLogo.height = ScoreLogoTex.getHeight();
		  ScoreLogo.x = 480 /2 - ScoreLogo.width / 2; 
		  ScoreLogo.y = 700;
	      
		  BackTex = new Texture(Gdx.files.internal("back.png"));
		  
	      Back = new Rectangle();
	      Back.width = BackTex.getWidth();
	      Back.height = BackTex.getHeight();
	      Back.x = 480 * 0.75f - Back.width / 2; 
	      Back.y = 20;
	      
	}
	//See abstrakt class Gamestate update(float dt);
	public void update(float dt)
	{
		
	}
	//See abstrakt class Gamestate draw(SpriteBatch b);
	public void draw(SpriteBatch batch)
	{
		batch.draw(BackTex, Back.x, Back.y);
		batch.draw(ScoreLogoTex, ScoreLogo.x, ScoreLogo.y);
	}
	
	//See abstrakt class Gamestate justTouched(x,y);
	public void justTouched(float x, float y)
	{
		if(buttonClick(Back,x,y)) gsm.setState(GameStateManager.MENU);
	}
	//Tells if user just pressed a corresponding rectangle
	//Takes in Rectangle Rekt that and x and y coordinates of world position
	public boolean buttonClick(Rectangle rekt, float x, float y) {
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
