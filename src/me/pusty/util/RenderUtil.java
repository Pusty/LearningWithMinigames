package me.pusty.util;



import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RenderUtil {
	
	/**Renders Text in the middle of point offset*/
	public static void renderCentured(AbstractGameClass engine,SpriteBatch g,PixelLocation offset,String txt){
		renderText(engine,g,new PixelLocation((int)engine.getCamera().viewportWidth/2 + offset.x - calculateOffset(txt,txt.length()-1)/2,(int)engine.getCamera().viewportHeight/2 +offset.y),txt);
	}
	/**calculate offset for a text with given chars*/
	public static int calculateOffset(String txt,int index) {
		if(txt == null || txt == "" || index >= txt.length())return 0;
		int tempsize = 0;
		for(int a=0;a<txt.length();a++) {
			if(a>index)break;
			tempsize = tempsize+6;	
			if(txt.toCharArray()[a]==' ') 
				tempsize=tempsize-3;
			else if(txt.toCharArray()[a]=='P') 
				tempsize=tempsize-1;	
			else if(txt.toCharArray()[a]=='M') 
				tempsize=tempsize+4;		
			else if(txt.toCharArray()[a]=='W') 
				tempsize=tempsize+4;		
			else if(Character.isUpperCase(txt.toCharArray()[a])) 
				tempsize=tempsize+2;	
			else if(txt.toCharArray()[a]=='l') 
				tempsize=tempsize-4;
			else if(txt.toCharArray()[a]=='t') 
				tempsize=tempsize-2;
			else if(txt.toCharArray()[a]=='r') 
				tempsize=tempsize-2;
			else if(txt.toCharArray()[a]=='p') 
				tempsize=tempsize-2;
			else if(txt.toCharArray()[a]=='m') 
				tempsize=tempsize+2;
			
			else if(txt.toCharArray()[a]=="'".toCharArray()[0]) 
				tempsize=tempsize-4;
			else if(txt.toCharArray()[a]==':') 
				tempsize=tempsize-4;
			
		}
		return tempsize;
	}
	
	/**Renders text on point loc*/
	public static void renderText(AbstractGameClass en,SpriteBatch g,PixelLocation loc,String txt){
		if(txt == null || txt == "")return;
		int tempsize = 0;
		for(int a=0;a<txt.length();a++) {
			TextureRegion image = en.getImageHandler().getImage("small_"+txt.toCharArray()[a]);
			if(image==null) continue;
			tempsize = tempsize+6;	
			if(txt.toCharArray()[a]==' ') {
				tempsize=tempsize-3;
				continue;
			}
			g.draw(image, (int)loc.x + tempsize, (int)loc.y ,image.getRegionWidth(),image.getRegionHeight());

			if(txt.toCharArray()[a]=='M') 
				tempsize=tempsize+4;		
			else if(txt.toCharArray()[a]=='W') 
				tempsize=tempsize+4;		
			else if(txt.toCharArray()[a]=='P') 
				tempsize=tempsize-1;		
			else if(Character.isUpperCase(txt.toCharArray()[a])) 
				tempsize=tempsize+2;	
			else if(txt.toCharArray()[a]=='l') 
				tempsize=tempsize-4;
			else if(txt.toCharArray()[a]=='t') 
				tempsize=tempsize-2;
			else if(txt.toCharArray()[a]=='r') 
				tempsize=tempsize-2;
			else if(txt.toCharArray()[a]=='p') 
				tempsize=tempsize-2;
			else if(txt.toCharArray()[a]=='m') 
				tempsize=tempsize+2;
		}
	}

}
