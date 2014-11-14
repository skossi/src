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
//Creates a new state when score is viewed
public class Scorestate extends Gamestate {

	   private RectTex Back;
	   private RectTex Score;
	   private RectangleManager RectMana;
	   
	//Constructor
	//See abskrakt class Gamestate(GameStateManager gsm);
	public Scorestate(GameStateManager gsm)
	{	
		super(gsm);
	}
	//See abstrakt class Gamestate init();
	public void init(RectangleManager RectMan)
	{
		RectMana = RectMan;
		/*
		 * DÆMI UM VISTUN Á GILDUM - þurfum að skoða shared preferences
		Preferences prefs = Gdx.app.getPreferences("My Preferences");
		
		String name = prefs.getString("name", "No name stored");

		prefs.putBoolean("soundOn", true);
		prefs.putInteger("highscore", 10);
		
		prefs.flush(); // kalla á þegar við ætlum að update-a
		*/
		Score = RectMana.Score;
		Back = RectMana.Back;
	      
	}
	//See abstrakt class Gamestate update(float dt);
	public void update(float dt)
	{
		
	}
	//See abstrakt class Gamestate draw(SpriteBatch b);
	public void draw(SpriteBatch batch)
	{
		batch.draw(Back.tex, Back.x, Back.y);
		batch.draw(Score.tex, Score.x, Score.y);
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
