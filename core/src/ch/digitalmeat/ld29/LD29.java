package ch.digitalmeat.ld29;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

public class LD29 extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	private GameWorld gameWorld;
	static final int VIEWPORT_WIDTH = 640;
	static final int VIEWPORT_HEIGHT = 480;

	private Stage ui;

	private List<UiScreen> screens = new ArrayList<UiScreen>();

	@Override
	public void create() {
		Assets.create();
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		gameWorld = new GameWorld();
		ui = new Stage(new ScalingViewport(Scaling.fit, VIEWPORT_WIDTH, VIEWPORT_HEIGHT));
	}

	@Override
	public void render() {
		float delta = Gdx.graphics.getDeltaTime();
		gameWorld.update(delta);
		ui.act(delta);
		for (UiScreen screen : screens) {
			screen.update(delta);
		}

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gameWorld.draw();
		ui.draw();
	}
}
