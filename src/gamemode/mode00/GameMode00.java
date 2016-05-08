package gamemode.mode00;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.pusty.game.ticks.InformationScreen;
import me.pusty.util.AbstractGameClass;
import me.pusty.util.PixelLocation;
import me.pusty.util.RenderUtil;
import me.pusty.util.Tick;
/**Annies Gamemode, made for test purpose*/
public class GameMode00 extends Tick{

	public GameMode00(AbstractGameClass engine) {
		super(engine);
	}

	
	/**Q to shoot, Escape to get back to the menu*/
	@Override
	public boolean keyEvent(AbstractGameClass e,int type,int keycode) {
		switch(keycode) {
		case Keys.Q:
			if(type==0) {
				if(canShoot) {
					shot=true;
					canShoot=false;
					qY=48;
					e.getSound().playClip("minion"); //fire ball sound
				}
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
	
	
	/**Called on window opened*/
	@Override
	public void show() {
		
	}

	float ticks=0;
	float ticks2=0;

	boolean shot=false;
	boolean canShoot=true;
	float minionX=98+16;
	float minionY=6;
	boolean minionDead=false;
	boolean gameOver=false;
	int score=0;
	float qY=16;
	
	/**Tick class*/
	@Override
	public void tick(AbstractGameClass e, float delta) {
		//Uppers variables used for animations
		if(ticks>0)
			ticks=ticks-(1/30f)*2;
		else
			ticks=1;
		
		if(ticks2>0)
			ticks2=ticks2-(1/30f)*4;
		else
			ticks2=1;
		
		//minion moves right or down if he died
		if(!minionDead)
			minionX=minionX-(1/30f)*30;
		else 
			minionY=minionY-(1/30f)*30;
		
		//minion respawn
		if(minionY< -4) {
			minionY=6;
			minionDead=false;
			minionX=98+16;
		}
		
		if(shot) {
			//if the fireball is shoot
			qY=qY-(1/30f)*50;
			shot=true;
			canShoot=false;
			
			if(qY < -8) {
				shot=false;
				canShoot=true;
			}
		
			if(qY > 0 && qY < 20  && minionX-12 < 48 && minionX+6 > 48 && !minionDead) {
				//if the fire ball collides with the minion
				minionDead=true;
				e.getSound().playClip("end");
				score++;
			}

		}
		
		if(minionX < 16) {
			//If the minion gets to near the game ends
			gameOver=true;
			e.getSound().playClip("death");
		}
		
		if(gameOver) {
			//Returns the player back to the information screen
			InformationScreen ic = new InformationScreen(e,0,false,Math.min(4,(score/10)),score);
			e.setScreen(ic);
		    Gdx.input.setInputProcessor(ic);
		}
	}
		
	
	/**Shoots if clicked with the mouse. (and canShoot is true)*/
	@Override
	public void mouse(AbstractGameClass engine, int screenX, int screenY,
			int pointer, int button) {
		if(canShoot) {
			shot=true;
			canShoot=false;
			qY=48;
		}
	}

	/**Render function*/
	@Override
	public void render(AbstractGameClass e, float delta) {
		SpriteBatch batch  = e.getBatch();
		//Draws a background with rgb(83,104,120)
		batch.setColor(83f/255f,104f/255f,120f/255f,1f);
		batch.draw(e.getImageHandler().getImage("empty"),0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.setColor(1,1,1,1);
		//draw the player char
		int frame = Math.round(Math.max(0,ticks * 1))*1; // frame = process * framecount
		batch.draw(e.getImageHandler().getImage("annie_"+frame),8,16);
		frame = Math.round(Math.max(0,ticks2 * 1))*1; // frame = process * framecount
		//Draws the grass background
		batch.draw(e.getImageHandler().getImage("background_00"),0,0);
		batch.draw(e.getImageHandler().getImage("background_02"),0,0);
		//Flips and then draws the zzrot minion
		e.getImageHandler().getImage("zzrot_"+frame).flip(true, false);
		batch.draw(e.getImageHandler().getImage("zzrot_"+frame),minionX,minionY);
		e.getImageHandler().getImage("zzrot_"+frame).flip(true, false);
		//Draws the fireball
		if(shot) 
			batch.draw(e.getImageHandler().getImage("q_"+frame),48,qY);
		else
			batch.draw(e.getImageHandler().getImage("q_"+frame),48,48);
		
		
		batch.setColor(0,0,0,1);
		RenderUtil.renderText(e, batch, new PixelLocation(8,48), ""+score+"cs");
		batch.setColor(1,1,1,1);
		
	
	}


}
