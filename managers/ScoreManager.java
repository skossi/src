package managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

//Class by Ottar Gudmundsson
//Written 26.2.2015
//Score manager takes care of all scores and statistics saved into the game.
public class ScoreManager {

	//preferences
	private Preferences prefs;
	public boolean NewHighScore;
	public boolean NewIndivScore;
	public int whichNewIndivScore;
	public boolean firstTime;
	private static String[] Type = new String[]{"Best","Square","Triangle","Circle","Ex","Currency"};
	public int[][] scoreHolder = new int[6][5];
	public int[] currentScore = new int[5];
	
	public ScoreManager()
	{
		getScores();
		firstTime = prefs.getBoolean("First");
	}
	
	//If user opens the game for the first time, the tutorial screen is set. 
	//After that, this void is called to open the menu screen the next time
	public void firstDone()
	{
		firstTime = true;
		prefs.putBoolean("First",true);
		prefs.flush();
		prefs = Gdx.app.getPreferences("My Preferences");
	}
	//Updates the resources manager variables by using data from the phones preferences
	private void getScores()
	{
		prefs = Gdx.app.getPreferences("My Preferences");
		for(int i = 0; i < 5; i++)for(int j = 0; j < 5; j++)scoreHolder[i][j] = prefs.getInteger(Type[i]+Type[j]);			
	}
	//Takes in parameter newScore that is players score when game is lost. 
	//Gets preferences and checks if a new high score was made
	//if so, it saves it and refreshes the preferences.
	public void checkScore(int[] newScore)
	{
		NewHighScore = false;
		NewIndivScore = false;
		getScores();
		
		int sum = 0;
		for(int k = 1 ; k < 5; k++)
		{
			sum += newScore[k-1];
			currentScore[k] = newScore[k-1];
			scoreHolder[5][k-1] += newScore[k-1];
			//currencyInt[k-1] = 0;
		}
		currentScore[0] = sum;

		for(int i = 0; i < 5; i++)
		{
			if(currentScore[i] > scoreHolder[i][i])
			{
				for(int j = 0; j < 5; j++)
				{
					prefs.putInteger(Type[i]+Type[j],currentScore[j]);
					if(i == 0)NewHighScore = true;
					else
					{
						NewIndivScore = true;
						whichNewIndivScore = i-1;
					}
				}
			}
		}
		prefs.flush();
		getScores();
	}
}
