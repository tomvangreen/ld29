package ch.digitalmeat.ld29;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

public class PlayerHandler {

	private static final float LINEAR_FORCE = 8f;
	private Entity entity;
	private Vector2 force = new Vector2();
	private Vector2 position = new Vector2();

	public PlayerHandler(Entity entity) {
		this.entity = entity;
	}

	public void handleInput() {
		force.set(0, 0);
		boolean left = Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A);
		boolean right = Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D);
		boolean up = Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W);
		boolean down = Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S);
		if (left) {
			force.x -= 1;
		}
		if (right) {
			force.x += 1;
		}
		if (up) {
			force.y += 1;
		}
		if (down) {
			force.y -= 1;
		}
		entity.body.applyForceToCenter(force.nor().scl(LINEAR_FORCE), true);
	}

	public Vector2 getPosition() {
		position.set(entity.getX(), entity.getY());
		return position;
	}

	public Vector2 getVelocity() {
		return entity.body.getLinearVelocity();
	}

	public Entity getEntity() {
		return entity;
	}

}
