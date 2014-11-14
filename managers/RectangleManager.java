package managers;

import states.RectTex;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class RectangleManager {
	
	//Menu
	public RectTex Menu;
	public RectTex EnterPlay;
	public RectTex EnterScore;
	public RectTex EnterTut;
	
	//Tutorial
	public RectTex Tutorial;
	public RectTex Info;
	public RectTex ObjBar;
	public RectTex CtrBar;
	public RectTex ScrBar;
	public Texture[] infoTex = new Texture[4];
	
	//Score
	public RectTex Score;
	
	//BackButton
	public RectTex Back;
	
	
	public RectangleManager()
	{
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
		yHolder = 450; 
		EnterPlay = new RectTex(xHolder,yHolder, width, height, PlayTex);
		
		Texture ScoreTex = new Texture(Gdx.files.internal("score.png"));
		width = ScoreTex.getWidth();
		height = ScoreTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 300; 
		EnterScore = new RectTex(xHolder,yHolder, width, height, ScoreTex);
		
		Texture TutorialTex = new Texture(Gdx.files.internal("tutorial.png"));
		width = TutorialTex.getWidth();
		height = TutorialTex.getHeight();
		xHolder = 480 /2 - width / 2; 
		yHolder = 150; 
		EnterTut = new RectTex(xHolder,yHolder, width, height, TutorialTex);
		
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
		
	}

}
