package gamemode.mode02;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import me.pusty.util.AbstractGameClass;
import me.pusty.util.Entity;
import me.pusty.util.PixelLocation;
/**Hook Class for Thresh*/
public class Hook extends Entity {
	
	public Hook(int x, int z) {
		super(x, z);
	}
	/**Returns object texture*/
	public String getTextureName() {
		return "thresh_hook_0";
	}
	boolean canBeRemoved=false;
	/**Garbage Collection*/
	public boolean canRemove() {
		return canBeRemoved;
	}
	int flew=0;
	/**Tick Travel Function*/
	public void tickTraveled(AbstractGameClass game,float delta) {
		//Adds one unit on the y axis
		PixelLocation addition = new PixelLocation(0,1);
		PixelLocation newL = getLocation().add(addition);
		flew=flew+1;
		getLocation().set(newL);
		
		if(newL.getY()<-32 || newL.getX()>128 || newL.getX()<-32 || newL.getY()>64)
			canBeRemoved=true;
	}
	
	/**Overrides Render function to make the hook come from thresh*/
	public void render(AbstractGameClass e,SpriteBatch g) {
		try {
			for(int i=0;i<flew/32;i++){
			TextureRegion image = e.getImageHandler().getImage("thresh_hook_0");
			PixelLocation move = new PixelLocation(getX(), getY()-(32*(i+1)));
			g.draw(image,move.getX(),move.getY());
			}
			if(flew > 7 && flew - (flew/32)*32 != 0){
			TextureRegion image = e.getImageHandler().getImage("thresh_hook_0");
			PixelLocation move = new PixelLocation(getX(), getY()-(flew - (flew/32)));
			g.draw(image,move.getX(),move.getY());
			}
			
			{
			TextureRegion image = e.getImageHandler().getImage("thresh_hook_1");
			PixelLocation move = new PixelLocation(getX(), getY());
			g.draw(image,move.getX(),move.getY());
			}
			
			
		} catch(Exception ex) { System.err.println(getImage()); }
	}

}
