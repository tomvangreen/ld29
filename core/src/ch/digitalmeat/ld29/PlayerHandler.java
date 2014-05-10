package ch.digitalmeat.ld29;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class PlayerHandler extends CellHandler {
	private Trap heal = new Trap();
	private Trap thrust = new Trap();
	private Entity entity;

	public PlayerHandler(Entity entity) {
		this.entity = entity;
	}

	Vector3 vec3 = new Vector3();

	int mainTouch = -1;

	public void handleInput(float delta) {
		force.set(0, 0);
		boolean left = Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A);
		boolean right = Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D);
		boolean up = Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W);
		boolean down = Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S);
		int touches = 0;
		if (Gdx.app.getType() == ApplicationType.Android) {
			int lastTouch = -1;
			for (int index = 0; index < 8; index++) {
				if (Gdx.input.isTouched(index)) {
					lastTouch = index;
					if (mainTouch == -1) {
						mainTouch = index;
					}
					touches++;
				}
			}
			if (touches == 1) {
				mainTouch = lastTouch;
			}
		} else {
			if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
				mainTouch = 0;
				touches++;
			}
			if (Gdx.input.isButtonPressed(Buttons.RIGHT)) {
				mainTouch = 0;
				touches++;
			}
		}

		if (touches == 0) {
			mainTouch = -1;
		}
		if (entity.body != null) {
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

			if (mainTouch >= 0) {
				vec3.x = Gdx.input.getX(mainTouch);
				vec3.y = Gdx.input.getY(mainTouch);
				vec3.z = 0;
				LD29.viewport.getCamera().unproject(vec3);
				vec3.x -= LD29.VIEWPORT_WIDTH / 2;
				vec3.y -= LD29.VIEWPORT_HEIGHT / 2;
				Gdx.app.log("Touch", vec3.x + "/" + vec3.y);
				if (vec3.len() > 75) {
					force.x = vec3.x;
					force.y = vec3.y;
				}
			}

			entity.body.applyForceToCenter(force.nor().scl(LINEAR_FORCE), true);
		}
		heal.update(Gdx.input.isKeyPressed(Keys.N));

		boolean thrusting = Gdx.input.isKeyPressed(Keys.SPACE) || touches > 1;
		Gdx.app.log("Touches", "" + touches);
		entity.thrusting = thrusting;
		handleThrust(entity, delta);
		if (heal.down()) {
			CellData cell = entity.cell;
			if (cell.food > 0 && cell.life < cell.lifeCap) {
				cell.food--;
				cell.life++;
			}
		}

	}

	public Vector2 getPosition() {
		position.set(entity.getX(), entity.getY());
		return position;
	}

	public Vector2 getVelocity() {

		return entity.body == null ? null : entity.body.getLinearVelocity();
	}

	public Entity getEntity() {
		return entity;
	}

}
