package gamemode.mode04;

import me.pusty.util.AbstractGameClass;
import me.pusty.util.EntityLiving;

/**Player Class for Game mode 4*/
public class Jinx extends EntityLiving{

	public Jinx(int x, int z) {
		super(x, z);
	}
	/**Returns player texture*/
	public String getTextureName() {
		return "jinx";
	}
	/**Returns player moving texture*/
	public String getMovingTexture() {
		return null;
	}
	/**Tick Travel Function*/
	public void tickTraveled(AbstractGameClass game,float delta) {
		super.tickTraveled(game, delta);
		//Idle Animation if no animation is played
		if(isAnimationNull())
			setAnimation(game.getAnimationHandler().getAnimation("jinx_idle"));
	}

}
