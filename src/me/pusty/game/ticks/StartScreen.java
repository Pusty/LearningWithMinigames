package me.pusty.game.ticks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.pusty.util.AbstractGameClass;
import me.pusty.util.PixelLocation;
import me.pusty.util.RenderUtil;
import me.pusty.util.Tick;
/**Selection Screen*/
public class StartScreen extends Tick{
	/**Static object so it keeps values and selected games*/
	public static StartScreen startScreen = null;
	/**Array of the the best ranks*/
	public int[] rank;
	/**Creates a new StartScreen with the AbstractGameClass as a argument*/
	public StartScreen(AbstractGameClass engine) {
		super(engine);
		if(startScreen==null)
			startScreen=this;
		rank = new int[5];
	}

	
	/**Key event, w/up to go up s/down to go down space/enter to start*/
	@Override
	public boolean keyEvent(AbstractGameClass e,int type,int keycode) {
			if(type==0) {
				if(keycode == Keys.W || keycode == Keys.UP) {
					selectedIndex--;
					if(selectedIndex<0)
						selectedIndex=mody.length-1;
					e.getSound().playClip("select",2f);
				}
				if(keycode == Keys.S || keycode == Keys.DOWN) {
					selectedIndex++;
					if(selectedIndex>=mody.length)
						selectedIndex=0;
					e.getSound().playClip("select",2f);
				}
				if(keycode == Keys.SPACE || keycode == Keys.ENTER) {
					e.getSound().playClip("select");
					selectGame(e);
				}
				return true;
			}
		
		return false;
	}
	/**Calls the InformationScreen for "selectedIndex" game.*/
	public void selectGame(AbstractGameClass e) {
			InformationScreen ic = new InformationScreen(e,selectedIndex,true,-1,-1);
			e.setScreen(ic);
		    Gdx.input.setInputProcessor(ic);
	}
	
	
	/**Called when the window gets opened*/
	@Override
	public void show() {
		
	}

	/**Called every tick*/
	@Override
	public void tick(AbstractGameClass e, float delta) {
	}
	/**Names of the diffrent champions*/
	static String[] mody = {
		"Annie","Vayne","Thresh","Lee Sin","Jinx"
	};
	/**Render Offset Stuff*/
	int xOffset = -34;
	/**Render Offset Stuff*/
	int yOffset = 0;
	/**Current Selected Game*/
	int selectedIndex = 1;
	
	/**Select a game with the mouse*/
	@Override
	public void mouse(AbstractGameClass engine, int screenX, int screenY,
			int pointer, int button) {
		engine.getSound().playClip("win");
		PixelLocation mouseLocation = new PixelLocation(screenX,screenY);
		String content = selectedIndex-1>=0?mody[selectedIndex-1]:mody[mody.length-1];
		if(StartScreen.overCentured(engine,mouseLocation,new PixelLocation(0 +xOffset,yOffset+6 -0*12),content)){		
				selectedIndex--;
			if(selectedIndex<0)
				selectedIndex=mody.length-1;
			engine.getSound().playClip("select");
		}
		if(StartScreen.overCentured(engine,mouseLocation,new PixelLocation(0 +xOffset,yOffset+6 -1*12),mody[selectedIndex])){selectGame(engine);}
		content = mody.length>selectedIndex+1?mody[selectedIndex+1]:mody[0];
		if(StartScreen.overCentured(engine,mouseLocation,new PixelLocation(0 +xOffset,yOffset+6 -2*12),content)){{		
			selectedIndex++;
			if(selectedIndex>=mody.length)
				selectedIndex=0;
			engine.getSound().playClip("select");
	}}

	}
	/**Is the mouse(m) over the centured (centured on off) String text*/
    public static boolean overCentured(AbstractGameClass engine,PixelLocation m,PixelLocation off,String txt){
    	PixelLocation centure = getCentured(engine,off,txt);
    	if(m.y >= centure.y && m.y < centure.y+16){
    		if(m.x < centure.x+16*txt.length()+32 && m.x > centure.x-32)
    			return true;
    	}
    	return false;
    }
    /**Get Centured Position of String txt on pos l*/
    public static PixelLocation getCentured(AbstractGameClass engine,PixelLocation l,String txt){
    	return new PixelLocation((int)engine.getCamera().viewportWidth/2 + l.x - 16*txt.length()/2,(int)engine.getCamera().viewportHeight/2 +l.y);
    }
    /**Render Function*/
	@Override
	public void render(AbstractGameClass e, float delta) {
		
			SpriteBatch batch  = e.getBatch();
			batch.setColor(179f/255f,191f/255f,168f/255f,1f);
			batch.draw(e.getImageHandler().getImage("empty"),0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
			
			
			String[] conten = null;
			
			if(selectedIndex==0) {
				String[] co = {"Test","Content","Grade:",""+InformationScreen.rankToString(rank[selectedIndex])};
				conten= co;
			}else if(selectedIndex==1) {
				String[] co = {"Most","Mained","Champion","Grade:",""+InformationScreen.rankToString(rank[selectedIndex])};
				conten= co;
			}else if(selectedIndex==2) {
				String[] co = {"2nd","Mained","Champion","Grade:",""+InformationScreen.rankToString(rank[selectedIndex])};
				conten= co;
			}else if(selectedIndex==3) {
				String[] co = {"3rd","Mained","Champion","Grade:",""+InformationScreen.rankToString(rank[selectedIndex])};
				conten= co;
			}else if(selectedIndex==4) {
				String[] co = {"4th","Mained","Champion","Grade:",""+InformationScreen.rankToString(rank[selectedIndex])};
				conten= co;
			}
			
			
			batch.setColor(0,0,0,1);
			String content = selectedIndex-1>=0?mody[selectedIndex-1]:mody[mody.length-1];
			RenderUtil.renderCentured(e, batch, new PixelLocation( +xOffset,yOffset+6 -0*12), content);
			RenderUtil.renderCentured(e, batch, new PixelLocation( +xOffset,yOffset+6 -1*12), mody[selectedIndex]);
			content = mody.length>selectedIndex+1?mody[selectedIndex+1]:mody[0];
			RenderUtil.renderCentured(e, batch, new PixelLocation( +xOffset,yOffset+6 -2*12), content);
			
			RenderUtil.renderCentured(e,e.getBatch(),new PixelLocation(-32+xOffset,-(1)*12 + 6+yOffset),"[");
			RenderUtil.renderCentured(e,e.getBatch(),new PixelLocation(32+xOffset,-(1)*12 + 6+yOffset),"]");
			
			
			for(int index=0;index<conten.length;index++) {
				String con = conten[index];
				RenderUtil.renderText(e,e.getBatch(),new PixelLocation(100+xOffset,55+12*-index + yOffset),con);
			}
			
			batch.setColor(1,1,1,1);
			

		
		
	
	}
	
	
	


}
