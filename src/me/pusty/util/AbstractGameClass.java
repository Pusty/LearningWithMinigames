package me.pusty.util;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

/**Abstract Class for the GameClass, contains lots of variables*/
public abstract class AbstractGameClass extends Game {
	//Is the time still running?
	boolean timeRunning=true;
	//Loader for Images
	TextureLoader pictureloader;
	//Loader for Sounds
	SoundLoader soundloader;
	//Loader for Animations
	RawAnimationHandler animationHandler;
	
	//libGDX stuff (graphics and stuff)
	OrthographicCamera camera = null;
	SpriteBatch batch = null;
	//Game still running?
	boolean running=true;

	/**Returns Sound Loader*/
	public SoundLoader getSound(){return soundloader;}
	
	/**Inits the loader classes*/
	public AbstractGameClass(){
		pictureloader=new TextureLoader();
		soundloader=new SoundLoader();
		animationHandler=new RawAnimationHandler();
		
	}
	/**Inits and starts the start screen*/
	public void startInit() {
		preInit();
		Init();
		postInit();
		initStartScreen(true);
	}
	/**Abstract StartScreen function*/
	public abstract void initStartScreen(boolean start);
	/**Returns animation handler*/
	public RawAnimationHandler getAnimationHandler() {
		return animationHandler;
	}
	/**is the time still running?*/
	public boolean isTimeRunning(){return timeRunning;}
	/**sets the time to running*/
	public void setTimeRunning(boolean b){timeRunning=b;}
	/**Returns the Image Loader */
	public TextureLoader getImageHandler(){return pictureloader;}
	/**Is the game still running?*/
	public boolean isRunning(){
		return running;
	}
	/**Sets the game running to b*/
	public void setRunning(boolean b){
		running=b;
	}
	/**first init*/
	public abstract void preInit();
	/**second init*/
	public abstract void Init();
	/**third init*/
	public abstract void postInit();
	
	
	
	/**Returns the SpriteBatch*/
	public SpriteBatch getBatch() {
		return batch;
	}
	/**Sets the SpriteBatch*/
	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}
	/**Returns the Camera*/
	public OrthographicCamera getCamera() {
		return camera;
	}
	/**Gets the Camera*/
	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}
	
	/**create() gets called by libGDX*/
	@Override
	public void create () {
			normalInit();

	}
	/**Starts initializing the FBO and after that calls startInit()*/
	public void normalInit() {
		initializeFBO();
		startInit();
	}
	
	/**If the window gets resized the FBO is recalled*/
	@Override
	public void resize(int w,int h) {
		initializeFBO();
	}
	/**Render function*/
	@Override
	public void render () {      
		sleep(30); //SET FPS!
	}
	/**Variables for the sleep function*/
	private long diff, start = com.badlogic.gdx.utils.TimeUtils.millis();
	/**Limits frame to 30 (on desktop,mobile and browser)*/
	public void sleep(int fps) {
			//OpenGL Stuff
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT |  GL20.GL_DEPTH_BUFFER_BIT );
	    if(fps>0){
	      diff = com.badlogic.gdx.utils.TimeUtils.millis() - start;
	      long targetDelay = 1000/fps;
	      if (diff < targetDelay) {
//	    	  initializeFBO(); // remaking the BufferedFrame. May be removed for performance sake! (only there cuz main screen has no background)
	    	  //saves a new buffered image
	    	  realRender();
	        }else 
	        	start = com.badlogic.gdx.utils.TimeUtils.millis();      
	      	//Begins drawing to the batch
			batch.begin();
			//Draws the buffered Frame to the screen
	  		batch.draw(fbo.getColorBufferTexture(), 0, 0, camera.viewportWidth, camera.viewportHeight, 0, 0, 1, 1);
	  		//Ends drawing to the batch
	  		batch.end();

	        
	    } 
	}
	
	 FrameBuffer fbo;
	 /**Inits the Frame Buffer*/
	public void initializeFBO() {
		if(fbo != null) fbo.dispose();
		fbo = new FrameBuffer(Format.RGB888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		if(batch != null) batch.dispose();
		batch = new SpriteBatch();

	}
	
	/**Render function*/
	public void realRender() {
			//Starts Buffering
			fbo.begin();
        	if (isRunning()) {
        		//Updates Camera
      	        camera.update();
      	        //Sets Matrix
        		batch.setProjectionMatrix(camera.combined);
        		//Begins drawing to the batch
        		batch.begin();
        		//Actual render stuff
        		super.render();
        		//Ends drawing to the batch
        		batch.end();
        	}
        	//Ends Buffering
        	fbo.end();
	}
	
	/**Disposes of native memory stuff*/
		@Override
		public void dispose() {
			batch.dispose();
   	    	SoundLoader.close();
        }



		
}
