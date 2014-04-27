package ch.digitalmeat.ld29;

import java.util.Random;

import ch.digitalmeat.ld29.event.Events;

import com.badlogic.gdx.math.Vector2;

public class Spawner {

	private GameWorldFactory factory;

	public int food;
	public int foodCap = 0;
	public int enemy;
	public int enemyCap = 3;

	private float minX;

	private float maxX;

	private float minY;

	private float maxY;

	private Vector2 vector = new Vector2();

	private Random random;

	public static float SPAWN_INTERVAL = 4f;
	private float timer = SPAWN_INTERVAL;

	public Spawner(GameWorldFactory factory, Random random, float minX, float maxX, float minY, float maxY) {
		this.factory = factory;
		this.random = random;
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
	}

	private Vector2 getSpawnLocation() {
		float width = maxX - minX;
		float height = maxY - minY;

		float x = random.nextFloat() * width - width / 2;
		float y = random.nextFloat() * height - height / 2;
		vector.set(x, y);
		return vector;
	}

	public void populate() {
		while (spawnFood()) {
		}
		while (spawnEnemy()) {
		}
	}

	public void update(float delta) {
		timer -= delta;
		while (timer < 0) {
			spawn();
			timer += SPAWN_INTERVAL;
		}
	}

	public void spawn() {
		int choice = random.nextInt(4);
		switch (choice) {
		case 0:
		default:
			spawnFood();
			break;
		case 2:
			spawnEnemy();
			break;
		}
	}

	private boolean spawnEnemy() {
		if (enemy < enemyCap) {
			Vector2 position = getSpawnLocation();
			Entity entity = factory.createCell(position.x, position.y, Colors.ENEMY_STRONG);
			Events.factory.spawn(entity);
			enemy++;
			// Gdx.app.log("Spawner", "Enemy created");
			return true;
		}
		return false;
	}

	private boolean spawnFood() {
		if (food < foodCap) {
			Vector2 position = getSpawnLocation();
			Entity entity = null;
			if (random.nextInt(4) == 0) {
				entity = factory.createMediumFood(position.x, position.y);
			} else {
				entity = factory.createSmallFood(position.x, position.y);
			}
			Events.factory.spawn(entity);
			food++;
			// Gdx.app.log("Spawner", "Food created");
			return true;
		}
		return false;
	}

	public void remove(Entity entity) {

		entity.active = false;
		factory.getWorld().destroyBody(entity.body);
		entity.remove();
		if (entity.light != null) {
			entity.light.remove();
			entity.light = null;
		}
		switch (entity.type) {
		default:
			break;
		case Cell:
			enemy--;
			break;
		case Food:
			food--;
			break;

		}

	}
}
