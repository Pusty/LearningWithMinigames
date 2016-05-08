package gamemode.mode03;

import me.pusty.util.AbstractGameClass;
import me.pusty.util.Entity;

public class Wall extends Entity {

	public Wall(int x, int z) {
		super(x, z);
	}
	/**Returns object texture*/
	public String getTextureName() {
		return "wall";
	}
	boolean canBeRemoved=false;
	/**Garbage Collection*/
	public boolean canRemove() {
		return canBeRemoved;
	}
	/**Tick Travel Function*/
	public void tickTraveled(AbstractGameClass game,float delta) {
		
		if(getLocation().getY()<-32 || getLocation().getX()>128 || getLocation().getX()<-32 || getLocation().getY()>128+64) {
			canBeRemoved=true;
		}
	}

}
