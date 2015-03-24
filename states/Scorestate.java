package states;

import managers.GameStateManager;
import managers.RectangleManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import entities.RectTex;
//import com.badlogic.gdx.audio.Sound;
//Class by Óttar Guðmundsson
//Written 30.10.2014
//Creates a new state when score is viewed
public class Scorestate extends Gamestate {

	   private RectTex Back;
	   private RectTex Score;
	   private RectangleManager Man;
	   
	   private int selectTab;
	   private RectTex[] tabs;
	   private String[] tabText;
	   
	   private String[] drawBestScore;
	   private String[] drawBestSquare;
	   private String[] drawBestTriangle;
	   private String[] drawBestCircle;
	   private String[] drawBestEx;
	   
	  // private int xOffset;
	   private int screenDir;
	   
	   
	//Constructor
	//See abskrakt class Gamestate(GameStateManager gsm);
	public Scorestate(GameStateManager gsm)
	{	
		super(gsm);
	}
	//See abstrakt class Gamestate init();
	public void init(RectangleManager RectMan)
	{
		Man = RectMan;

		Score = Man.ButtonM.Score;
		tabs = new RectTex[4];
		tabText = new String[3];
		
		selectTab = 0;
		
		tabs[0] = Man.ButtonM.TabBest;
		tabs[1] = Man.ButtonM.TabStats;
		tabs[2] = Man.ButtonM.TabAbout;
		tabText[0] = "Best";
		tabText[1] = "Stats";
		tabText[2] = "About";
		
		drawBestScore = new String[4];
		drawBestSquare = new String[4];
		drawBestTriangle = new String[4];
		drawBestCircle = new String[4];
		drawBestEx = new String[4];
		for(int i = 0; i < 4; i++)
		{
			drawBestScore[i] = Integer.toString(Man.ScoreM.scoreHolder[0][i+1]);
			drawBestSquare[i] = Integer.toString(Man.ScoreM.scoreHolder[1][i+1]);
			drawBestTriangle[i] = Integer.toString(Man.ScoreM.scoreHolder[2][i+1]);
			drawBestCircle[i] = Integer.toString(Man.ScoreM.scoreHolder[3][i+1]);
			drawBestEx[i] = Integer.toString(Man.ScoreM.scoreHolder[4][i+1]);
		}
		
		Man.AnimationM.SideAnimation = true;
		Man.AnimationM.SideXOffset = -480;
		screenDir = 1;
		
		Back = Man.ButtonM.BackScore;
	      
	}
	//See abstrakt class Gamestate update(float dt);
	public void update(float dt)
	{
		if(Man.AnimationM.SideAnimation)
		{
			if(Man.AnimationM.SideXOffset < 0 && screenDir > 0)
			{
				Man.AnimationM.SideToMenu(dt,screenDir,true);
			}
			else
			{
				Man.AnimationM.SideToMenu(dt,screenDir,false);
			}
		}
	}
	//See abstrakt class Gamestate draw(SpriteBatch b);
	public void draw(SpriteBatch batch)
	{
		int xOffset = Man.AnimationM.SideXOffset;
		batch.draw(Score.tex, Score.x+xOffset, Score.y);
		
		for(int i = 0 ; i < 3; i++)
		{
			batch.draw(tabs[i].tex,tabs[i].x+xOffset,tabs[i].y);
			if(i == selectTab)Man.fontWhite.draw(batch, tabText[i], 30+160*i+xOffset, 690);
			else Man.fontfff60.draw(batch, tabText[i], 30+160*i+xOffset, 690);

		}
		batch.draw(Man.TextureM.scoreTabSelect,selectTab*160+xOffset,625);
		
		batch.draw(Man.TextureM.scoreBack,20+xOffset,150);
		
		if(selectTab == 0)
		{
			//Man.fontBlack.draw(batch,"Your best score is :", 115+xOffset, 695);
			//Man.drawScoreBoard(batch, 100+xOffset, 560, drawBestScore, false, -1,Man.fontBlack);
			
			Man.fontBlack.draw(batch,"Your best individual score is :", 50+xOffset, 550);
			
			Man.fontBlack.draw(batch,"Red Run : ", 20+xOffset, 510);
			Man.drawScoreBoard(batch, 190+xOffset, 465, drawBestSquare, true, 0,Man.fontWhite);
			
			Man.fontBlack.draw(batch,"Green Run : ", 20+xOffset, 410);
			Man.drawScoreBoard(batch, 190+xOffset, 365, drawBestTriangle, true, 1,Man.fontWhite);
	
			Man.fontBlack.draw(batch,"Yellow Run : ", 20+xOffset, 310);
			Man.drawScoreBoard(batch, 190+xOffset, 265, drawBestCircle, true, 2,Man.fontWhite);
	
			Man.fontBlack.draw(batch,"Blue Run : ", 20+xOffset, 210);
			Man.drawScoreBoard(batch, 190+xOffset, 165, drawBestEx, true, 3,Man.fontWhite);
		}
		
		batch.draw(Back.tex, Back.x+xOffset, Back.y);
	}
	
	//See abstrakt class Gamestate justTouched(x,y);
	public void justTouched(float x, float y)
	{
		if(Man.AnimationM.SideAnimation) return;
		if(buttonClick(tabs[0],x,y)) selectTab = 0;
		if(buttonClick(tabs[1],x,y)) selectTab = 1;
		if(buttonClick(tabs[2],x,y)) selectTab = 2;
		if(buttonClick(Back,x,y))
		{
			screenDir = -1;
			Man.AnimationM.SideAnimation= true;
		}
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
