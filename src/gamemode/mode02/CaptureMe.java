package gamemode.mode02;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import me.pusty.util.AbstractGameClass;
import me.pusty.util.Entity;
import me.pusty.util.PixelLocation;

/**Minion that comes from the left or right*/
public class CaptureMe extends Entity {

	boolean dir=false;
	public CaptureMe(int x, int z,boolean direction) {
		super(x, z);
		dir = direction;
	}
	/**Returns object texture*/
	public String getTextureName() {
		return "zzrot_"+(ticks/10);
	}
	boolean canBeRemoved=false;
	/**Garbage Collection*/
	public boolean canRemove() {
		return canBeRemoved;
	}
	boolean naturRem=false;
	/**Returns if this entity ends the game*/
	public boolean gameOver() {
		return naturRem;
	}
	int ticks;
	Random random = new Random();
	/**Tick Travel Function*/
	public void tickTraveled(AbstractGameClass game,float delta) {
		ticks++;
		if(ticks>=20)
			ticks=0;
		PixelLocation addition = new PixelLocation((dir?1:-1)*(random.nextInt(3)+1),0);
		
		PixelLocation newL = getLocation().add(addition);
		getLocation().set(newL);
		
		if(newL.getY()<-32 || newL.getX()>128 || newL.getX()<-32 || newL.getY()>128+64) {
			canBeRemoved=true;
			naturRem=true;
		}
	}
	/**Overrides the render function to flip it if the entity comes from a different direction*/
	public void render(AbstractGameClass e,SpriteBatch g) {
		try {
			TextureRegion image = e.getImageHandler().getImage(getImage());
			PixelLocation move = new PixelLocation(getX(), getY());
			if(!dir)
			image.flip(true, false);
	
			g.draw(image,move.getX(),move.getY());
			
			if(!dir)
			image.flip(true, false);
			
		} catch(Exception ex) { System.err.println(getImage()); }
	}

}
