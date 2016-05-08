package gamemode.mode01;

import java.util.Random;

import me.pusty.util.AbstractGameClass;
import me.pusty.util.EntityLiving;
import me.pusty.util.PixelLocation;
/**Player Class for Game mode 1*/
public class Vayne extends EntityLiving{

	public Vayne(int x, int z) {
		super(x, z);
	}
	boolean canDodge=true;
	float dodgeTime=0f;
	Random random = new Random();
	/**Tumbles on q press*/
	public void q(AbstractGameClass e) {
		if(canDodge) {
			canDodge=false;
			dodgeTime=0.5f;
			//plays the animation on q press
			setAnimation(e.getAnimationHandler().getAnimation("vayne_tumble"));
			e.getSound().playClip("death",0.5f+random.nextFloat());
		}
	}
	/**Returns player texture*/
	public String getTextureName() {
		return "vayne";
	}
	/**Returns player moving texture*/
	public String getMovingTexture() {
		return null;
	}
	/**Tick Travel Function*/
	public void tickTraveled(AbstractGameClass game,float delta) {
		super.tickTraveled(game, delta);
		//if no animation is played: play idle animation
		if(isAnimationNull())
			setAnimation(game.getAnimationHandler().getAnimation("vayne_idle"));
		if(dodgeTime>0) {
			//Tumbles for "dodgeTime"
			dodgeTime=dodgeTime-0.04f;
				PixelLocation newL = getLocation().add(new PixelLocation((int)getAddLocation(false).x,(int)getAddLocation(false).y));
				if(newL.getX() > 0 && newL.getX()<128-32)
				getLocation().set(newL);
			
		}else
			canDodge=true;
		
	}

}
