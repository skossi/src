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
	 private Texture[] TutorialStep;
	 private boolean toPlay;
	 private int stateDir;
	 private boolean animation;
	 private int step;
	 
	 private int xOffset = -480;
	 private int offsetSpeed = 3800;
	 private int offsetAdd = 10;
	 private int dir = 1;
	 private int yOffset = 0;
	 private int shake = 3;
	 
	 private int xLineOffset;
	 private boolean INTRO;
	 private boolean isFirstTheme;
	//Constructor
	//See abskrakt class Gamestate(GameStateManager gsm);
	public Tutorialstate(GameStateManager gsm)
	{
		super(gsm);
	}

	//See abstrakt class Gamestate init();
	@Override
	public void init(RectangleManager RectMan)
	{
		TutorialArray = new RectTex[2];
		TutorialStep = new Texture[7];
		step = 0;
		Man = RectMan;

		TutorialArray[0] = Man.ButtonM.TutorialPlay;
		TutorialArray[1] = Man.ButtonM.TutorialNext;
		TutorialStep[0] = Man.TextureM.tutorialStepZero;
		TutorialStep[1] = Man.TextureM.tutorialStepOne;
		TutorialStep[2] = Man.TextureM.tutorialStepTwo;
		TutorialStep[3] = Man.TextureM.tutorialStepThree;
		TutorialStep[4] = Man.TextureM.tutorialStepFour;
		TutorialStep[5] = Man.TextureM.tutorialStepFive;
		TutorialStep[6] = Man.TextureM.tutorialStepSix;

		if(Man.activeTheme == 0) isFirstTheme = true;
		
		animation = true;
		toPlay = false;
		xLineOffset = 0;
		INTRO = false;
	}
	//See abstrakt class Gamestate update(float dt);
	@Override
	public void update(float dt)
	{
		if(INTRO)
		{
			if (animation) 
			{
				if(toPlay) 
				{
					offsetSpeed -= offsetAdd*dir;
					yOffset += offsetSpeed*dt*dir;
					offsetAdd += Man.AnimationM.accel*dir;
					if(yOffset < -1020)Man.AnimationM.MenuAnimateMethod(800, stateDir, GameStateManager.PLAY, GameStateManager.STORE,dt);
				}
				else
				{
					// Animate images
					offsetSpeed -= offsetAdd*dir;
					xOffset += offsetSpeed*dt*dir;
					offsetAdd += Man.AnimationM.accel*dir;
					if(xOffset*dir > 0 && dir > 0) 
					{
						animation = false;
						xOffset = 0;
						offsetSpeed = 0;
						offsetAdd = 0;
					}
					if(xOffset*dir < -480 && dir < 0)
					{
						dir *= -1;
						xOffset = -480;
						offsetSpeed = 3800;
						offsetAdd = 10;
						if (step < TutorialStep.length-2) step++;
						else 
						{
							step++;
							Man.ButtonM.TutorialNext.x += 1500;
							Man.ButtonM.TutorialNext.disX += 1500;
							Man.ButtonM.TutorialPlay.x -= 1500;
							Man.ButtonM.TutorialPlay.disX -= 1500;
						}
					}
				}
			}
		}
		else
		{
			shake *= -1;
			xLineOffset -= 800*dt*5;
			if(Man._r < Man._rOrg) Man._r += 4*dt;
			if(Man._g < Man._gOrg) Man._g += 4*dt;
			if(Man._b < Man._bOrg) Man._b += 4*dt;
			if(Man._b >= 1) 
			{
				INTRO = true;
			}
		}
	}

	//See abstrakt class Gamestate draw(SpriteBatch b);
	@Override
	public void draw(SpriteBatch batch)
	{
		batch.draw(Man.TextureM.loseLine, 0, 700 + xLineOffset + shake);
		//if(!isFirstTheme) batch.draw(Man.TextureM.tutorialBorder,0,220);
		batch.setColor(Color.BLACK);
		Man.drawButton(batch, TutorialArray[0], 0, yOffset,true);


		Man.drawButton(batch, TutorialArray[1], 0, 0, true);
		batch.setColor(Color.WHITE);
		batch.draw(TutorialStep[step], xOffset, 220 + yOffset);
		batch.setColor(1,1,1,1);
	}

	//Sets the direction of the transition.
	private void TransitionToState(boolean aActionState, int aDirection)
	{
		animation = true;
		toPlay = aActionState;
		stateDir = aDirection;
	}
	//See abstrakt class Gamestate justTouched(x,y);
	@Override
	public void justTouched(float x, float y)
	{
		if(animation) return; 
		//Play
		if(buttonClick(TutorialArray[0],x,y))
		{
			Man.playSoundEffect(AudioManager.PUSH);
			TutorialArray[0].pressedEffect();

			GameStateManager.hasFinishedTutorial = true;
			Man.ScoreM.firstDone();
			TransitionToState(true,-1);
			gsm.introStart = true;
		}
		//Tutorial
		if(buttonClick(TutorialArray[1],x,y))
		{
			Man.playSoundEffect(AudioManager.PUSH);
			TutorialArray[1].pressedEffect();
			animation=true;
			dir *= -1;
			xOffset += 1;
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