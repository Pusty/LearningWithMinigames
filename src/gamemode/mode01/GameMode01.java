package gamemode.mode01;


import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.pusty.game.ticks.InformationScreen;
import me.pusty.util.AbstractGameClass;
import me.pusty.util.PixelLocation;
import me.pusty.util.RenderUtil;
import me.pusty.util.Tick;
/**1st actual game mode for Vayne*/
public class GameMode01 extends Tick{

	public GameMode01(AbstractGameClass engine) {
		super(engine);
		
	}

	Vayne vayne = new Vayne(64,8);
	Dodgable[] dodge = new Dodgable[5];
	/**Adds a projectile*/
	public void addDodge(Dodgable d) {
		for(int index=0;index<dodge.length;index++) {
			if(dodge[index]==null) {
				dodge[index]=d;
				engine.getSound().playClip("minion");
				return;
			}
		}
	}
	/**Handles projectiles (garbage collection)*/
	public void checkDodge() {
		for(int index=0;index<dodge.length;index++) {
			if(dodge[index]!=null) {
				if(dodge[index].canRemove())
					dodge[index]=null;
			}
		}
	}
	
	
	/**KeyEvent for tumbling (q) and gameOver (escape)*/
	@Override
	public boolean keyEvent(AbstractGameClass e,int type,int keycode) {
		switch(keycode) {
		case Keys.Q:
			if(type==0) {
				vayne.q(e);
			}
			return true;
		case Keys.D:
			if(type==0)
				vayne.queueDirection(1);
			return true;
		case Keys.A:
			if(type==0)
				vayne.queueDirection(2);
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
	//Score in time in game
	float timeIn=0;

	Random r = new Random();
	float spawnTime=1;
	/**Game Tick function*/
	@Override
	public void tick(AbstractGameClass e, float delta) {
		timeIn=timeIn+(1f/30f);	
	
		//Spawn Minion Code
		if(spawnTime>0)
			spawnTime=spawnTime-0.04f;
		else {
			//Lets the projectile spawn around 40 units near the player
			int x = vayne.getX();
			int mix = Math.max(x-40,0);
			int max = Math.min(128-32, x+40);
			x = mix + r.nextInt(max-mix);
			addDodge(new Dodgable(x,72));
			spawnTime=r.nextFloat()+0.5f;
		}
		
			//If the player gets hit the game ends
		for(int index=0;index<dodge.length;index++) {
			if(dodge[index]!=null) {
				dodge[index].tickTraveled(e, delta);
				if(dodge[index].getX()+8>=vayne.getX() && dodge[index].getX()<=vayne.getX()+8 && dodge[index].getY()+8>=vayne.getY() && dodge[index].getY()<=vayne.getY()+8) {
					gameOver=true;
				engine.getSound().playClip("minion",0.7f);
				}
			}
		}
		//Calls Vaynes tickTravel function
		vayne.tickTraveled(e, delta);
		//Garbage collection
		checkDodge();

		//Back to the InformationScreen
		if(gameOver) {
			InformationScreen ic = new InformationScreen(e,1,false,(int)Math.min(4,(timeIn/10)),(int)timeIn);
			e.setScreen(ic);
		    Gdx.input.setInputProcessor(ic);
		}
	}
		
	
	PixelLocation mouse = new PixelLocation(0,0);

	/**On Mouse Move event to tumble to the mouse direction*/
	public void genericMouse(AbstractGameClass engine,int type,int screenX, int screenY, int pointer, int button){
		//Mouse Move is the 5th mouse event
		if(type==5) {
			mouse = new PixelLocation(screenX,screenY);		
			if(mouse.getX()>vayne.getX()+16) {
					vayne.queueDirection(1);
			}else
				vayne.queueDirection(2);
		}
		
	}
	/**Render Tick*/
	@Override
	public void render(AbstractGameClass e, float delta) {
		SpriteBatch batch  = e.getBatch();
		//Draws a background
		batch.draw(e.getImageHandler().getImage("background_01"),0,0);
		batch.setColor(1,1,1,1);
		
		//Renders and updates the projectile
		for(int index=0;index<dodge.length;index++) {
			if(dodge[index]!=null) {
				dodge[index].render(e, batch);
				dodge[index].renderTick(e, index);
			}
		}
		//Renders and updates the player
		vayne.render(e, batch);
		vayne.renderTick(e, -1);
		
		batch.setColor(0,0,0,1);
		//Draw the time the player plays on screen
		RenderUtil.renderText(e, batch, new PixelLocation(8,28), (int)timeIn+"s");
		batch.setColor(1,1,1,1);
		
	
	}
	@Override
	public void mouse(AbstractGameClass engine, int screenX, int screenY,
			int pointer, int button) {
	}


}
