package states;

import managers.GameStateManager;
import managers.RectangleManager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import entities.RectTex;

//import com.badlogic.gdx.audio.Sound;
//Class by Óttar Guðmundsson
//Written 14.11.2014
//Creates a new state when game is lost
public class Loststate extends Gamestate{

	private RectTex GameOver;
	private RectTex Replay;
	private RectTex MainMenu;
	private RectangleManager R_Man;
	
	private String[] scoreMade;
			
	private String[] drawBestScore;
	
	private String newHigh;
	private String newIndiv;
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
		R_Man = RectMan;
		GameOver = R_Man.ButtonM.Over;
		scoreMade = new String[4];
		drawBestScore = new String[4];
		for(int i = 0; i < 4; i++)
		{
			scoreMade[i] = Integer.toString(R_Man.ScoreM.currentScore[i+1]);
			drawBestScore[i] = Integer.toString(R_Man.ScoreM.scoreHolder[0][i+1]);
		}
		
		newHigh = R_Man.newHighString;
		newIndiv = R_Man.newIndivString;
		worse = R_Man.worseString;
		
		Replay = R_Man.ButtonM.Replay;
		MainMenu = R_Man.ButtonM.MainMenu;
		R_Man.AudioM.resetThemeMusic();
		
	}
	//See abstrakt class Gamestate update(float dt);
	public void update(float dt)
	{
	}
	
	//See abstrakt class Gamestate draw(SpriteBatch b);
	public void draw(SpriteBatch batch)
	{
		batch.draw(GameOver.tex, GameOver.x, GameOver.y);
		if(R_Man.ScoreM.NewHighScore)R_Man.fontWhite.draw(batch,newHigh , 60, 675);
		else if (R_Man.ScoreM.NewIndivScore)R_Man.fontWhite.draw(batch,newIndiv , 60, 675);
		else R_Man.fontWhite.draw(batch,worse , 45, 675);
		
		R_Man.fontWhite.draw(batch,"Your score was :", 135, 630);
		R_Man.drawScoreBoard(batch, 100, 530, scoreMade, R_Man.ScoreM.NewIndivScore, R_Man.ScoreM.whichNewIndivScore, R_Man.fontWhite );
	
		R_Man.fontWhite.draw(batch,"Your best score is :", 115, 470);
		R_Man.drawScoreBoard(batch, 100, 370, drawBestScore, false, -1, R_Man.fontWhite);

		batch.draw(Replay.tex, Replay.x, Replay.y);
		batch.draw(MainMenu.tex, MainMenu.x, MainMenu.y);
	}
	
	//See abstrakt class Gamestate justTouched(x,y);
	public void justTouched(float x, float y)
	{
		if(buttonClick(Replay,x,y))gsm.setState(GameStateManager.PLAY);
		if(buttonClick(MainMenu,x,y)) gsm.setState(GameStateManager.MENU);
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
