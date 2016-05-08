package me.pusty.util;


import me.pusty.util.AbstractGameClass;
import me.pusty.util.PixelLocation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**EntityLiving Class*/
public class EntityLiving extends Entity {
	//Current Direction
	int direction = 0;
	//Last Direction
	int lastDirection = 1;
	
	public EntityLiving(int x, int z) {
		super(x, z);
	}

	

	/**Returns Last Direction*/
	public int getLastDirection() {
		return lastDirection;
	}
	/**Returns Direction*/
	public int getDirection() {
		return direction;
	}

	/**Sets Direction*/
	public void setDirection(int d) {
		direction = d;
		if(direction!=0)
			lastDirection=direction;
	}
	/**Sets Last Direction*/
	public void setLastDirection(int d) {
			lastDirection=d;
	}
	/**Returns Moving Texture*/
	public String getMovingTexture() {
		return null;
	}
	/**Has directions?*/
	public boolean hasDirections() { return false; }
	/**Returns the image*/
	public String getImage() {
		if(img!=null)
			return img;
		if(getDirection() == 0 || getMovingTexture() == null) {
				return getTextureName()+"_"+(this.getLastDirection()-1);
		}else if(getDirection() != 0) {
			float percent = ((float)getSpeed()-traveled)/getSpeed();
			int frame = Math.min(3,(int)(percent*4))  ; // frame = process * framecount
			return getMovingTexture()+"_"+frame;
		}
		return getTextureName();
	}
	/**Returns Speed*/
	public float getSpeed() {
		return 10f;
	}
	
	/**Render Function*/
	public void render(AbstractGameClass e,SpriteBatch g) {
		try {
			TextureRegion image = e.getImageHandler().getImage(getImage());
			PixelLocation move = new PixelLocation(getX(), getY());
			if(getLastDirection()==2)
			image.flip(true, false);
	
			g.draw(image,move.getX(),move.getY());
			
			if(getLastDirection()==2)
			image.flip(true, false);
			
		} catch(Exception ex) { System.err.println(getImage()); }
	}
	
	/**Render Tick*/
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
	/**Returns add location*/
	public PixelLocation getAddLocation(boolean tick) {		
		PixelLocation location = new PixelLocation(0,0);
		if(direction==1)
			location = location.add(new PixelLocation(1,0));
		else if(direction==2)
			location = location.add(new PixelLocation(-1,0));	
		if(canMoveVertical() && directionVertical!=0)
			location = location.add(new PixelLocation(0,1*directionVertical));
			
		return location;
	}
	/**Can move Vertical?*/
	public boolean canMoveVertical() {
		return false;
	}
	/**Direction Vertical Stuff*/
	int directionVertical=0;
	public void up() {
		directionVertical=1;
	}
	public void down() {
		directionVertical=-1;
	}
	public void setDirectionVertical(int i) {
		directionVertical=i;
	}
	public int getDirectionVertical() {
		return directionVertical;
	}
	
	
	
    /**Set Direction Stuff*/
	boolean setDirection = false;
	int setDirectionInt = 0;
	boolean setDirectionNull = false;
	/**Adds direction to the queue*/
	public void queueDirection(int d) {
		setDirection = true;
		if(d == 0) {
			setDirectionNull = true;
		}else {
			setDirectionNull = false;
			setDirectionInt = d;
		}
		
	}
	//Traveled Variable for animations
	protected float traveled = 0;
	/**Returns Traveled*/
	public float getTraveled() {
		return traveled;
	}
	/**Travel Tick*/
	public void tickTraveled(AbstractGameClass game,float delta) {
		traveled=traveled-(1f/30f);
		if(traveled<=0)
			traveled=getSpeed();
		if(setDirection || setDirectionNull) {
			if(setDirectionNull && setDirectionInt==0) 
				setDirection(0);
			else
				setDirection(setDirectionInt);
			setDirectionInt = 0;
			setDirection = false;
			setDirectionNull = false;
		}
	}

	
}
