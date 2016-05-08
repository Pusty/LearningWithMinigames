package me.pusty.util;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public class SoundFile {

	Sound sound;
	
	boolean loop;
	/**New SoundFile*/
	public SoundFile(FileHandle file, boolean loop) {
		this.loop=loop;
		sound = Gdx.audio.newSound(file);
	}
	/**Plays Sounds*/
	public  void start() {
		float l = 1f;
		l = l * 0.75f;
		if(loop)
			sound.loop(l);
		else
			sound.play(l);
	}
	
	/**Plays Sounds piched*/
	public  void start(float pitch) {
		float l = 1f;
		l = l * 0.75f;
		if(loop)
			sound.loop(l,pitch,0f);
		else
			sound.play(l,pitch,0f);
	}
	
	/**Disposes of the Sound File*/
	public void close() {
		sound.stop();
		sound.dispose();
	}

}
