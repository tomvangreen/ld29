package ch.digitalmeat.ld29;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Entity extends Actor {
	public final static float METERS_TO_PIXELS = 16;

	public Body body;
	private final TextureRegion region = Assets.cell;

	@Override
	public void act(float delta) {
		super.act(delta);
		setPosition(body.getPosition().x * METERS_TO_PIXELS, body.getPosition().y * METERS_TO_PIXELS);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(region, getX() - region.getRegionWidth() / 2, getY() - region.getRegionHeight() / 2);
	}
}
