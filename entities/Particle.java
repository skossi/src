package entities;

//Class by Ottar Gudmundsson
//Written 28.3.2015
//A particle that only holds it x and y positions as well as increasing its speed.
public class Particle {

	public int x; public float y;
	private float speed = 1000;
	private float accel = 2f;
	private Particle p;
	//public int indexPosition;
	
	public Particle(int xPos,int yPos, int index)
	{
		p = this;
		x = xPos;
		y = yPos;
		//this.indexPosition = index;
	}
	
	public void update(float dy)
	{
		speed += accel;
		y -= speed * dy;
		
		if(y-20 < 0) p = null;
	}
}
