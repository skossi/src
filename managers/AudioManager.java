package managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

//Class by Ottar Gudmundsson
//Written 25.2.2015
//Audio manager that controls all sounds of the game.
public class AudioManager {

	private String location;
	private String asset;
	
	//Main themesound and audio effects
	private float Volume;
	private float FXVolume;
	private int activeMusic;
	private Music[] MusicThemes;
	private Sound[] SoundEffects;
	
	public static final int COLLECT = 0;
	public static final int SWAP = 1;
	public static final int MATCH = 2;
	public static final int MUTE = 3;
	public static final int PAUSE = 4;
	public static final int START = 5;
	public static final int LOST = 6;
	public static final int POWERDOWN3 = 7;
	public static final int PUSH = 10;
	public static final int UNLOCK = 11;
	public static final int ERROR = 12;
	
	
	public AudioManager(String aAsset, String aLoc)
	{
		asset = aAsset;
		location = aLoc;
		createSounds();
	}
	
	//Loads in proper sound effects and themeSongs
	private void createSounds()
	{
		//Sounds initiated
		MusicThemes = new Music[4];
		SoundEffects = new Sound[14];
		
		MusicThemes[0] = Gdx.audio.newMusic(Gdx.files.internal(asset+location+"ThemeLevel_1.wav"));
		MusicThemes[1] = Gdx.audio.newMusic(Gdx.files.internal(asset+location+"ThemeLevel_2.wav"));
		MusicThemes[2] = Gdx.audio.newMusic(Gdx.files.internal(asset+location+"ThemeLevel_3.wav"));
		MusicThemes[3] = Gdx.audio.newMusic(Gdx.files.internal(asset+location+"ThemeLevel_4.wav"));
		
		SoundEffects[COLLECT] = Gdx.audio.newSound(Gdx.files.internal("MutualSounds/Collect.wav"));
		SoundEffects[SWAP] = Gdx.audio.newSound(Gdx.files.internal("MutualSounds/SwapTile.wav"));
		SoundEffects[MATCH] = Gdx.audio.newSound(Gdx.files.internal("MutualSounds/Match.wav"));
		SoundEffects[MUTE] = Gdx.audio.newSound(Gdx.files.internal("MutualSounds/Mute.mp3"));
		SoundEffects[PAUSE] = Gdx.audio.newSound(Gdx.files.internal("MutualSounds/Pause.wav"));
		SoundEffects[START] = Gdx.audio.newSound(Gdx.files.internal("MutualSounds/StartGame.wav"));
		SoundEffects[LOST] = Gdx.audio.newSound(Gdx.files.internal("MutualSounds/Lost.wav"));
		SoundEffects[POWERDOWN3] = Gdx.audio.newSound(Gdx.files.internal("MutualSounds/Powerdown3.wav"));
		SoundEffects[PUSH] = Gdx.audio.newSound(Gdx.files.internal("MutualSounds/Push.wav"));
		SoundEffects[UNLOCK] = Gdx.audio.newSound(Gdx.files.internal("MutualSounds/Unlock.wav"));
		SoundEffects[ERROR] = Gdx.audio.newSound(Gdx.files.internal("MutualSounds/Error.wav"));
		
	    // start the playback of the background music immediately
		
		Volume = 1;
		FXVolume = 0.7f;
		resetThemeMusic();
	}
	
	//Plays requested sound effect
	public void soundEffect(int i)
	{
		SoundEffects[i].play(FXVolume);
	}
	
	//Raises the chosen themes intensity. 
	public void raiseThemeMusic()
	{	
		if(activeMusic > 2) return; // was 2, supposed to be 3?
		
		/*
		MusicThemes[activeMusic].setLooping(false);
		MusicThemes[activeMusic].stop();
    	activeMusic++;
    	MusicThemes[activeMusic].setLooping(true);
		MusicThemes[activeMusic].play();
		//MusicThemes[activeMusic].setPosition(soundPos);
	    MusicThemes[activeMusic].setVolume(Volume);
		*/
		
		MusicThemes[activeMusic].setLooping(false);
		MusicThemes[activeMusic].setOnCompletionListener(new Music.OnCompletionListener()
		{
	        public void onCompletion(Music aMusic)
	        {  
	        	MusicThemes[activeMusic].stop();
	        	activeMusic++;
	        	MusicThemes[activeMusic].setLooping(true);
	    		MusicThemes[activeMusic].play();
	    	    MusicThemes[activeMusic].setVolume(Volume);
	        }
	    });
	}
	
	public void stopMusic()
	{
		MusicThemes[activeMusic].setLooping(false);
		MusicThemes[activeMusic].stop();
	}
	
	//Sets the theme music back to the basic beat of the game.
	public void resetThemeMusic()
	{
		//gameOverSound.play();
		MusicThemes[activeMusic].stop();
		activeMusic = 0;
		MusicThemes[activeMusic].setLooping(true);
		MusicThemes[activeMusic].play();
	    MusicThemes[activeMusic].setVolume(Volume);   
	}
	
	public void setMainVolume(int aVolume)
	{
		Volume = aVolume;
	}
	
	//Mutes all active theme music/sound effects.
	public boolean mute(boolean aMute)
	{
		if(!aMute)
		{	
			Volume = 0;
			FXVolume = 0f;
			MusicThemes[activeMusic].setVolume(Volume);
		}
		else
		{
			Volume = 1;
			FXVolume = 0.7f;
			MusicThemes[activeMusic].setVolume(Volume);
		}
		aMute =! aMute;
		return aMute;
	}
}
