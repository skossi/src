package managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import entities.RectTex;



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
	
	//Store
	public RectTex Store;
	
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
	
	private void createButtons()
	{
		//variable float for temp x,y coordinates
		float xHolder;
		float yHolder;
		float width;
		float height;
		
		//Menu
		Texture MenuTex = new Texture(Gdx.files.internal(asset+location+"logo_main.png"));
		width = MenuTex.getWidth();
		height = MenuTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 700; 
		Menu = new RectTex(xHolder,yHolder, width, height, MenuTex);
		
		Texture PlayTex = new Texture(Gdx.files.internal(asset+location+"play.png"));
		width = PlayTex.getWidth();
		height = PlayTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 550; 
		EnterPlay = new RectTex(xHolder,yHolder, width, height, PlayTex);
		
		Texture ScoreTex = new Texture(Gdx.files.internal(asset+location+"score.png"));
		width = ScoreTex.getWidth();
		height = ScoreTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 400; 
		EnterScore = new RectTex(xHolder,yHolder, width, height, ScoreTex);
		
		Texture TutorialTex = new Texture(Gdx.files.internal(asset+location+"tutorial.png"));
		width = TutorialTex.getWidth();
		height = TutorialTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 250; 
		EnterTut = new RectTex(xHolder,yHolder, width, height, TutorialTex);
		
		Texture StoreTex = new Texture(Gdx.files.internal(asset+location+"store.png"));
		width = StoreTex.getWidth();
		height = StoreTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 100; 
		EnterStore = new RectTex(xHolder,yHolder, width, height, StoreTex);
		
		//Play - Pause State
		
		Texture ResumeTex = new Texture(Gdx.files.internal(asset+location+"pauseResume.png"));
		width = StoreTex.getWidth();
		height = StoreTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 400; 
		PauseResume = new RectTex(xHolder,yHolder, width, height, ResumeTex);
		
		Texture RestartTex = new Texture(Gdx.files.internal(asset+location+"pauseRestart.png"));
		width = StoreTex.getWidth();
		height = StoreTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 250; 
		PauseRestart = new RectTex(xHolder,yHolder, width, height, RestartTex);
		
		Texture QuitTex = new Texture(Gdx.files.internal(asset+location+"pauseQuit.png"));
		width = StoreTex.getWidth();
		height = StoreTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 100; 
		PauseQuit = new RectTex(xHolder,yHolder, width, height, QuitTex);
		
		//BackButton
		Texture BackStoreTex = new Texture(Gdx.files.internal(asset+location+"backStore.png"));
		width = BackStoreTex.getWidth();
		height = BackStoreTex.getHeight();
		xHolder = 480 * 0.75f - width/ 2; 
		yHolder = 20;
		BackStore = new RectTex(xHolder, yHolder, width, height, BackStoreTex);
		
		Texture BackScoreTex = new Texture(Gdx.files.internal(asset+location+"backScore.png"));
		width = BackScoreTex.getWidth();
		height = BackScoreTex.getHeight();
		xHolder = 480 * 0.75f - width/ 2; 
		yHolder = 20;
		BackScore = new RectTex(xHolder, yHolder, width, height, BackScoreTex);
		
		//ScoreLogo  
		Texture ScoreLogoTex = new Texture(Gdx.files.internal(asset+location+"logo_score.png"));
		width = ScoreLogoTex.getWidth();
		height = ScoreLogoTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 700;
		Score = new RectTex(xHolder, yHolder, width, height, ScoreLogoTex);	
		
		//ScoreLogo  
		Texture StoreLogoTex = new Texture(Gdx.files.internal(asset+location+"logo_store.png"));
		width = StoreLogoTex.getWidth();
		height = StoreLogoTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 700;
		Store = new RectTex(xHolder, yHolder, width, height, StoreLogoTex);
		
		//Game Over Logo
		Texture OverTex = new Texture(Gdx.files.internal(asset+location+"logo_over.png"));
		width = OverTex.getWidth();
		height = OverTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 700;
		Over = new RectTex(xHolder, yHolder, width, height, OverTex);	
		
		Texture ReplayTex = new Texture(Gdx.files.internal(asset+location+"playAgain.png"));
		width = ReplayTex.getWidth();
		height = ReplayTex.getHeight();
		xHolder = 480 * 0.75f - width/ 2; 
		yHolder = 150;
		Replay = new RectTex(xHolder, yHolder, width, height, ReplayTex);
		
		Texture MainMenuTex = new Texture(Gdx.files.internal(asset+location+"mainMenu.png"));
		width = MainMenuTex.getWidth();
		height = MainMenuTex.getHeight();
		xHolder = 480 * 0.25f - width/ 2; 
		yHolder = 150;
		MainMenu = new RectTex(xHolder, yHolder, width, height, MainMenuTex);
	}

}
