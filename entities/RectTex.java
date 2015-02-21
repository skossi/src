package entities;

import com.badlogic.gdx.graphics.Texture;
//Class by Óttar Guðmundsson
//Written 14.11.2014
//Creates a new RecTex which is a nickname for a rectangle with texture
public class RectTex {

	public float x,y, width, height;
	public Texture tex;
	//Creates a new Rectangle that holds its own position and size. Also contains it own Texture
	public RectTex(float xCons, float yCons, float widthCons, float heightCons, Texture texCons)
	{
		x = xCons;
		y = yCons;
		width = widthCons;
		height = heightCons;
		tex = texCons;
	}
}
