package ch.digitalmeat.ld29.client;

import ch.digitalmeat.ld29.LD29;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class HtmlLauncher extends GwtApplication {

	@Override
	public GwtApplicationConfiguration getConfig() {
		return new GwtApplicationConfiguration(LD29.VIEWPORT_WIDTH, LD29.VIEWPORT_HEIGHT);
	}

	@Override
	public ApplicationListener getApplicationListener() {
		return new LD29(0.15f, 0.5f);
	}
}