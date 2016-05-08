package me.pusty.util;

import java.util.HashMap;

/**Animation Handler*/
public class RawAnimationHandler {
	HashMap<String,RawAnimation> animations;
	/**New Animation Handler*/
	public RawAnimationHandler() {
		animations = new HashMap<String,RawAnimation>();
	}
	/**Get Animation HashMap*/
	public HashMap<String,RawAnimation> getAnimations() {
		return animations;
	}
	/**Adds a animation to the handler*/
	public void addAnimation(String s,RawAnimation a){
		animations.put(s, a);
	}
	/**Gets the animation from the handler*/
	public RawAnimation getAnimation(String s){
		if(!animations.containsKey(s))
			return null;
		return animations.get(s);
	}
}
