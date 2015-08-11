package managers;
import com.badlogic.gdx.graphics.Color;

//Class by Ottar Gudmundsson
//Written 7.4.2015
//Holds info about all colors themes in the game.
public class ThemeManager {
 	 
	private static final float rate = 255f;

	private Color[] Logo = 		new Color[]{
								new Color(1,127/rate,39/rate,1), new Color(0,247/rate,218/rate,1),
								new Color(1,216/rate,0,1),new Color(0,206/rate,95/rate,1)};
	
	private Color[] Play = 		new Color[]{
								new Color(160f/rate,211/rate,110/rate,1), new Color(138/rate,155/rate,15/rate,1),
								new Color(92/rate,254/rate,255/rate,1),new Color(255/rate,206/rate,95/rate,1)};
	
	private Color[] Score = 	new Color[]{
								new Color(250/rate, 203/rate, 71/rate,1), new Color(247/rate, 197/rate, 0/rate,1), 
								new Color(255/rate,100/rate,80/rate,1),new Color(206/rate,220/rate,221/rate,1)};
	
	private Color[] Tutorial = 	new Color[]{
								new Color(170/rate, 209/rate, 216/rate,1), new Color(233/rate, 127/rate, 2/rate,1), 
								new Color(220/rate,221/rate,239/rate,1),new Color(222/rate,154/rate,79/rate,1)};
	
	private Color[] Store = 	new Color[]{
								new Color(169/rate, 7/rate, 30/rate,1), new Color(188/rate, 22/rate, 83/rate,1), 
								new Color(171/rate,67/rate,102/rate,1),new Color(240/rate,101/rate,98/rate,1)};
	
	private Color[] ScoreBar = 	new Color[]{
								new Color(223/rate, 181/rate, 63/rate,1),new Color(221/rate, 177/rate, 0,1) , 
								new Color(204/rate,79/rate,65/rate,1),new Color(158/rate,167/rate,168/rate,1)};
	
	private Color[] StoreBar = 	new Color[]{
								new Color(151/rate, 6/rate, 26/rate,1), new Color(163/rate, 19/rate, 72/rate,1), 
								new Color(119/rate,47/rate,73/rate,1),new Color(188/rate,79/rate,77/rate,1)};
	
	private Color[][] COLORS = new Color[][]{Logo, Play, Score, Tutorial, Store, ScoreBar, StoreBar};
	
	
	// Returns a set of color given by which item and theme is supposed to be colored
	public Color accessColor(int aColor,int aTheme)
	{
		return COLORS[aColor][aTheme];
	}
	
	
}
