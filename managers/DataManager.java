package managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import entities.RectTex;
//Class by �ttar Gu�mundsson
//Written 14.11.2014
//Creates Manager that was originally supposed to take care of all rectangles for the game.
//Was changed to be also resource manager 18.11.2014
public class DataManager 
{	
	public boolean firstTime;
	public int size;
	
	//Textures for gameplay
	public Texture square;
	public Texture triangle;
	public Texture circle;
	public Texture ex;
	public Texture black;
	public Texture blinkBlack;
	public Texture selected;
	public Texture ui_bg;
	public Texture ui_pauseOn;
	public Texture ui_pauseOff;
	public Texture ui_soundOn;
	public Texture ui_soundOff;
	public Texture pauseBlock;
	public Texture redline;
	public Texture ScoreBoard;
	public Texture newScore;
	public Texture Power_Multi;
	public Texture Power_50;
	
	//Backgrounds
	public Texture back_1;
	public Texture back_2;
	public Texture back_3;
	
	//Menu
	public RectTex Menu;
	public RectTex EnterPlay;
	public RectTex EnterScore;
	public RectTex EnterTut;
	public RectTex EnterStore;
	public int MenuXOffset;
	public int MenuYOffset;
	public int horizontalSpeed;
	public int verticalSpeed;
	public int speedAdd;
	public boolean moveMenu;
	public boolean moveFromSides;
	public int sideDir;
	public boolean isMenuDown;
	
	//Play
	public RectTex PauseResume;
	public RectTex PauseRestart;
	public RectTex PauseQuit;
	
	//Tutorial
	public RectTex Tutorial;
	public RectTex Info;
	public RectTex ObjBar;
	public RectTex CtrBar;
	public RectTex ScrBar;
	public Texture[] infoTex = new Texture[4];
	
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
	
	//preferences
	private Preferences prefs;
	
	//ScoreHolder
	public int sumRecord,squareRecord, triangleRecord, circleRecord, exRecord;
	public int[] sumRecordHolder;
	public int[] squareRecordHolder;
	public int[] triangleRecordHolder;
	public int[] circleRecordHolder;
	public int[] exRecordHolder;
	public boolean NewHighScore;
	public boolean NewIndivScore;
	public int whichNewIndivScore;
	public int[] currentScore;
	public int[] currencyInt;
	
	//RGB values
	public float _rOrg;
	public float _gOrg;
	public float _bOrg;
	public float _r;
	public float _g;
	public float _b;
	public float _w;
	
	//Font writing
	public BitmapFont fontBlack;
	public BitmapFont fontWhite;
	public String newHighString;
	public String newIndivString;
	public String worseString;
	
	//Main themesound and audio effects
	public float Volume;
	public boolean isMuted;
	private Music themeLevel_1;
	private Music themeLevel_2;
	private Music themeLevel_3;
	private Music themeLevel_4;
	public Sound swapSound;
	public Sound collectSound;
	public Sound shootSound;
	public Sound pauseSound;
	public Sound muteSound;
	public Sound startGame;
	
	//Creates a resource entity each time the game is opened
	public DataManager()
	{
		createTextures();
		createButtons();
		createSounds();
		
		size = 68;
		speedAdd = 1;
		horizontalSpeed = 600;
		verticalSpeed = 600;
		
		sumRecordHolder = new int[4];
		squareRecordHolder = new int[4];
		triangleRecordHolder = new int[4];
		circleRecordHolder = new int[4];
		exRecordHolder = new int[4];
		currencyInt = new int[4];
		getScores();

		firstTime = prefs.getBoolean("First");
		
		_rOrg = 1;
		_gOrg = 1;
		_bOrg = 1;
		_r = _rOrg;
		_g = _gOrg;
		_b = _bOrg;
		_w = 0;
		
		fontBlack = new BitmapFont();
	    fontBlack.setColor(Color.BLACK); //var Color.BLACK
	    fontBlack.setScale(2,2);
	    
	    fontWhite = new BitmapFont();
	    fontWhite.setColor(Color.WHITE); //var Color.BLACK
	    fontWhite.setScale(2,2);
	    
	    newHighString = "Congratulations, new score!";
	    newIndivString = "You made a new record run!";
	    worseString = "Sorry, no high score was made.";
	    
	}
	//Mutes or unmutes the maintheme of the game
	public void soundMute()
	{
		if(!isMuted)
		{	
			Volume = 0;
			themeLevel_1.setVolume(Volume);
		}
		else
		{
			Volume = 1;
			themeLevel_1.setVolume(1);
		}
		isMuted =! isMuted;
	}
	//If user opens the game for the first time, the tutorial screen is set. 
	//After that, this void is called to open the menu screen the next time
	public void firstDone()
	{
		firstTime = true;
		prefs.putBoolean("First",true);
		prefs.flush();
		prefs = Gdx.app.getPreferences("My Preferences");
	}
	//Updates the resources manager variables by using data from the phones preferences
	private void getScores()
	{
		prefs = Gdx.app.getPreferences("My Preferences");
		//Best overall Score
		sumRecord = prefs.getInteger("SumRecord");
		sumRecordHolder[0] = prefs.getInteger("SumRecordSquare");
		sumRecordHolder[1] = prefs.getInteger("SumRecordTriangle");
		sumRecordHolder[2] = prefs.getInteger("SumRecordCircle");
		sumRecordHolder[3] = prefs.getInteger("SumRecordEx");
		//Individual blocks
		//Square
		squareRecord = prefs.getInteger("SquareRecord");
		squareRecordHolder[0] = prefs.getInteger("SquareRecord");
		squareRecordHolder[1] = prefs.getInteger("SquareRecordTriangle");
		squareRecordHolder[2] = prefs.getInteger("SquareRecordCircle");
		squareRecordHolder[3] = prefs.getInteger("SquareRecordEx");
		//Triangle
		triangleRecord = prefs.getInteger("TriangleRecord");
		triangleRecordHolder[0] = prefs.getInteger("TriangleRecordSquare");
		triangleRecordHolder[1] = prefs.getInteger("TriangleRecord");
		triangleRecordHolder[2] = prefs.getInteger("TriangleRecordCircle");
		triangleRecordHolder[3] = prefs.getInteger("TriangleRecordEx");
		//Circle
		circleRecord =  prefs.getInteger("CircleRecord");
		circleRecordHolder[0] = prefs.getInteger("CircleRecordSquare");
		circleRecordHolder[1] = prefs.getInteger("CircleRecordTriangle");
		circleRecordHolder[2] = prefs.getInteger("CircleRecord");
		circleRecordHolder[3] = prefs.getInteger("CircleRecordEx");
		//Ex
		exRecord = prefs.getInteger("ExRecord");	
		exRecordHolder[0] = prefs.getInteger("ExRecordSquare");
		exRecordHolder[1] = prefs.getInteger("ExRecordTriangle");
		exRecordHolder[2] = prefs.getInteger("ExRecordCircle");
		exRecordHolder[3] = prefs.getInteger("ExRecord");
		
		
		currencyInt[0] = prefs.getInteger("currencySquare");
		currencyInt[1] = prefs.getInteger("currencyTriangle");
		currencyInt[2] = prefs.getInteger("currencyCircle");
		currencyInt[3] = prefs.getInteger("currencyEx");
		
		//currency = prefs.getInteger("currency");
	}
	//Takes in parameter newScore that is players score when game is lost. 
	//Gets preferences and checks if a new high score was made
	//if so, it saves it and refreshes the preferences.
	public void checkScore(int[] newScore)
	{
		currentScore = newScore;
		NewHighScore = false;
		NewIndivScore = false;
		getScores();
		//Check if the sum of all bricks are new record
		int sum = 0;
		for(int i = 0; i < 4; i++)
		{
			sum += newScore[i];
			//currencyInt[i] += newScore[i];
			currencyInt[i] = 0;
		}
		
		prefs.putInteger("currencySquare", currencyInt[0]);
		prefs.putInteger("currencyTriangle", currencyInt[1]);
		prefs.putInteger("currencyCircle", currencyInt[2]);
		prefs.putInteger("currencyEx", currencyInt[3]);
		
		//Check if the made a new total high score
		if(sum > sumRecord) 
		{
			CopyArray(sumRecordHolder,newScore);
			prefs.putInteger("SumRecord",sum);
			prefs.putInteger("SumRecordSquare", newScore[0]);
			prefs.putInteger("SumRecordTriangle", newScore[1]);
			prefs.putInteger("SumRecordCircle", newScore[2]);
			prefs.putInteger("SumRecordEx", newScore[3]);
			NewHighScore = true;
		}

		//Check if we made a new record of different cubes
		if(newScore[0] > squareRecord)
		{
			CopyArray(squareRecordHolder,newScore);
			prefs.putInteger("SquareRecord", newScore[0]);
			prefs.putInteger("SquareRecordTriangle", newScore[1]);
			prefs.putInteger("SquareRecordCircle", newScore[2]);
			prefs.putInteger("SquareRecordEx", newScore[3]);
			NewIndivScore = true;
			whichNewIndivScore = 0;
		}
		if(newScore[1] > triangleRecord)
		{
			CopyArray(triangleRecordHolder,newScore);
			prefs.putInteger("TriangleRecordSquare", newScore[0]);
			prefs.putInteger("TriangleRecord", newScore[1]);
			prefs.putInteger("TriangleRecordCircle", newScore[2]);
			prefs.putInteger("TriangleRecordEx", newScore[3]);
			NewIndivScore = true;
			whichNewIndivScore = 1;
		}
		if(newScore[2] > circleRecord)
		{
			CopyArray(circleRecordHolder,newScore);
			prefs.putInteger("CircleRecordSquare", newScore[0]);
			prefs.putInteger("CircleRecordTriangle", newScore[1]);
			prefs.putInteger("CircleRecord", newScore[2]);
			prefs.putInteger("CircleRecordEx", newScore[3]);
			NewIndivScore = true;
			whichNewIndivScore = 2;
		}
		if(newScore[3] > exRecord)
		{
			CopyArray(exRecordHolder,newScore);
			prefs.putInteger("ExRecordSquare", newScore[0]);
			prefs.putInteger("ExRecordTriangle", newScore[1]);
			prefs.putInteger("ExRecordCircle", newScore[2]);
			prefs.putInteger("ExRecord", newScore[3]);
			NewIndivScore = true;
			whichNewIndivScore = 3;
		}

		
		//TODO : Here we need to add all the blocks to currency. Impliment when store is made
		/*int temp = currency;
		temp += newScore;
		prefs.putInteger("currency", temp);
		*/
		
		prefs.flush();
		getScores();
	}
	
	//Changes an array to take other values. Used to change scores.
	private void CopyArray(int[] aArray, int[] aNewArray)
	{
		for(int i = 0; i < 4; i++)aArray[i] = aNewArray[i];
	}
	
	//Draws a scoreboard at given co ordinates. Takes is an array of Strings which represents
	//high scores, current highscores or the users currency. 
	//Can also draw a texture at chosen position (to indicate where a new score was made).
	public void drawScoreBoard(SpriteBatch batch, float x, int y, String[] aString, boolean showSpecial, int specialPos, BitmapFont font )
	{
		
		batch.draw(square,x+0*size,y);
		batch.draw(triangle,x+1*size,y);
		batch.draw(circle,x+2*size,y);
		batch.draw(ex,x+3*size,y);
		if(showSpecial && specialPos != -1)batch.draw(newScore, x+specialPos*size, y);
		for(int i = 0; i < 4; i++)
	    {
			if(aString[i].length() == 4)font.draw(batch, aString[i], x+size*i, y+45);
			else if(aString[i].length() == 3)font.draw(batch, aString[i], x+10+size*i, y+45); 
    		else if(aString[i].length() == 2)font.draw(batch, aString[i], x+20+size*i, y+45);
    		else font.draw(batch, aString[i], x+30+size*i, y+45); 	  
	    }
	}
	
	//When the menu entities have moved down after the animation has completed, they are reset
	//That way the user can access the menu after the game is finished
	//TODO: change to sub class later.
	public void resetMenu()
	{
		Menu.y = 700;
		Menu.x = 480 /2 - Menu.width / 2;
		EnterPlay.y = 550;
		EnterPlay.x = 480 /2 - EnterPlay.width / 2;
		EnterScore.y = 400;
		EnterScore.x = 480 /2 - EnterScore.width / 2;
		EnterTut.y = 250;
		EnterTut.x = 480 /2 - EnterTut.width / 2;
		EnterStore.y = 100;
		EnterStore.x = 480 /2 - EnterStore.width / 2;
		MenuXOffset = 0;
		MenuYOffset = 0;
		moveMenu = false;
		speedAdd = 1;
	}
	
	//Creates all sounds used in the game.
	//TODO: change to sub class later.
	private void createSounds()
	{
		//Sounds initiated
		themeLevel_1 = Gdx.audio.newMusic(Gdx.files.internal("Sounds/ThemeLevel_1.wav"));
		themeLevel_2 = Gdx.audio.newMusic(Gdx.files.internal("Sounds/ThemeLevel_2.wav"));
		themeLevel_3 = Gdx.audio.newMusic(Gdx.files.internal("Sounds/ThemeLevel_3.wav"));
		themeLevel_4 = Gdx.audio.newMusic(Gdx.files.internal("Sounds/ThemeLevel_4.wav"));
	    collectSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/Collect.wav"));
	    swapSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/SwapTile.wav"));
		shootSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/Match.wav"));
		muteSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/Mute.mp3"));
		pauseSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/Pause.wav"));
		startGame = Gdx.audio.newSound(Gdx.files.internal("Sounds/StartGame.wav"));

	    // start the playback of the background music immediately
		themeLevel_1.setLooping(true);
		themeLevel_1.play();
	    
	    Volume = 1;
	}
	
	//Creates all textures used in game. 
	//TODO: change to sub class later.
	private void createTextures()
	{
		square = new Texture(Gdx.files.internal("square.png"));
		triangle = new Texture(Gdx.files.internal("triangle.png"));
		circle = new Texture(Gdx.files.internal("circle.png"));
		ex = new Texture(Gdx.files.internal("ex.png"));
		black = new Texture(Gdx.files.internal("black.png"));
		blinkBlack = new Texture(Gdx.files.internal("blackBlikk.png"));
		selected = new Texture(Gdx.files.internal("selected.png"));
		ui_bg = new Texture(Gdx.files.internal("ui_bg.png"));
		redline = new Texture(Gdx.files.internal("redline.png"));
		ui_pauseOn = new Texture(Gdx.files.internal("pauseOn.png"));
		ui_pauseOff = new Texture(Gdx.files.internal("pauseOff.png"));
		ui_soundOn = new Texture(Gdx.files.internal("soundOn.png"));
		ui_soundOff = new Texture(Gdx.files.internal("soundOff.png"));
		pauseBlock = new Texture(Gdx.files.internal("pauseBlock.png"));
		ScoreBoard = new Texture(Gdx.files.internal("scoreboard.png"));
		newScore = new Texture(Gdx.files.internal("newScore.png"));
	}
	
	//creates all buttons that are used in the game architecture except the blocks in the main game
	//Each state references to each RectTex to use instead of creating each instance
	//TODO: change to sub class later.
	private void createButtons()
	{
		//variable float for temp x,y coordinates
		float xHolder;
		float yHolder;
		float width;
		float height;
		
		//Menu
		Texture MenuTex = new Texture(Gdx.files.internal("logo_main.png"));
		width = MenuTex.getWidth();
		height = MenuTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 700; 
		Menu = new RectTex(xHolder,yHolder, width, height, MenuTex);
		
		Texture PlayTex = new Texture(Gdx.files.internal("play.png"));
		width = PlayTex.getWidth();
		height = PlayTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 550; 
		EnterPlay = new RectTex(xHolder,yHolder, width, height, PlayTex);
		
		Texture ScoreTex = new Texture(Gdx.files.internal("score.png"));
		width = ScoreTex.getWidth();
		height = ScoreTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 400; 
		EnterScore = new RectTex(xHolder,yHolder, width, height, ScoreTex);
		
		Texture TutorialTex = new Texture(Gdx.files.internal("tutorial.png"));
		width = TutorialTex.getWidth();
		height = TutorialTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 250; 
		EnterTut = new RectTex(xHolder,yHolder, width, height, TutorialTex);
		
		Texture StoreTex = new Texture(Gdx.files.internal("store.png"));
		width = StoreTex.getWidth();
		height = StoreTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 100; 
		EnterStore = new RectTex(xHolder,yHolder, width, height, StoreTex);
		
		//Play - Pause State
		
		Texture ResumeTex = new Texture(Gdx.files.internal("pauseResume.png"));
		width = StoreTex.getWidth();
		height = StoreTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 400; 
		PauseResume = new RectTex(xHolder,yHolder, width, height, ResumeTex);
		
		Texture RestartTex = new Texture(Gdx.files.internal("pauseRestart.png"));
		width = StoreTex.getWidth();
		height = StoreTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 250; 
		PauseRestart = new RectTex(xHolder,yHolder, width, height, RestartTex);
		
		Texture QuitTex = new Texture(Gdx.files.internal("pauseQuit.png"));
		width = StoreTex.getWidth();
		height = StoreTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 100; 
		PauseQuit = new RectTex(xHolder,yHolder, width, height, QuitTex);
		
		//TutorialLogo
		Texture TutorialLogoTex = new Texture(Gdx.files.internal("logo_tutorial.png"));
		width = TutorialLogoTex.getWidth();
		height = TutorialLogoTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 700; 
		Tutorial = new RectTex(xHolder,yHolder, width, height, TutorialLogoTex);
		
		//InfoTextureArray
		infoTex[0] = new Texture(Gdx.files.internal("obj.png"));
		infoTex[1] = new Texture(Gdx.files.internal("ctr.png"));
		infoTex[2] = new Texture(Gdx.files.internal("scr.png"));
		infoTex[3] = new Texture(Gdx.files.internal("orgInfo.png"));
		
		//InfoButtonsCreation	
	    width = infoTex[3].getWidth();
	    height = infoTex[3].getHeight();
	    xHolder = 480 /2 - width / 2; 
	    yHolder = 300; 
	    Info = new RectTex(xHolder, yHolder, width, height, infoTex[3]);
	    
	    Texture objTex = new Texture(Gdx.files.internal("objBarTex.png"));
		float barWidth = objTex.getWidth();
		float barHeight = objTex.getHeight();
		int buttonPos = 1;
		width = barWidth;
		height = barHeight;
		xHolder = (480 / 6)*buttonPos - width/2; buttonPos += 2;
		yHolder = 150;
		ObjBar = new RectTex(xHolder, yHolder, width, height, objTex);
		
		Texture ctrTex = new Texture(Gdx.files.internal("ctrBarTex.png"));
		width = barWidth;
		height = barHeight;
		xHolder = (480 / 6)*buttonPos - width/2; buttonPos +=2;
		yHolder = 150;
		CtrBar = new RectTex(xHolder, yHolder, width, height, ctrTex);
		
		Texture scrTex = new Texture(Gdx.files.internal("scrBarTex.png"));
		width = barWidth;
		height = barHeight;
		xHolder = (480 / 6)*buttonPos - width/2;
		yHolder = 150;
		ScrBar = new RectTex(xHolder, yHolder, width, height, scrTex);
		
		//BackButton
		Texture BackStoreTex = new Texture(Gdx.files.internal("backStore.png"));
		width = BackStoreTex.getWidth();
		height = BackStoreTex.getHeight();
		xHolder = 480 * 0.75f - width/ 2; 
		yHolder = 20;
		BackStore = new RectTex(xHolder, yHolder, width, height, BackStoreTex);
		
		Texture BackScoreTex = new Texture(Gdx.files.internal("backScore.png"));
		width = BackScoreTex.getWidth();
		height = BackScoreTex.getHeight();
		xHolder = 480 * 0.75f - width/ 2; 
		yHolder = 20;
		BackScore = new RectTex(xHolder, yHolder, width, height, BackScoreTex);
		
		//ScoreLogo  
		Texture ScoreLogoTex = new Texture(Gdx.files.internal("logo_score.png"));
		width = ScoreLogoTex.getWidth();
		height = ScoreLogoTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 700;
		Score = new RectTex(xHolder, yHolder, width, height, ScoreLogoTex);	
		
		//ScoreLogo  
		Texture StoreLogoTex = new Texture(Gdx.files.internal("logo_store.png"));
		width = StoreLogoTex.getWidth();
		height = StoreLogoTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 700;
		Store = new RectTex(xHolder, yHolder, width, height, StoreLogoTex);
		/*
		Texture ScoreBoardTex = new Texture(Gdx.files.internal("scoreboard.png"));
		width = ScoreBoardTex.getWidth();
		height = ScoreBoardTex.getHeight();
		xHolder = 480/2 - width/2;
		yHolder = 50;
		ScoreBoard = new RectTex(xHolder,yHolder,width, height, ScoreBoardTex);*/
		//Game Over Logo
		Texture OverTex = new Texture(Gdx.files.internal("logo_over.png"));
		width = OverTex.getWidth();
		height = OverTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 700;
		Over = new RectTex(xHolder, yHolder, width, height, OverTex);	
		
		Texture ReplayTex = new Texture(Gdx.files.internal("playAgain.png"));
		width = ReplayTex.getWidth();
		height = ReplayTex.getHeight();
		xHolder = 480 * 0.75f - width/ 2; 
		yHolder = 150;
		Replay = new RectTex(xHolder, yHolder, width, height, ReplayTex);
		
		Texture MainMenuTex = new Texture(Gdx.files.internal("mainMenu.png"));
		width = MainMenuTex.getWidth();
		height = MainMenuTex.getHeight();
		xHolder = 480 * 0.25f - width/ 2; 
		yHolder = 150;
		MainMenu = new RectTex(xHolder, yHolder, width, height, MainMenuTex);
	}

}
