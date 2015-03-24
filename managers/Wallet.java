package managers;

import com.badlogic.gdx.Preferences;

public class Wallet {

	private Preferences prefs;
	
	private String[] storeTabs = new String[]{"Powers", "Levels", "Themes","Extras"};
	private String[] itemsAvailable = new String[]{"item_1","item_2","item_3","item_4"};
	
	//THESE MUST BE HARDCODED AND DECIDED
	private int[] powerPrice = new int[]{300, 300, 300, 300};
	private int[] levelPrice = new int[]{200, 300, 400, 500};
	private int[] themePrice = new int[]{0, 250, 400, 800};
	private int[] extraPrice = new int[]{800, 700, 600, 900};
	public int[][] price = new int[][]{powerPrice,levelPrice,themePrice,extraPrice};
	
	public boolean[][] owned = new boolean[storeTabs.length][itemsAvailable.length];
			
	public Wallet(Preferences p)
	{
		prefs = p;
		checkOwnedItems();
		//initiatePriceWallet();
	}
	
	public boolean canBuy(int select, int item, int currency)
	{
		if(currency >= price[select][item]) return true;
		else return false;
	}

	
	public int aquireAsset(int selected, int item)
	{
		owned[selected][item] = true;
		prefs.putBoolean(storeTabs[selected]+itemsAvailable[item], true);
		prefs.flush();
		return price[selected][item];
	}
	
	private void checkOwnedItems()
	{
		for(int i = 0; i < 4; i++)for(int j = 0; j < 4; j++)
			owned[i][j] =prefs.getBoolean(storeTabs[i]+itemsAvailable[j]);		
	}
}
