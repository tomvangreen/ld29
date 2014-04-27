package ch.digitalmeat.ld29;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GroundRenderer extends Actor {
	private TextureRegion ground = Assets.ground;
	// private SpriteBatch batch = new SpriteBatch();
	private OrthographicCamera cam;
	private int startX;
	private int endX;
	private int startY;
	private int endY;

	public GroundRenderer(float width, float height) {
		float tilesX = (width * Entity.METERS_TO_PIXELS) / ground.getRegionWidth() + 6;
		float tilesY = (width * Entity.METERS_TO_PIXELS) / ground.getRegionWidth() + 6;
		int htx = (int) tilesX / 2;
		int hty = (int) tilesY / 2;
		this.startX = -htx;
		this.endX = htx;
		this.startY = -hty;
		this.endY = hty;
	}

	public void setCam(OrthographicCamera cam) {
		this.cam = cam;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		float zoom = cam.zoom;
		float stageWidth = LD29.VIEWPORT_WIDTH;
		float stageHeight = LD29.VIEWPORT_HEIGHT;
		float regionWidth = ground.getRegionWidth();
		float regionHeight = ground.getRegionHeight();

		int xDraws = (int) (stageWidth / regionWidth) + 2;
		int yDraws = (int) (stageHeight / regionHeight) + 2;

		int xOffset = 0;
		int yOffset = 0;
		Gdx.app.log("Cam", cam.position.toString());
		Gdx.app.log("x offset", "" + xOffset);
		batch.setColor(Color.WHITE);
		int xs = -5;
		int ys = -5;
		for (int y = startY; y < endY; y++) {
			for (int x = startX; x < endX; x++) {
				batch.draw(ground, x * regionWidth + xOffset, y * regionHeight + yOffset, regionWidth, regionHeight);
			}
		}
		// batch.end();
	}
}
