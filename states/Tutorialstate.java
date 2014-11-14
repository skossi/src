package states;

import managers.GameStateManager;
import managers.RectangleManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
//Class by Óttar Guðmundsson
//Written 30.10.2014
//Creates a new state when tutorial is viewed
public class Tutorialstate extends Gamestate {

	   private RectTex Back;
	   private RectTex Tutorial;
	   private RectTex Info;
	   private RectTex ObjBar;
	   private RectTex CtrBar;
	   private RectTex ScrBar;
	   private int infoDisp;
	   
	   //private Texture BackTex;
//	   private Texture TutorialLogoTex;
//	   private Texture ObjBarTex;
//	   private Texture CtrBarTex;
//	   private Texture ScrBarTex;
//	   private Texture ObjTex;
//	   private Texture CtrTex;
//	   private Texture ScrTex;
//	   private Texture orgInfoTex;
	   private Texture[] infoTex;
	   private RectangleManager RectMana;
	//Constructor
	//See abskrakt class Gamestate(GameStateManager gsm);
	public Tutorialstate(GameStateManager gsm)
	{	
		super(gsm);
	}
	//See abstrakt class Gamestate init();
	public void init(RectangleManager RectMan)
	{
		RectMana = RectMan;
		Tutorial = RectMana.Tutorial;
		Info = RectMana.Info;
		ObjBar = RectMana.ObjBar;
		CtrBar = RectMana.CtrBar;
		ScrBar = RectMana.ScrBar;
		
		infoTex = RectMana.infoTex;
		infoDisp = 3;
		
		Back = RectMana.Back;
	}
	//See abstrakt class Gamestate update(float dt);
	public void update(float dt)
	{
		
	}
	//See abstrakt class Gamestate draw(SpriteBatch b);
	public void draw(SpriteBatch batch)
	{
		batch.draw(Back.tex,Back.x,Back.y);
		batch.draw(Tutorial.tex, Tutorial.x, Tutorial.y);
		batch.draw(ObjBar.tex, ObjBar.x, ObjBar.y);
		batch.draw(CtrBar.tex, CtrBar.x, CtrBar.y);
		batch.draw(ScrBar.tex, ScrBar.x, ScrBar.y);
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
	public boolean buttonClick(RectTex rekt, float x, float y) {
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