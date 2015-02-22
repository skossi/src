package managers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//Class by �ttar Gu�mundsson
//Written 14.11.2014
//Creates Manager that was originally supposed to take care of all rectangles for the game.
//Was changed to be also resource manager 18.11.2014
public class RectangleManager 
{	
	public int size;
	
	private String _Asset = "Asset_1/";
	
	private static final String _Sounds = "Sounds/";
	private static final String _Buttons = "Buttons/";
	private static final String _Textures = "Textures/";
	
	public TextureManager TextureM;
	public ButtonManager ButtonM;
	public AudioManager AudioM;
	public ScoreManager ScoreM;
	
	public boolean isMuted;
	
	//Animation
	public int MenuXOffset;
	public int MenuYOffset;
	public int horizontalSpeed;
	public int verticalSpeed;
	public int speedAdd;
	public boolean moveMenu;
	public boolean moveFromSides;
	public int sideDir;
	public boolean isMenuDown;

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
	public String newHighString;
	public String newIndivString;
	public String worseString;
	
	
	
	//Creates a resource entity each time the game is opened
	public RectangleManager()
	{
		TextureM = new TextureManager(_Asset,_Textures);
		ButtonM = new ButtonManager(_Asset,_Buttons);
		AudioM = new AudioManager(_Asset,_Sounds);
		ScoreM = new ScoreManager();
		
		size = 68;
		speedAdd = 1;
		horizontalSpeed = 600;
		verticalSpeed = 600;
		
		_rOrg = 1;
		_gOrg = 1;
		_bOrg = 1;
		_r = _rOrg;
		_g = _gOrg;
		_b = _bOrg;
		
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
		isMuted = AudioM.Mute(isMuted);
	}
	
	public void PlaySoundEffect(int aSound)
	{
		AudioM.SoundEffect(aSound);
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
