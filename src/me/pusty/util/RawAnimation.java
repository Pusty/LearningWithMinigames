package me.pusty.util;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.utils.JsonValue;

/**Raw Animation Class*/
public class RawAnimation {
	//Names of the images
	String[] imageNames;
	//Delays between images
	Integer[] frameDelay;
	//Current Frame
	int currentFrame;
	//Current Tick
	int currentTick;
	/**Creates a new Animation with "frames" frames */
	public RawAnimation(int frames){
		imageNames = new String[frames];
		frameDelay = new Integer[frames];
		currentFrame=0;
		currentTick=0;
	}
	/**Creates a new Animation from a JsonObject*/
	public RawAnimation(JsonValue obj) {
		if(obj.has("textures")) {
			List<String> list = new ArrayList<String>();
			for(JsonValue string:obj.get("textures"))
				list.add(string.asString());
			imageNames = list.toArray(new String[list.size()]);
		}else imageNames = null;
		
		if(obj.has("times")) {
			List<Integer> list = new ArrayList<Integer>();
			for(JsonValue num:obj.get("times"))
				list.add(num.asInt());
			frameDelay = list.toArray(new Integer[list.size()]);
		}else frameDelay = null;
		
		currentFrame=0;
		currentTick=0;
	}
	
	int full=0;
	/**Adds a frame to the animation*/
	public void addImage(String name,int delay){
		if(full>imageNames.length)return;
		imageNames[full]=name;
		frameDelay[full]=delay;
		full++;
	}
	/**Returns the Image to frame "frame"*/
	public String getImage(int frame){
		return imageNames[frame];
	}
	/**Returns the Delay to frame "frame"*/
	public int getDelay(int frame){
		return frameDelay[frame];
	}
	/**Returns ImageNames*/
	public String[] getImageNames() {
		return imageNames;
	}
	/**Returns ImageDelays*/
	public Integer[] getFrameDelays() {
		return frameDelay;
	}
	/**Returns the last frame*/
	public String getLastFrame() {
		return imageNames[currentFrame];
	}
	/**Returns the currentTick*/
	public int getCurrentTick() {
		return currentTick;
	}
	/**Returns the currentFrame*/
	public int getCurrentFrame() {
		return currentFrame;
	}
	/**Returns currentFrame*/
	public String getFrame(){
		String frame="";
		if(currentTick>=getLength())
			return null;
		int curtick = currentTick-getLength(currentFrame);
		if(curtick>frameDelay[currentFrame])
			currentFrame++;
		frame = imageNames[currentFrame];
		currentTick=currentTick+1;
		return frame;
	}
	/**Returns length (delays)*/
	public int getLength() {
		int length=0;
		for(int d:frameDelay)
			length=length+d;
		return length;
	}
	/**Returns length till a*/
	public int getLength(int a){
		int length=0;
		for(int i=0;i<a;i++){
			int d = frameDelay[i];
			length=length+d;
		}
		return length;
	}
	/**Makes a copy*/
	public RawAnimation getWorkCopy() {
		RawAnimation anim = new RawAnimation(imageNames.length);
		anim.imageNames = new String[imageNames.length];
		for(int index=0;index<imageNames.length;index++)
			anim.imageNames[index] = this.imageNames[index]+"";
		anim.frameDelay = new Integer[frameDelay.length];
		for(int index=0;index<frameDelay.length;index++)
			anim.frameDelay[index] = this.frameDelay[index]+0;
		anim.full=this.full;
		anim.currentFrame=0;
		anim.currentTick=0;
		return anim;
	}
}
