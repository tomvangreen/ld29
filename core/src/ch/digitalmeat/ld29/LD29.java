package ch.digitalmeat.ld29;

import java.util.ArrayList;
import java.util.List;

import ch.digitalmeat.ld29.event.Events;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LD29 extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	private GameWorld gameWorld;
	public static final int VIEWPORT_WIDTH = 800;
	public static final int VIEWPORT_HEIGHT = 480;
	public static float LIGHT_LENGTH = 1f;
	public static float EFFECTS_SCALE = 1f;

	public static Viewport viewport;
	private Stage ui;

	private List<UiScreen> screens = new ArrayList<UiScreen>();

	public LD29() {

	}

	public LD29(float effectsScale, float lightLength) {
		EFFECTS_SCALE = effectsScale;
		LIGHT_LENGTH = lightLength;
	}

	@Override
	public void create() {
		Assets.create();
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		gameWorld = new GameWorld();
		viewport = new ScalingViewport(Scaling.fit, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
		ui = new Stage(viewport);

		screens.add(new Hud(gameWorld.getPlayer()));
		screens.add(new Messages());

		for (UiScreen screen : screens) {
			screen.create(ui);
			// screen.hide();
		}
		Assets.script.setGameWorld(gameWorld);
		// Events.factory.message("start-01", true, false);
	}

	@Override
	public void render() {
		float delta = Gdx.graphics.getDeltaTime();
		Assets.script.update(delta);
		gameWorld.update(delta);
		ui.act(delta);
		for (UiScreen screen : screens) {
			screen.update(delta);
		}
		Events.factory.getQueue().dispatch();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gameWorld.draw();
		ui.draw();
	}
}
