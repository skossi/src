package managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
//Class by Ottar Gudmundsson
//Written 21.2.2015
//Texture manager that creates and hold all textures of the game.

public class TextureManager {

	private String location;
	private String asset;

	public Texture logo;
	public Texture square;
	public Texture triangle;
	public Texture circle;
	public Texture ex;
	public Texture black;
	public Texture blinkBlack;
	public Texture powerDown;
	public Texture selected;
	public Texture ui_bg;
	public Texture ui_soundOn;
	public Texture ui_soundOff;
	public Texture loseLine;
	public Texture logo_Main;
	public Texture logo_Over;
	public Texture swapTheme;
	public Texture scoreBack;
	public Texture lockedItem;
	public Texture unlockedItem;
	public Texture priceHolder;
	public Texture currentlyActive;
	public Texture currencyBack;
	public Texture newScore;
	public Texture tutorialStepZero;
	public Texture tutorialStepOne;
	public Texture tutorialStepTwo;
	public Texture tutorialStepThree;
	public Texture tutorialStepFour;
	public Texture tutorialStepFive;
	public Texture tutorialStepSix;
	public Texture tutorialBorder;
	

	public Texture[] kill;

	public TextureManager(String aAsset, String aLoc)
	{
		asset = aAsset;
		location = aLoc;
		createTextures();
	}

	//Loads all textures into the game by the chosen asset/theme.
	private void createTextures()
	{
		//*-------THEME BASED ASSETS------*
		square = new Texture(Gdx.files.internal(asset+location+"square.png"));
		triangle = new Texture(Gdx.files.internal(asset+location+"triangle.png"));
		circle = new Texture(Gdx.files.internal(asset+location+"circle.png"));
		ex = new Texture(Gdx.files.internal(asset+location+"ex.png"));
		selected = new Texture(Gdx.files.internal(asset+location+"selectedNEW.png"));
		loseLine = new Texture(Gdx.files.internal(asset+location+"redline.png"));

		//*-------MUTUAL ASSETS------*
		logo = new Texture(Gdx.files.internal("MutualAssets/logo_main.png"));
		black = new Texture(Gdx.files.internal("MutualAssets/black.png"));
		blinkBlack = new Texture(Gdx.files.internal("MutualAssets/blackBlikk.png"));
		powerDown = new Texture(Gdx.files.internal("MutualAssets/powerDown.png"));
		swapTheme = new Texture(Gdx.files.internal("MutualAssets/SwapTheme.png"));
		scoreBack = new Texture(Gdx.files.internal("MutualAssets/scoreBack.png"));
		lockedItem = new Texture(Gdx.files.internal("MutualAssets/locked.png"));
		unlockedItem = new Texture(Gdx.files.internal("MutualAssets/unLocked.png"));
		priceHolder = new Texture(Gdx.files.internal("MutualAssets/PriceHolder.png"));
		ui_bg = new Texture(Gdx.files.internal("MutualAssets/ui_bg.png"));
		ui_soundOn = new Texture(Gdx.files.internal("MutualAssets/soundOn.png"));
		ui_soundOff = new Texture(Gdx.files.internal("MutualAssets/soundOff.png"));
		currentlyActive = new Texture(Gdx.files.internal("MutualAssets/currentlyActive.png"));
		currencyBack = new Texture(Gdx.files.internal("MutualAssets/currencyBack.png"));
		newScore = new Texture(Gdx.files.internal("MutualAssets/logo_new.png"));
		
		tutorialStepZero = new Texture(Gdx.files.internal("MutualAssets/tutorialStepZero.png"));
		tutorialStepOne = new Texture(Gdx.files.internal("MutualAssets/tutorialStepOne.png"));
		tutorialStepTwo = new Texture(Gdx.files.internal("MutualAssets/tutorialStepTwo.png"));
		tutorialStepThree = new Texture(Gdx.files.internal("MutualAssets/tutorialStepThree.png"));
		tutorialStepFour = new Texture(Gdx.files.internal("MutualAssets/tutorialStepFour.png"));
		tutorialStepFive = new Texture(Gdx.files.internal("MutualAssets/tutorialStepFive.png"));
		tutorialStepSix = new Texture(Gdx.files.internal("MutualAssets/tutorialStepSix.png"));
		tutorialBorder = new Texture(Gdx.files.internal("MutualAssets/tutorialBorder.png"));

		kill = new Texture[8];
		for(int i = 0; i < 8; i++)
		{
			kill[i] = new Texture(Gdx.files.internal("MutualAssets/kill_"+ Integer.toString(i) + ".png"));
		}
	}
}




