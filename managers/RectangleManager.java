package managers;

import states.RectTex;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
//Class by Óttar Guðmundsson
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
	public Texture selected;
	public Texture ui_bg;
	public Texture ui_pause;
	public Texture ui_soundOn;
	public Texture ui_soundOff;
	public Texture pauseBlock;
	public Texture redline;
	public Texture ScoreBoard;
	
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
	public boolean grats;
	public int[] currentScore;
	public int currency;
	
	//RGB values
	public float _r;
	public float _g;
	public float _b;
	public float _w;
	
	//Font writing
	public BitmapFont font;
	public String betterString;
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
		getScores();

		firstTime = prefs.getBoolean("First");
		
		_r = 1f;
		_g = 178/255f;
		_b = 0;
		_w = 0;
		
		font = new BitmapFont();
	    font.setColor(Color.BLACK);
	    font.setScale(2,2);
	    
	    betterString = "Congratulations, new score!";
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
		squareRecord = prefs.getInteger("SquareRecord");
		triangleRecord = prefs.getInteger("triangleRecord");
		circleRecord =  prefs.getInteger("CircleRecord");
		exRecord = prefs.getInteger("ExRecord");
		currency = prefs.getInteger("currency");
	}
	//Takes in parameter newScore that is players score when game is lost. 
	//Gets preferences and checks if a new high score was made
	//if so, it saves it and refreshes the preferences.
	public void checkScore(int[] newScore)
	{
		currentScore = newScore;
		grats = false;
		getScores();
		//Check if the sum of all bricks are new record
		int sum = 0;
		for(int i = 0; i < 4; i++)
		{
			sum += newScore[i];
		}
		if(sum > sumRecord) 
		{
			sumRecordHolder[0] = newScore[0];
			sumRecordHolder[1] = newScore[1];
			sumRecordHolder[2] = newScore[2];
			sumRecordHolder[3] = newScore[3];
			prefs.putInteger("SumRecord",sum);
			prefs.putInteger("SumRecordSquare", newScore[0]);
			prefs.putInteger("SumRecordTriangle", newScore[1]);
			prefs.putInteger("SumRecordCircle", newScore[2]);
			prefs.putInteger("SumRecordEx", newScore[3]);
			grats = true;
		}

		//Check if we made a new record of different cubes
		if(newScore[0] > squareRecord) prefs.putInteger("SquareRecord", newScore[0]);
		if(newScore[1] > triangleRecord) prefs.putInteger("TriangleRecord", newScore[1]);
		if(newScore[2] > circleRecord) prefs.putInteger("CircleRecord", newScore[2]);
		if(newScore[3] > exRecord) prefs.putInteger("ExRecord", newScore[3]);

		
		//TODO : Here we need to add all the blocks to currency. Impliment when store is made
		/*int temp = currency;
		temp += newScore;
		prefs.putInteger("currency", temp);
		*/
		
		prefs.flush();
		getScores();
	}
	//When the menu entities have moved down after the animation has completed, they are reset
	//That way the user can access the menu after the game is finished
	public void resetMenu()
	{
		Menu.y = 700;
		EnterPlay.y = 450;
		EnterScore.y = 300;
		EnterTut.y = 150;
		EnterStore.y = 50;
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
		selected = new Texture(Gdx.files.internal("selected.png"));
		ui_bg = new Texture(Gdx.files.internal("ui_bg.png"));
		redline = new Texture(Gdx.files.internal("redline.png"));
		ui_pause = new Texture(Gdx.files.internal("pause.png"));
		ui_soundOn = new Texture(Gdx.files.internal("soundOn.png"));
		ui_soundOff = new Texture(Gdx.files.internal("soundOff.png"));
		pauseBlock = new Texture(Gdx.files.internal("pauseBlock.png"));
		ScoreBoard = new Texture(Gdx.files.internal("scoreboard.png"));
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
		yHolder = 480; 
		EnterPlay = new RectTex(xHolder,yHolder, width, height, PlayTex);
		
		Texture ScoreTex = new Texture(Gdx.files.internal("score.png"));
		width = ScoreTex.getWidth();
		height = ScoreTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 330; 
		EnterScore = new RectTex(xHolder,yHolder, width, height, ScoreTex);
		
		Texture TutorialTex = new Texture(Gdx.files.internal("tutorial.png"));
		width = TutorialTex.getWidth();
		height = TutorialTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 180; 
		EnterTut = new RectTex(xHolder,yHolder, width, height, TutorialTex);
		
		Texture StoreTex = new Texture(Gdx.files.internal("store.png"));
		width = StoreTex.getWidth();
		height = StoreTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 80; 
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
