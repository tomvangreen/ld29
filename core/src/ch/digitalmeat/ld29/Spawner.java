package ch.digitalmeat.ld29;

import java.util.Random;

import ch.digitalmeat.ld29.event.ChangeEnemyLevel;
import ch.digitalmeat.ld29.event.ChangeEnemyLevelListener;
import ch.digitalmeat.ld29.event.Events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Spawner implements ChangeEnemyLevelListener {

	private GameWorldFactory factory;

	public int food;
	public int foodCap = 0;
	public int enemy;
	public int enemyCap = 0;

	private float minX;

	private float maxX;

	private float minY;

	private float maxY;

	private Vector2 vector = new Vector2();

	private Random random;

	public static float SPAWN_INTERVAL = 0.5f;
	private float timer = SPAWN_INTERVAL;

	private int enemyLevel;

	public Spawner(GameWorldFactory factory, Random random, float minX, float maxX, float minY, float maxY) {
		Events.factory.getQueue().listen(ChangeEnemyLevel.class, this);
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
			int levelups = enemyLevel;
			while (levelups-- > 0) {
				entity.cell.levelUp(random);
			}
			entity.cell.life = entity.cell.lifeCap;
			enemy++;
			// Gdx.app.log("Spawner", "Enemy created");
			return true;
		}
		return false;
	}

	private boolean spawnFood() {
		if (food < foodCap) {
			Vector2 position = getSpawnLocation();
			spawnFood(position);
			return true;
		}
		return false;
	}

	private void spawnFood(Vector2 position) {
		Entity entity = null;
		if (random.nextInt(4) == 0) {
			entity = factory.createMediumFood(position.x, position.y);
		} else {
			entity = factory.createSmallFood(position.x, position.y);
		}
		Events.factory.spawn(entity);
		food++;
		// Gdx.app.log("Spawner", "Food created");
	}

	public void remove(final Entity entity) {
		if (!entity.active) {
			return;

		}
		entity.active = false;
		factory.getWorld().destroyBody(entity.body);
		entity.addAction(Actions.sequence(Actions.color(Colors.TRANSPARENT, 0.5f), Actions.run(new Runnable() {

			@Override
			public void run() {
				entity.remove();
				if (entity.light != null) {
					entity.light.remove();
				}
			}

		})));
		switch (entity.type) {
		default:
			break;
		case Cell:
			dropFoodOnDeath(entity);
			enemy--;
			break;
		case Food:
			food--;
			break;

		}
		entity.body = null;

	}

	private void dropFoodOnDeath(Entity entity) {
		CellData cell = entity.cell;
		int count = (cell.food + cell.attackCap + cell.foodCap) / 2;
		if (count > 50) {
			count = 50;
		}
		float width = 1;
		float height = 1;
		Gdx.app.log("Drop Food On Death:", "" + count);
		while (count-- > 0) {

			float x = random.nextFloat() * width - width / 2;
			float y = random.nextFloat() * height - height / 2;
			vector.set(x + entity.body.getPosition().x, y + entity.body.getPosition().y);
			spawnFood(vector);
		}
	}

	@Override
	public void changeEnemyLevel(int value) {
		enemyLevel += value;
		if (enemyLevel < 0) {
			enemyLevel = 0;
		}
		Gdx.app.log("Spawner", "Enemy Level: " + enemyLevel);
	}
}
