package states;

import junit.framework.TestCase;

import org.junit.Test;

public class TestWhichColumn extends TestCase {
	/** Testcase for the whichColumn function.
	 
	   Authors: Þorsteinn Örn Gunnarsson & Hlynur Davíð Hlynsson
	*/
	  @Test
	  public void testHighestValue() {
		final int[] warning = {1, 2, 5, 4, 7, 1, 2, 3, 0, 2, 5, 6, 1};
		final int rows = 13;
	    assertEquals( "The highest value in the array in 7.", 
	                  7, Playstate.whichColumn(warning, rows)); 
	  }
}
