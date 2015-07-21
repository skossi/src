package managers;

import com.badlogic.gdx.Preferences;

import entities.GameStats;

//Class by Ottar Gudmundsson
//Written 26.2.2015
//Score manager takes care of all scores and statistics saved into the game.
public class ScoreManager {

	//preferences
	private Wallet wallet;
	
	private Preferences prefs;
	public boolean NewHighScore;
	public boolean firstTime;
	private String[] infoString = {"Currency","HighScore","Play","Pause","Swap","Match","Blocks"};
	public int[] infoHolder = new int[infoString.length];
	public int currency;
	public int newestScore;

	
	public ScoreManager(Preferences p)
	{
		prefs = p;
		getScores();
		firstTime = prefs.getBoolean("First");
		
		wallet = new Wallet(prefs);
		
		//TODO:Just for debugging - should be taken out before release or build
		firstDone();
	}
	
	//Gives other states access to the wallet to gain info on prices and if they are available.
	public Wallet accesWallet()
	{
		return wallet;
	}
	
	//If user opens the game for the first time, the tutorial screen is set. 
	//After that, this void is called to open the menu screen the next time
	public void firstDone()
	{
		firstTime = true;
		prefs.putBoolean("First",true);
		int basicTheme = wallet.aquireAsset(0, 0);
		int basicAudio = wallet.aquireAsset(1, 0);
		// Start with 1000 score for debugging
		//prefs.putInteger(infoString[0], 1000);
		prefs.flush();
		
	}
	//Updates the resources manager variables by using data from the phones preferences
	private void getScores()
	{	
		for(int i = 0 ; i < infoString.length; i++)
		{
			infoHolder[i] = prefs.getInteger(infoString[i]);
		}
		currency = infoHolder[0];
	}
	
	//Reduces the players currency by amount of price item bought.
	public void payForAsset(int price)
	{
		currency -= price;
		prefs.putInteger(infoString[0], currency);
		prefs.flush();
	}
	
	//TODO : Add stats to a public array and display in scorestate!
	//Takes in parameter newScore that is players score when game is lost. 
	//Gets preferences and checks if a new high score was made
	//if so, it saves it and refreshes the preferences.
	public void checkScore(int score, GameStats aStats)
	{
		int[] infoArray = aStats.loadToArray();
		NewHighScore = false;
		getScores();
		
		currency = infoHolder[0];
		
		if(score > infoHolder[1])
		{
			NewHighScore = true;
			prefs.putInteger(infoString[1], score);
		}
		
		for(int i = 2 ; i < infoHolder.length-1; i++)
		{
			infoHolder[i] += infoArray[i-2];
			prefs.putInteger(infoString[i], infoHolder[i]);
		}
		
		infoHolder[6] += score;
		prefs.putInteger(infoString[6], infoHolder[6]);
		
		newestScore = score;
		currency += score;
		prefs.putInteger(infoString[0], currency);

		prefs.flush();
		getScores();
	}
}
