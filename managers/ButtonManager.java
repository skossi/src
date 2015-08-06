package managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import entities.RectTex;

//Class by Ottar Gudmundsson
//Written 23.2.2015
//Button manager that creates and hold all interactive buttons of the game.

public class ButtonManager {
	
	//Menu
	public RectTex Menu;
	public RectTex EnterPlay;
	public RectTex EnterStore;
	public RectTex TutorialPlay;
	public RectTex TutorialNext;
	
	//Play
	public RectTex PauseResume;
	public RectTex PauseRestart;
	public RectTex PauseQuit;
	
	//Store
	public RectTex Store;
	public RectTex TabThemes;
	public RectTex TabAudios;
	public RectTex TabStats;
	
	public RectTex[] StoreThemes;
	public RectTex[] StoreAudios;
	public RectTex[][] StoreSelect = new RectTex[][]{StoreThemes,StoreAudios};
		
	//BackButton and GameOver
	public RectTex BackStore;
	public RectTex Over;
	public RectTex Replay;
	public RectTex MainMenu;
	public RectTex SoundOn;
	public RectTex SoundOff;
	
	public ButtonManager(String aAsset, String aLoc)
	{
		createButtons();
	}
	
	//Sets up new interactive buttons corresponding to chosen theme.
	private void createButtons()
	{
		//variable float for temp x,y coordinates
		float xHolder;
		float yHolder;
		float width;
		float height;
		
		Texture PlayButtonTex = new Texture(Gdx.files.internal("MutualAssets/playButton.png"));
		width = PlayButtonTex.getWidth();
		height = PlayButtonTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 400; 
		EnterPlay = new RectTex(xHolder,yHolder, width, height, PlayButtonTex, "");
		yHolder = 500;
		
		Texture ButtonTex = new Texture(Gdx.files.internal("MutualAssets/button.png"));
		width = ButtonTex.getWidth();
		height = ButtonTex.getHeight();
		
		xHolder = 480 /2 - width / 2; 
		yHolder = 200; 
		EnterStore = new RectTex(xHolder,yHolder, width, height, ButtonTex, "EXTRAS");
		
		xHolder = 480 /2 - width / 2 + 120; 
		yHolder = 50;
		float nextButtonWidth = 160;
		float nextButtonHeight = 160;
		TutorialPlay = new RectTex(xHolder+1500,yHolder, nextButtonWidth, nextButtonHeight, ButtonTex, "PLAY");
		TutorialNext = new RectTex(xHolder,yHolder, nextButtonWidth, nextButtonHeight, ButtonTex, "NEXT");

		//Play - Pause State
		xHolder = 480 /2 - width / 2; 
		yHolder = 400; 
		PauseResume = new RectTex(xHolder,yHolder, width, height, ButtonTex, "RESUME");
		
		xHolder = 480 /2 - width / 2; 
		yHolder = 250; 
		PauseRestart = new RectTex(xHolder,yHolder, width, height, ButtonTex, "RESTART");
		
		xHolder = 480 /2 - width / 2; 
		yHolder = 100; 
		PauseQuit = new RectTex(xHolder,yHolder, width, height, ButtonTex, "QUIT");

		xHolder = 480 * 0.75f - width/ 2; 
		yHolder = 150;
		Replay = new RectTex(xHolder, yHolder, width, height, ButtonTex,"RESTART");

		xHolder = 480 * 0.25f - width/ 2; 
		yHolder = 150;
		MainMenu = new RectTex(xHolder, yHolder, width, height, ButtonTex, "MENU");
		
		//BackButton
		Texture BackStoreTex = new Texture(Gdx.files.internal("MutualAssets/backStore.png"));
		width = BackStoreTex.getWidth();
		height = BackStoreTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 675;//20;
		BackStore = new RectTex(xHolder, yHolder, width, height, BackStoreTex,"");
	
		//STORETABS
		Texture StoreTab = new Texture(Gdx.files.internal("MutualAssets/tab.png"));
		width = StoreTab.getWidth();
		height = StoreTab.getHeight();
		
		xHolder = 0; 
		yHolder = 0;//650;
		TabThemes = new RectTex(xHolder, yHolder, width, height, StoreTab,"");
		
		xHolder = 160; 
		yHolder = 0;//650;
		TabAudios = new RectTex(xHolder, yHolder, width, height, StoreTab,"");
		
		xHolder = 320; 
		yHolder = 0;//650;
		TabStats = new RectTex(xHolder, yHolder, width, height, StoreTab,"");
		
		//STORE ARRAYS CREATED
		
		//THEMES
		StoreThemes = new RectTex[4];
		
		Texture ThemeOneTex = new Texture(Gdx.files.internal("MutualAssets/"+"themeOne.png"));
		width = ThemeOneTex.getWidth();
		height = ThemeOneTex.getHeight();
		xHolder = 480 /4 - width / 2; 
		yHolder = 450;
		StoreThemes[0] = new RectTex(xHolder, yHolder, width, height, ThemeOneTex,"");
		
		Texture ThemeTwoTex = new Texture(Gdx.files.internal("MutualAssets/"+"themeTwo.png"));
		width = ThemeTwoTex.getWidth();
		height = ThemeTwoTex.getHeight();
		xHolder = 480 /4 - width / 2; 
		yHolder = 230;
		StoreThemes[1] = new RectTex(xHolder, yHolder, width, height, ThemeTwoTex,"");
		
		Texture ThemeThreeTex = new Texture(Gdx.files.internal("MutualAssets/"+"themeThree.png"));
		width = ThemeTwoTex.getWidth();
		height = ThemeTwoTex.getHeight();
		xHolder = 3*480 /4 - width / 2; 
		yHolder = 450;
		StoreThemes[2] = new RectTex(xHolder, yHolder, width, height, ThemeThreeTex,"");
		
		Texture ThemeFourTex = new Texture(Gdx.files.internal("MutualAssets/"+"themeFour.png"));
		width = ThemeTwoTex.getWidth();
		height = ThemeTwoTex.getHeight();
		xHolder = 3*480 /4 - width / 2; 
		yHolder = 230;
		StoreThemes[3] = new RectTex(xHolder, yHolder, width, height, ThemeFourTex,"");
		
		StoreSelect[0] = StoreThemes;
		
		//Audios
		StoreAudios = new RectTex[4];
			
		Texture AudioOneTex = new Texture(Gdx.files.internal("MutualAssets/"+"audioOne.png"));
		width = ThemeOneTex.getWidth();
		height = ThemeOneTex.getHeight();
		xHolder = 480 /4 - width / 2; 
		yHolder = 450;
		StoreAudios[0] = new RectTex(xHolder, yHolder, width, height, AudioOneTex,"");
		
		Texture AudioTwoTex = new Texture(Gdx.files.internal("MutualAssets/"+"audioTwo.png"));
		width = ThemeTwoTex.getWidth();
		height = ThemeTwoTex.getHeight();
		xHolder = 480 /4 - width / 2; 
		yHolder = 230;
		StoreAudios[1] = new RectTex(xHolder, yHolder, width, height, AudioTwoTex,"");
		
		Texture AudioThreeTex = new Texture(Gdx.files.internal("MutualAssets/"+"audioThree.png"));
		width = ThemeTwoTex.getWidth();
		height = ThemeTwoTex.getHeight();
		xHolder = 3*480 /4 - width / 2; 
		yHolder = 450;
		StoreAudios[2] = new RectTex(xHolder, yHolder, width, height, AudioThreeTex,"");
		
		Texture AudioFourTex = new Texture(Gdx.files.internal("MutualAssets/"+"audioFour.png"));
		width = ThemeTwoTex.getWidth();
		height = ThemeTwoTex.getHeight();
		xHolder = 3*480 /4 - width / 2; 
		yHolder = 230;
		StoreAudios[3] = new RectTex(xHolder, yHolder, width, height, AudioFourTex,"");
			
		StoreSelect[1] = StoreAudios;
		
		//Game Over Logo
		Texture OverTex = new Texture(Gdx.files.internal("MutualAssets/logo_over.png"));
		width = OverTex.getWidth();
		height = OverTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 700;
		Over = new RectTex(xHolder, yHolder, width, height, OverTex,"");
		
		Texture SoundOnTex = new Texture(Gdx.files.internal("MutualAssets/soundOn.png"));
		width = SoundOnTex.getWidth();
		height = SoundOnTex.getHeight();
		xHolder = 480 -width; 
		yHolder = 800-height;
		SoundOn = new RectTex(xHolder, yHolder, width, height, SoundOnTex,"");
		
		Texture SoundOffTex = new Texture(Gdx.files.internal("MutualAssets/soundOff.png"));
		SoundOff = new RectTex(xHolder, yHolder, width, height, SoundOffTex,"");
	
	}

}
