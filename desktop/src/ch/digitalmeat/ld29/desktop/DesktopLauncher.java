package ch.digitalmeat.ld29.desktop;

import ch.digitalmeat.ld29.LD29;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.foregroundFPS = 60;
		config.backgroundFPS = 0;

		new LwjglApplication(new LD29(), config);
	}
}
