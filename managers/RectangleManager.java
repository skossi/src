package managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//Class by Ottar Gudmundsson
//Written 14.11.2014
//Creates Manager that was originally supposed to take care of all rectangles for the game.
//Was changed to be also resource manager 18.11.2014
public class RectangleManager 
{	
	public int size;

	private String _Asset;
	public int activeTheme;
	
	private static final String _Sounds = "Sounds/";
	private static final String _Buttons = "Buttons/";
	private static final String _Textures = "Textures/";
	
	public TextureManager TextureM;
	public ButtonManager ButtonM;
	public AudioManager AudioM;
	public ScoreManager ScoreM;
	public AnimationManager AnimationM;
	public ThemeManager ThemeM;
	
	public Color Color_Logo;
	public Color Color_Play;
	public Color Color_Score;
	public Color Color_Tutorial;
	public Color Color_Store;
	public Color Color_ScoreBar;
	public Color Color_StoreBar;
	
	public GameStateManager gsm;
	
	public boolean isMuted;
	
	private Preferences prefs;

	//RGB values
	public float _rOrg;
	public float _gOrg;
	public float _bOrg;
	public float _r;
	public float _g;
	public float _b;
	
	//Font writing
	public BitmapFont fontBlack;
	public BitmapFont fontWhite;
	public BitmapFont fontfff60;
	public String newHighString;
	public String newIndivString;
	public String worseString;

	
	
	//Creates a resource entity each time the game is opened
	public RectangleManager()
	{
		prefs = Gdx.app.getPreferences("My Preferences");
		activeTheme = prefs.getInteger("ActiveTheme");
		ThemeM = new ThemeManager();
		
		assignAssets(activeTheme);
		
		
		TextureM = new TextureManager(_Asset,_Textures);
		ButtonM = new ButtonManager(_Asset,_Buttons);
		AudioM = new AudioManager(_Asset,_Sounds);
		ScoreM = new ScoreManager(prefs);
		
		
		size = 68;

		_r = _rOrg;
		_g = _gOrg;
		_b = _bOrg;
		
		fontBlack = new BitmapFont();
	    fontBlack.setColor(Color.BLACK); //var Color.BLACK
	    fontBlack.setScale(2,2);
	    
	    fontWhite = new BitmapFont();
	    fontWhite.setColor(Color.WHITE); //var Color.BLACK
	    fontWhite.setScale(2,2);
	    
	    fontfff60 = new BitmapFont();
	    fontfff60.setColor(153f/255,153f/255,153f/255,1); //var Color.BLACK
	    fontfff60.setScale(2,2);
	    
	    newHighString = "Congratulations, new score!";
	    newIndivString = "You made a new record run!";
	    worseString = "Sorry, no high score was made.";
	    
	}
	
	private void assignAssets(int newTheme)
	{
		_Asset = "Asset_" + (Integer.toString(newTheme)) + "/";
		activeTheme = newTheme;
		prefs.putInteger("ActiveTheme", newTheme);
		prefs.flush();
		setColors(activeTheme);	
	}
	
	private void assignColors(int aTheme)
	{
		Color_Logo = ThemeM.accessColor(0, aTheme);
		Color_Play = ThemeM.accessColor(1, aTheme);
		Color_Score = ThemeM.accessColor(2, aTheme);
		Color_Tutorial = ThemeM.accessColor(3, aTheme);
		Color_Store = ThemeM.accessColor(4, aTheme);
		Color_ScoreBar = ThemeM.accessColor(5, aTheme);
		Color_StoreBar = ThemeM.accessColor(6, aTheme);
	}
	
	private void setColors(int theme)
	{
		if(theme == 0)
		{
			_rOrg = 1;
			_gOrg = 1;
			_bOrg = 1;
		}
		else if (theme == 1)
		{
			_rOrg = 73f/255;
			_gOrg = 10f/255;
			_bOrg = 61f/255;
		}
		else if (theme == 2)
		{
			_rOrg = 205f/255;
			_gOrg = 186f/255;
			_bOrg = 169f/255;
		}
		else if (theme == 3)
		{
			_rOrg = 103f/255;
			_gOrg = 84f/255;
			_bOrg = 86f/255;
		}
		assignColors(theme);
	}
	
	//Loads in a new asset file and resets all buttons and textures.
	public void setTheme(int aTheme)
	{
		assignAssets(aTheme);
		TextureM = new TextureManager(_Asset,_Textures);
		//AudioM.stopMusic();
		//AudioM = new AudioManager(_Asset,_Sounds); //Custom sounds?
		ButtonM = new ButtonManager(_Asset,_Buttons); 
		
		_r = _rOrg;
		_g = _gOrg;
		_b = _bOrg;
	}
	
	//Gives the animation manager acces to the state machine
	public void assignAnimation()
	{
		AnimationM = new AnimationManager(gsm);
	}
	
	//Mutes or unmutes the maintheme of the game
	public void soundMute()
	{
		isMuted = AudioM.mute(isMuted);
	}
	
	//Sends request to the audio manager to play chosen sound
	public void playSoundEffect(int aSound)
	{
		AudioM.soundEffect(aSound);
	}
	
	//Draws a scoreboard at given co ordinates. Takes is an array of Strings which represents
	//high scores, current highscores or the users currency. 
	//Can also draw a texture at chosen position (to indicate where a new score was made).
	public void drawScoreBoard(SpriteBatch batch, float x, int y, String[] aString, boolean showSpecial, int specialPos, BitmapFont font )
	{
		batch.draw(TextureM.square,x+0*size,y);
		batch.draw(TextureM.triangle,x+1*size,y);
		batch.draw(TextureM.circle,x+2*size,y);
		batch.draw(TextureM.ex,x+3*size,y);
		if(showSpecial && specialPos != -1)batch.draw(TextureM.newScore, x+specialPos*size, y);
		for(int i = 0; i < 4; i++)
	    {
			if(aString[i].length() == 4)font.draw(batch, aString[i], x+size*i, y+45);
			else if(aString[i].length() == 3)font.draw(batch, aString[i], x+10+size*i, y+45); 
    		else if(aString[i].length() == 2)font.draw(batch, aString[i], x+20+size*i, y+45);
    		else font.draw(batch, aString[i], x+30+size*i, y+45); 	  
	    }
	}
}
