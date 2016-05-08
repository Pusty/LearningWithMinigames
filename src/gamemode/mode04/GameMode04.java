package gamemode.mode04;


import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.pusty.game.ticks.InformationScreen;
import me.pusty.util.AbstractGameClass;
import me.pusty.util.PixelLocation;
import me.pusty.util.RenderUtil;
import me.pusty.util.Tick;
/**1st actual game mode for Jinx*/
public class GameMode04 extends Tick{

	public GameMode04(AbstractGameClass engine) {
		super(engine);
		
	}

	Jinx jinx = new Jinx(64-16,8);
	Minion[] minion = new Minion[3];
	JinxAmmo[] ammo = new JinxAmmo[3];
	/**Adds a minion*/
	public void addMinion(Minion d) {
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
				if(minion[index].canRemove())
					minion[index]=null;
				else
					minion[index].tickTraveled(g, d);
			}
		}
	}
	/**Adds a bullet*/
	public void addAmmo(JinxAmmo d) {
		for(int index=0;index<ammo.length;index++) {
			if(ammo[index]==null) {
				ammo[index]=d;
				engine.getSound().playClip("death",1.5f);
				return;
			}
		}
	}
	/**Checks bullets and updates them*/
	public void checkAmmo(AbstractGameClass g,float d) {
		for(int index=0;index<ammo.length;index++) {
			if(ammo[index]!=null) {
				if(ammo[index].addScore) {
					minions++;
					engine.getSound().playClip("end");
				}
				if(ammo[index].canRemove())
					ammo[index]=null;
				else
					ammo[index].tickTraveled(g, d);
			}
		}
	}
	
	
	/**Key Event (esc to get back to the menu)*/
	@Override
	public boolean keyEvent(AbstractGameClass e,int type,int keycode) {
		switch(keycode) {
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


	int minions=0;
	boolean gameOver=false;
	float timeIn=0;

	Random r = new Random();
	float spawnTime=1;
	/**Game Tick function*/
	@Override
	public void tick(AbstractGameClass e, float delta) {
		timeIn=timeIn+(1f/30f);

		//Spawns a minion with different delays
		if(spawnTime>0)
			spawnTime=spawnTime-0.02f;
		else {
			addMinion(new Minion(0,40));
			spawnTime=r.nextFloat()+0.5f;
		}
		
		//Ticks down the attack speed cooldown
		if(shotTimer>0f)
			shotTimer=shotTimer-0.04f;
		
		//after 60s the game is over
		if(timeIn>60f) {
			gameOver=true;
			engine.getSound().playClip("minion");
		}

		//Updates Jinx
		jinx.tickTraveled(e, delta);

		//Checks Minions and Bullets
		checkMinion(e,delta);
		checkAmmo(e,delta);

		//Returns the player back to the menu
		if(gameOver) {
			InformationScreen ic = new InformationScreen(e,4,false,(int)Math.min(4,(minions/(35/4))),minions);
			e.setScreen(ic);
		    Gdx.input.setInputProcessor(ic);
		}
	}
		
	float shotTimer=0f;
	
	/**Shots at the minions that the player clicked on*/
	@Override
	public void mouse(AbstractGameClass engine, int screenX, int screenY,
			int pointer, int button) {
		PixelLocation mo = new PixelLocation(screenX,screenY);
		//if the weaopong isn't ready yet: return;
		if(shotTimer>0)return;
		for(int index=0;index<minion.length;index++) {
			if(minion[index]!=null) {
				//if a minion is clicked on: shoot a bulled
				if(minion[index].getX()+8<=mo.getX() && minion[index].getX()+24>=mo.getX() && minion[index].getY()+8<=mo.getY() && minion[index].getY()+24>=mo.getY()) {
					addAmmo(new JinxAmmo(jinx.getX()+8,jinx.getY()+8,minion[index]));
					jinx.setAnimation(engine.getAnimationHandler().getAnimation("jinx_shot"));
					shotTimer=0.8f;
				}
					
			}
		}
		
	}
	/**Render Function*/
	@Override
	public void render(AbstractGameClass e, float delta) {
		SpriteBatch batch  = e.getBatch();
		//Draws a background
		batch.draw(e.getImageHandler().getImage("background_01"),0,0);
		batch.setColor(1,1,1,1);
		
		//Renders and updates the minions
		for(int index=0;index<minion.length;index++) {
			if(minion[index]!=null) {
				minion[index].render(e, batch);
				minion[index].renderTick(e, index);
			}
		}
		//Renders and updates the bullets
		for(int index=0;index<ammo.length;index++) {
			if(ammo[index]!=null) {
				ammo[index].render(e, batch);
				ammo[index].renderTick(e, index);
			}
		}
		//Renders and updates the player
		jinx.render(e, batch);
		jinx.renderTick(e, -1);
		
		batch.setColor(0,0,0,1);
		//Draws the cs and time left
		RenderUtil.renderText(e, batch, new PixelLocation(8,18), (60-(int)timeIn)+"s");
		RenderUtil.renderText(e, batch, new PixelLocation(8,8), (int)minions+"cs");
		batch.setColor(1,1,1,1);
		
	
	}


}
