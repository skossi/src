package states;

import managers.AudioManager;
import managers.GameStateManager;
import managers.RectangleManager;
import managers.Wallet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
	   
	   private String drawCurrency;
	   private int currency;
	   private int currencyOffset;
	   private int currencyOffsetDir;
	   public static int screenDir;
	   
	   private RectTex[] tabs;
	   private String[] tabText;
	   private int selectTab;
	   private int lastPressed;
	   
	   //Animation for changing themes
	   private boolean swapThemeAni;
	   private int themeOffsetX;
	   private int themeOffsetY;
	   private boolean moveX;
	   private boolean moveY;
	   private int themeToSet;
	   private int audioToSet;
	   private int maxAudio;
	   
	   private boolean isSwappingTheme;
	   
	   	//Speed variables
		private int themeSpeed;
		private int themeAdd;
		private int xDir;
		private int yDir;
	   
		private int unlockX;
		private int unlockY;
		private boolean unlockAnimation;
		private int unlockSpeed;
		private int unlockAdd;	   
		
		private String[] gameInfo;
		private String[] gameInfoString = {"High Score", "Time Played","Time Paused", "Swaps Made","Matches Made", "Total Score"};
		private int[] fixSetup = {0,5,3,4,1,2};
	   
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
		//Man.AnimationM.isMenuDown = true;
		
		tabs = new RectTex[3];
		tabText = new String[3];
		
		selectTab = 0;
		tabs[0] = Man.ButtonM.TabThemes;
		tabs[1] = Man.ButtonM.TabAudios;
		tabs[2] = Man.ButtonM.TabStats;
		tabText[0] = "THEME";
		tabText[1] = "MUSIC";
		tabText[2] = "STATS";
		
		swapThemeAni = false;
		themeOffsetX = 0;
		themeOffsetY = -800;
		moveX = false;
		moveY = false;
		themeSpeed = 2600;
		themeAdd = 70;
		xDir = 1;
		yDir = 1;
		
		unlockSpeed = -60;
		unlockAdd = 1;
		
		themeToSet = Man.activeTheme;
		maxAudio = 1;
		currency = Man.ScoreM.currency;
		currencyOffset = 0;
		currencyOffsetDir = 1;
		
		if(currency > 9999) drawCurrency = "+9999";
		else drawCurrency = Integer.toString(currency);
		
		StoreMain = Man.ButtonM.Store;
		Back = Man.ButtonM.BackStore;
		
		gameInfo = new String[6];
		
		for(int i = 0 ; i < gameInfo.length; i++)
		{
			if(i == 1 || i == 2) gameInfo[i] = timeConversion(Man.ScoreM.infoHolder[i+1]);
			else gameInfo[i] = Integer.toString(Man.ScoreM.infoHolder[i+1]);
		}
		
		Man.AnimationM.SideAnimation = true;
		Man.AnimationM.SideYOffset = -800;
		screenDir = 1;
	      
	}
	
	//See abstrakt class Gamestate update(float dt);
	public void update(float dt)
	{
		
		if(Man.AnimationM.SideAnimation)
		{
			if(screenDir < 0)Man.AnimationM.SideToMenu(dt,screenDir,false);
			//Man.AnimationM.SideYOffset > 0 && 
			else Man.AnimationM.SideToMenu(dt,screenDir,true);
		}
		
		if(unlockAnimation)
		{
			unlockSpeed += unlockAdd;
			unlockY += unlockSpeed*dt;
			unlockAdd += Man.AnimationM.accel;
			if(unlockY >= 800)
			{
				unlockSpeed = -60;
				unlockAdd = 1;
				unlockAnimation = false;
			}
		}
		
		if(swapThemeAni)
		{
			if(moveY)animateYChange(dt);
			if(moveX)animateXChange(dt);
		}
		
		if(Math.abs(currencyOffset) > 0)
		{
			currencyOffset -= dt;
			currencyOffsetDir *= -1;
		}
		
	}
	
	//See abstrakt class Gamestate draw(SpriteBatch b);
	public void draw(SpriteBatch batch)
	{
		int yOffset = Man.AnimationM.SideYOffset;
		batch.setColor(Man.Color_Store);
		//batch.draw(StoreMain.tex, StoreMain.x, StoreMain.y+yOffset);
		batch.setColor(Man.Color_StoreBar);
		batch.draw(Man.TextureM.tabSelect,160*selectTab,tabs[0].y+75+yOffset);
		batch.draw(Man.TextureM.scoreBack,0,150+yOffset);
		
		//Draw the tabs
		for(int i = 0; i < 3; i++)
		{
			if(i == selectTab)
			{
				batch.setColor(Man.Color_StoreBar);
				batch.draw(tabs[i].tex,tabs[i].x,tabs[i].y+yOffset);
				Man.fontWhite.draw(batch, tabText[i], 30+160*i, tabs[i].y+85+yOffset);
				//batch.setColor(1,1,1,1);
			}
			else
			{
				batch.setColor(Man.Color_Store);
				batch.draw(tabs[i].tex,tabs[i].x,tabs[i].y+yOffset);
				Man.fontBlack.draw(batch, tabText[i], 30+160*i, tabs[i].y+85+yOffset);
			}
			//batch.draw(tabs[i].tex,tabs[i].x,tabs[i].y+yOffset);
			//if(i == selectTab)Man.fontWhite.draw(batch, tabText[i], 30+160*i, tabs[i].y+85+yOffset);
			//else Man.fontBlack.draw(batch, tabText[i], 30+160*i, tabs[i].y+85+yOffset);
		}	
		
		// Currency Direction
		int cd = currencyOffset * currencyOffsetDir; 
		batch.draw(Man.TextureM.currencyBack,80,590+yOffset);
		//Man.fontWhite.draw(batch, "Your currency: ", 120+cd, 635+yOffset);
		Man.fontWhite.draw(batch,drawCurrency,220+cd, 635+yOffset);
		
		batch.setColor(1,1,1,1);
		
		if(selectTab != 2)
		{
			//DRAW ACTIVE STORE 
			for(int i = 0; i < 4; i++)
			{
				float itemX =  Man.ButtonM.StoreSelect[selectTab][i].x;
				float itemY =  Man.ButtonM.StoreSelect[selectTab][i].y+yOffset;
				batch.draw(Man.ButtonM.StoreSelect[selectTab][i].tex,
						  itemX,
						  itemY);
	
				if(!wallet.owned[selectTab][i])
				{
					if(i == lastPressed)batch.draw(Man.TextureM.lockedItem,itemX+cd,itemY);
					else batch.draw(Man.TextureM.lockedItem,itemX,itemY);
					batch.draw(Man.TextureM.priceHolder,itemX,itemY-64);
					Man.fontBlack.draw(batch,Integer.toString(wallet.price[selectTab][i]),itemX+40,itemY-30);
				}
				else if(i == Man.activeTheme && selectTab == 0) 
				{
					batch.setColor(1,1,1,1);
					batch.draw(Man.TextureM.currentlyActive, itemX-4, itemY-4);
					batch.setColor(1,1,1,1);
				}
				else if(i == Man.activeAudio && selectTab == 1) 
				{
					batch.setColor(1,1,1,1);
					batch.draw(Man.TextureM.currentlyActive, itemX-4, itemY-4);
					batch.setColor(1,1,1,1);
				}
			}
		}
		else
		{
			// DRAW STATS SAVED
			for( int i = 0 ; i < gameInfo.length; i++)
			{
				int pos = fixSetup[i];
				Man.fontBlack.draw(batch, gameInfoString[pos], 60, 540 - 60*i+yOffset);
				Man.fontBlack.draw(batch, gameInfo[pos], 300, 540 - 60*i+yOffset);
			}
		}
		
		if(unlockAnimation) batch.draw(Man.TextureM.unlockedItem, unlockX,unlockY);
		
		batch.setColor(Color.BLACK);
		//batch.setColor(Man.Color_Logo);
		batch.draw(Back.tex, Back.x, Back.y+yOffset);
		batch.setColor(1,1,1,1);
		
		if(swapThemeAni)
		{
			batch.draw(Man.TextureM.swapTheme,0,themeOffsetY);
			
			int x = 100+themeOffsetX;
			int y = 430+themeOffsetY;
			if(isSwappingTheme)
			{
				batch.draw(Man.TextureM.square,x+0*68,y);
				batch.draw(Man.TextureM.triangle,x+1*68,y);
				batch.draw(Man.TextureM.circle,x+2*68,y);
				batch.draw(Man.TextureM.ex,x+3*68,y);
			}
			else batch.draw(Man.ButtonM.StoreAudios[Man.activeAudio].tex,x+1*68,y-64);
			//batch.draw(Man.TextureM.swapAudio,x+1*68,y-64);
				
		}	
		
	}
	
	//See abstrakt class Gamestate justTouched(x,y);
	public void justTouched(float x, float y)
	{
		if(swapThemeAni || Man.AnimationM.SideAnimation || unlockAnimation) return;
		if(buttonClick(tabs[0],x,y) && selectTab != 0) 
		{
			selectTab = 0;
			Man.playSoundEffect(AudioManager.SWITCH);
		}
		if(buttonClick(tabs[1],x,y)&& selectTab != 1) 
		{
			selectTab = 1;
			Man.playSoundEffect(AudioManager.SWITCH);
		}
		if(buttonClick(tabs[2],x,y)&& selectTab != 2) 
		{
			selectTab = 2;
			Man.playSoundEffect(AudioManager.SWITCH);
		}
		
		storeItemClick(selectTab,x,y);
		
		if(buttonClick(Back,x,y))
		{
			screenDir = -1;
			Man.AnimationM.SideAnimation = true;
			Back.pressedEffect();
			Man.playSoundEffect(AudioManager.PUSH);
		}
	}
	//Handles users input given in which store tab is active
	//Glorified switch/case instead of ugly else ifses
	//ToBe finished!
	private void storeItemClick(int aTab,float x, float y)
	{
		switch (aTab){
			case 0 : 
			{
				for(int i = 0; i < 4; i++)
				{
					if(buttonClick(Man.ButtonM.StoreSelect[selectTab][i],x,y))
					{
						if(wallet.owned[selectTab][i])canChangeTheme(i);
						else checkCapital(selectTab,i);	
					}
				}
				break;
			}
			case 1 : 
			{
				for(int i = 0; i < 4; i++)
				{
					if(buttonClick(Man.ButtonM.StoreSelect[selectTab][i],x,y))
					{
						if(wallet.owned[selectTab][i])canChangeAudio(i);
						else checkCapital(selectTab,i);	
					}
				}
				break;
			}
		}
	}
	
	//Sends request to wallet if user has enough currency to buy item.
	private void checkCapital(int selected, int item)
	{
		if(wallet.canBuy(selected, item,currency)) handlePayment(selected, item);
		else
		{
			lastPressed = item;
			denyUser(); //Display error message
		}
	}
	
	//Handles the payment, updates the score and updates the manager.
	private void handlePayment(int selected, int item)
	{
		Man.ScoreM.payForAsset(wallet.aquireAsset(selected, item)); 
		currency = Man.ScoreM.currency;
		drawCurrency = Integer.toString(currency);
		
		unlockX = Math.round(Man.ButtonM.StoreSelect[selected][item].x);
		unlockY = Math.round(Man.ButtonM.StoreSelect[selected][item].y);
		unlockAnimation = true;
		Man.playSoundEffect(AudioManager.UNLOCK);
	}
	
	//Lets user know that he has not enough currency to buy chosen item.
	private void denyUser()
	{
		currencyOffset = 20;
		Man.playSoundEffect(AudioManager.ERROR);
		Gdx.input.vibrate(500);
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
		isSwappingTheme = true;
	}
	
	private void canChangeAudio(int toAudio)
	{
		if(toAudio == Man.activeAudio)return;
		isSwappingTheme = false;
		// Do audio animation here
		audioToSet = toAudio;
		xDir = 1;
		yDir = 1;
		moveY = true;
		swapThemeAni = true;	
	}
	
	private void callAudioChange()
	{
		Man.AudioM.stopMusic();
		Man.setAudio(audioToSet);
	}
	
	//Sets the chosen theme. 
	private void callThemeChange()
	{
		Man.setTheme(themeToSet);
		resetStore();
	}
	
	//Resets the store textures. Redraws the store so it will fit the corresponding theme.
	private void resetStore()
	{
		tabs[0] = Man.ButtonM.TabThemes;
		tabs[1] = Man.ButtonM.TabAudios;
		tabs[2] = Man.ButtonM.TabStats;
		//tabText[0] = "Themes";
		//tabText[1] = "Audios";
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
	//TODO : Simplify this in one function. Nah fuck it
	//Changes the yOffset of theme changing animation.
	private void animateYChange(float dt)
	{
		{
			if(yDir > 0)
			{
				themeSpeed -= themeAdd*yDir;
				themeOffsetY += themeSpeed*dt*yDir;
				themeAdd -= Man.AnimationM.accel*yDir;
				decrementAudio(dt);
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
				incrementAudio(dt);
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
			decrementAudio(dt);
			if(themeOffsetX > 480)
			{
				if(isSwappingTheme)callThemeChange();
				else callAudioChange();
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
			incrementAudio(dt);
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
	
	private void incrementAudio(float dt)
	{
		if(!isSwappingTheme) return;
		if(maxAudio <= 1)
		{
			maxAudio += dt*6;
			Man.AudioM.setMainVolume(maxAudio);
		}
	}
	
	private void decrementAudio(float dt)
	{
		if(!isSwappingTheme) return;
		if(maxAudio >= 0)
		{
			maxAudio -= dt*6;
			Man.AudioM.setMainVolume(maxAudio);
		}	
	}
	
	// Changes a sum of integers to time converted string
		private String timeConversion(int totalSeconds) {

		    final int MINUTES_IN_AN_HOUR = 60;
		    final int SECONDS_IN_A_MINUTE = 60;

		    int seconds = totalSeconds % SECONDS_IN_A_MINUTE;
		    int totalMinutes = totalSeconds / SECONDS_IN_A_MINUTE;
		    int minutes = totalMinutes % MINUTES_IN_AN_HOUR;
		    int hours = totalMinutes / MINUTES_IN_AN_HOUR;
		    
		    String sec = adjustNumerals(seconds);
		    String min = adjustNumerals(minutes);
		    String hour = adjustNumerals(hours);

		    return hour + ":" + min + ":" + sec;
		}
		
		// Writes a time conversion to the specific format XX:XX:XX
		private String adjustNumerals(int aNumber)
		{
			String aString;
			
			if(aNumber == 0) aString = "00";
		    else if (aNumber < 10) aString = "0"+Integer.toString(aNumber);
		    else aString = Integer.toString(aNumber);
			
			return aString;
		}
	
	//See abstrakt class Gamestate dispose();
	public void dispose()
	{
	
	}
}
