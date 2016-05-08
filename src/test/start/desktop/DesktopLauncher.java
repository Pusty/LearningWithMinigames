package test.start.desktop;


import me.pusty.game.main.GameClass;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "LearningWithMinigames";
//		config.addIcon("resources/fox_sit.png", FileType.Internal);
		config.width = 128*6 ;
		config.height = 72*6;
		config.resizable = true;
		config.foregroundFPS = 120;
		config.backgroundFPS = 30;
		
		GameClass gameclass = new GameClass();

		 new LwjglApplication(gameclass, config);
	}
}
