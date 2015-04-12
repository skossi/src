package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


//Class by Ottar Gudmundsson
//Written 28.3.2015
//A particle emitter that spawns x many particles as long as it is being thrusted up
//TODO : Need to set it as null when it is not being thrusted.
//TODO : Call upgrade() when double-swap has been made.
public class ParticleEmitter {

	private float timer;
	private float timerThreshold = .05f;
	private Texture drop;
	public int x,y;
	private int size = 68;
	private int index;
	private int maxIndex = 150;
	private float originalTimer;
	
	private Particle[] particleBuffer = new Particle[maxIndex];
	
	public ParticleEmitter(int xPos, int yPos, Texture dropTexture, float speed)
	{
		index = 0;
		spawnNewParticle();
		
		x = xPos;
		y = yPos; 
		
		timerThreshold += Math.random() * .15f;
		
		drop = dropTexture;
		
		//originalSpeed = (int)speed;
		
	}
	
	public void update(float dy, float speed)
	{
		timer += dy;
		//originalTimer += dy;
		
		//float thresHold = timerThreshold+originalTimer / 20;
		
		if(timer >= timerThreshold && speed > 0)spawnNewParticle();
		for(int i = 0; i < index; i++)
		{
			particleBuffer[i].update(dy);
			//TODO: Destroy particles.
			//if(particleBuffer[i].y < 0)particleBuffer[i] = null;
		}	
	}
	
	public void upgrade()
	{
		Particle[] holder = new Particle[maxIndex];
		particleBuffer = new Particle[maxIndex*2];
		for(int i = 0; i < maxIndex; i++)
		{
			particleBuffer[i] = holder[i];
		}
		timerThreshold /= 2;
		System.out.println(timerThreshold);
	}
	
	private void spawnNewParticle()
	{
		timer = 0;
		timerThreshold += .005f;
		if(index > particleBuffer.length) return;
		int newX =  x + (int)(Math.random() * size);
		particleBuffer[index] = new Particle(newX, y,index);
		index++;
	}
	
	public void drawParticle(SpriteBatch b)
	{
		for(int i = 0; i < index; i++)
			//particleBuffer[i].y > 0 && 
			if(particleBuffer[i] != null)
				b.draw(drop,particleBuffer[i].x, particleBuffer[i].y);
		
	}
	
}
