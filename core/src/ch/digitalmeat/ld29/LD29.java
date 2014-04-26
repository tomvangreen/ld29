package ch.digitalmeat.ld29;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LD29 extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	private GameWorld gameWorld;
	static final int VIEWPORT_WIDTH = 640;
	static final int VIEWPORT_HEIGHT = 480;

	@Override
	public void create() {
		Assets.create();
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		gameWorld = new GameWorld();
	}

	@Override
	public void render() {
		gameWorld.update(Gdx.graphics.getDeltaTime());

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gameWorld.draw();
	}
}
