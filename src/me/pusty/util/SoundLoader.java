package me.pusty.util;

import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.files.FileHandle;

public class SoundLoader {

	 HashMap<String, SoundFile> list;
     boolean muted=false;
     static SoundLoader instance = null;
     /**Creates a new SoundLoader which is a handler for sounds*/
	public SoundLoader() {
		list = new HashMap<String, SoundFile>();
		SoundLoader.instance = this;
	}
	/**Mutes the handler*/
	public void mute(){muted=true;}
	/**Unmutes the handler*/
	public void unmute(){muted=false;}

	/**Adds a sound to the handler*/
	public void addSound(String name, FileHandle file,boolean l) {
		list.put(name, new SoundFile(file,l));
	}
	/**Closes all sound files*/
	public static void close() {
		if(instance!=null)
		for(Entry<String,SoundFile> sound:instance.list.entrySet()) {
			sound.getValue().close();
		}
	}
	/**Get SoundFile with name "name"*/
	public SoundFile getSound(String name){
		return list.get(name);
	}
	/**Plays Clip*/
	public synchronized  void playClip(String name){
	     if(list.get(name) != null){
	    	 if(!muted)
	    		 list.get(name).start();
	   
	     }
	}
	
	/**Plays Clip piched*/
	public synchronized  void playClip(String name,float pitch){
	     if(list.get(name) != null){
	    	 if(!muted)
	    		 list.get(name).start(pitch);
	   
	     }
	}

}
