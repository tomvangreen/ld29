package ch.digitalmeat.ld29;

import java.util.Random;

import ch.digitalmeat.ld29.event.Events;
import ch.digitalmeat.ld29.event.Spawn.SpawnType;

import com.badlogic.gdx.math.Vector2;

public class Spawner {

	private GameWorldFactory factory;

	public int smallFood;
	public int smallFoodCap = 10;
	public int mediumFood = 0;
	public int mediumFoodCap = 10;
	public int enemy;
	public int enemyCap = 0;

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
			spawnSmallFood();
			break;
		case 1:
			spawnMediumFood();
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
			Events.factory.spawn(entity, SpawnType.Enemy);
			enemy++;
			return true;
		}
		return false;
	}

	private boolean spawnMediumFood() {
		if (smallFood < smallFoodCap) {
			Vector2 position = getSpawnLocation();
			Entity entity = factory.createSmallFood(position.x, position.y);
			Events.factory.spawn(entity, SpawnType.SmallFood);
			smallFood++;
			return true;
		}
		return false;
	}

	private boolean spawnSmallFood() {
		if (mediumFood < mediumFoodCap) {
			Vector2 position = getSpawnLocation();
			Entity entity = factory.createMediumFood(position.x, position.y);
			Events.factory.spawn(entity, SpawnType.MediumFood);
			mediumFood++;
			return true;
		}
		return false;
	}
}
