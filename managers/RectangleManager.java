package managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import entities.RectTex;

//Class by Ottar Gudmundsson
//Written 14.11.2014
//Creates Manager that was originally supposed to take care of all rectangles for the game.
//Was changed to be also resource manager 18.11.2014
public class RectangleManager
{
	public int size;

	private String _Asset;
	public int activeTheme;
	public int activeAudio;

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
	public BitmapFont fontMain;
	public BitmapFont fontSecond;
	public BitmapFont fontBlack;
	public BitmapFont fontWhite;


	public String newHighString;
	public String newIndivString;
	public String worseString;

	//Creates a resource entity each time the game is opened
	public RectangleManager()
	{
		prefs = Gdx.app.getPreferences("My Preferences");
		//prefs.clear();
		activeTheme = prefs.getInteger("ActiveTheme");
		activeAudio = prefs.getInteger("ActiveAudio");
		ThemeM = new ThemeManager();

		assignAssets(activeTheme);
		assignAudio(activeAudio);

		TextureM = new TextureManager(_Asset,_Textures);
		ButtonM = new ButtonManager(_Asset,_Buttons);
		ScoreM = new ScoreManager(prefs);

		size = 68;

		_r = _rOrg;
		_g = _gOrg;
		_b = _bOrg;

		fontMain = new BitmapFont(Gdx.files.internal("Fonts/Segui_White_40.fnt"));
		fontMain.setColor(Color.BLACK);

		fontSecond = new BitmapFont(Gdx.files.internal("Fonts/Segui_White_40.fnt"));
		fontSecond.setColor(Color.WHITE); //var Color.BLACK

		fontBlack = new BitmapFont(Gdx.files.internal("Fonts/Segui_White_40.fnt"));
		fontBlack.setColor(Color.BLACK); //var Color.BLACK

		fontWhite = new BitmapFont(Gdx.files.internal("Fonts/Segui_White_40.fnt"));
		fontWhite.setColor(Color.WHITE); //var Color.BLACK

	}

	private void assignAudio(int newAudio)
	{
		String newSound = "Asset_" + (Integer.toString(newAudio)) + "/";
		prefs.putInteger("ActiveAudio", newAudio);
		prefs.flush();
		activeAudio = newAudio;
		AudioM = new AudioManager(newSound,_Sounds);
		
		isMuted = prefs.getBoolean("Mute"); 
		
		boolean firstTimeBug = prefs.getBoolean("First");
		if(firstTimeBug)isMuted = AudioM.mute(isMuted);
		else isMuted = AudioM.mute(!isMuted);
	}

	// Changes the current asset string that points to folder of given assets.
	// Saves the current used theme and updates the prefered colors.
	private void assignAssets(int newTheme)
	{
		_Asset = "Asset_" + (Integer.toString(newTheme)) + "/";
		activeTheme = newTheme;
		prefs.putInteger("ActiveTheme", newTheme);
		prefs.flush();
		setColors(activeTheme);
	}

	// Updates the main colors in the game used to draw interactive elements non-relating to the gameplay
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

	// Sets the main background color of the game and updates the colors of each buttn
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
			_rOrg = 117f/255;
			_gOrg = 16f/255;
			_bOrg = 97f/255;
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
		ButtonM = new ButtonManager(_Asset,_Buttons);

		_r = _rOrg;
		_g = _gOrg;
		_b = _bOrg;
	}

	public void setAudio(int aAudio)
	{
		assignAudio(aAudio);
	}

	//Gives the animation manager acces to the state machine
	public void assignAnimation(GameStateManager g)
	{
		AnimationM = new AnimationManager(g);
	}

	//Mutes or unmutes the maintheme of the game
	public void soundMute()
	{
		isMuted = AudioM.mute(isMuted);
		prefs.putBoolean("Mute", !isMuted);
		prefs.flush();
	}

	//Sends request to the audio manager to play chosen sound
	public void playSoundEffect(int aSound)
	{
		AudioM.soundEffect(aSound);
	}

	public void drawButton(SpriteBatch batch, RectTex button, int xOffset, int yOffset, boolean white)
	{
		batch.draw(button.tex,button.x+xOffset,button.y+yOffset);
		if(white)fontWhite.draw(batch, button.display, button.disX + xOffset, button.disY +yOffset);
		else fontBlack.draw(batch, button.display, button.disX + xOffset, button.disY +yOffset);
	}
}
