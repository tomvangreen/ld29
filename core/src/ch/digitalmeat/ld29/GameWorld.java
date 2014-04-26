package ch.digitalmeat.ld29;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class GameWorld {
	public final static Vector2 gravity = new Vector2(0, 0);
	private World world;
	private GameWorldRenderer renderer;
	private GameWorldFactory factory;
	private PlayerHandler player;

	public GameWorld() {
		world = new World(gravity, true);
		renderer = new GameWorldRenderer(world);
		renderer.useDebugRenderer = false;
		factory = new GameWorldFactory(world, renderer.getStage(), renderer.getRayHandler());
		player = new PlayerHandler(factory.createCell(-10, 1, Colors.PLAYER_COLOR));
		factory.createCell(5, 2, Colors.ENEMY_WEAK);
		factory.createCell(10, 10, Colors.ENEMY_NEUTRAL);
		factory.createCell(-10, -10, Colors.ENEMY_STRONG);

		Random random = new Random();
		int generateFood = 500;
		while (generateFood-- > 0) {
			float x = random.nextFloat() * 60 - 30;
			float y = random.nextFloat() * 60 - 30;
			if (random.nextBoolean()) {
				factory.createSmallFood(x, y);
			} else {
				factory.createMediumFood(x, y);
			}
		}
	}

	public void update(float delta) {
		if (player != null) {
			player.handleInput();
		}
		world.step(1f / 60f, 6, 3);
		renderer.update();
	}

	public void draw() {
		renderer.draw(player.getPosition());
	}
}
