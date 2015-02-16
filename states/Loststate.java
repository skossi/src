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
		GameOver = R_Man.Over;
		scoreMade = new String[4];
		drawBestScore = new String[4];
		for(int i = 0; i < 4; i++)
		{
			scoreMade[i] = Integer.toString(R_Man.currentScore[i]);
			drawBestScore[i] = Integer.toString(R_Man.sumRecordHolder[i]);
		}
		
		newHigh = R_Man.newHighString;
		newIndiv = R_Man.newIndivString;
		worse = R_Man.worseString;
		
		Replay = R_Man.Replay;
		MainMenu = R_Man.MainMenu;
		
	}
	//See abstrakt class Gamestate update(float dt);
	public void update(float dt)
	{
		if(R_Man._r < R_Man._rOrg) R_Man._r += dt;
		if(R_Man._g < R_Man._gOrg) R_Man._g += dt;
		if(R_Man._b < R_Man._bOrg) R_Man._b += dt;
		if(R_Man._w > 0) R_Man._w -= 3*dt;
	}
	//See abstrakt class Gamestate draw(SpriteBatch b);
	
	
	public void draw(SpriteBatch batch)
	{
		batch.draw(GameOver.tex, GameOver.x, GameOver.y);
		if(R_Man.NewHighScore)R_Man.fontBlack.draw(batch,newHigh , 60, 675);
		else if (R_Man.NewIndivScore)R_Man.fontBlack.draw(batch,newIndiv , 60, 675);
		else R_Man.fontBlack.draw(batch,worse , 45, 675);
		
		R_Man.fontBlack.draw(batch,"Your score was :", 135, 630);
		R_Man.drawScoreBoard(batch, 100, 530, scoreMade, R_Man.NewIndivScore, R_Man.whichNewIndivScore, R_Man.fontBlack );
	
		R_Man.fontBlack.draw(batch,"Your best score is :", 115, 470);
		R_Man.drawScoreBoard(batch, 100, 370, drawBestScore, false, -1, R_Man.fontBlack);

		batch.draw(Replay.tex, Replay.x, Replay.y);
		batch.draw(MainMenu.tex, MainMenu.x, MainMenu.y);
	}
	
	//See abstrakt class Gamestate justTouched(x,y);
	public void justTouched(float x, float y)
	{
		if(buttonClick(Replay,x,y)) gsm.setState(GameStateManager.PLAY);
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
