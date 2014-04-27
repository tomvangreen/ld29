package ch.digitalmeat.ld29;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.digitalmeat.ld29.event.Eat;
import ch.digitalmeat.ld29.event.EatListener;
import ch.digitalmeat.ld29.event.Events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class GameWorld implements EatListener {
	public final static Vector2 gravity = new Vector2(0, 0);
	private World world;
	private GameWorldRenderer renderer;
	private GameWorldFactory factory;
	private PlayerHandler player;
	private List<Entity> enemies = new ArrayList<Entity>();
	private EntityContactListener contactListener;

	public GameWorld() {
		world = new World(gravity, true);
		renderer = new GameWorldRenderer(world);
		renderer.useDebugRenderer = false;
		factory = new GameWorldFactory(world, renderer.getStage(), renderer.getRayHandler());
		player = new PlayerHandler(factory.createCell(-10, 1, Colors.PLAYER_COLOR));

		contactListener = new EntityContactListener();
		world.setContactListener(contactListener);

		enemies.add(factory.createCell(5, 2, Colors.ENEMY_WEAK));
		enemies.add(factory.createCell(10, 10, Colors.ENEMY_NEUTRAL));
		enemies.add(factory.createCell(-10, -10, Colors.ENEMY_STRONG));

		Random random = new Random();
		int generateFood = 100;
		while (generateFood-- > 0) {
			float x = random.nextFloat() * 60 - 30;
			float y = random.nextFloat() * 60 - 30;
			if (random.nextBoolean()) {
				factory.createSmallFood(x, y);
			} else {
				factory.createMediumFood(x, y);
			}
		}

		Events.factory.getQueue().listen(Eat.class, this);

	}

	public Entity getPlayer() {
		return player.getEntity();
	}

	public void update(float delta) {
		player.handleInput();
		world.step(1f / 60f, 6, 3);
		renderer.update();
		if (player != null) {
			Vector2 velocity = player.getVelocity();
			// Gdx.app.log("PlayerSpeed", velocity.toString());
			float length = velocity.len();
			float minZoomVel = 0f;
			float maxZoomVel = 32f;
			if (length < minZoomVel) {
				length = minZoomVel;
			}
			if (length > maxZoomVel) {
				length = maxZoomVel;
			}
			// Gdx.app.log("Trimmed Length", "" + length);
			float value = (length - 1) / (maxZoomVel - minZoomVel);
			// Gdx.app.log("Value", "" + value);
			float zoom = Interpolation.linear.apply(0.4f, 0.6f, value);
			// Gdx.app.log("Zoom", "" + zoom);
			renderer.setSceneZoom(zoom);
		}
	}

	public void draw() {
		renderer.draw(player.getPosition());
	}

	@Override
	public void eat(Entity cell, Entity food) {
		Gdx.app.log("Contact", "Eat");
		Assets.blip01.play(0.5f);
		cell.cell.eat(food.food.foodValue);
		world.destroyBody(food.body);
		food.remove();
	}
}
