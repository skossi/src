package states;

import managers.GameStateManager;
import managers.RectangleManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import entities.RectTex;
//import com.badlogic.gdx.audio.Sound;
//Class by Óttar Guðmundsson
//Written 30.10.2014
//Creates a new state when score is viewed
public class Scorestate extends Gamestate {

	   private RectTex Back;
	   private RectTex Score;
	   private RectangleManager Man;
	   
	   
	   private int selectTab;
	   private RectTex[] tabs;
	   private String[] tabText;
	   private String[] gameInfo;
	   private String[] gameInfoString = {"High Score", "Time Played","Time Paused", "Swaps Made","Matches Made", "Total Score"};
	   
	  // private int xOffset;
	   public static int screenDir;
	   
	   
	//Constructor
	//See abskrakt class Gamestate(GameStateManager gsm);
	public Scorestate(GameStateManager gsm)
	{	
		super(gsm);
	}
	
	//See abstrakt class Gamestate init();
	public void init(RectangleManager RectMan)
	{
		Man = RectMan;

		Score = Man.ButtonM.Score;
		tabs = new RectTex[2];
		tabText = new String[2];
		gameInfo = new String[6];
		
		for(int i = 0 ; i < gameInfo.length; i++)
		{
			if(i == 1 || i == 2) gameInfo[i] = timeConversion(Man.ScoreM.infoHolder[i+1]);
			else gameInfo[i] = Integer.toString(Man.ScoreM.infoHolder[i+1]);
		}
		
		selectTab = 0;
		
		tabs[0] = Man.ButtonM.TabStats;
		tabs[1] = Man.ButtonM.TabAbout;
		tabText[0] = "Stats";
		tabText[1] = "About";
		
		Man.AnimationM.SideAnimation = true;
		Man.AnimationM.SideXOffset = -480;
		screenDir = 1;
		
		Back = Man.ButtonM.BackScore;
	      
	}
	//See abstrakt class Gamestate update(float dt);
	public void update(float dt)
	{
		if(Man.AnimationM.SideAnimation)
		{
			if(Man.AnimationM.SideXOffset < 0 && screenDir > 0)
			{
				Man.AnimationM.SideToMenu(dt,screenDir,true);
			}
			else
			{
				Man.AnimationM.SideToMenu(dt,screenDir,false);
			}
		}
	}
	//See abstrakt class Gamestate draw(SpriteBatch b);
	public void draw(SpriteBatch batch)
	{
		int xOffset = Man.AnimationM.SideXOffset;
		batch.setColor(Man.Color_Score);
		batch.draw(Score.tex, Score.x+xOffset, Score.y);
		
		for(int i = 0 ; i < 2; i++)
		{
			batch.draw(tabs[i].tex,tabs[i].x+xOffset,tabs[i].y);
			if(i == selectTab)Man.fontWhite.draw(batch, tabText[i], 50+240*i+xOffset, 690);
			else Man.fontfff60.draw(batch, tabText[i], 50+240*i+xOffset, 690);

		}
		batch.setColor(Man.Color_ScoreBar);
		batch.draw(Man.TextureM.scoreTabSelect,selectTab*240+xOffset,625);	
		batch.draw(Man.TextureM.scoreBack,20+xOffset,150);
		
		batch.setColor(1,1,1,1);
		if(selectTab == 0)
		{
			for( int i = 0 ; i < gameInfo.length; i++)
			{
				Man.fontBlack.draw(batch, gameInfoString[i], 60+xOffset, 560 - 60*i);
				Man.fontBlack.draw(batch, gameInfo[i], 300+xOffset, 560 - 60*i);
			}
		}
		else if (selectTab == 1)
		{
			batch.draw(Man.ButtonM.Facebook.tex,Man.ButtonM.Facebook.x+xOffset,Man.ButtonM.Facebook.y);
			Man.fontBlack.draw(batch, "Learn about us on facebook.", 60+xOffset, 250);
		}
		batch.setColor(Man.Color_Logo);
		batch.draw(Back.tex, Back.x+xOffset, Back.y);
		batch.setColor(1,1,1,1);
	}
	
	//See abstrakt class Gamestate justTouched(x,y);
	public void justTouched(float x, float y)
	{
		if(Man.AnimationM.SideAnimation) return;
		if(buttonClick(tabs[0],x,y)) selectTab = 0;
		if(buttonClick(tabs[1],x,y)) selectTab = 1;
		if(selectTab == 1 && buttonClick(Man.ButtonM.Facebook,x,y)) Gdx.net.openURI("http://facebook.com/ottardagreat");
		if(buttonClick(Back,x,y))
		{
			screenDir = -1;
			Man.AnimationM.SideAnimation= true;
		}
	}
	//Tells if user just pressed a corresponding rectangle
	//Takes in Rectangle Rekt that and x and y coordinates of world position
	public boolean buttonClick(RectTex rekt, float x, float y) {
		if (x < (rekt.x + rekt.width) && x > rekt.x && y > rekt.y && y < (rekt.y + rekt.height)) return true;
		return false;
	}
	
	private String timeConversion(int totalSeconds) {

	    final int MINUTES_IN_AN_HOUR = 60;
	    final int SECONDS_IN_A_MINUTE = 60;

	    int seconds = totalSeconds % SECONDS_IN_A_MINUTE;
	    int totalMinutes = totalSeconds / SECONDS_IN_A_MINUTE;
	    int minutes = totalMinutes % MINUTES_IN_AN_HOUR;
	    int hours = totalMinutes / MINUTES_IN_AN_HOUR;
	    
	    String sec = adjustNumerals(seconds);
	    String min = adjustNumerals(minutes);
	    String hour = adjustNumerals(hours);

	    return hour + ":" + min + ":" + sec;
	}
	
	private String adjustNumerals(int aNumber)
	{
		String aString;
		
		if(aNumber == 0) aString = "00";
	    else if (aNumber < 10) aString = "0"+Integer.toString(aNumber);
	    else aString = Integer.toString(aNumber);
		
		return aString;
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
