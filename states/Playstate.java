package states;

import java.util.Random;

import managers.AudioManager;
import managers.GameStateManager;
import managers.RectangleManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.blokk.game.Utils;

import entities.GameStats;
import entities.Movable;
import entities.RectTex;
import entities.UI;


//Class by ï¿½ttar, Hlynur and ï¿½orsteinn.
//
//Written 30.10.2014
//Creates a new state when user is playing.
//This is the main game loop that is in charge of the game.
//This class might be refactored by using the resource manager but currently that is not a priority
public class Playstate extends Gamestate{

	private Movable[][] Movables;
	private long lastDropTime;
	private int rows;
	private int columns;
	private int superSpeed;
	public static boolean isPaused;
	private int size;
	private float selectedX;
	private float selectedY;
	private float touchedY;
	private int takeoffSpeed;
	private int steps;
	private int startingRows;
	private RectangleManager Man;
	private UI UI;
	private long lastWave;
	private long lastWaveDrop;
	private int loseCondition;
	private int loseConditionOffset;
	private int losePos;
	private boolean canPlay;
	private float loseSpeed;
	private float defaultSpeed;
	private long actionTime;
	private int actions;
	private int IDs;
	private double pseudo;
	private boolean startAnimation;
	private boolean isWave;
	private int scoreBoardPos;
	private int score;
	private int introSpeed;
	private int lvlDisp;
	private double noob;
	private long shootEmUp;
	private int harass;
	private GameStats stats;
	private int scoreThreshold;
	// public for global access
	public static Movable selectedM;
	public static boolean isSelected;
	public static double difficulty;
	private int musicThreshold;

	//chosen background

	private boolean UIFIX;

	private boolean isTesting;


	//Constructor
	//See abskrakt class Gamestate(GameStateManager gsm);
	public Playstate(GameStateManager gsm)
	{
		super(gsm);
	}
	//See abstrakt class Gamestate init();
	@Override
	public void init(RectangleManager RectMan)
	{
		actions = 0;
		actionTime = System.currentTimeMillis()-500;
		Man = RectMan;
		isPaused = false;
		Man.AnimationM.isMenuDown = true;
		size = 68;
		steps = size/2; //pixel perfect updating
		columns = 7;
		rows = 13;
		takeoffSpeed = 950;
		startingRows = 3;
		IDs = 1;
		Movables = new Movable[columns][rows];
		lastWave = 0;
		UI = new UI(0, 800, 480, size);
		difficulty = 1.0;
		pseudo = 0;
		defaultSpeed = 300;
		loseCondition = 840;
		losePos = 1;
		canPlay = false;
		startAnimation = true;
		introSpeed = 300;
		loseSpeed = 20;
		superSpeed = 3000;
		score = 0;
		scoreThreshold = 15;
		prepareMatrix();
		isWave = true;
		shootEmUp = Long.MAX_VALUE;
		noob = 1.7;
		lastDropTime = 0;
		harass = 0;
		lastWaveDrop = System.currentTimeMillis();

		stats = new GameStats();

		isTesting = false;

		//Man.AudioM.raiseThemeMusic();
		Man.AudioM.makeLoop();
		musicThreshold = 0;
		Man._r = Man._rOrg;
		Man._g = Man._gOrg;
		Man._b = Man._bOrg;

		UIFIX = Man.ScoreM.firstTime;
		if(!UIFIX) difficulty = 0.4;
	}
	/**
	 * Creates a new cube on a timed interval. Itï¿½ï¿½ï¿½s type is randomed. This method is a temporary solution for spawning cubes in debugging mode
	 *
	 * @return            a new cube of some sort is created and placed in the grid
	 */
	private void spawnMovable(int col, float speed) {
		Movable m1;

		m1 = new Movable(true);
		m1.col = col;
		m1.type = createType(m1.typeOne, m1.typeTwo);

		if (Movables[m1.col][rows-1] != null) return;

		int available_row = 0;
		for (int i = 0; i < rows; i++) {
			if (Movables[m1.col][i] == null) {
				available_row = i;
				break;
			}
		}
		m1.row = available_row;
		m1.x = (size+1)*m1.col;
		m1.y = 800;
		m1.speed = -speed;
		m1.width = size;
		m1.height = size;
		m1.isBeingThrusted = false;
		m1.justSpawned = true;
		m1.ID = 0;
		m1.timeShuffled1 = Long.MAX_VALUE;
		m1.timeShuffled2 = Long.MAX_VALUE;
		m1.timeShuffled3 = Long.MAX_VALUE;
		m1.delayBlacked = Long.MAX_VALUE;
		double r = Math.random();
		m1.isPowerDown = false;
		if(r > 0.99-pseudo){
			pseudo = 0;
			m1.isPowerDown = true;
		} else{
			pseudo += 0.003;
		}

		Movables[m1.col][available_row] = m1;
		giveLegalType(m1);

		lastDropTime = System.currentTimeMillis();
	}

	// Use: spawnWave(speed);
	// After: A new wave of blocks has been spawned with velocity speed
	private void spawnWave(float speed) {
		for(int j = 0; j < columns; j++){
			spawnMovable(j, speed);
		}
	}
	/**
	 * Prepares the Movables matrix by adding a row of immovable blocks below the screen for collision purposes
	 * 
	 * @return            one row of immovable blocks are created below the screen
	 */
	public void prepareMatrix() {
		for (int i = 0; i < columns; i++) {
			Movable movable = new Movable(false);
			movable.type = createType(movable.typeOne, movable.typeTwo);
			movable.row = 0;
			movable.y = -size;
			movable.speed = 0;
			movable.width = size;
			movable.height = size;
			movable.isBeingThrusted = false;
			movable.justSpawned = false;
			movable.x = (size+1)*i;
			movable.isPowerDown = false;
			movable.ID = -1;
			Movables[i][0] = movable;
		}
	}

	// Use: killBlock(m1);
	// After: Called when a block is deleted by thrusting it up above the loseCondition line
	//       Deletes the movable, adds scores and plays sound
	public void killBlock(Movable m1)
	{
		addScore(Movables[m1.col][m1.row].typeOne, Movables[m1.col][m1.row].typeTwo,1);
		Movables[m1.col][m1.row] = null;
		// ensure that there is no gap between blocks inside the array at any time
		int lowestEmptyIndex = -1;
		for (int i=0; i < rows; i++) {
			// find the first empty row in the same column as m1
			if (Movables[m1.col][i] == null && lowestEmptyIndex == -1) {
				lowestEmptyIndex = i;
				continue;
			}
			// this case only happens if there is a block with a higher index than
			// the first empty one => there is a gap in the array
			else if (Movables[m1.col][i] != null && lowestEmptyIndex > -1) {
				Movable temp = new Movable(Movables[m1.col][i]);
				temp.row = lowestEmptyIndex;
				Movables[m1.col][lowestEmptyIndex] = temp;
				Movables[m1.col][i] = null;
			}
		}
		Man.playSoundEffect(AudioManager.COLLECT);
	}

	private void activateKillBlock(Movable m)
	{
		if (m.y > loseCondition && m.speed > 0 && canPlay)
		{
			m.isDead = true;
			m.deathFrame = 0;
		}
	}

	/**
	 * Gives a created cube its texture depend on his boolean tree structure
	 * 
	 * @param typeOne file Boolean which decides if it is a movable cube or black block
	 * @param typeTwo boolean that decides what kind of color the cube is
	 * @return            returns Texture corresponding to itï¿½ï¿½ï¿½s boolean structure
	 */
	private Texture createType(Boolean typeOne, boolean typeTwo)
	{
		if (typeOne == null)
		{
			if(!typeTwo)
				return Man.TextureM.black;
			else
				return Man.TextureM.blinkBlack;
		}
		return (typeOne ? (typeTwo ? Man.TextureM.square : Man.TextureM.circle)
				: (typeTwo ? Man.TextureM.triangle : Man.TextureM.ex));
	}

	// Use: int type = translateType(m1);
	// After: Returns value 1 if m1 has types True/True,
	//        value 2 if m1 has types True/False,
	//        value 3 if m1 has types False/True and
	//        value 4 if m1 has types False/False
	private int translateType(Movable m) {
		if (m == null) return 0;

		Boolean typeOne = m.typeOne;
		boolean typeTwo = m.typeTwo;
		if (typeOne == null) return -1;

		else if (typeOne && typeTwo) return 1;
		else if (typeOne && !typeTwo) return 3;
		else if (!typeOne && typeTwo) return 2;
		else if (!typeOne && !typeTwo) return 4;

		return 0;
	}

	// Use: beginAction()
	// After: A wave of blocks has been spawned
	public void beginAction() {
		spawnWave(2500); // test, var ï¿½ 2000
	}


	//See abstrakt class Gamestate update(float dt);
	@Override
	public void update(float dt)
	{
		if(isTesting) return;
		if (isPaused) {
			stats.timePaused += dt*3;
			delayMovableTimers(dt);
			return;
		}
		if(canPlay)
		{
			stats.timePlayed += dt*3;
			if(actions < startingRows){
				if(System.currentTimeMillis() - actionTime > 1000) { // testing, var 1000
					beginAction();
					actionTime = System.currentTimeMillis();
					actions++;
					if (actions == 3) spawnMovable(MathUtils.random(0, 6), (float)((1+(1-difficulty))*defaultSpeed));
				}
				lastWave = score;
			}
			if (System.currentTimeMillis() - lastWaveDrop > 15000*(1+(1-difficulty))*1.2) {
				spawnWave((float)((1+(1-difficulty))*defaultSpeed));
				lastWaveDrop = System.currentTimeMillis();
			}

			if(System.currentTimeMillis() - shootEmUp > 1500 ){
				spawnWave((float)((1+(1-difficulty))*defaultSpeed));
				shootEmUp = Long.MAX_VALUE;
			}
			if(score - lastWave > scoreThreshold)
			{
				if (noob == 2) noob = 1;
				shootEmUp = System.currentTimeMillis();
				if(score > 500 && scoreThreshold == 15){
					scoreThreshold = 30;
				}
				lastWave = score;
				if(difficulty > 0.15)
				{
					// spawna wave og hækka difficulty til skiptis, setja difficulty -= 0.03
					difficulty -= 0.02;
					musicThreshold++;
					//TODO:This is just a placeholder. Will be fixed
					if(musicThreshold == 4 && Man.AudioM.activeMusic < 3)
					{
						difficulty -= 0.04;
						musicThreshold = 0;
						//Man.AudioM.upgradeGame();
						//Man.playSoundEffect(AudioManager.PAUSE);
						Man.AudioM.raiseThemeMusic();
					}
				}
			}
			else if (actions == startingRows && System.currentTimeMillis() - lastDropTime > 1100*difficulty*noob) spawnMovable(MathUtils.random(0, 6), (float)((1+(1-difficulty))*defaultSpeed));
			
		}
		else if (startAnimation) playIntro(dt,-1);
		else blackMovableAnimation(dt);
		for (int i = 0; i < steps; i++) computeSubStep(dt/steps);

	}

	// Animates the losing line and the top UI
	private void playIntro(float dt, int dir)
	{
		introSpeed += dir;

		if(UI.y > 800-size) UI.y += introSpeed * dt * dir;
		else UI.y = 800-size;
		if(dir < 0)
		{
			scoreBoardPos = (int)UI.y+45;
			loseCondition += introSpeed * dt * dir;

			if(loseCondition < 670)
			{
				loseCondition = 670;//720-size;
				canPlay = true;
				startAnimation = false;
			}
		}
	}

	// Use: blackMovableAnimation(dt);
	// After: Starts the losing animation when game is lost.
	//        Blacks every Movable on the screen as they fall down
	private void blackMovableAnimation(float dt)
	{
		playIntro(dt,1);
		if(Man._r >= 0) Man._r -= dt*4;
		if(Man._g >= 0) Man._g -= dt*4;
		if(Man._b >= 0) Man._b -= dt*4;
		for(int i = 0; i < rows; i++)
		{
			for (int j = 0; j < columns; j++)
			{
				Movable m = Movables[j][i];
				if(m != null)
				{
					if(m.typeOne != null)
					{
						m.typeOne = null;
						m.typeTwo = false;
						return;
					}
					else
					{
						m.y -= loseSpeed*dt;
						loseSpeed += 2;
						if(m.row == 11 && m.y + size*4 <= 0)gameOver();
					}
				}
			}
		}

	}

	// Changes the offset of the lose condition to give the lose line a vibrating effect
	// if blocks in each row have stacked high enough
	private void checkLoseOffset(boolean aMethod)
	{
		if(aMethod)
		{
			losePos *= -1;
			loseConditionOffset = 0;
		}
		else
		{
			loseConditionOffset -= 7; //8
			if(loseConditionOffset < 0) loseConditionOffset = 0;
			else loseConditionOffset *= losePos;
		}
	}

	//See abstract class Gamestate draw(SpriteBatch b);
	@Override
	public void draw(SpriteBatch batch)
	{
		checkLoseOffset(true);
		if(!isPaused)
		{
			for(int i = 0; i < columns; i++) {
				for (int j = 0; j < rows; j++) {
					Movable m = Movables[i][j];
					if (m != null)
					{
						//TODO: this should also not be here!!!! just for demo atm
						if(m.row > loseConditionOffset && m.speed  == 0) loseConditionOffset = m.row;
						if(m.isPowerDown)
						{
							if(!m.isDead)batch.draw(Man.TextureM.powerDown, m.x, m.y);
						}
						if (m.isDead)
						{
							batch.draw(Man.TextureM.kill[m.deathFrame], m.x, m.y);
							m.deathFrame++;
							if(m.deathFrame > 7)killBlock(m);
						}
						else if (!m.isPowerDown) batch.draw(createType(m.typeOne,m.typeTwo), m.x, m.y);
					}
				}
			}

			checkLoseOffset(false);
			if(isSelected && selectedM != null) {
				if (!selectedM.justSpawned)
					// -3 offset since picture is 70x70
					batch.draw(Man.TextureM.selected, selectedM.x-3, selectedM.y-3);
			}
		}
		else
		{
			batch.setColor(Color.BLACK);
			Man.drawButton(batch, Man.ButtonM.PauseResume, 0, 0, true);
			Man.drawButton(batch, Man.ButtonM.PauseRestart, 0, 0, true);
			Man.drawButton(batch, Man.ButtonM.PauseQuit, 0, 0, true);
			batch.setColor(1,1,1,1);
		}

		batch.draw(Man.TextureM.loseLine, 0, loseCondition+loseConditionOffset);
		//if(UIFIX)
		//{
			batch.draw(Man.TextureM.ui_bg, UI.x, UI.y, UI.width, UI.height);
			int addX = Integer.toString(score).length();
			Man.fontWhite.draw(batch,Integer.toString(score), 230-addX*4, scoreBoardPos);
			Man.drawButton(batch, Man.ButtonM.Pause, 0, scoreBoardPos-790, false);
		//}
	}

	public static void touchUp(int x, int y) {
		isSelected = false;
		selectedM = null;
	}

	//See abstrakt class Gamestate justTouched(x,y);
	@Override
	public void justTouched(float x, float y)
	{
		isSelected = true;
		selectedM = locateMovable(x, y);
		touchedY = y;
		if(buttonClick(Man.ButtonM.Pause,x,y)) isPaused = !isPaused;
		if(isPaused)
		{
			if(buttonClick(Man.ButtonM.PauseResume,x,y)) isPaused = false;
			if(buttonClick(Man.ButtonM.PauseRestart,x,y)) RestartGame();
			
			if(buttonClick(Man.ButtonM.PauseQuit,x,y))
			{
				isPaused = false;
				Man.AnimationM.isMenuDown = true;
				Man.AudioM.resetThemeMusic();
				gsm.introEnd = false;
				gsm.setState(GameStateManager.MENU);
			}
		}
	}

	// Use: RestartGame();
	// After: All starting variables have been reset so the game can be played from start
	private void RestartGame()
	{
		for(int i = 0; i < columns; i++)
		{
			for(int j = 0; j < rows ; j++)
			{
				Movables[i][j] = null;
			}
		}
		difficulty = 1.0;
		score = 0;
		isPaused = false;
		prepareMatrix();
		actions = 0;
		actionTime = System.currentTimeMillis()-500;
		Man.AnimationM.isMenuDown = true;
		Man.AudioM.resetThemeMusic();
		Man.AudioM.upgradeGame();
		shootEmUp = Long.MAX_VALUE;
		noob = 1.7;
		isWave = true;
		lastDropTime = 0;
		harass = 0;
		lastWaveDrop = System.currentTimeMillis();
		//Man.AudioM.raiseThemeMusic();
		Man.playSoundEffect(AudioManager.START);
	}

	//See abstrakt class Gamestate isTouched(x,y);
	@Override
	public void isTouched(float x, float y)
	{
		if(isPaused) return;
		if (isSelected) findMovable(x, y);


		if (selectedM != null) {
			if (selectedM.isPowerDown) {
				selectedM.isPowerDown = false;
				activatePowerDown();
				return;
			}
			selectedX = selectedM.x + size/2;
			selectedY = selectedM.y + size/2;
		}
	}

	public void delayMovableTimers(float dt)
	{
		for(Movable[] row : Movables)
			for(Movable m1 : row)
			{
				if(m1==null || m1.row ==0) continue;
				if(m1.timeBlacked!=Long.MAX_VALUE)
				{
					m1.timeBlacked=m1.timeBlacked+(long) (3000*dt);
				}
				if(m1.isBeingThrusted)
				{
					m1.timeThrusted=m1.timeThrusted+(long)(3000*dt);
				}
			}
	}
	/**
	 *Breaks the entities update into smaller steps so it wont render out of bounds.
	 * @param dy is the delta time of each frame rendered
	 */

	//TODO : FIX THIS. We are doing this wrong. It should check m1 first and then
	//		update 64 times. Then m2 and update 64 times not 64 times update everyone.
	public void computeSubStep(float dy) {
		for(int j = 0; j < rows; j++) {
			for (int i = 0; i < columns; i++) {
				boolean intersect = false;
				Movable m1 = Movables[i][j];
				if(m1 == null || m1.row == 0) continue;
				if (m1.speed != 0) {
					if (Movables[m1.col][m1.row-1] != null && m1 != Movables[m1.col][m1.row-1] &&
							m1.intersects(Movables[m1.col][m1.row-1])) {
						if (m1.justSpawned) intersect = true;
						//assume here that m1 and m2 are some colliding blocks
						Movable m2 = Movables[m1.col][m1.row-1];
						m1.speed = Math.max(m1.speed, m2.speed);
						m1.timeThrusted = Math.max(m2.timeThrusted, m1.timeThrusted);
						m1.isBeingThrusted = m2.isBeingThrusted;
						m1.ID = m2.ID;
						m1.justSpawned = false;
						if (m1.y < m2.y+size) m1.y = m2.y+size;
						//if(m1.row == 11) endGameAnimation();
						if(m1.row == 11 && m1.speed == 0) endGameAnimation();
						//					  if(m1.speed>0){
						//	    				  m1.isBeingThrusted = true;
						//	    				  m1.timeThrusted = m2.timeThrusted;
						//	    			  }
						IDs++;
						handleMatches(m1, IDs);
					}
				}
				// this happens when block can be unblacked/afsvertist
				if(m1.movableCheck()){
					// Skítamix
					m1.typeOne = true;
					giveLegalType(m1);
				}
				if(System.currentTimeMillis() - m1.timeShuffled1> 0) {
					giveLegalType(m1);
					m1.timeShuffled1 = Long.MAX_VALUE;
				}
				else if(System.currentTimeMillis() - m1.timeShuffled2> 0) {
					giveLegalType(m1);
					m1.timeShuffled2 = Long.MAX_VALUE;
				}
				else if(System.currentTimeMillis() - m1.timeShuffled3> 0) {
					giveLegalType(m1);
					m1.timeShuffled3 = Long.MAX_VALUE;
				}
				if(System.currentTimeMillis() - m1.delayBlacked > 0){
					m1.delayBlacked = Long.MAX_VALUE;
					m1.typeOne = null;
					m1.typeTwo = false;
					m1.isPowerDown = false;
					m1.timeBlacked = System.currentTimeMillis();
				}
				if(!m1.isDead)activateKillBlock(m1);
				if (intersect) continue;
				m1.update(dy);
			}
		}
	}



	// Use: giveLegalType(m1);
	// Before: m1 is a movable block
	// After: m1 has been given a type so he doesn't match with nearby blocks
	public void giveLegalType(Movable m1){
		if (m1.typeOne == null) return;
		Random ran = new Random();
		int x = ran.nextInt(4);
		// first we attempt random type if this doesn't work we walk through them all until someone fits
		for(int attempts = 0; attempts < 4; attempts++){
			x = ran.nextInt(4);
			int[] tryType = Utils.integerType(x);
			boolean tryOne = Utils.toBoolean(tryType[0]);
			boolean tryTwo = Utils.toBoolean(tryType[1]);
			m1.setType(tryOne, tryTwo);
			boolean thrustCase = false;
			int[] resultsHorizontal = findHorizontalMatches(m1, thrustCase);
			int[] resultsVertical = findVerticalMatches(m1, thrustCase);
			if(resultsHorizontal[0] == 0 && resultsVertical[0] == 0)
				return;
		}
		for(int i = 0; i<4; i++){
			int[] tryType = Utils.integerType((x+i)%4);
			boolean tryOne = Utils.toBoolean(tryType[0]);
			boolean tryTwo = Utils.toBoolean(tryType[1]);
			m1.setType(tryOne, tryTwo);
			boolean thrustCase = false;
			int[] resultsHorizontal = findHorizontalMatches(m1, thrustCase);
			int[] resultsVertical = findVerticalMatches(m1, thrustCase);
			if(resultsHorizontal[0] == 0 && resultsVertical[0] == 0)
				return;
		}
	}


	// Use: int[] shootCoordinates = findHorizontalMatches(m1);
	// After: Returns an array of size 3 if 3 or more blocks of same type are "matched"
	//        in the same row. The first index returns 1 if there is a match, else 0.
	//        The second index returns in what column the first match occurs.
	//        The third index returns how many blocks are matched.

	public int[] findHorizontalMatches(Movable m1, boolean thrustCase) {
		int count = 0;
		int row = m1.row;
		int index = -1;
		int ID = m1.ID;
		for(int j = 0; j < columns; j++){
			for(int i = j;  i< columns; i++){
				Movable m2 = Movables[i][row];
				if(isSameType(m2, m1) && (m1.speed == m2.speed || !thrustCase)){
					if(thrustCase){
						if(m2.ID == ID ){
							count++;
						}
					} else
						count++;

				} else{
					break;
				}
			}
			if(count >= 3){
				index = j;
				break;
			}
			count = 0;
		}
		int[] result = new int[3];
		if(count==0) {
			return result;
		}
		else {
			result[0] = 1;
			result[1] = index;
			result[2] = count;
			return result;
		}
	}

	// Use: int[] shootCoordinates = findVerticalMatches(m1);
	// After: Returns an array of size 3 if 3 or more blocks of same type are "matched"
	//        in the same column. The first index returns 1 if there is a match, else 0.
	//        The second index returns in what column the first match occurs.
	//        The third index returns how many blocks are matched.
	public int[] findVerticalMatches(Movable m1, boolean thrustCase) {
		int count = 0;
		int index = -1;
		int col = m1.col;
		int ID = m1.ID;
		for(int j = 0; j < rows; j++){
			for(int i = j;  i< rows; i++){
				Movable m2 = Movables[col][i];
				if(isSameType(m2, m1) && (m1.speed == m2.speed || !thrustCase)){
					if (thrustCase) {
						if(m2.ID == ID ){
							count++;
						}
					}
					else
						count++;
				} else{
					break;
				}
			}
			if(count >= 3){
				index = j;
				break;
			}
			count = 0;
		}
		int[] result = new int[3];
		if(count==0) {
			return result;
		}
		else {
			result[0] = 1;
			result[1] = index;
			result[2] = count;
			return result;
		}
	}

	//	public void setUnmatchedType(){
	//viljum ad handlematches gefi bara boolean hvort thad se column eda row match
	//kollum svo a thau foll her:
	//gongum i gegnum typurnar, gefum kubb lit og gerum svo handlematches
	//ef thad matchast eitthvad tha profum vid naesta lit
	//	}

	/**
	 *Checks to see if the player did indeed move a block to a valid position and to find if he added *three or more together
	 * @param m1 A moved Movable block by the user
	 */
	public void handleMatches(Movable m1, int thrustID)
	{
		boolean thrustCase = true;
		if (m1 == null || m1.typeOne == null) return;
		checkRowMatches(m1, thrustCase, thrustID);
		checkColMatches(m1, thrustCase, thrustID);
		return;
	}

	/**
	 *Checks out if anyone is linked to the moved Movable in the Column
	 * @param m1 A moved Movable block by the user
	 */

	public void checkColMatches(Movable m1, boolean thrustCase, int thrustID){
		int col = m1.col;
		int[] shootCoordinates = findVerticalMatches(m1, thrustCase);
		int index = shootCoordinates[1];
		int count = shootCoordinates[2];
		if(count > 2){
			Movable m3 = Movables[col][index];
			//Here the blocks become black/svartir/svertun
			m3.typeOne = null;
			m3.typeTwo = false;
			m3.timeBlacked = System.currentTimeMillis();
			for (int i = index; i < count+index; i++){
				Movable m5 = Movables[col][i];
				m5.typeOne = null;
				m5.typeTwo = false;
				m5.timeBlacked = System.currentTimeMillis();
			}

			//TODO: skoda
			if(m1.ID != -1) shootByID(m1.ID, superSpeed);
			else shootRows(m1.col, 1, index, takeoffSpeed, false, thrustID);
			Man.playSoundEffect(AudioManager.MATCH);
		}
	}

	/**
	 *Checks out if anyone is linked to the moved Movable in the Row. This one is bugged and needs *refactoring
	 * @param m1 A moved Movable block by the user
	 */
	public void checkRowMatches(Movable m1, boolean thrustCase, int thrustID){
		int row = m1.row;
		int[] shootCoordinates = findHorizontalMatches(m1, thrustCase);
		int index = shootCoordinates[1];
		int count = shootCoordinates[2];
		if(count > 2){
			for(int j = index; j < index+count; j++){
				Movable m3 = Movables[j][row];
				//Here the blocks become black/svartir/svertun
				m3.typeOne = null;
				m3.typeTwo = false;
				m3.timeBlacked = System.currentTimeMillis();

				/*
				m3.spawnParticles = true;
				m3.particleEmit = new ParticleEmitter((int)m3.x,(int)m3.y,Man.TextureM.dropParticle);
				 */
			}
			//TODO: skoda
			if(m1.ID != -1) shootByID(m1.ID, superSpeed);
			else shootRows(index, count, row, takeoffSpeed + (count - 3) * 125, false, thrustID);
			Man.playSoundEffect(AudioManager.MATCH);
		}
	}

	private void activatePowerDown(){
		isSelected = false;
		selectedM = null;
		Man.playSoundEffect(AudioManager.POWERDOWN3);
		for(int j = 0; j < rows; j++){
			for (int i = 0; i < columns; i++){
				Movable m2 = Movables[i][j];
				if(m2 != null) {
					int secs = 2;
					m2.timeShuffled1 = System.currentTimeMillis() + j*secs*columns+i*secs;
					m2.timeShuffled2 = System.currentTimeMillis() + j*2*secs*columns+i*secs*2;
					m2.timeShuffled3 = System.currentTimeMillis() + j*3*secs*columns+i*secs*3;
				}
			}
		}
	}

	// Use: addScore(typeOne, typeTwo, pts);
	// After: The score has been incremented by amount pts
	private void addScore(Boolean typeOne, boolean typeTwo, int aScore)
	{
		//if(typeOne == null) return;
		score++;
	}

	// Use: shootRows(blockIndex, blockCount, blockRow, isBeingThrusted);
	// After: Thrusts the blocks from row blockRow from index blockIndex to index
	//        blockIndex+blockCount and all blocks above that.
	//        The variable isBeingThrusted is not being used currently.
	public void shootRows(int index, int count, int row, int speed, boolean isBeingThrusted, int ID){

		stats.matchesMade ++;
		isSelected = false;
		selectedM = null;
		if(isBeingThrusted){
			//TODO: Resevered for thrustage
			//Stop reserving space. We must save space for powerups!
		}
		for(int j = index; j < index+count; j++){
			for (int i = row; i < rows; i++){
				Movable m1 = Movables[j][i];
				if(m1 != null) {
					if(m1.justSpawned || m1.ID != -1) continue;
					m1.speed = speed;
					m1.timeThrusted = System.currentTimeMillis();
					m1.isBeingThrusted = true;
					m1.ID = ID;
				}
			}
		}
	}

	// Use: shootByID(ID, superSpeed);
	// After: Every block with id "ID" will be thrusted upwards with speed "superSpeed"
	public void shootByID(int ID, int superSpeed){
		isSelected = false;
		selectedM = null;
		for(int i=0; i<columns; i++){
			for(int j=0; j<rows; j++){
				Movable m1 = Movables[i][j];
				if(m1 != null) {
					if (m1.ID == ID) m1.speed = superSpeed;
				}
			}
		}
		Man.playSoundEffect(AudioManager.MATCH2);
	}

	/**
	 *Finds out if the moved block was indeed the same color is the one moved in itï¿½ï¿½ï¿½s direction
	 * @param m1 A moved Movable block by the user
	 * @param typeOne lets the method know what kind of cube it is.
	 * @param typeOne lets the method know what kind of cube it is.
	 * @return true if a corresponding block is matched
	 */
	//IMPORTANT: If the second parameter is PowerDown we continue because the behaviour above
	//"fixes" m1 into the second parameter then iterates through the row or column inside the first parameter
	public boolean isSameType(Movable m1, Movable m2){
		if(m1 == null || m2 == null || m1.typeOne == null || m2.typeOne == null){return false;}
		if (m1.isPowerDown || m2.isPowerDown) return false;
		return (m1.typeOne == m2.typeOne && m1.typeTwo == m2.typeTwo);
	}

	/**
	 *Passes the x,y coordinates of the screen on to itï¿½ï¿½ï¿½s chiled methods to find out if the player is  *indeed clicking at a cube.
	 * @param x X-coordinates of the screen
	 * @param y Y-coordinates of the screen
	 */
	public Movable locateMovable(float x, float y) {
		int col = (int)(x/size);

		Movable selected = null;

		if (col < 0 || col > columns-1) return selected;

		for (int i = 0; i < rows; i++) {
			if (Movables[col][i] == null || Movables[col][i].typeOne == null) continue;
			if (y > Movables[col][i].y && y < (Movables[col][i].y + size)) {
				return Movables[col][i];
			}
		}
		return selected;
	}

	// Use: findMovable(x, y);
	// After: Finds the block that player when pressing down and handles all swipe gestures
	//        by the user.
	public void findMovable(float x, float y) {
		y = y+20; // offset for fat fingers
		if(selectedM == null) return;
		int col = selectedM.col;
		int row = selectedM.row;
		if (row < 0 || row > rows-1 || col < 0 || col > columns-1) return;
		if (selectedM.justSpawned) return;
		if (Math.abs((y-20) - touchedY) > 8)
			if (y > selectedY + size/2) {
				handleSwap(col, row, 1);
				return;
			}

			else if (y < selectedY - size/2) {
				handleSwap(col, row, -1);
				return;
			}
	}

	// Use: handleSwap(col, row, direction);
	// After: If the conditions for a swap have happened, two blocks have swapped positions
	//        and possible matches are checked for
	public void handleSwap(int col, int row, int direction){
		if (Movables[col][row+direction] == null) return;
		Movable targetM = Movables[col][row+direction];
		//TODO: see if checking for same speed has any meaning: targetM.speed != selectedM.speed
		// removed when gravity was added
		if (targetM == null || targetM.typeOne == null || targetM.justSpawned) return;
		swapTypes(selectedM, targetM);
		IDs++;
		//DOUBLE SWAP CASES, DOUBLESWAP, SUPERSWAP
		if (row < row + direction) {
			handleMatches(Movables[col][row], IDs);
			handleMatches(Movables[col][row+direction], IDs);
		}
		else {
			handleMatches(Movables[col][row+direction], IDs);
			handleMatches(Movables[col][row], IDs);
		}
		handleMatches(Movables[col][row], IDs);
		selectedY += direction*size;
		handleMatches(Movables[col][row+direction], IDs);
		handleMatches(Movables[col][row], IDs);
	}

	/**
	 *Swaps to different Movables if the move of the player was legit
	 * @param m1 A moved Movable
	 * @param m2 Movable that is going to be swapped
	 */

	public void swapTypes(Movable m1, Movable m2) {

		stats.swapsMade ++;

		Boolean tempOne = m1.typeOne;
		boolean tempTwo = m1.typeTwo;
		boolean tempDown = m1.isPowerDown;
		m1.typeOne = m2.typeOne;
		m1.typeTwo = m2.typeTwo;
		m1.isPowerDown = m2.isPowerDown;
		m2.typeOne = tempOne;
		m2.typeTwo = tempTwo;
		m2.isPowerDown = tempDown;

		selectedM = m2;
		Man.playSoundEffect(AudioManager.SWAP);
	}
	//See abstrakt class Gamestate dispose();
	@Override
	public void dispose()
	{

	}

	// Use: buttonClick(rekt, x, y);
	// After: Returns true if the button rekt at position x,y is being pressed.
	public boolean buttonClick(RectTex rekt, float x, float y) {
		if (x < (rekt.x + rekt.width) && x > rekt.x && y > rekt.y && y < (rekt.y + rekt.height)) return true;
		return false;
	}

	// Starts the endgame animation and disables players inpute
	private void endGameAnimation()
	{
		canPlay = false;
		UI.y += 1;
		//Gdx.input.vibrate(1500);
		//TODO: Find new loosing sound
		Man.playSoundEffect(AudioManager.LOST);
		Man.AudioM.resetThemeMusic();
	}

	//Saves current score and sets the state to Lost. Called when game is lost
	private void gameOver()
	{
		Man.AnimationM.isMenuDown = true;

		if(gsm.hasFinishedTutorial)
		{
			Man.ScoreM.checkScore(score,stats);
			gsm.setState(GameStateManager.LOST);
		}
		else gsm.setState(GameStateManager.TUTORIAL);
	}
}
