package com.blokk.game;

public class Utils {
	// Use: int[] tryType = integerType(i)
	// After: Returns array [0,0] if i==1, [0,1] if i==2,
	//        [1,0] if i==3 and [1,1] if i==4
	public static int[] integerType(int i){
		int[] result = new int[2];
		switch(i){
		case 0:
			return result;
		case 1:
			result[1] = 1;
			return result;
		case 2:
			result[0] = 1;
			return result;
		case 3:
			result[0] = 1;
			result[1] = 1;
			return result;
		}
		return result;
	}

	// Use: boolean type = toBoolean(i)
	// After: The boolean variable type is True if i == 1, else False.
	public static boolean toBoolean(int i){
		return i == 1;
	}


}
