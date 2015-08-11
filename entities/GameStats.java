package entities;

//Class by Ottar Gudmundsson
//Written 29.3.2015
//Info about the players currently played game. Will be added to preferences and collected over
//lifetime.
public class GameStats {

	//TODO : Decide what info we want to save.
	public int swapsMade, matchesMade;
	public float timePlayed, timePaused;

	//Returns a array of integers that are rounded sums of certain values.
	public int[] loadToArray()
	{
		int timePlayedInt = Math.round(timePlayed);
		int timePausedInt = Math.round(timePaused);
		int[] aArray = {timePlayedInt, timePausedInt, swapsMade, matchesMade};
		return aArray;
	}
}
