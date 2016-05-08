package gamemode.mode03;

import me.pusty.util.AbstractGameClass;

import me.pusty.util.EntityLiving;
import me.pusty.util.PixelLocation;

/**Player Class for Game mode 3*/
public class LeeSin extends EntityLiving{

	public LeeSin(int x, int z) {
		super(x, z);
	}

	/**Ward Jump on w press (if there is a ward on the mouse position)*/
	public void w(GameMode03 gm,PixelLocation m) {
		for(int index=0;index<gm.ward.length;index++) {
			if(gm.ward[index]!=null) {
				if(gm.ward[index].getX()+6<=m.getX() && gm.ward[index].getX()+26>=m.getX() && gm.ward[index].getY()<=m.getY() && gm.ward[index].getY()+32>=m.getY()) {
					gm.move(gm.ward[index].getX()-getLocation().getX());
					gm.E().getSound().playClip("death",2f);
				}
					
			}
		}
	}
	/**Places a ward on position m)*/
	public void ward(GameMode03 gm,PixelLocation m) {
		gm.addWard(new Ward(m.getX()-16,16));
	}
	/**Returns player texture*/
	public String getTextureName() {
		return "leesin";
	}
	
	boolean flying=false;
	
	/**Returns player moving texture*/
	public String getMovingTexture() {
		return null;
	}
	/**Tick Travel Function*/
	public void tickTraveled(AbstractGameClass game,float delta) {
		super.tickTraveled(game, delta);
		//If running: normal idle animation
		if(isAnimationNull() && !flying)
			setAnimation(game.getAnimationHandler().getAnimation("leesin_idle"));
		//if flying set the jump animation
		if(flying)
				setAnimation(game.getAnimationHandler().getAnimation("leesin_jump"));
	}

}
