package gamemode.mode04;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.pusty.util.AbstractGameClass;
import me.pusty.util.Entity;
import me.pusty.util.PixelLocation;
/**Minion Class for Jinx*/
public class Minion extends Entity {

	int health=100;
	public Minion(int x, int z) {
		super(x, z);
		Random random = new Random();
		health = 75 + random.nextInt(50);
	}
	/**Texture of the zzrot minion*/
	public String getTextureName() {
		return "zzrot_"+(ticks/10);
	}
	boolean canBeRemoved=false;
	/**Garbage Collection*/
	public boolean canRemove() {
		return canBeRemoved;
	}
	/**Overrides render function to add a health bar*/
	public void render(AbstractGameClass e,SpriteBatch g) {
		super.render(e,g);
		g.draw(e.getImageHandler().getImage("health_"+Math.min(9,Math.max(((int)health/10),0))),getX(),getY()+12);
	}
	int ticks;
	/**Tick Travel Function*/
	public void tickTraveled(AbstractGameClass game,float delta) {
		ticks++;
		if(ticks>=20)
			ticks=0;
		//removes health from it per tick
		health--;
		//Makes it move one unit to the right per tick
		PixelLocation addition = new PixelLocation(1,0);
		if(health<=0)
			canBeRemoved=true;
	
		PixelLocation newL = getLocation().add(addition);
		getLocation().set(newL);
		if(newL.getY()<-32 || newL.getX()>128 || newL.getX()<-32 || newL.getY()>128+64)
			canBeRemoved=true;
	}

}
