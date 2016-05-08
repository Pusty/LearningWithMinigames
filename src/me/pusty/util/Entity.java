package me.pusty.util;

import me.pusty.util.AbstractGameClass;
import me.pusty.util.PixelLocation;
import me.pusty.util.RawAnimation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


/**Entity Class*/
public class Entity{
	//Its location
	PixelLocation location;
	//Current Animation
	RawAnimation animation=null;
	
	/**Inits entity with a position*/
	public Entity(int x,int y){
		location=new PixelLocation(x,y);
	}
	/**Abstract function which gets called each tick*/
	public void tickTraveled(AbstractGameClass game,float delta) {
		
	}

	/**Returns Location*/
	public PixelLocation getLocation(){
		return location;
	}
	/**Returns X Pos*/
	public int getX(){
		return location.getX();
	}
	/**Returns Y Pos*/
	public int getY(){
		return location.getY();
	}
	/**Renders Entity*/
	public void render(AbstractGameClass e,SpriteBatch g) {
		try {
			//Image from memory
			TextureRegion image = e.getImageHandler().getImage(getImage());
			//Offset Image if necessary
			g.draw(image,getX(),getY());
		} catch(Exception ex) {
			//Error message if Image not found
			System.err.println(getImage());
		}
	}
	//Current frame from animations
	protected String img=null;
	
	/**Returns texture name*/
	public String getTextureName() {
		return "empty";
	}
	/**The image name that gets used in render(e,g)*/
	public String getImage() {
		if(img!=null)
			return img;
		return getTextureName();
	}
	/**Sets the animation to a copy of the animation a*/
	public void setAnimation(RawAnimation a){
		if(a!=null)
			animation=a.getWorkCopy();
		else
			animation=null;
	}
	/** returns true of animation==null*/
	public boolean isAnimationNull(){
		return animation==null;
	}
	/**render tick (too add ticks to animations)*/
	public void renderTick(AbstractGameClass engine,int ind){
		if(animation!=null) {
			String img = animation.getFrame();
			if(img!=null)
			setImage(img);
			else
			{setAnimation(null);setDefault();}
		}else if(img!=null)
			setDefault();
	}
	/**Returns image to default*/
	public void setDefault(){
		img=null;
	}
	/**Sets image*/
	public void setImage(String i) {
		img = i;
	}
	
	
}
