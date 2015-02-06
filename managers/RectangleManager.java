package managers;

import states.RectTex;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
//Class by �ttar Gu�mundsson
//Written 14.11.2014
//Creates Manager that was originally supposed to take care of all rectangles for the game.
//Was changed to be also resource manager 18.11.2014
public class RectangleManager 
{	
	public boolean firstTime;
	
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
	public int backgroundSpeed;
	
	//Tutorial
	public RectTex Tutorial;
	public RectTex Info;
	public RectTex ObjBar;
	public RectTex CtrBar;
	public RectTex ScrBar;
	public Texture[] infoTex = new Texture[4];
	
	//Score
	public RectTex Score;
	//public RectTex ScoreBoard;
	//Store
	public RectTex Store;
	
	//BackButton and GameOver
	public RectTex Back;
	public RectTex Over;
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
	public BitmapFont font;
	public String newHighString;
	public String newIndivString;
	public String worseString;
	
	//Main themesound and audio effects
	public float Volume;
	public boolean isMuted;
	private Music mainTheme;
	public Sound destSound;
	public Sound shootSound;
	public Sound pauseSound;
	public Sound muteSound;
	
	//Creates a resource entity each time the game is opened
	public RectangleManager()
	{
		createTextures();
		createButtons();
		createSounds();
		sumRecordHolder = new int[4];
		squareRecordHolder = new int[4];
		triangleRecordHolder = new int[4];
		circleRecordHolder = new int[4];
		exRecordHolder = new int[4];
		currencyInt = new int[4];
		getScores();

		firstTime = prefs.getBoolean("First");
		
		_rOrg = 0/255f;
		_gOrg = 0/255f;
		_bOrg = 32f/255f;
		_r = _rOrg;
		_g = _gOrg;
		_b = _bOrg;
		_w = 0;
		
		font = new BitmapFont();
	    font.setColor(Color.WHITE); //var Color.BLACK
	    font.setScale(2,2);
	    
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
			mainTheme.setVolume(Volume);
		}
		else
		{
			Volume = 1;
			mainTheme.setVolume(1);
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
		triangleRecord = prefs.getInteger("triangleRecord");
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
			currencyInt[i] += newScore[i];
		}
		
		prefs.putInteger("currencySquare", currencyInt[0]);
		prefs.putInteger("currencyTriangle", currencyInt[1]);
		prefs.putInteger("currencyCircle", currencyInt[2]);
		prefs.putInteger("currencyEx", currencyInt[3]);
		
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
		}
		if(newScore[1] > triangleRecord)
		{
			CopyArray(triangleRecordHolder,newScore);
			prefs.putInteger("TriangleRecordSquare", newScore[0]);
			prefs.putInteger("TriangleRecord", newScore[1]);
			prefs.putInteger("TriangleRecordCircle", newScore[2]);
			prefs.putInteger("TriangleRecordEx", newScore[3]);
			NewIndivScore = true;
		}
		if(newScore[2] > circleRecord)
		{
			CopyArray(circleRecordHolder,newScore);
			prefs.putInteger("CircleRecordSquare", newScore[0]);
			prefs.putInteger("CircleRecordTriangle", newScore[1]);
			prefs.putInteger("CircleRecord", newScore[2]);
			prefs.putInteger("CircleRecordEx", newScore[3]);
			NewIndivScore = true;
		}
		if(newScore[3] > exRecord)
		{
			CopyArray(exRecordHolder,newScore);
			prefs.putInteger("ExRecordSquare", newScore[0]);
			prefs.putInteger("ExRecordTriangle", newScore[1]);
			prefs.putInteger("ExRecordCircle", newScore[2]);
			prefs.putInteger("ExRecord", newScore[3]);
			NewIndivScore = true;
		}

		
		//TODO : Here we need to add all the blocks to currency. Impliment when store is made
		/*int temp = currency;
		temp += newScore;
		prefs.putInteger("currency", temp);
		*/
		
		prefs.flush();
		getScores();
	}
	public void CopyArray(int[] aArray, int[] aNewArray)
	{
		for(int i = 0; i < 4; i++)
		{
			aArray[i] = aNewArray[i];
		}
	}
	//When the menu entities have moved down after the animation has completed, they are reset
	//That way the user can access the menu after the game is finished
	public void resetMenu()
	{
		Menu.y = 700;
		EnterPlay.y = 550;
		EnterScore.y = 400;
		EnterTut.y = 250;
		EnterStore.y = 100;
	}
	private void createSounds()
	{
		//Sounds initiated
	    mainTheme = Gdx.audio.newMusic(Gdx.files.internal("mainTheme.mp3"));
	    destSound = Gdx.audio.newSound(Gdx.files.internal("destroy.wav"));
		shootSound = Gdx.audio.newSound(Gdx.files.internal("shoot.wav"));
		muteSound = Gdx.audio.newSound(Gdx.files.internal("muteSound.mp3"));
		pauseSound = Gdx.audio.newSound(Gdx.files.internal("pauseSound.wav"));

	    // start the playback of the background music immediately
	    mainTheme.setLooping(true);
	    mainTheme.play();
	    
	    Volume = 1;
	}
	
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
		Power_Multi = new Texture(Gdx.files.internal("powerMulti.png"));
		Power_50 = new Texture(Gdx.files.internal("power50.png"));
		back_1 = new Texture(Gdx.files.internal("background_1.png"));
		back_2 = new Texture(Gdx.files.internal("background_2.png"));
		back_3 = new Texture(Gdx.files.internal("background_3.png"));
	}
	
	//creates all buttons that are used in the game architecture except the blocks in the main game
	//Each state references to each RectTex to use instead of creating each instance
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
		Texture BackTex = new Texture(Gdx.files.internal("back.png"));
		width = BackTex.getWidth();
		height = BackTex.getHeight();
		xHolder = 480 * 0.75f - width/ 2; 
		yHolder = 20;
		Back = new RectTex(xHolder, yHolder, width, height, BackTex);
		
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
	}

}
