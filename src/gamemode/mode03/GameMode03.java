package gamemode.mode03;


import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.pusty.game.ticks.InformationScreen;
import me.pusty.util.AbstractGameClass;
import me.pusty.util.PixelLocation;
import me.pusty.util.RenderUtil;
import me.pusty.util.Tick;
/**1st actual game mode for LeeSin*/
public class GameMode03 extends Tick{

	public GameMode03(AbstractGameClass engine) {
		super(engine);
		
	}

	LeeSin leesin = new LeeSin(32-16,12);
	Ward ward[] = new Ward[1];
	Wall wall[] = new Wall[3];

	/**Adds a Ward*/
	public void addWard(Ward d) {
		for(int index=0;index<ward.length;index++) {
			if(ward[index]==null) {
				ward[index]=d;
				engine.getSound().playClip("death",1.5f);
				return;
			}
		}
	}
	/**Checks wards and updates them*/
	public void checkWard(AbstractGameClass g,float d,int move) {
		for(int index=0;index<ward.length;index++) {
			if(ward[index]!=null) {
				ward[index].getLocation().set(ward[index].getLocation().add(new PixelLocation(move,0)));
				
				
				if(ward[index].canRemove())
					ward[index]=null;
				else
					ward[index].tickTraveled(g, d);
			}
		}
	}
	
	/**Adds a wall*/
	public void addWall(Wall d) {
		for(int index=0;index<wall.length;index++) {
			if(wall[index]==null) {
				wall[index]=d;
				return;
			}
		}
	}
	/**Checks walls and updates them*/
	public void checkWall(AbstractGameClass g,float d,int move) {
		for(int index=0;index<wall.length;index++) {
			if(wall[index]!=null) {
				if(extraMove == 0 && wall[index].getX()+8<=leesin.getX()+16 && wall[index].getX()+20>=leesin.getX()+16) {
					gameOver=true;
					engine.getSound().playClip("minion");
				}
				wall[index].getLocation().set(wall[index].getLocation().add(new PixelLocation(move,0)));
				if(wall[index].canRemove())
					wall[index]=null;
				else
					wall[index].tickTraveled(g, d);
			}
		}
	}
	
	
	
	/**Key Event (q to jump, 4 to place a ward, esc to get back to the menu)*/
	@Override
	public boolean keyEvent(AbstractGameClass e,int type,int keycode) {
		switch(keycode) {
		case Keys.W:
			if(type==0) {
				leesin.w(this,mouseLocation);
			}
			return true;
		case Keys.NUM_4:
			if(type==0) {
				leesin.ward(this,mouseLocation);
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
	float spawnTime=0f;

	Random r = new Random();
	int runningDistance=0;
	/**Game Tick function*/
	@Override
	public void tick(AbstractGameClass e, float delta) {
		timeIn=timeIn+(1f/30f);

		//Spawns a wall with different delays
		if(spawnTime>0)
			spawnTime=spawnTime-0.01f;
		else {
			spawnTime=r.nextFloat()+0.5f;
			addWall(new Wall(128,16));
			
		}
		//Updates Lee Sin
		leesin.tickTraveled(e, delta);
		leesin.flying=extraMoveTime>0;
		
		//Moves everything at least 1 unit to the left
		int move = -1;
		checkWall(e,delta,Math.min(-1, move-extraMove));
		checkWard(e,delta,Math.min(-1, move-extraMove));
		
		//Increases the distance traveled
		runningDistance = runningDistance + (move-extraMove)*-1;

	
		//Reduces the time flying
		if(extraMove>0) {
			if(extraMoveTime<=0)
				extraMove=0;
				extraMoveTime--;
		}
		//returns the player back to the menu
		if(gameOver) {
			InformationScreen ic = new InformationScreen(e,3,false,(int)Math.min(4,(timeIn/10)),(int)timeIn);
			e.setScreen(ic);
		    Gdx.input.setInputProcessor(ic);
		}
	}
	int extraMove=0;
	int extraMoveTime=0;
	/**Moves units to the left*/
	public void move(int i) {
		if(extraMoveTime<=0) {
			extraMove=i/6;
			extraMoveTime=4;
		}
	}
		
	
	PixelLocation mouseLocation = new PixelLocation(0,0);
	/**Empty Mouse Click Event*/
	@Override
	public void mouse(AbstractGameClass engine, int screenX, int screenY,
			int pointer, int button) {
	}
	/**updates mouseLocation to the current mouse location*/
	public void genericMouse(AbstractGameClass engine,int type,int screenX, int screenY, int pointer, int button){
		if(type==5) {
			mouseLocation=new PixelLocation(screenX,screenY);
		}
	}
	/**Render Function*/
	@Override
	public void render(AbstractGameClass e, float delta) {
		SpriteBatch batch  = e.getBatch();
		batch.setColor(97f/255f,104f/255f,120f/255f,1);
		//Draws a background
		batch.draw(e.getImageHandler().getImage("empty"),0,0,e.getCamera().viewportWidth,e.getCamera().viewportHeight);
		batch.setColor(1,1,1,1);
		
		//Renders and updates the walls
		for(int index=0;index<wall.length;index++) {
			if(wall[index]!=null) {
				wall[index].render(e, batch);
				wall[index].renderTick(e, index);
			}
		}
		//Renders and updates the wards
		for(int index=0;index<ward.length;index++) {
			if(ward[index]!=null) {
				ward[index].render(e, batch);
				ward[index].renderTick(e, index);
			}
		}
		
		//Draw the ground (and let is loop fluently)
		batch.draw(e.getImageHandler().getImage("background_00"),-runningDistance%128,0,e.getCamera().viewportWidth,18);
		batch.draw(e.getImageHandler().getImage("background_00"),(-runningDistance%128+128),0,e.getCamera().viewportWidth,18);
		batch.setColor(1,1,1,1);

		//Renders and updates the player
		leesin.render(e, batch);
		leesin.renderTick(e, -1);
		
		batch.setColor(0,0,0,1);
		//Draw the current time playing
		RenderUtil.renderText(e, batch, new PixelLocation(16,48), (int)timeIn+"s");
		batch.setColor(1,1,1,1);
		if(ward[ward.length-1]==null)
			batch.draw(e.getImageHandler().getImage("ward_icon"),8,60);
		
		//Draws the foreground
		batch.draw(e.getImageHandler().getImage("background_02"),-runningDistance%128,0,e.getCamera().viewportWidth,18);
		batch.draw(e.getImageHandler().getImage("background_02"),(-runningDistance%128+128),0,e.getCamera().viewportWidth,18);
		
	
	}



}
