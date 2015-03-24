package states;

import managers.GameStateManager;
import managers.RectangleManager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import entities.RectTex;
import entities.UI;

//import com.badlogic.gdx.audio.Sound;
//Class by Óttar Guðmundsson
//Written 14.11.2014
//Creates a new state when game is lost
public class Loststate extends Gamestate{

	private RectTex GameOver;
	private RectTex Replay;
	private RectTex MainMenu;
	private RectangleManager Man;
	
	private String[] scoreMade;			
	private String[] drawBestScore;
	private String[] funnyMessage;
	private int funnyDisplay;
	
	private boolean Animation;
	private boolean lowerAnimation;
	private boolean gameEnd;
	private int yOffset;
	private int lowerOffset;
	private int lowerSpeed;
	private int lowerSpeedAdd;
	private int scoreOffset;
	private int screenDir;
	private int speedAdd;
	private String currencyToDisplay;
	
	
	//Constructor
	//See abskrakt class Gamestate(GameStateManager gsm);
	public Loststate(GameStateManager gsm)
	{	
		super(gsm);
	}
	
	//See abstrakt class Gamestate init();
	public void init(RectangleManager RectMan)
	{
		Man = RectMan;
		GameOver = Man.ButtonM.Over;
		scoreMade = new String[4];
		drawBestScore = new String[4];
		funnyMessage = new String[]{"You cant always win..", "Is that the best you can do?", 
				"How about trying for real ?","Push it to the limit, dandadan.."};
		funnyDisplay = (int) (Math.random() * 4);
		for(int i = 0; i < 4; i++)
		{
			scoreMade[i] = Integer.toString(Man.ScoreM.currentScore[i+1]);
			//drawBestScore[i] = Integer.toString(Man.ScoreM.scoreHolder[0][i+1]);
		}
		currencyToDisplay = Integer.toString(Man.ScoreM.newestScore);
		
		Replay = Man.ButtonM.Replay;
		MainMenu = Man.ButtonM.MainMenu;
		Man.AudioM.resetThemeMusic();
		
		Animation = true;
		gameEnd = true;
		lowerAnimation = false;
		yOffset = 800;
		lowerOffset = -800;
		lowerSpeed = 2400;
		lowerSpeedAdd = 70;
		scoreOffset = 207;
		screenDir = -1;
		
	}
	
	//See abstrakt class Gamestate update(float dt);
	public void update(float dt)
	{
		if(Animation)
		{
			if(gameEnd)
			{
				Man.AnimationM.verticalSpeed-= Man.AnimationM.speedAdd;
				yOffset += Man.AnimationM.verticalSpeed*dt*screenDir;
				if(scoreOffset > 0)scoreOffset += Man.AnimationM.verticalSpeed*dt*screenDir;
				else scoreOffset = 0;
				Man.AnimationM.speedAdd -= Man.AnimationM.accel;
				if(yOffset <= 300) lowerAnimation = true;
				if(yOffset <= 0) 
				{
					yOffset = 0;
					scoreOffset = 0;
					Man.AnimationM.speedAdd = 0;
					lowerAnimation = true;
				}
				if(lowerAnimation)
				{
					lowerSpeed -= lowerSpeedAdd;
					lowerOffset += lowerSpeed*dt;
					lowerSpeedAdd -= Man.AnimationM.accel;
					if(lowerOffset >= 0)
					{
						lowerOffset = 0;
						Animation = false;
						gameEnd = false;
					}
				}
			}
			else
			{
				if(Man._r < Man._rOrg) Man._r += 4*dt;
				if(Man._g < Man._gOrg) Man._g += 4*dt;
				if(Man._b < Man._bOrg) Man._b += 4*dt;
				
				Man.AnimationM.verticalSpeed += Man.AnimationM.speedAdd;
				yOffset += Man.AnimationM.verticalSpeed*dt*screenDir;
				scoreOffset += Man.AnimationM.verticalSpeed*dt*screenDir;
				Man.AnimationM.speedAdd += Man.AnimationM.accel;
				if(yOffset < -800)gsm.setState(GameStateManager.PLAY);
				if(yOffset > 800) gsm.setState(GameStateManager.MENU);
			}
		}
	}
	
	//See abstrakt class Gamestate draw(SpriteBatch b);
	public void draw(SpriteBatch batch)
	{
		batch.draw(GameOver.tex, GameOver.x, GameOver.y+yOffset);
		if(Man.ScoreM.NewHighScore)Man.fontWhite.draw(batch,"Congratulations, new score!" , 60, 675+yOffset);
		else Man.fontWhite.draw(batch,funnyMessage[funnyDisplay] , 60, 675+yOffset);
		
		Man.fontWhite.draw(batch,"Your score was :", 135, 630+yOffset);
		
		//Man.drawScoreBoard(batch, 100, 530 +scoreOffset, scoreMade, Man.ScoreM.NewIndivScore, Man.ScoreM.whichNewIndivScore, Man.fontWhite );
		Man.fontWhite.draw(batch,"         "+currencyToDisplay,100, 530+scoreOffset);
		
		if(gameEnd)
		{
			batch.draw(Man.TextureM.redline, 0, 670-800+yOffset);
			
			Man.fontWhite.draw(batch,"You collected :", 145, 470+lowerOffset);
			Man.drawScoreBoard(batch, 100, 370+lowerOffset, scoreMade, false, -1, Man.fontWhite);
	
			batch.draw(Replay.tex, Replay.x, Replay.y+lowerOffset);
			batch.draw(MainMenu.tex, MainMenu.x, MainMenu.y+lowerOffset);
		}
		else 
		{
			Man.fontWhite.draw(batch,"You collected :", 145, 470+yOffset);
			Man.drawScoreBoard(batch, 100, 370+yOffset, scoreMade, false, -1, Man.fontWhite);
	
			batch.draw(Replay.tex, Replay.x, Replay.y+yOffset);
			batch.draw(MainMenu.tex, MainMenu.x, MainMenu.y+yOffset);
		}
		//Man.drawScoreBoard(batch, 100, 530 +scoreOffset, scoreMade, Man.ScoreM.NewIndivScore, Man.ScoreM.whichNewIndivScore, Man.fontWhite );	
	}
	
	//See abstrakt class Gamestate justTouched(x,y);
	public void justTouched(float x, float y)
	{
		if(buttonClick(Replay,x,y))
		{
			screenDir = -1;
			Animation = true;
		}
		if(buttonClick(MainMenu,x,y))
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
