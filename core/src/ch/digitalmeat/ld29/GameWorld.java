package ch.digitalmeat.ld29;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class GameWorld {
	public final static Vector2 gravity = new Vector2(0, 0);
	private World world;
	private GameWorldRenderer renderer;
	private GameWorldFactory factory;

	public GameWorld() {
		world = new World(gravity, true);
		renderer = new GameWorldRenderer(world);
		factory = new GameWorldFactory(world, renderer.getStage());
		factory.createCell(1, 1);
		factory.createCell(5, 2);
		factory.createCell(10, 10);
	}

	public void update(float delta) {
		world.step(1f / 60f, 6, 3);
		renderer.update();
	}

	public void draw() {

		renderer.draw();
	}

}
