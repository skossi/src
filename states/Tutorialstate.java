package states;

import managers.AudioManager;
import managers.GameStateManager;
import managers.RectangleManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import entities.RectTex;
//Class by Óttar Guðmundsson
//Written 30.10.2014
//Creates a new state when tutorial is viewed
public class Tutorialstate extends Gamestate {

	 
	 private RectangleManager Man;
	 private RectTex[] TutorialArray;
	 private Color[] MenuColors;
	 private Texture[] TutorialStep;
	 private boolean toPlay;
	 private int stateDir;
	 private boolean moveMenu;
	 private int step;
	//Constructor
	//See abskrakt class Gamestate(GameStateManager gsm);
	public Tutorialstate(GameStateManager gsm)
	{	
		super(gsm);
	}
	
	//See abstrakt class Gamestate init();
	public void init(RectangleManager RectMan)
	{
		TutorialArray = new RectTex[2];
		MenuColors = new Color[5];
		TutorialStep = new Texture[4];
		step = 0;
		Man = RectMan;
		
		TutorialArray[0] = Man.ButtonM.TutorialPlay;
		TutorialArray[1] = Man.ButtonM.TutorialNext;
		
		MenuColors[0] = Man.Color_Logo;
		MenuColors[1] = Man.Color_Play;
		MenuColors[2] = Man.Color_Score;
		MenuColors[3] = Man.Color_Tutorial;
		MenuColors[4] = Man.Color_Store;
		
		TutorialStep[0] = Man.TextureM.tutorialStepOne;
		TutorialStep[1] = Man.TextureM.tutorialStepTwo;
		TutorialStep[2] = Man.TextureM.tutorialStepThree;
		TutorialStep[3] = Man.TextureM.tutorialStepFour;
	
		moveMenu = false;
	}
	//See abstrakt class Gamestate update(float dt);
	public void update(float dt)
	{
		if (moveMenu) {
			if(toPlay) {
				Man.AnimationM.MenuAnimateMethod(800, stateDir, GameStateManager.PLAY, GameStateManager.STORE,dt);
			}
		}
		if (Man.AnimationM.isMenuDown) {
			if(Man._r < Man._rOrg) Man._r += 4*dt;
			if(Man._g < Man._gOrg) Man._g += 4*dt;
			if(Man._b < Man._bOrg) Man._b += 4*dt;
			Man.AnimationM.MenuDown(dt);
		}
	}
	
	//See abstrakt class Gamestate draw(SpriteBatch b);
	public void draw(SpriteBatch batch)
	{
		batch.setColor(Color.BLACK);
		Man.drawButton(batch, TutorialArray[0], 0, 0,true);
		Man.drawButton(batch, TutorialArray[1], 0, 0, true);
		batch.setColor(Color.WHITE);
		
		batch.draw(TutorialStep[step], 200, 300);
		batch.setColor(1,1,1,1);
	}
	
	//Sets the direction of the transition.
	private void TransitionToState(boolean aActionState, int aDirection)
	{
		moveMenu = true;
		toPlay = aActionState;
		stateDir = aDirection;
	}
	//See abstrakt class Gamestate justTouched(x,y);
	public void justTouched(float x, float y)
	{
		if(moveMenu) return; //|| !Man.isMenuDown
		
		//Play
		if(buttonClick(TutorialArray[0],x,y)) 
		{
			GameStateManager.hasFinishedTutorial = true;
			TransitionToState(true,-1);
			gsm.introStart = true; 
			Man.playSoundEffect(AudioManager.START);
			
		}
		//Tutorial
		if(buttonClick(TutorialArray[1],x,y))
		{
			if (step < TutorialStep.length-2) step++;
			else {
				step++;
				Man.ButtonM.TutorialNext.x += 1500;
				Man.ButtonM.TutorialNext.disX += 1500;
				Man.ButtonM.TutorialPlay.x -= 1500;
				Man.ButtonM.TutorialPlay.disX -= 1500;
			}
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
	public void isTouched(float x, float y)
	{
		
	}
	//See abstrakt class Gamestate dispose();
	public void dispose()
	{
	
	}
}