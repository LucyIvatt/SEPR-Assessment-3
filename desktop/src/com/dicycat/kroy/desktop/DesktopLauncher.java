package com.dicycat.kroy.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dicycat.kroy.Kroy;
import com.badlogic.gdx.Files;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.addIcon("DicyCatSmol.png", Files.FileType.Internal);
		config.width = Kroy.width;
		config.height = Kroy.height;
		config.resizable = false;
		config.foregroundFPS = 60;
		config.fullscreen = false;
		
		new LwjglApplication(new Kroy(), config);
	}
}
