package gamemode.mode02;


import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.pusty.game.ticks.InformationScreen;
import me.pusty.util.AbstractGameClass;
import me.pusty.util.PixelLocation;
import me.pusty.util.RenderUtil;
import me.pusty.util.Tick;
/**1st actual game mode for Thresh*/
public class GameMode02 extends Tick{

	public GameMode02(AbstractGameClass engine) {
		super(engine);
		
	}

	Thresh thresh = new Thresh(64-16,8);
	
	CaptureMe[] minion = new CaptureMe[3];
	Hook[] hooks = new Hook[3];
	/**Adds a minion*/
	public void addMinion(CaptureMe d) {
		for(int index=0;index<minion.length;index++) {
			if(minion[index]==null) {
				minion[index]=d;
				return;
			}
		}
	}
	/**Checks minions and updates them*/
	public void checkMinion(AbstractGameClass g,float d) {
		for(int index=0;index<minion.length;index++) {
			if(minion[index]!=null) {
				if(minion[index].gameOver()) {
					gameOver=true;
					g.getSound().playClip("minion");
				}
				if(minion[index].canRemove())
					minion[index]=null;
				else
					minion[index].tickTraveled(g, d);
			}
		}
	}
	
	
	/**Add a hook*/
	public void addHook(Hook d) {
		for(int index=0;index<hooks.length;index++) {
			if(hooks[index]==null) {
				hooks[index]=d;
				return;
			}
		}
	}
	/**Checks hook (if it hits something) and removes them*/
	public void checkHooks(AbstractGameClass g,float d) {
		for(int index=0;index<hooks.length;index++) {
			if(hooks[index]!=null) {
				if(hooks[index].canRemove())
					hooks[index]=null;
				else {
					hooks[index].tickTraveled(g, d);
					
					for(int i2=0;i2<minion.length;i2++) {
						if(minion[i2]!=null) {
							if(minion[i2].getX()+8>=hooks[index].getX() && minion[i2].getX()<=hooks[index].getX()+8 && minion[i2].getY()+8>=hooks[index].getY() && minion[i2].getY()<=hooks[index].getY()+8) {
								minion[i2].canBeRemoved=true;
								hitted++;
								engine.getSound().playClip("end");
							}
						}
					}
					
				}
			}
		}
	}
	
	
	/**Key Event (q to hook, esc to get back to the menu)*/
	@Override
	public boolean keyEvent(AbstractGameClass e,int type,int keycode) {
		switch(keycode) {
		case Keys.Q:
			if(type==0) {
				thresh.q(this);
			}
			return true;
		case Keys.ESCAPE:
			if(type==0) {
				gameOver=true;
			}
			return true;
		}
		
		return false;
	}
	
	
	/**Gets called on window opening*/
	@Override
	public void show() {
		
	}


	boolean gameOver=false;
	float timeIn=0;

	Random r = new Random();
	float spawnTime=1;
	
	int hitted = 0;
	/**Game Tick function*/
	@Override
	public void tick(AbstractGameClass e, float delta) {
		timeIn=timeIn+(1f/30f);

		//Adds a minion either on the left or the right side
		if(spawnTime>0)
			spawnTime=spawnTime-0.01f;
		else {
			boolean dir = r.nextBoolean();
			addMinion(new CaptureMe(dir?-32:128,48,dir));
			spawnTime=r.nextFloat()+0.5f;

		}

		//Travel tick for Thresh
		thresh.tickTraveled(e, delta);

		//Checks minions and hooks
		checkMinion(e,delta);
		checkHooks(e,delta);
		//Back to the information screen
		if(gameOver) {
			InformationScreen ic = new InformationScreen(e,2,false,(int)Math.min(4,(hitted/4)),hitted);
			e.setScreen(ic);
		    Gdx.input.setInputProcessor(ic);
		}
	}
		
	
	/**Qs if the mouse is pressed*/
	@Override
	public void mouse(AbstractGameClass engine, int screenX, int screenY,
			int pointer, int button) {
		thresh.q(this);
	}
	/**Render Function*/
	@Override
	public void render(AbstractGameClass e, float delta) {
		SpriteBatch batch  = e.getBatch();
		batch.setColor(1,1,1,1);
		//Draws a background
		batch.draw(e.getImageHandler().getImage("background_01"),0,0,e.getCamera().viewportWidth,e.getCamera().viewportHeight);
		batch.setColor(1,1,1,1);
		
		//Renders Minions and updates them
		for(int index=0;index<minion.length;index++) {
			if(minion[index]!=null) {
				minion[index].render(e, batch);
				minion[index].renderTick(e, index);
			}
		}
		//Renders Hook and updates it
		for(int index=0;index<hooks.length;index++) {
			if(hooks[index]!=null) {
				hooks[index].render(e, batch);
				hooks[index].renderTick(e, index);
			}
		}
		//Renders Thresh and updates him
		thresh.render(e, batch);
		thresh.renderTick(e, -1);
		
		batch.setColor(0,0,0,1);
		//Shows the current cs
		RenderUtil.renderText(e, batch, new PixelLocation(8,18), hitted+"cs");
		batch.setColor(1,1,1,1);
		
	
	}


}
