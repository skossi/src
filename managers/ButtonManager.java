package managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import entities.RectTex;

//Class by Ottar Gudmundsson
//Written 23.2.2015
//Button manager that creates and hold all interactive buttons of the game.

public class ButtonManager {
	
	private String location;
	private String asset;
	
	//Menu
	public RectTex Menu;
	public RectTex EnterPlay;
	public RectTex EnterScore;
	public RectTex EnterTut;
	public RectTex EnterStore;
	
	//Play
	public RectTex PauseResume;
	public RectTex PauseRestart;
	public RectTex PauseQuit;
	
	//Score
	public RectTex Score;
	public RectTex TabStats;
	public RectTex TabAbout;
	public RectTex Facebook;
	
	//Store
	public RectTex Store;
	public RectTex TabPowers;
	public RectTex TabLevels;
	public RectTex TabThemes;
	public RectTex TabExtras;
	
	public RectTex[] StorePowers;
	public RectTex[] StoreLevels;
	public RectTex[] StoreThemes;
	public RectTex[] StoreExtras;
	public RectTex[][] StoreSelect = new RectTex[][]{StorePowers,StoreLevels,StoreThemes,StoreExtras};
	
	
	//BackButton and GameOver
	public RectTex BackStore;
	public RectTex BackScore;
	public RectTex Over;
	public RectTex Replay;
	public RectTex MainMenu;
	
	public ButtonManager(String aAsset, String aLoc)
	{
		asset = aAsset;
		location = aLoc;
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
		
		//Menu
		Texture MenuTex = new Texture(Gdx.files.internal("MutualAssets/logo_main.png"));
		width = MenuTex.getWidth();
		height = MenuTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 700; 
		Menu = new RectTex(xHolder,yHolder, width, height, MenuTex);
		
		Texture PlayTex = new Texture(Gdx.files.internal("MutualAssets/play.png"));
		width = PlayTex.getWidth();
		height = PlayTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 550; 
		EnterPlay = new RectTex(xHolder,yHolder, width, height, PlayTex);
		
		Texture ScoreTex = new Texture(Gdx.files.internal("MutualAssets/score.png"));
		width = ScoreTex.getWidth();
		height = ScoreTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 400; 
		EnterScore = new RectTex(xHolder,yHolder, width, height, ScoreTex);
		
		Texture TutorialTex = new Texture(Gdx.files.internal("MutualAssets/tutorial.png"));
		width = TutorialTex.getWidth();
		height = TutorialTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 250; 
		EnterTut = new RectTex(xHolder,yHolder, width, height, TutorialTex);
		
		Texture StoreTex = new Texture(Gdx.files.internal("MutualAssets/store.png"));
		width = StoreTex.getWidth();
		height = StoreTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 100; 
		EnterStore = new RectTex(xHolder,yHolder, width, height, StoreTex);
		
		//Play - Pause State
		
		Texture ResumeTex = new Texture(Gdx.files.internal("MutualAssets/pauseResume.png"));
		width = StoreTex.getWidth();
		height = StoreTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 400; 
		PauseResume = new RectTex(xHolder,yHolder, width, height, ResumeTex);
		
		Texture RestartTex = new Texture(Gdx.files.internal("MutualAssets/pauseRestart.png"));
		width = StoreTex.getWidth();
		height = StoreTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 250; 
		PauseRestart = new RectTex(xHolder,yHolder, width, height, RestartTex);
		
		Texture QuitTex = new Texture(Gdx.files.internal("MutualAssets/pauseQuit.png"));
		width = StoreTex.getWidth();
		height = StoreTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 100; 
		PauseQuit = new RectTex(xHolder,yHolder, width, height, QuitTex);
		
		//BackButton
		Texture BackStoreTex = new Texture(Gdx.files.internal("MutualAssets/backStore.png"));
		width = BackStoreTex.getWidth();
		height = BackStoreTex.getHeight();
		xHolder = 480 * 0.75f - width/ 2; 
		yHolder = 20;
		BackStore = new RectTex(xHolder, yHolder, width, height, BackStoreTex);
		
		Texture BackScoreTex = new Texture(Gdx.files.internal("MutualAssets/backScore.png"));
		width = BackScoreTex.getWidth();
		height = BackScoreTex.getHeight();
		xHolder = 480 * 0.75f - width/ 2; 
		yHolder = 20;
		BackScore = new RectTex(xHolder, yHolder, width, height, BackScoreTex);
		
		//ScoreLogo  
		Texture ScoreLogoTex = new Texture(Gdx.files.internal("MutualAssets/logo_score.png"));
		width = ScoreLogoTex.getWidth();
		height = ScoreLogoTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 700; //var 700
		Score = new RectTex(xHolder, yHolder, width, height, ScoreLogoTex);	
		
		Texture ScoreTab = new Texture(Gdx.files.internal("MutualAssets/scoreTab.png"));
		width = ScoreTab.getWidth();
		height = ScoreTab.getHeight();
		
		xHolder = 0; 
		yHolder = 625;
		TabStats = new RectTex(xHolder, yHolder, width, height, ScoreTab);
		
		xHolder = 240; 
		yHolder = 625;
		TabAbout = new RectTex(xHolder, yHolder, width, height, ScoreTab);
		
		Texture FacebookTex = new Texture(Gdx.files.internal("MutualAssets/facebook.png"));
		width = FacebookTex.getWidth();
		height = FacebookTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 300;
		Facebook = new RectTex(xHolder, yHolder, width, height, FacebookTex);
		
		//STORELOGO
		Texture StoreLogoTex = new Texture(Gdx.files.internal("MutualAssets/logo_store.png"));
		width = StoreLogoTex.getWidth();
		height = StoreLogoTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 725;// Was 700
		Store = new RectTex(xHolder, yHolder, width, height, StoreLogoTex);
		
		//STORETABS
		
		Texture StoreTab = new Texture(Gdx.files.internal("MutualAssets/storeTab.png"));
		width = StoreTab.getWidth();
		height = StoreTab.getHeight();
		
		xHolder = 0; 
		yHolder = 650;
		TabPowers = new RectTex(xHolder, yHolder, width, height, StoreTab);
		
		xHolder = 120; 
		yHolder = 650;
		TabLevels = new RectTex(xHolder, yHolder, width, height, StoreTab);
		
		xHolder = 240; 
		yHolder = 650;
		TabThemes = new RectTex(xHolder, yHolder, width, height, StoreTab);
		
		xHolder = 360; 
		yHolder = 650;
		TabExtras = new RectTex(xHolder, yHolder, width, height, StoreTab);
		
		//STORE ARRAYS CREATED
		
		//THEMES
		StoreThemes = new RectTex[4];
		
		Texture ThemeOneTex = new Texture(Gdx.files.internal("MutualAssets/"+"themeOne.png"));
		width = ThemeOneTex.getWidth();
		height = ThemeOneTex.getHeight();
		xHolder = 480 /4 - width / 2; 
		yHolder = 450;
		StoreThemes[0] = new RectTex(xHolder, yHolder, width, height, ThemeOneTex);
		
		Texture ThemeTwoTex = new Texture(Gdx.files.internal("MutualAssets/"+"themeTwo.png"));
		width = ThemeTwoTex.getWidth();
		height = ThemeTwoTex.getHeight();
		xHolder = 480 /4 - width / 2; 
		yHolder = 230;
		StoreThemes[1] = new RectTex(xHolder, yHolder, width, height, ThemeTwoTex);
		
		Texture ThemeThreeTex = new Texture(Gdx.files.internal("MutualAssets/"+"themeThree.png"));
		width = ThemeTwoTex.getWidth();
		height = ThemeTwoTex.getHeight();
		xHolder = 3*480 /4 - width / 2; 
		yHolder = 450;
		StoreThemes[2] = new RectTex(xHolder, yHolder, width, height, ThemeThreeTex);
		
		Texture ThemeFourTex = new Texture(Gdx.files.internal("MutualAssets/"+"themeFour.png"));
		width = ThemeTwoTex.getWidth();
		height = ThemeTwoTex.getHeight();
		xHolder = 3*480 /4 - width / 2; 
		yHolder = 230;
		StoreThemes[3] = new RectTex(xHolder, yHolder, width, height, ThemeFourTex);
		
		StoreSelect[2] = StoreThemes;
		
		//POWERS
		StorePowers = new RectTex[4];

		//Texture ThemeOneTex = new Texture(Gdx.files.internal("MutualAssets/"+"themeOne.png"));
		width = ThemeOneTex.getWidth();
		height = ThemeOneTex.getHeight();
		xHolder = 480 /4 - width / 2; 
		yHolder = 450;
		StorePowers[0] = new RectTex(xHolder, yHolder, width, height, ThemeOneTex);
		
//		Texture ThemeTwoTex = new Texture(Gdx.files.internal("MutualAssets/"+"themeTwo.png"));
		width = ThemeTwoTex.getWidth();
		height = ThemeTwoTex.getHeight();
		xHolder = 480 /4 - width / 2; 
		yHolder = 230;
		StorePowers[1] = new RectTex(xHolder, yHolder, width, height, ThemeTwoTex);
		
//		Texture ThemeThreeTex = new Texture(Gdx.files.internal("MutualAssets/"+"themeThree.png"));
		width = ThemeTwoTex.getWidth();
		height = ThemeTwoTex.getHeight();
		xHolder = 3*480 /4 - width / 2; 
		yHolder = 450;
		StorePowers[2] = new RectTex(xHolder, yHolder, width, height, ThemeThreeTex);
		
//		Texture ThemeFourTex = new Texture(Gdx.files.internal("MutualAssets/"+"themeFour.png"));
		width = ThemeTwoTex.getWidth();
		height = ThemeTwoTex.getHeight();
		xHolder = 3*480 /4 - width / 2; 
		yHolder = 230;
		StorePowers[3] = new RectTex(xHolder, yHolder, width, height, ThemeFourTex);
		
		StoreSelect[0] = StorePowers;
		
		//Audios
		StoreLevels = new RectTex[4];
			
//		Texture ThemeOneTex = new Texture(Gdx.files.internal("MutualAssets/"+"themeOne.png"));
		width = ThemeOneTex.getWidth();
		height = ThemeOneTex.getHeight();
		xHolder = 480 /4 - width / 2; 
		yHolder = 450;
		StoreLevels[0] = new RectTex(xHolder, yHolder, width, height, ThemeOneTex);
		
//		Texture ThemeTwoTex = new Texture(Gdx.files.internal("MutualAssets/"+"themeTwo.png"));
		width = ThemeTwoTex.getWidth();
		height = ThemeTwoTex.getHeight();
		xHolder = 480 /4 - width / 2; 
		yHolder = 230;
		StoreLevels[1] = new RectTex(xHolder, yHolder, width, height, ThemeTwoTex);
		
//		Texture ThemeThreeTex = new Texture(Gdx.files.internal("MutualAssets/"+"themeThree.png"));
		width = ThemeTwoTex.getWidth();
		height = ThemeTwoTex.getHeight();
		xHolder = 3*480 /4 - width / 2; 
		yHolder = 450;
		StoreLevels[2] = new RectTex(xHolder, yHolder, width, height, ThemeThreeTex);
		
//		Texture ThemeFourTex = new Texture(Gdx.files.internal("MutualAssets/"+"themeFour.png"));
		width = ThemeTwoTex.getWidth();
		height = ThemeTwoTex.getHeight();
		xHolder = 3*480 /4 - width / 2; 
		yHolder = 230;
		StoreLevels[3] = new RectTex(xHolder, yHolder, width, height, ThemeFourTex);
			
		StoreSelect[1] = StoreLevels;
		
		
		StoreExtras = new RectTex[4];

		//Texture ThemeOneTex = new Texture(Gdx.files.internal("MutualAssets/"+"themeOne.png"));
		width = ThemeOneTex.getWidth();
		height = ThemeOneTex.getHeight();
		xHolder = 480 /4 - width / 2; 
		yHolder = 450;
		StoreExtras[0] = new RectTex(xHolder, yHolder, width, height, ThemeOneTex);
		
		//Texture ThemeTwoTex = new Texture(Gdx.files.internal("MutualAssets/"+"themeTwo.png"));
		width = ThemeTwoTex.getWidth();
		height = ThemeTwoTex.getHeight();
		xHolder = 480 /4 - width / 2; 
		yHolder = 230;
		StoreExtras[1] = new RectTex(xHolder, yHolder, width, height, ThemeTwoTex);
		
		//Texture ThemeThreeTex = new Texture(Gdx.files.internal("MutualAssets/"+"themeThree.png"));
		width = ThemeTwoTex.getWidth();
		height = ThemeTwoTex.getHeight();
		xHolder = 3*480 /4 - width / 2; 
		yHolder = 450;
		StoreExtras[2] = new RectTex(xHolder, yHolder, width, height, ThemeThreeTex);
		
		//Texture ThemeFourTex = new Texture(Gdx.files.internal("MutualAssets/"+"themeFour.png"));
		width = ThemeTwoTex.getWidth();
		height = ThemeTwoTex.getHeight();
		xHolder = 3*480 /4 - width / 2; 
		yHolder = 230;
		StoreExtras[3] = new RectTex(xHolder, yHolder, width, height, ThemeFourTex);
		
		StoreSelect[3] = StoreExtras;
	
		
		//Game Over Logo
		Texture OverTex = new Texture(Gdx.files.internal("MutualAssets/logo_over.png"));
		width = OverTex.getWidth();
		height = OverTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 700;
		Over = new RectTex(xHolder, yHolder, width, height, OverTex);
		
		Texture ReplayTex = new Texture(Gdx.files.internal("MutualAssets/playAgain.png"));
		width = ReplayTex.getWidth();
		height = ReplayTex.getHeight();
		xHolder = 480 * 0.75f - width/ 2; 
		yHolder = 150;
		Replay = new RectTex(xHolder, yHolder, width, height, ReplayTex);
		
		Texture MainMenuTex = new Texture(Gdx.files.internal("MutualAssets/mainMenu.png"));
		width = MainMenuTex.getWidth();
		height = MainMenuTex.getHeight();
		xHolder = 480 * 0.25f - width/ 2; 
		yHolder = 150;
		MainMenu = new RectTex(xHolder, yHolder, width, height, MainMenuTex);
	}

}
