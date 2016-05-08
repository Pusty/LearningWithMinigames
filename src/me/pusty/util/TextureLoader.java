package me.pusty.util;


import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.TextureRegion;




public class TextureLoader {
	HashMap<String, TextureRegion> list = new HashMap<String, TextureRegion>();

	/**TextureLoader is handler for TextureRegions*/
	public TextureLoader() {
		list.clear();
	}

	/**Adds a image to the handler*/
	public void addImage(String name, TextureRegion img) {
		list.put(name, img);
	}
	/**Get the image with name "name", if not found returns null*/
	public TextureRegion getImage(String name) {
		if (list.containsKey(name))
			return list.get(name);
		else
			return null;
	}
	/**Removes image from the handler*/
	public TextureRegion removeImage(String name) {
		if (list.containsKey(name))
			return list.remove(name);
		else
			return null;
	}
	/**Get the HashMap filled with images*/
	public HashMap<String, TextureRegion> getList() {
		return list;
	}
	/**Sets the content of this handler*/
	public void setList(HashMap<String, TextureRegion> list) {
		this.list = list;
	}

	
}
