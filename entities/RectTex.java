package entities;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.graphics.Texture;
//Class by Óttar Guðmundsson
//Written 14.11.2014
//Creates a new RecTex which is a nickname for a rectangle with texture
public class RectTex {

	private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	
	public float x,y, width, height;
	public Texture tex;
	public String display;
	public int disX,disY;
	
	//Creates a new Rectangle that holds its own position and size. Also contains it own Texture
	public RectTex(float xCons, float yCons, float widthCons, float heightCons, Texture texCons, String displayCons)
	{
		x = xCons;
		y = yCons;
		width = widthCons;
		height = heightCons;
		tex = texCons;
		display = displayCons;
		disX = Math.round(xCons)+100 - calculateXPos(displayCons);//83 + Math.round(xCons) - calculateXPos(displayCons);
		disY = 72+Math.round(yCons);
	}
	
	private int calculateXPos(String aText)
	{
		int xPos = aText.length() * 10;		
		return xPos;
	}
	
	public void pressedEffect()
	{
		int adjustment = 5;
		this.x += adjustment;
		this.y += adjustment;
		ResetThread resetter=new ResetThread(adjustment,this);
		scheduler.schedule(resetter, 35, TimeUnit.MILLISECONDS);
	}
	
	private class ResetThread extends Thread
	{
		int adjustment;
		RectTex rekt;
		public ResetThread(int ad,RectTex r)
		{
			super();
			this.adjustment=ad;
			this.rekt=r;
		}
		
		public void run()
		{
			rekt.x=rekt.x-adjustment;
			rekt.y=rekt.y-adjustment;
		}
	}
	
}
