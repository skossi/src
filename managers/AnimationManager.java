package managers;

//Class by Ottar Gudmundsson
//Written 1.3.2015
//Animation manager that takes care of all state transitions of the game. 
//For revelance, main menu theme transitions are not written in camelCase.
public class AnimationManager {

	private GameStateManager gsm;
	
	//Animation
	public int MenuXOffset;
	public int MenuYOffset;
	public int SideXOffset;
	public int SideYOffset;
	public static boolean SideAnimation;
	public int backgroundSpeed = -600;
	public int horizontalSpeed = 0;
	public int verticalSpeed = 0;
	public float speedAdd = 1;
	public int sideDir;
	public boolean moveMenu;
	public boolean moveFromSides;
	public boolean isMenuDown;
	public static double accel = 5.1;
	
	public AnimationManager(GameStateManager g)
	{
		gsm = g;
		speedAdd = 1;
	}
	
	//Main animation theme for menu states. Takes care of all animations that menu the user can control in menu state.
	public void MenuAnimateMethod( boolean horizontal, int maxValue, int direction, int underDest, int overDest,float dt)
	{
		int changeSpeed;
		int value;
		if(horizontal)
		{
			changeSpeed = horizontalSpeed;
			value = MenuXOffset;
		}
		else
		{
			changeSpeed = verticalSpeed;
			value = MenuYOffset;
		}
		changeSpeed += speedAdd;
		value += changeSpeed*dt*direction;
		speedAdd += accel;
		if(horizontal)
		{
			horizontalSpeed = changeSpeed;
			MenuXOffset = value;
			if(value <= -maxValue)gsm.setState(underDest);
			if(value >= maxValue) gsm.setState(overDest);			
		}
		else
		{
			verticalSpeed = changeSpeed;
			MenuYOffset = value;
			if(value <= -maxValue)setPlayState(underDest);
			if(value >= maxValue) setPlayState(overDest);
		}	
	}
	
	//Animation that takes care of resetting the menu from the sides.
	public void SideToMenu(float dt, int screenDir, boolean starting)
	{
		if(!starting)
		{
			horizontalSpeed += speedAdd;
			SideXOffset += horizontalSpeed*dt*screenDir;
			speedAdd += accel;
			if(SideXOffset < -480 || SideXOffset > 480)
			{
				moveFromSides = true;
				sideDir = screenDir;
				MenuXOffset = -480 * screenDir;
				gsm.setState(GameStateManager.MENU);
			}
		}
		else
		{
			horizontalSpeed -= speedAdd;
			SideXOffset += horizontalSpeed*dt*screenDir;
			speedAdd -= accel;
			if(SideXOffset*screenDir >= 0) 
			{
				SideXOffset = 0;
				speedAdd = 0;
				SideAnimation = false;	
			}
		}	
	}
	
	//Animation that takes care of resetting the menu from the the bottom/top.
	public void MenuDown(float dt)
	{
		verticalSpeed -= speedAdd;
		MenuYOffset += verticalSpeed*dt;
		speedAdd -= accel;
		
		if(MenuYOffset >= 0)
		{
			MenuYOffset = 0;
			verticalSpeed = 0;
			speedAdd = 0;
			gsm.introEnd = false;
			isMenuDown = false;
		}
	}
	
	//Moves the menu back to its original position when user switches to Menu state.
	public void EaseMenuBack(float dt)
	{
		horizontalSpeed -= speedAdd;
		MenuXOffset += horizontalSpeed*dt*sideDir; 
		speedAdd -= accel;
		if(MenuXOffset*sideDir*-1 <= 0)
		{
			MenuXOffset = 0;
			speedAdd = 0;
			moveFromSides = false;
		}
	}
	
	//calls the main game loop to stop rendering the background with introend and tells the state machine manager to set the next state to playing state since the intro is finished
	private void setPlayState(int aState)
	{
		gsm.setState(aState);
		gsm.introEnd = true;
		gsm.introStart = false;
	}
}
