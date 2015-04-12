package managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
//Class by Ottar Gudmundsson
//Written 21.2.2015
//Texture manager that creates and hold all textures of the game.

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
	public Texture loseLine;
	public Texture newScore;
	public Texture Power_Multi;
	public Texture Power_50;
	public Texture logo_Main;
	public Texture logo_Score;
	public Texture logo_Store;
	public Texture logo_Over;
	public Texture storeTabSelect;
	public Texture scoreTabSelect;
	public Texture swapTheme;
	public Texture scoreBack;
	public Texture lockedItem;
	public Texture priceHolder;
	public Texture dropParticle;
	
	public TextureManager(String aAsset, String aLoc)
	{
		asset = aAsset;
		location = aLoc;
		createTextures();
	}

	//Loads all textures into the game by the chosen asset/theme.
	private void createTextures()
	{
		//*-------THEME BASED ASSETS------*
		square = new Texture(Gdx.files.internal(asset+location+"square.png"));
		triangle = new Texture(Gdx.files.internal(asset+location+"triangle.png"));
		circle = new Texture(Gdx.files.internal(asset+location+"circle.png"));
		ex = new Texture(Gdx.files.internal(asset+location+"ex.png"));		
		blinkBlack = new Texture(Gdx.files.internal(asset+location+"/blackBlikk.png"));
		selected = new Texture(Gdx.files.internal(asset+location+"selectedNEW.png"));
		loseLine = new Texture(Gdx.files.internal(asset+location+"redline.png"));	
		//*-------MUTUAL ASSETS------*
		
		black = new Texture(Gdx.files.internal("MutualAssets/black.png"));
		dropParticle = new Texture(Gdx.files.internal("MutualAssets/dropParticle.png"));
		swapTheme = new Texture(Gdx.files.internal("MutualAssets/SwapTheme.png"));
		scoreBack = new Texture(Gdx.files.internal("MutualAssets/scoreBack.png"));
		lockedItem = new Texture(Gdx.files.internal("MutualAssets/locked.png"));
		priceHolder = new Texture(Gdx.files.internal("MutualAssets/PriceHolder.png"));
		ui_bg = new Texture(Gdx.files.internal("MutualAssets/ui_bg.png"));
		ui_soundOn = new Texture(Gdx.files.internal("MutualAssets/soundOn.png"));
		ui_soundOff = new Texture(Gdx.files.internal("MutualAssets/soundOff.png"));
		storeTabSelect = new Texture(Gdx.files.internal("MutualAssets/storeBar.png"));
		scoreTabSelect = new Texture(Gdx.files.internal("MutualAssets/scoreBar.png"));
	}
}




