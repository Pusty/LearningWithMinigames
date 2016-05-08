package gamemode.mode01;

import me.pusty.util.AbstractGameClass;
import me.pusty.util.Entity;
import me.pusty.util.PixelLocation;
/**Class for projectiles*/
public class Dodgable extends Entity {
	public Dodgable(int x, int z) {
		super(x, z);
	}
	/**Returns texture*/
	public String getTextureName() {
		return "binding_"+(ticks/10);
	}
	
	boolean canBeRemoved=false;
	/**Garbage Collection*/
	public boolean canRemove() {
		return canBeRemoved;
	}
	int ticks;
	/**Tick Travel Function*/
	public void tickTraveled(AbstractGameClass game,float delta) {
		ticks++;
		if(ticks>=20)
			ticks=0;
		//Lets it move 2 units per tick to the left
		PixelLocation addition = new PixelLocation(0,-2);
		PixelLocation newL = getLocation().add(addition);
		getLocation().set(newL);
		if(newL.getY()<-32 || newL.getX()>128 || newL.getX()<-32 || newL.getY()>128+64)
			canBeRemoved=true;
	}

}
