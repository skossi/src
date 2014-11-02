package states;

import managers.GameStateManager;

import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
//Class by Óttar Guðmundsson
//Written 30.10.2014
//Creates a new state when tutorial is viewed
public class Tutorialstate extends Gamestate {

	   private Rectangle Back;
	   private Rectangle TutorialLogo;
	   private Rectangle Info;
	   
	   private Rectangle ObjBar;
	   private Rectangle CtrBar;
	   private Rectangle ScrBar;
	   private int infoDisp;
	   
	   private int buttonPos;
	   
	   private Texture BackTex;
	   private Texture TutorialLogoTex;
	   private Texture ObjBarTex;
	   private Texture CtrBarTex;
	   private Texture ScrBarTex;
	   private Texture ObjTex;
	   private Texture CtrTex;
	   private Texture ScrTex;
	   private Texture orgInfoTex;
	   private Texture[] infoTex;
	//Constructor
	//See abskrakt class Gamestate(GameStateManager gsm);
	public Tutorialstate(GameStateManager gsm)
	{	
		super(gsm);
	}
	//See abstrakt class Gamestate init();
	public void init()
	{
		  TutorialLogoTex = new Texture(Gdx.files.internal("logo_tutorial.png"));
		  TutorialLogo = new Rectangle();
		  TutorialLogo.width = TutorialLogoTex.getWidth();
		  TutorialLogo.height = TutorialLogoTex.getHeight();
		  TutorialLogo.x = 480 /2 - TutorialLogo.width / 2; 
		  TutorialLogo.y = 700; 
		
	      infoTex = new Texture[4];
	      
	      infoTex[0] = ObjTex = new Texture(Gdx.files.internal("obj.png"));
	      infoTex[1] = CtrTex = new Texture(Gdx.files.internal("ctr.png"));
	      infoTex[2] = ScrTex = new Texture(Gdx.files.internal("scr.png"));
	      infoTex[3] = orgInfoTex = new Texture(Gdx.files.internal("orgInfo.png"));
	      
	      ObjBarTex = new Texture(Gdx.files.internal("objBarTex.png"));
		  CtrBarTex = new Texture(Gdx.files.internal("ctrBarTex.png"));
		  ScrBarTex = new Texture(Gdx.files.internal("scrBarTex.png"));
		  
		  buttonPos = 1;
		  infoDisp = 3;
	      
	      Info = new Rectangle();
	      Info.width = ObjTex.getWidth();
	      Info.height = ObjTex.getHeight();
	      Info.x = 480 /2 - Info.width / 2; 
	      Info.y = 300; 
		  
		  ObjBar = new Rectangle();
		  ObjBar.width = ObjBarTex.getWidth();
		  ObjBar.height = ObjBarTex.getHeight();
		  ObjBar.x = (480 / 6)*buttonPos - ObjBar.width/2; buttonPos += 2;
		  ObjBar.y = 150;
		  CtrBar = new Rectangle();
		  CtrBar.width = ObjBarTex.getWidth();
		  CtrBar.height = ObjBarTex.getHeight();
		  CtrBar.x = (480 / 6)*buttonPos - ObjBar.width/2; buttonPos +=2;
		  CtrBar.y = 150;
		  ScrBar = new Rectangle();
		  ScrBar.width = ObjBarTex.getWidth();
		  ScrBar.height = ObjBarTex.getHeight();
		  ScrBar.x = (480 / 6)*buttonPos - ObjBar.width/2;
		  ScrBar.y = 150;
	      
	      BackTex = new Texture(Gdx.files.internal("back.png"));
	      Back = new Rectangle();
	      Back.width = BackTex.getWidth();
	      Back.height = BackTex.getHeight();
	      Back.x = 480 * 0.75f - Back.width / 2; 
	      Back.y = 20;
	}
	//See abstrakt class Gamestate update(float dt);
	public void update(float dt)
	{
		
	}
	//See abstrakt class Gamestate draw(SpriteBatch b);
	public void draw(SpriteBatch batch)
	{
		batch.draw(BackTex, Back.x, Back.y);
		batch.draw(TutorialLogoTex, TutorialLogo.x, TutorialLogo.y);
		batch.draw(ObjTex, Info.x, Info.y);
		batch.draw(ObjBarTex, ObjBar.x, ObjBar.y);
		batch.draw(CtrBarTex, CtrBar.x, CtrBar.y);
		batch.draw(ScrBarTex, ScrBar.x, ScrBar.y);
		batch.draw(infoTex[infoDisp], Info.x, Info.y);
		
	}
	
	//See abstrakt class Gamestate justTouched(x,y);
	public void justTouched(float x, float y)
	{
		if(buttonClick(Back,x,y)) gsm.setState(GameStateManager.MENU);
		if(buttonClick(ObjBar,x,y)) infoDisp = 0;
		if(buttonClick(CtrBar,x,y)) infoDisp = 1;
		if(buttonClick(ScrBar,x,y)) infoDisp = 2;
	}
	//Tells if user just pressed a corresponding rectangle
	//Takes in Rectangle Rekt that and x and y coordinates of world position
	public boolean buttonClick(Rectangle rekt, float x, float y) {
		if (x < (rekt.x + rekt.width) && x > rekt.x && y > rekt.y && y < (rekt.y + rekt.height)) return true;
		return false;
	}
	//See abstrakt class Gamestate isTouched(x,y);
	public void isTouched(float x, float y)
	{
		
	}
	//See abstrakt class Gamestate dispose();
	public void dispose()
	{
	
	}
}