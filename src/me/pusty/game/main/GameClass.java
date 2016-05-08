package me.pusty.game.main;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonValue;

import me.pusty.game.ticks.StartScreen;
import me.pusty.util.AbstractGameClass;
import me.pusty.util.RawAnimation;
import me.pusty.util.json.JsonHandler;

/**Loading and initializing class*/
public class GameClass extends AbstractGameClass {


	
	/**Creates a new object*/
	public GameClass(){
		super();
	}
	
	/**1st Init*/
	@Override
	public void preInit() {
		//sets batch
		this.setBatch(new SpriteBatch());
		//sets camera to 128px x 72px
		OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, 128, 72);
        this.setCamera(camera);
        
        
		try{
			FileHandle fileHandle = null;
			//Loads images with only 1 frame
			String fileNames[] = {"resources/empty.png","resources/background_00.png","resources/background_01.png","resources/background_02.png","resources/mode03/ward.png","resources/mode03/wall.png","resources/mode04/jinx_bullet.png","resources/mode03/ward_icon.png"};
			for(String fileName:fileNames) {			
				fileHandle = Gdx.files.internal(fileName);
				String name = fileHandle.nameWithoutExtension();
				Texture texture = new Texture(fileHandle);
						getImageHandler().addImage(name, new TextureRegion(texture));
			}
			
			//Loads images with more than 1 frame (32px x 32px each frame)
			String fileNames2[] = {"resources/zzrot.png","resources/mode00/annie.png","resources/mode00/q.png","resources/mode01/vayne.png","resources/mode01/vayne_tumble.png","resources/mode01/binding.png","resources/mode02/thresh.png","resources/mode02/thresh_hook.png","resources/mode03/leesin.png","resources/mode04/jinx.png","resources/mode04/health.png"};	
			for(String fileName:fileNames2) {			
			fileHandle = Gdx.files.internal(fileName);
			String name = fileHandle.nameWithoutExtension();
			Texture texture = new Texture(fileHandle);
				int splitterX = texture.getWidth()/32;
				TextureRegion[][]  tmp = TextureRegion.split(texture, texture.getWidth()/splitterX, texture.getHeight());
		        int index = 0;
		        for (int i = 0; i < tmp.length; i++) {
		            for (int j = 0; j < tmp[i].length; j++) {
		            	getImageHandler().addImage(name+"_"+index, tmp[i][j]);
		                index++;
		            }
		        }   
			}

			//Loads the font
			{
				fileHandle = Gdx.files.internal("resources/chars.png");
				char[] smallletters = { ' ','a','b','c','d','e','f','g','h','i',
										'j','k','l','m','n','o','p','q','r','s',
										't','u','v','w','x','y','z','A','B','C',
										'D','E','F','G','H','I','J','K','L','M',
										'N','O','P','Q','R','S','T','U','V','W',
										'X','Y','Z','0','1','2','3','4','5','6',
										'7', '8','9','!','"','%','&','/','(',')',
										'=', '?','[',']','{','}','\\','|','<','>',
										'*', '+','~',"'".toCharArray()[0],'#','-','_','.',':',',',
										';'};
				
				
				
				Texture tex = new Texture(fileHandle);
				TextureRegion[][]  tmp = TextureRegion.split(tex, tex.getWidth()/10, tex.getHeight()/10);
		        int index = 0;
		        for (int i = 0; i < tmp.length; i++) {
		            for (int j = 0; j < tmp[i].length; j++) {
		            	getImageHandler().addImage("small_" + smallletters[index], tmp[i][j]);
		                index++;
		                if(index >= smallletters.length)
		                	break;
		            }
	                if(index >= smallletters.length)
	                	break;
		        }
			}
			


						
				
			
		
			


			//Animation loader
			//Loads animation script from the animation.json
			JsonHandler handler = new JsonHandler();
		
				try {
					JsonValue jsobj  = handler.getArrayFromFile(Gdx.files.getFileHandle("scripts/animations.json", FileType.Internal).read());;
					for(JsonValue jobj:jsobj){
						RawAnimation animation = new RawAnimation(jobj);
						this.getAnimationHandler().addAnimation(jobj.getString("name"), animation);
					}
				}catch (Exception e){
					e.printStackTrace();
				}
			
		

		}catch(Exception e){e.printStackTrace();}
		

		
		//Loads sound files
		String fileNames[] = {"resources/sounds/death.wav","resources/sounds/end.wav","resources/sounds/minion.wav","resources/sounds/select.wav"};
		for(String fileName:fileNames) {			
			FileHandle fileHandle = Gdx.files.internal(fileName);
			getSound().addSound(fileHandle.nameWithoutExtension(),fileHandle,fileHandle.nameWithoutExtension().contains("bg"));
		}
//		getSound().addSound("select", StartClass.getURL("resources/select.wav"),false);
//		getSound().addSound("bg_1",  StartClass.getURL("resources/bg_1.wav"),true);

		getSound().playClip("bg");
		
	}
	/**Splits without a regex (for the web port)*/
	public static String[] splitNonRegex(String input, String delim)
		{
    List<String> l = new ArrayList<String>();
    int offset = 0;

    while (true)
    	{
	        int index = input.indexOf(delim, offset);
	        if (index == -1)
	        {
	            l.add(input.substring(offset));
	            return l.toArray(new String[l.size()]);
	        } else
	        {
	            l.add(input.substring(offset, index));
	            offset = (index + delim.length());
	        }
    	}
	}

	/**Replace all without a regex (for the web port)*/
	public static String replaceAll(String in, String ths, String that) {
	    StringBuilder sb = new StringBuilder(in);
	    int idx = sb.indexOf(ths); 
	    
	    while (idx > -1) {
	        sb.replace(idx, idx + ths.length(), that);
	        idx = sb.indexOf(ths);

	    }
	    
	    return sb.toString();

	}

	/**2nd Init*/
	@Override
	public void Init() {

	}
	
	

	/**3rd Init*/
	@Override
	public void postInit() {

	}

	/**Function to init the start screen*/
	@Override
	public void initStartScreen(boolean start) {
		StartScreen screenTick = new StartScreen(this);
		this.setScreen(screenTick);
	    Gdx.input.setInputProcessor(screenTick);
	}

}
