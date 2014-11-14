package states;

import com.badlogic.gdx.graphics.Texture;

public class RectTex {

	public float x,y, width, height;
	public Texture tex;
	
	public RectTex(float xCons, float yCons, float widthCons, float heightCons, Texture texCons)
	{
		x = xCons;
		y = yCons;
		width = widthCons;
		height = heightCons;
		tex = texCons;
	}
}
