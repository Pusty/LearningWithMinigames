package gamemode.mode02;

import me.pusty.util.AbstractGameClass;
import me.pusty.util.EntityLiving;
/**Player Class for Game mode 2*/
public class Thresh extends EntityLiving{

	public Thresh(int x, int z) {
		super(x, z);
	}

	boolean hookOut=false;
	Hook hook = null;
	/**Hooks on q press*/
	public void q(GameMode02 gameMode02) {
		if(!hookOut) {
			hook = new Hook(getLocation().x+4,getLocation().y+8);
			gameMode02.addHook(hook);
			gameMode02.E().getSound().playClip("death");
			hookOut=true;
		}
	}

	/**Returns player texture*/
	public String getTextureName() {
		return "thresh";
	}
	
	/**Overrides the getImage() class. different texture if the hook is out*/
	public String getImage() {
		if(hookOut)
			return "thresh_2";
		return super.getImage();
	}
	/**Returns player moving texture*/
	public String getMovingTexture() {
		return null;
	}
	/**Tick Travel Function*/
	public void tickTraveled(AbstractGameClass game,float delta) {
		super.tickTraveled(game, delta);
		//HookOut false if the hook entity disappears
		if(hook!=null && hook.canRemove())
			hookOut=false;
		//idle if no hook is there and no animaion is played
		if(isAnimationNull() && !hookOut)
			setAnimation(game.getAnimationHandler().getAnimation("thresh_idle"));
	}

}
