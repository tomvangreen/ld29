package ch.digitalmeat.ld29;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class GameWorld {
	public final static Vector2 gravity = new Vector2(0, 0);
	private World world;
	private boolean running = false;
	private GameWorldRenderer renderer;

	public GameWorld() {
		world = new World(gravity, true);
		renderer = new GameWorldRenderer(world);
	}

	public void update(float delta) {
		if (world != null && running) {
			world.step(1f / 60f, 6, 3);
		}
	}

	public void draw() {
		renderer.draw();
	}
}
