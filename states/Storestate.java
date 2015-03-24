package states;

import managers.GameStateManager;
import managers.RectangleManager;
import managers.Wallet;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import entities.RectTex;
//Class by Óttar Guðmundsson
//Written 25.11.2014
//Creates a new state when store is viewed. There is no activity here
//This is only to show what this app might be capable off
public class Storestate extends Gamestate {

	   private RectTex Back;
	   private RectTex StoreMain;
	   private RectangleManager Man;

	   private Wallet wallet;
	   
	  // private String[] drawCurrency;
	   
	   private String drawCurrency;
	   private int currency;
	   private int screenDir;
	   
	   private RectTex[] tabs;
	   private RectTex[][] Store;
	   private String[] tabText;
	   private int selectTab;
	   
	   //Animation for changing themes
	   private boolean swapThemeAni;
	   private int themeOffsetX;
	   private int themeOffsetY;
	   private boolean moveX;
	   private boolean moveY;
	   private int themeToSet;
	   	//Speed variables
	   	private int themeSpeed;
	   	private int themeAdd;
	   	private int xDir;
	   	private int yDir;
	   
	   
	//Constructor
	//See abskrakt class Gamestate(GameStateManager gsm);
	public Storestate(GameStateManager gsm)
	{	
		super(gsm);
	}
	//See abstrakt class Gamestate init();
	public void init(RectangleManager RectMan)
	{
		Man = RectMan;
		wallet = Man.ScoreM.accesWallet();
		
		//drawCurrency = new String[4];
		tabs = new RectTex[4];
		tabText = new String[4];
		
		selectTab = 0;
		
		tabs[0] = Man.ButtonM.TabPowers;
		tabs[1] = Man.ButtonM.TabLevels;
		tabs[2] = Man.ButtonM.TabThemes;
		tabs[3] = Man.ButtonM.TabExtras;
		tabText[0] = "Powers";
		tabText[1] = "Levels";
		tabText[2] = "Themes";
		tabText[3] = "Extras";
		
		swapThemeAni = false;
		themeOffsetX = 0;
		themeOffsetY = -800;
		moveX = false;
		moveY = false;
		themeSpeed = 2600;
		themeAdd = 70;
		xDir = 1;
		yDir = 1;
		
		themeToSet = Man.activeTheme;
		
		for(int i = 0; i < 4; i++)
		{
			//drawCurrency[i] = Integer.toString(Man.ScoreM.scoreHolder[5][i]);
		}
		currency = Man.ScoreM.currency;
		drawCurrency = Integer.toString(currency);
		
		Man.AnimationM.SideAnimation = true;
		Man.AnimationM.SideXOffset = 480;
		screenDir = -1;
		
		StoreMain = Man.ButtonM.Store;
		Back = Man.ButtonM.BackStore;
	      
	}
	
	//See abstrakt class Gamestate update(float dt);
	public void update(float dt)
	{
		if(Man.AnimationM.SideAnimation)
		{
			if(Man.AnimationM.SideXOffset > 0 && screenDir < 0)Man.AnimationM.SideToMenu(dt,screenDir,true);
			else Man.AnimationM.SideToMenu(dt,screenDir,false);
		}
		
		if(swapThemeAni)
		{
			if(moveY)animateYChange(dt);
			if(moveX)animateXChange(dt);
		}
	}
	
	
	
	//See abstrakt class Gamestate draw(SpriteBatch b);
	public void draw(SpriteBatch batch)
	{
		int xOffset = Man.AnimationM.SideXOffset;
		
		batch.draw(StoreMain.tex, StoreMain.x+xOffset, StoreMain.y);
		
		for(int i = 0; i < 4; i++)
		{
			batch.draw(tabs[i].tex,tabs[i].x+xOffset,tabs[i].y);
			if(i == selectTab)Man.fontWhite.draw(batch, tabText[i], 15+120*i+xOffset, 715);
			else Man.fontfff60.draw(batch, tabText[i], 15+120*i+xOffset, 715);
		}
		
		Man.fontBlack.draw(batch, "Your currency : ", 10+xOffset, 625);
		//Man.drawScoreBoard(batch, 210+xOffset, 580, drawCurrency,false, -1, Man.fontBlack);
		
		Man.fontBlack.draw(batch,drawCurrency,210+xOffset, 625);
		
		batch.draw(Man.TextureM.storeTabSelect,120*selectTab+xOffset,650);
		
		
		//DRAW ACTIVE STORE 
		for(int i = 0; i < 4; i++)
		{
			float itemX =  Man.ButtonM.StoreSelect[selectTab][i].x+xOffset;
			float itemY =  Man.ButtonM.StoreSelect[selectTab][i].y;
			batch.draw(Man.ButtonM.StoreSelect[selectTab][i].tex,
					  itemX,
					  itemY);
			if(!wallet.owned[selectTab][i])
			{
				batch.draw(Man.TextureM.lockedItem,itemX,itemY);
				batch.draw(Man.TextureM.priceHolder,itemX,itemY-64);
				Man.fontBlack.draw(batch,Integer.toString(wallet.price[selectTab][i]),itemX+40,itemY-10);
			}
			//else batch.draw unlocked
		}
		
		batch.draw(Back.tex, Back.x+xOffset, Back.y);
		
		if(swapThemeAni)
		{
			batch.draw(Man.TextureM.swapTheme,0,themeOffsetY);
			
			int x = 100+themeOffsetX;
			int y = 430+themeOffsetY;
			
			batch.draw(Man.TextureM.square,x+0*68,y);
			batch.draw(Man.TextureM.triangle,x+1*68,y);
			batch.draw(Man.TextureM.circle,x+2*68,y);
			batch.draw(Man.TextureM.ex,x+3*68,y);
		}
	}
	
	//See abstrakt class Gamestate justTouched(x,y);
	public void justTouched(float x, float y)
	{
		if(swapThemeAni || Man.AnimationM.SideAnimation) return;
		if(buttonClick(tabs[0],x,y)) selectTab = 0;
		if(buttonClick(tabs[1],x,y)) selectTab = 1;
		if(buttonClick(tabs[2],x,y)) selectTab = 2;
		if(buttonClick(tabs[3],x,y)) selectTab = 3;
		
		storeItemClick(selectTab,x,y);
		
		if(buttonClick(Back,x,y))
		{
			screenDir = 1;
			Man.AnimationM.SideAnimation = true;
		}
	}
	
	//Glorified switch/case instead of ugly else ifses
	//ToBe finished!
	private void storeItemClick(int aTab,float x, float y)
	{
		switch (aTab){
			case 0 :
			{
				
				break;
			}
			case 1 : 
			{
				
				break;
			}
			case 2 : 
			{
				if(buttonClick(Man.ButtonM.StoreSelect[selectTab][0],x,y))
				{
					if(wallet.owned[selectTab][0])canChangeTheme(0);
					else checkCapital(selectTab,0);
					
				}
				if(buttonClick(Man.ButtonM.StoreSelect[selectTab][1],x,y))
				{
					if(wallet.owned[selectTab][1])canChangeTheme(1);
					else checkCapital(selectTab,1);
				}
				break;
			}
			case 3 : 
			{
				
				break;
			}
		}
	}
	
	private void checkCapital(int selected, int item)
	{
		if(wallet.canBuy(selected, item,currency)) handlePayment(selected, item);
		else denyUser(); //Display error message
	}
	
	private void handlePayment(int selected, int item)
	{
		Man.ScoreM.payForAsset(wallet.aquireAsset(selected, item)); 
		currency = Man.ScoreM.currency;
		drawCurrency = Integer.toString(currency);
	}
	
	private void denyUser()
	{
		//Some kind of warning sound should be played here!! argarg
	}
	
	//Checks if chosen theme to change is not the same as the one active.
	//If the action is legal, starts the theme changing animation
	private void canChangeTheme(int toTheme)
	{
		if(toTheme == Man.activeTheme)return;
		themeToSet = toTheme;
		xDir = 1;
		yDir = 1;
		moveY = true;
		swapThemeAni = true;
	}
	
	//Sets the chosen theme. 
	private void callThemeChange(int aTheme)
	{
		Man.setTheme(aTheme);
		resetStore();
	}
	
	//Resets the store textures. Redraws the store so it will fit the corresponding theme.
	private void resetStore()
	{
		tabs[0] = Man.ButtonM.TabPowers;
		tabs[1] = Man.ButtonM.TabLevels;
		tabs[2] = Man.ButtonM.TabThemes;
		tabs[3] = Man.ButtonM.TabExtras;
		tabText[0] = "Powers";
		tabText[1] = "Levels";
		tabText[2] = "Themes";
		tabText[3] = "Extras";
		StoreMain = Man.ButtonM.Store;
		Back = Man.ButtonM.BackStore;
	      
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
	//TODO : Simplify this in one function
	//Changes the yOffset of theme changing animation.
	private void animateYChange(float dt)
	{
		{
			if(yDir > 0)
			{
				themeSpeed -= themeAdd*yDir;
				themeOffsetY += themeSpeed*dt*yDir;
				themeAdd -= Man.AnimationM.accel*yDir;
				if(themeOffsetY > 0)
				{
					themeOffsetY = 0;
					yDir = -1;
					moveY = false;
					moveX = true;
					themeSpeed = 0;
					themeAdd = 0;
				}
			}
			else
			{
				themeSpeed -= themeAdd*yDir;
				themeOffsetY += themeSpeed*dt*yDir;
				themeAdd += Man.AnimationM.accel;
				if(themeOffsetY < -800)
				{
					moveY = false;
					themeOffsetY = -800;
					themeSpeed = 2600;
					themeAdd = 70;
					swapThemeAni = false;
				}
			}
		}
	}
	//Changes the xOffset of theme changing animation.
	private void animateXChange(float dt)
	{
		if(xDir > 0)
		{
			themeSpeed -= themeAdd*xDir;
			themeOffsetX += themeSpeed*dt*xDir;
			themeAdd -= Man.AnimationM.accel*xDir;
			if(themeOffsetX > 480)
			{
				callThemeChange(themeToSet);
				themeOffsetX *= -1;
				themeOffsetX -= 300;
				xDir = -1;
			}
		}
		else
		{
			themeSpeed += themeAdd*xDir;
			themeOffsetX += themeSpeed*dt;
			themeAdd -= Man.AnimationM.accel;
			if(themeOffsetX > 0)
			{
				themeOffsetX = 0;
				xDir = -1;
				themeSpeed = 0;
				themeAdd = 0;
				moveX = false;
				moveY = true;
			}
		}
	}
	
	//See abstrakt class Gamestate dispose();
	public void dispose()
	{
	
	}
}
