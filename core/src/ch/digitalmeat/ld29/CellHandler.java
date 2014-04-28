package ch.digitalmeat.ld29;

import com.badlogic.gdx.math.Vector2;

public class CellHandler {

	protected static final float LINEAR_FORCE = 18f;
	public static final float THRUST_FUEL_TIME = 1f;
	protected Vector2 force = new Vector2();
	protected Vector2 position = new Vector2();

	public CellHandler() {

	}

	public void handleThrust(Entity e, float delta) {
		CellData cell = e.cell;
		if (!e.thrusting) {
			cell.thrustTimer = 0;
			return;
		}
		cell.thrustTimer -= delta;
		// Gdx.app.log("Thrust", "Timer: " + cell.thrustTimer);
		while (cell.food > 0 && cell.thrustTimer < 0) {
			// Gdxa.app.log("Thrust", "Adding Fuel");
			cell.food--;
			cell.thrustTimer += THRUST_FUEL_TIME;
		}
		// Gdx.app.log("Thrust", "Timer: " + cell.thrustTimer);
		if (cell.thrustTimer < 0) {
			e.thrusting = false;
			// Gdx.app.log("Thrusting", "Disabled. Timer: " + cell.thrustTimer);
			cell.thrustTimer = 0;
		} else {
			// Gdx.app.log("Thrusting", "");
		}
	}
}