package states;

import managers.AudioManager;
import managers.GameStateManager;
import managers.RectangleManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import entities.RectTex;
//import com.badlogic.gdx.audio.Sound;

//Class by �ttar Gu�mundsson
//Written 30.10.2014
//Creates a new state when user is viewing the menu
public class Menustate extends Gamestate {

	private RectangleManager Man;

	private RectTex[] MenuArray;
	private Color[] MenuColors;

	private boolean toPlay;
	private int stateDir;
	private boolean moveMenu;
	//Constructor
	//See abskrakt class Gamestate(GameStateManager gsm);
	public Menustate(GameStateManager gsm)
	{
		super(gsm);
	}

	//See abstrakt class Gamestate init();
	@Override
	public void init(RectangleManager RectMan)
	{
		MenuArray = new RectTex[2];
		MenuColors = new Color[5];
		Man = RectMan;

		//MenuArray[0] = Man.ButtonM.Menu;
		MenuArray[0] = Man.ButtonM.EnterPlay;
		MenuArray[1] = Man.ButtonM.EnterStore;

		MenuColors[0] = Man.Color_Logo;
		MenuColors[1] = Man.Color_Play;
		MenuColors[2] = Man.Color_Score;
		MenuColors[3] = Man.Color_Tutorial;
		MenuColors[4] = Man.Color_Store;

		moveMenu = false;
		Man.AudioM.makeLoop();

	}
	//See abstrakt class Gamestate update(float dt);
	@Override
	public void update(float dt)
	{
		if(moveMenu)
		{
			if(toPlay)
			{
				Man.AnimationM.MenuAnimateMethod(800, stateDir, GameStateManager.PLAY, GameStateManager.STORE,dt);
			}
		}
		if(Man.AnimationM.isMenuDown)
		{
			if(Man._r < Man._rOrg) Man._r += 4*dt;
			if(Man._g < Man._gOrg) Man._g += 4*dt;
			if(Man._b < Man._bOrg) Man._b += 4*dt;
			Man.AnimationM.MenuDown(dt);
		}
	}

	//See abstrakt class Gamestate draw(SpriteBatch b);
	@Override
	public void draw(SpriteBatch batch)
	{
		for(int i = 0; i < MenuArray.length; i++)
		{
			batch.setColor(Man.Color_Logo);
			Man.drawButton(batch, MenuArray[i], Man.AnimationM.MenuXOffset, Man.AnimationM.MenuYOffset,true);
		}
		batch.setColor(1,1,1,1);
		batch.draw(Man.TextureM.logo, MenuArray[0].x+Man.AnimationM.MenuXOffset,MenuArray[0].y+Man.AnimationM.MenuYOffset+50);
	}

	//Sets the direction of the transition.
	private void TransitionToState(boolean aActionState, int aDirection)
	{
		moveMenu = true;
		toPlay = aActionState;
		stateDir = aDirection;
	}
	//See abstrakt class Gamestate justTouched(x,y);
	@Override
	public void justTouched(float x, float y)
	{
		if(moveMenu) return; //|| !Man.isMenuDown

		//Play
		if(buttonClick(MenuArray[0],x,y))
		{
			TransitionToState(true,-1);
			gsm.introStart = true;
			Man.playSoundEffect(AudioManager.START);
			Man.AudioM.upgradeGame();
			MenuArray[0].pressedEffect();
		}
		//Tutorial
		if(buttonClick(MenuArray[1],x,y))
		{
			TransitionToState(true,1);
			Man.playSoundEffect(AudioManager.PUSH);
			MenuArray[1].pressedEffect();
		}
	}
	//Tells if user just pressed a corresponding rectangle
	//Takes in Rectangle Rekt that and x and y coordinates of world position
	public boolean buttonClick(RectTex rekt, float x, float y) {
		if (x < (rekt.x + rekt.width) && x > rekt.x && y > rekt.y && y < (rekt.y + rekt.height))
		{
			rekt.pressedEffect();
			return true;
		}
		return false;
	}

	//See abstrakt class Gamestate isTouched(x,y);
	@Override
	public void isTouched(float x, float y)
	{

	}
	//See abstrakt class Gamestate dispose();
	@Override
	public void dispose()
	{

	}
}
