package me.pusty.util;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

/**Abstract Tick Class for the different gamemodes*/
public abstract class Tick implements Screen, InputProcessor  {


	/**Pause Variable*/
	boolean pause=false;
	protected AbstractGameClass engine;
	public Tick(AbstractGameClass engine){
		this.engine = engine;
	}
	/**Get the dedicated engine */
	public AbstractGameClass E() { return engine; }
	/**Calls every tick (30 times a second)*/
	public abstract void tick(AbstractGameClass engine,float delta);
	/**Mouse click event */
	public  abstract void mouse(AbstractGameClass engine,int screenX, int screenY, int pointer, int button);
	/**Render function*/
	public abstract void render(AbstractGameClass e,float delta);
	/**Mouse events
	 * 3 = touchUp
	 * 4 = touchDragged
	 * 5 = mouse moved*/
	public void genericMouse(AbstractGameClass engine,int type,int screenX, int screenY, int pointer, int button){}
	/**
	 * KeyEvent
	 * type == 0 key up
	 * type == 1 key down
	 * returns if the key is used
	 */
	public boolean keyEvent(AbstractGameClass e,int type,int keycode){return false;}
	
	/**Key Down Function*/
	@Override
	public boolean keyDown(int keycode) {
		if(keyEvent(E(),0,keycode)) return true;
		
		switch(keycode) {
		//Pauses the game on P press
			case Keys.P:
				if(!pause){
				engine.setTimeRunning(!engine.isTimeRunning());
				pause=true;
				}
				break;
	}
		return true;
	}
	/**Key Up Function*/
	@Override
	public boolean keyUp(int keycode) {
		if(keyEvent(E(),1,keycode)) return true;
		switch(keycode) {
		case Keys.P:
			pause= false;
			break;
}
		return true;
	}
	/**Key Typed Function*/
	@Override
	public boolean keyTyped(char character) {
		return false;
	}
	/**Touch Down Function*/
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Ray ray = engine.getCamera().getPickRay(screenX, screenY);
		Vector3 out = new Vector3();
		ray.getEndPoint(out, engine.getCamera().near);
		mouse(E(),(int)out.x,(int)out.y,pointer,button);
		return true;
	}
	/**Touch Up Function*/
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Ray ray = engine.getCamera().getPickRay(screenX, screenY);
		Vector3 out = new Vector3();
		ray.getEndPoint(out, engine.getCamera().near);
		genericMouse(E(),3,(int)out.x,(int)out.y,pointer,button);
		return true;
	}
	/**Touch Dragged Function*/
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		Ray ray = engine.getCamera().getPickRay(screenX, screenY);
		Vector3 out = new Vector3();
		ray.getEndPoint(out, engine.getCamera().near);
		genericMouse(E(),4,(int)out.x,(int)out.y,pointer,0);
		return true;
	}
	/**Mouse Moved Function*/
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		Ray ray = engine.getCamera().getPickRay(screenX, screenY);
		Vector3 out = new Vector3();
		ray.getEndPoint(out, 1f);
		genericMouse(E(),5,(int)out.x,(int)out.y,0,0);
		return true;
	}
	/**scrolled Function*/
	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	/**Direct Render function. Links to render(AbstractGameClass,delta)*/
	@Override
	public void render(float delta) {
		if(engine.isTimeRunning())
			tick(E(),delta);
		
		render(E(),delta);

	}
	/**Not uses abstract function*/
	@Override
	public void resize(int width, int height) {
	}
	/**Not uses abstract function*/
	@Override
	public void pause() {
	}
	/**Not uses abstract function*/
	@Override
	public void resume() {
	}
	/**Not uses abstract function*/
	@Override
	public void hide() {
	}
	/**Not uses abstract function*/
	@Override
	public void dispose() {
	}
	
}
