package states;

import managers.GameStateManager;

import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.blokk.game.Movable;

//Class by Óttar Guðmundsson
//Written 30.10.2014
//Creates a new state when user is viewing the menu
public class Menustate extends Gamestate {

	 private Texture LogoTex;
	 private Texture PlayTex;
	 private Texture ScoreTex;
	 private Texture TutorialTex;
	   
	 public Rectangle Logo;
	 public Rectangle Play;
	 public Rectangle Score;
	 public Rectangle Tutorial;
	   
	 private int width;
	 private int height;
	 private int numButtons;
	 private int[] buttonPos;
	 private Rectangle[] buttons;
	 public boolean intro;
	   
	//Constructor
	//See abskrakt class Gamestate(GameStateManager gsm);
	public Menustate(GameStateManager gsm)
	{	
		super(gsm);
	}
	//See abstrakt class Gamestate init();
	public void init()
	{
		   LogoTex = new Texture(Gdx.files.internal("logo_main.png"));
		   PlayTex = new Texture(Gdx.files.internal("play.png"));
		   ScoreTex = new Texture(Gdx.files.internal("score.png"));
		   TutorialTex = new Texture(Gdx.files.internal("tutorial.png"));

		  numButtons = 4;
		   
		  width = PlayTex.getWidth();
		  height = PlayTex.getHeight();
		  
		  buttonPos = new int[]		{700,450,300,150}; //Manual positions, could be created with for loop and interval
		  buttons = new Rectangle[numButtons];
		  //Manually created, could be done with string array?
		  numButtons = 0;
		  buttons[numButtons] = Logo = new Rectangle();numButtons++;
		  buttons[numButtons] = Play = new Rectangle();numButtons++;
		  buttons[numButtons] = Score = new Rectangle();numButtons++;
		  buttons[numButtons] = Tutorial = new Rectangle();numButtons++;
		  
		  for(int i = 0; i < numButtons; i++)
		  {
			  buttons[i].x = 480 / 2 - width / 2; //center position
			  buttons[i].y = buttonPos[i];
			  buttons[i].width = width;
			  buttons[i].height = height;
		  }
		  buttons[0].x = 480 / 2 - 448 / 2; //size of logo
	}
	//See abstrakt class Gamestate update(float dt);
	public void update(float dt)
	{
		
		if(intro)
		{
			for(int i = 0; i < numButtons; i++)
			{
				buttons[i].y += -600*dt;
			}
			if(buttons[0].y <= -(buttons[0].height + 64)) setGame();
		}
		
	}
	//See abstrakt class Gamestate draw(SpriteBatch b);
	public void draw(SpriteBatch batch)
	{
		  batch.draw(LogoTex, Logo.x, Logo.y);
	      batch.draw(PlayTex, Play.x, Play.y);
	      batch.draw(ScoreTex, Score.x, Score.y);
	      batch.draw(TutorialTex, Tutorial.x, Tutorial.y);
	}
	//See abstrakt class Gamestate justTouched(x,y);
	public void justTouched(float x, float y)
	{
		if(buttonClick(Play,x,y)) {gsm.introStart = true; intro = true;}
		if(buttonClick(Score,x,y))gsm.setState(GameStateManager.SCORE);
		if(buttonClick(Tutorial,x,y))gsm.setState(GameStateManager.TUTORIAL);
	}
	//Tells if user just pressed a corresponding rectangle
	//Takes in Rectangle Rekt that and x and y coordinates of world position
	public boolean buttonClick(Rectangle rekt, float x, float y) {
		if (x < (rekt.x + rekt.width) && x > rekt.x && y > rekt.y && y < (rekt.y + rekt.height)) return true;
		return false;
	}
	//calls the main game loop to stop rendering the background with introend and tells the state machine manager to set the next state to playing state since the intro is finished
	private void setGame()
	{
		gsm.setState(GameStateManager.PLAY);
		gsm.introEnd = true;
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
