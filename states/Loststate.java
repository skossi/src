package states;

import managers.AudioManager;
import managers.GameStateManager;
import managers.RectangleManager;

import com.badlogic.gdx.graphics.Color;
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
	private RectangleManager Man;
	
	private boolean Animation;
	private boolean lowerAnimation;
	private boolean gameEnd;
	private int yOffset;
	private int lowerOffset;
	private int lowerSpeed;
	private int lowerSpeedAdd;
	private int scoreOffset;
	private int screenDir;
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
		
		currencyToDisplay = Integer.toString(Man.ScoreM.newestScore);
		
		Replay = Man.ButtonM.Replay;
		MainMenu = Man.ButtonM.MainMenu;
		//Man.AudioM.resetThemeMusic();
		
		Animation = true;
		gameEnd = true;
		lowerAnimation = false;
		yOffset = 800;
		lowerOffset = -800;
		lowerSpeed = 2800;
		lowerSpeedAdd = 70;
		scoreOffset = 207;
		screenDir = -1;
		if(Man.ScoreM.NewHighScore)gsm.introEnd = false;
		
	}
	
	//See abstrakt class Gamestate update(float dt);
	public void update(float dt)
	{	
		if(Animation)
		{
			if(gameEnd)
			{
				Man.AnimationM.verticalSpeed-= Man.AnimationM.speedAdd;
				if(!lowerAnimation)yOffset += Man.AnimationM.verticalSpeed*dt*screenDir;
				if(scoreOffset > 0)scoreOffset += Man.AnimationM.verticalSpeed*dt*screenDir;
				else scoreOffset = 0;
				Man.AnimationM.speedAdd -= Man.AnimationM.accel;
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
						yOffset = 0;
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
		batch.draw(GameOver.tex, GameOver.x, GameOver.y+yOffset-68);
		if(Man.ScoreM.NewHighScore)
		{
			batch.draw(Man.TextureM.newScore,GameOver.x,GameOver.y+yOffset);
		}
		int scoreXOffset = currencyToDisplay.length();
		Man.fontWhite.draw(batch,currencyToDisplay, 230-4*scoreXOffset, 530+scoreOffset);
		
		if(gameEnd)
		{
			batch.draw(Man.TextureM.loseLine, 0, 670-800+yOffset);
			batch.setColor(Color.WHITE);
			Man.drawButton(batch, Replay, 0, lowerOffset, false);
			batch.setColor(Color.WHITE);
			Man.drawButton(batch, MainMenu, 0, lowerOffset, false);
		}
		else 
		{
			batch.setColor(Color.WHITE);
			Man.drawButton(batch, Replay, 0, yOffset, false);
			batch.setColor(Color.WHITE);
			Man.drawButton(batch, MainMenu, 0, yOffset, false);
		}
		batch.setColor(1,1,1,1);
	}
	
	//See abstrakt class Gamestate justTouched(x,y);
	public void justTouched(float x, float y)
	{
		if(buttonClick(Replay,x,y))
		{
			screenDir = -1;
			Animation = true;
			Replay.pressedEffect();
			Man.playSoundEffect(AudioManager.START);
			Man.AudioM.upgradeGame();
			if(!gsm.introEnd) gsm.introStart = true;
		}
		if(buttonClick(MainMenu,x,y))
		{
			screenDir = 1;
			Animation = true;
			MainMenu.pressedEffect();
			gsm.introEnd = false;
			Man.playSoundEffect(AudioManager.PUSH);
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
