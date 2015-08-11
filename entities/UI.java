package entities;

public class UI {
	public float x;
	public float y;
	public float width;
	public float height;

	/**
	 *  Constructs a user UI
	 * @param float newX is the UI’s lower-left x co-ordinate
	 * @param float newY is the UI’s upper-left y co-ordinate
	 * @param float newWidth is the UI’s width
	 * @param float newHeight is the UI’s height
	 */
	public UI(float newX, float newY, float newWidth, float newHeight) {
		x = newX;
		y = newY;
		width = newWidth;
		height = newHeight;
	}
}
