package me.pusty.game.ticks;

import gamemode.mode00.GameMode00;
import gamemode.mode01.GameMode01;
import gamemode.mode02.GameMode02;
import gamemode.mode03.GameMode03;
import gamemode.mode04.GameMode04;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.pusty.util.AbstractGameClass;
import me.pusty.util.PixelLocation;
import me.pusty.util.RenderUtil;
import me.pusty.util.Tick;

/**Information Screen Class*/
public class InformationScreen extends Tick{
	/**Index of the game it is bound to */
	int gameIndex=0;
	/**If the InformationScreen is from or should lead to a game*/
	boolean sendTo=true;
	/**The rank you got in the last game*/
	int rank;
	/**The number (cs,time) you got in your last game*/
	int num;
	/**Creates a InformationScreen*/
	public InformationScreen(AbstractGameClass engine,int index,boolean in,int rated,int num) {
		super(engine);
		gameIndex=index;
		sendTo=in;
		rank=rated;
		this.num=num;
	}

	
	/**Key Press Event (Enter/Space) to start the game*/
	@Override
	public boolean keyEvent(AbstractGameClass e,int type,int keycode) {
			if(type==0) {
				if(keycode == Keys.SPACE || keycode == Keys.ENTER) {
					selectGame(e);
				}
				return true;
			}
		
		return false;
	}
	/**Starts a gamemode with the the index "gameIndex"*/
	public void selectGame(AbstractGameClass e) {
		if(timeIn<0.5f)return;
		e.getSound().playClip("select",2f);
		if(!sendTo) {		
			StartScreen ss = StartScreen.startScreen;
			if(rank>ss.rank[gameIndex])
				ss.rank[gameIndex]=rank;
			e.setScreen(ss);
		    Gdx.input.setInputProcessor(ss);
		}else {
			if(gameIndex==0) {
				GameMode00 gm = new GameMode00(e);
				e.setScreen(gm);
			    Gdx.input.setInputProcessor(gm);
			}else if(gameIndex==1) {
				GameMode01 gm = new GameMode01(e);
				e.setScreen(gm);
			    Gdx.input.setInputProcessor(gm);
			}else if(gameIndex==2) {
				GameMode02 gm = new GameMode02(e);
				e.setScreen(gm);
			    Gdx.input.setInputProcessor(gm);
			}else if(gameIndex==3) {
				GameMode03 gm = new GameMode03(e);
				e.setScreen(gm);
			    Gdx.input.setInputProcessor(gm);
			}else if(gameIndex==4) {
				GameMode04 gm = new GameMode04(e);
				e.setScreen(gm);
			    Gdx.input.setInputProcessor(gm);
			}
			
		}
	}
	
	
	/**Window Show Event*/
	@Override
	public void show() {
		
	}


	float timeIn=0f;
	/**Ticks tick up. After 0.5s the window will react*/
	@Override
	public void tick(AbstractGameClass e, float delta) {
		timeIn=timeIn+(1f/30f);
	}
	
	/**Offset of everything rendered*/
	int xOffset = -10;
	int yOffset = 0;
	
	/**Start Game with the mouse*/
	@Override
	public void mouse(AbstractGameClass engine, int screenX, int screenY,
			int pointer, int button) {
		engine.getSound().playClip("win");
		selectGame(engine);

	}
	/**number to a readable rank*/
	public static String rankToString(int r) {
		if(r==0)
			return "D";
		else if(r==1)
			return "C";
		else if(r==2)
			return "B";
		else if(r==3)
			return "A";
		else if(r==4)
			return "S";
		return "/";
	}
	/**Render function*/
	@Override
	public void render(AbstractGameClass e, float delta) {
		
			SpriteBatch batch  = e.getBatch();
			batch.setColor(179f/255f,191f/255f,168f/255f,1f);
			batch.draw(e.getImageHandler().getImage("empty"),0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
			
			String[] content = null;
			
			if(gameIndex==0) {
				if(sendTo) {
					String[] co = {"Farming with Annie","",
									"Press Q"," (or use the mouse)"};
					content= co;
				}else {
					String[] co = {"Playing","Annie","","Rank: "+rankToString(rank),num+"cs"};
					content= co;
				}
			}else if(gameIndex==1) {
				if(sendTo) {
					String[] co = {"Dodging with Vayne","",
									"Press Q to tumble"};
					content= co;
				}else {
					String[] co = {"Playing","Vayne","","Rank: "+rankToString(rank),num+"s"};
					content= co;
				}
			}else if(gameIndex==2) {
				if(sendTo) {
					String[] co = {"Hooking with Thresh","",
									"Press Q or use",
									"the mouse to",
									"hook"};
					content= co;
				}else {
					String[] co = {"Playing","Thresh","","Rank: "+rankToString(rank),num+"cs"};
					content= co;
				}
			}else if(gameIndex==3) {
				if(sendTo) {
					String[] co = {"Ward Jumping with","",
									"Lee Sin",
									"4 - Place Ward",
									"W - Jump to Ward"};
					content= co;
				}else {
					String[] co = {"Playing","Lee Sin","","Rank: "+rankToString(rank),num+"s"};
					content= co;
				}
			}else if(gameIndex==4) {
				if(sendTo) {
					String[] co = {"Last Hitting with","",
									"Jinx",
									"Mouse - Attack"};
					content= co;
				}else {
					String[] co = {"Playing","Jinx","","Rank: "+rankToString(rank),num+"cs"};
					content= co;
				}
			}
			
			
			batch.setColor(0,0,0,1);
			if(content!=null)
				for(int index=0;index<content.length;index++)	{
					String con = content[index];
					RenderUtil.renderCentured(e, batch, new PixelLocation( +xOffset,yOffset+6 -(index-content.length/2+1)*10), con);
				}
			
			batch.setColor(1,1,1,1);
			

		
		
	
	}
	
	
	


}
