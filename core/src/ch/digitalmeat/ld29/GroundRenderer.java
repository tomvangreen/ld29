package ch.digitalmeat.ld29;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GroundRenderer {
	private TextureRegion ground = Assets.ground;
	private SpriteBatch batch = new SpriteBatch();

	public void draw(OrthographicCamera cam) {
		float zoom = cam.zoom;
		float stageWidth = LD29.VIEWPORT_WIDTH / zoom;
		float stageHeight = LD29.VIEWPORT_HEIGHT / zoom;
		float regionWidth = ground.getRegionWidth() / zoom;
		float regionHeight = ground.getRegionHeight() / zoom;

		int xDraws = (int) (stageWidth / regionWidth) + 2;
		int yDraws = (int) (stageHeight / regionHeight) + 2;

		int xOffset = (int) (((cam.position.x / zoom + stageWidth / 2) % regionWidth));
		int yOffset = (int) (((cam.position.y / zoom + stageHeight / 2) % regionHeight));

		batch.begin();
		for (int y = -1; y < yDraws; y++) {
			for (int x = -1; x < xDraws; x++) {
				batch.draw(ground, x * regionWidth - xOffset, y * regionHeight - yOffset, regionWidth, regionHeight);
			}
		}
		batch.end();
	}
}
