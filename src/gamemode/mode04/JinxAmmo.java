package gamemode.mode04;

import me.pusty.util.AbstractGameClass;
import me.pusty.util.Entity;
import me.pusty.util.PixelLocation;
/**The bullet (Jinxs auto attacks*/
public class JinxAmmo extends Entity {

	Minion entity=null;
	public JinxAmmo(int x, int z,Minion goal) {
		super(x, z);
		entity=goal;
	}
	/**Sets the goal this bullet aims for*/
	public Minion getGoal() {
		return entity;
	}
	/**Returns object texture*/
	public String getTextureName() {
		return "jinx_bullet";
	}
	boolean canBeRemoved=false;
	/**Garbage Collection*/
	public boolean canRemove() {
		return canBeRemoved;
	}
	boolean addScore=false;
	/**Tick Travel Function*/
	public void tickTraveled(AbstractGameClass game,float delta) {
		PixelLocation addition = new PixelLocation(0,0);
		//Calculates where to fly to
		if(entity!=null && !entity.canRemove()) {
			float dis = PixelLocation.getDistance(getLocation(), entity.getLocation());
			float x = entity.getLocation().x-getLocation().x;
			float y = entity.getLocation().y-getLocation().y;
			float calcX = x>0?Math.max(4,x/dis):Math.min(-4, x/dis) * 1;
			float calcY = y>0?Math.max(4,y/dis):Math.min(-4, y/dis) * 1;
			if(x==0)calcX=0;
			if(y==0)calcY=0;
			addition = new PixelLocation((int)calcX,(int)calcY);
		}else
			//remove if the entity doesn't exist or should be removed
			canBeRemoved=true;
		
		//removes it if it hits
		if(!canBeRemoved) {
			float dis = PixelLocation.getDistance(getLocation(), entity.getLocation());
			if(dis < 8f) {
				canBeRemoved=true;
				//Damage the goal
				entity.health=entity.health-25;
				if(entity.health<=0)
					addScore=true;
			}
		}
		
		PixelLocation newL = getLocation().add(addition);
		getLocation().set(newL);
		if(newL.getY()<-32 || newL.getX()>128 || newL.getX()<-32 || newL.getY()>128+64)
			canBeRemoved=true;
	}

}
