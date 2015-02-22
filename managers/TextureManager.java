package managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;


public class TextureManager {

	private String location;
	private String asset;
	
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
	public Texture newScore;
	public Texture Power_Multi;
	public Texture Power_50;
	public Texture logo_Main;
	public Texture logo_Score;
	public Texture logo_Store;
	public Texture logo_Over;
	
	public TextureManager(String aAsset, String aLoc)
	{
		asset = aAsset;
		location = aLoc;
		createTextures();
	}
	
	private void createTextures()
	{
		square = new Texture(Gdx.files.internal(asset+location+"square.png"));
		triangle = new Texture(Gdx.files.internal(asset+location+"triangle.png"));
		circle = new Texture(Gdx.files.internal(asset+location+"circle.png"));
		ex = new Texture(Gdx.files.internal(asset+location+"ex.png"));
		black = new Texture(Gdx.files.internal(asset+location+"black.png"));
		blinkBlack = new Texture(Gdx.files.internal(asset+location+"blackBlikk.png"));
		selected = new Texture(Gdx.files.internal(asset+location+"selected.png"));
		ui_bg = new Texture(Gdx.files.internal(asset+location+"ui_bg.png"));
		redline = new Texture(Gdx.files.internal(asset+location+"redline.png"));
		ui_pauseOn = new Texture(Gdx.files.internal(asset+location+"pauseOn.png"));
		ui_pauseOff = new Texture(Gdx.files.internal(asset+location+"pauseOff.png"));
		ui_soundOn = new Texture(Gdx.files.internal(asset+location+"soundOn.png"));
		ui_soundOff = new Texture(Gdx.files.internal(asset+location+"soundOff.png"));
		pauseBlock = new Texture(Gdx.files.internal(asset+location+"pauseBlock.png"));
		newScore = new Texture(Gdx.files.internal(asset+location+"newScore.png"));
	}
}
