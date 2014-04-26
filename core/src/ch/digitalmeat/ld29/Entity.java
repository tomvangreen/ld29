package ch.digitalmeat.ld29;

import box2dLight.PositionalLight;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Entity extends Actor {
	public final static float METERS_TO_PIXELS = 16;

	private Color lightColor = new Color();

	public Body body;
	public PositionalLight light;

	public TextureRegion region = Assets.cell;

	@Override
	public void act(float delta) {
		super.act(delta);
		lightColor.set(getColor());
		lightColor.a = 0.75f;
		setPosition(body.getPosition().x * METERS_TO_PIXELS, body.getPosition().y * METERS_TO_PIXELS);
		light.setColor(lightColor);
		light.setPosition(body.getPosition());
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(getColor());
		batch.draw(region, getX() - region.getRegionWidth() / 2, getY() - region.getRegionHeight() / 2);
	}
}
