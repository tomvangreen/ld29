package ch.digitalmeat.ld29;

import box2dLight.PositionalLight;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Entity extends Actor {
	public static final float MAX_SPEED = 4f;
	public static final float MAX_SPEED_THRUSTED = 6f;
	public final static float METERS_TO_PIXELS = 16;
	public float maxSpeed = MAX_SPEED;
	public boolean thrusting;
	private Color lightColor = new Color();

	public Body body;
	public PositionalLight light;
	public CellData cell;

	public TextureRegion region = Assets.cell;

	public EntityType type;

	public float regionScale = 1f;

	public FoodData food;

	public boolean active;

	@Override
	public void act(float delta) {
		super.act(delta);
		maxSpeed = thrusting ? MAX_SPEED_THRUSTED : MAX_SPEED;
		if (body != null) {
			Vector2 velocity = body.getLinearVelocity();
			float speed = velocity.len();

			if (speed > maxSpeed) {
				body.setLinearVelocity(velocity.scl(maxSpeed / speed));
			}
			setPosition(body.getPosition().x * METERS_TO_PIXELS, body.getPosition().y * METERS_TO_PIXELS);
			setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		}

		if (type == EntityType.Cell && active) {
			float scale = (0f + cell.life) / cell.lifeCap;
			//@formatter:off
			setColor(
				Interpolation.linear.apply(Colors.ENEMY_STRONG.r, Colors.PLAYER_COLOR.r, scale)
				, Interpolation.linear.apply(Colors.ENEMY_STRONG.g, Colors.PLAYER_COLOR.g, scale)
				, Interpolation.linear.apply(Colors.ENEMY_STRONG.b, Colors.PLAYER_COLOR.b, scale)
				, Interpolation.linear.apply(Colors.ENEMY_STRONG.a, Colors.PLAYER_COLOR.a, scale)
			);
			//@formatter:on
		}

		lightColor.set(getColor());
		// lightColor.a = 0.75f;
		if (light != null) {
			light.setColor(lightColor);
			if (body != null) {
				light.setPosition(body.getPosition());
			}
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		int regionWidth = (int) (region.getRegionWidth() * regionScale);
		int regionHeight = (int) (region.getRegionHeight() * regionScale);
		int halfRegionWidth = regionWidth / 2;
		int halfRegionHeight = regionHeight / 2;
		float x = getX() - halfRegionWidth;
		float y = getY() - halfRegionHeight;

		batch.setColor(getColor());
		batch.draw(region, x, y, halfRegionWidth, halfRegionHeight, regionWidth, regionHeight, 1f, 1f, getRotation());
		// batch.draw(region, x, y);
	}

	public enum EntityType {
		Cell, Food
	}
}
