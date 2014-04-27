package ch.digitalmeat.ld29;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.digitalmeat.ld29.Entity.EntityType;
import ch.digitalmeat.ld29.event.Eat;
import ch.digitalmeat.ld29.event.EatListener;
import ch.digitalmeat.ld29.event.Events;
import ch.digitalmeat.ld29.event.Spawn;
import ch.digitalmeat.ld29.event.SpawnListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class GameWorld implements EatListener, SpawnListener {
	public final static Vector2 gravity = new Vector2(0, 0);
	private World world;
	private GameWorldRenderer renderer;
	private GameWorldFactory factory;
	private PlayerHandler player;
	private List<Entity> enemies = new ArrayList<Entity>();
	private List<Entity> food = new ArrayList<Entity>();
	private List<Entity> cells = new ArrayList<Entity>();
	private EntityContactListener contactListener;
	private Random random;
	private Spawner spawner;

	private Vector2 v1 = new Vector2();
	private Vector2 v2 = new Vector2();
	private AiCellHandler ai;

	public GameWorld() {
		Events.factory.getQueue().listen(Spawn.class, this);
		world = new World(gravity, true);
		float min = -30;
		float max = 30;
		renderer = new GameWorldRenderer(world, max - min, max - min);
		renderer.useDebugRenderer = false;

		factory = new GameWorldFactory(world, renderer.getStage(), renderer.getRayHandler());
		player = new PlayerHandler(factory.createCell(-10, 1, Colors.PLAYER_COLOR));
		cells.add(player.getEntity());
		contactListener = new EntityContactListener();
		world.setContactListener(contactListener);
		random = new Random();
		spawner = new Spawner(factory, random, min, max, min, max);
		spawner.populate();
		min = -35;
		max = 35;
		float size = max - min;
		float wallWidth = 2.5f;
		factory.createSolidBox(min, min, wallWidth, size + wallWidth);
		factory.createSolidBox(max, min, wallWidth, size + wallWidth);
		factory.createSolidBox(min, min, size + wallWidth, wallWidth);
		factory.createSolidBox(min, max, size + wallWidth, wallWidth);

		Events.factory.getQueue().listen(Eat.class, this);
		ai = new AiCellHandler(this);
		camPosition.set(player.getPosition());
		camTarget.set(player.getPosition());
		nextTarget.set(player.getPosition());
		lastTarget.set(player.getPosition());
		zoom = 0.35f;
		nextZoom = zoom;
		lastZoom = zoom;
		zoomTarget = zoom;
	}

	public Entity getPlayer() {
		return player.getEntity();
	}

	private float zoom;
	private float zoomTarget;
	private float lastZoom;
	private float nextZoom;
	private Vector2 camPosition = new Vector2();
	private Vector2 camTarget = new Vector2();
	private Vector2 nextTarget = new Vector2();
	private Vector2 lastTarget = new Vector2();
	private float camTimer = 0;
	public final static float CAM_UPDATE_TIME = 0.4f;

	public void update(float delta) {
		player.handleInput();
		for (Entity enemy : enemies) {
			ai.update(enemy, delta);
		}
		world.step(1f / 60f, 6, 3);
		renderer.update();
		if (player != null) {
			Vector2 velocity = player.getVelocity();
			// Gdx.app.log("PlayerSpeed", velocity.toString());
			float length = velocity.len();
			float minZoomVel = 0f;
			float maxZoomVel = Entity.maxSpeed;
			if (length < minZoomVel) {
				length = minZoomVel;
			}
			if (length > maxZoomVel) {
				length = maxZoomVel;
			}
			// Gdx.app.log("Trimmed Length", "" + length);
			float value = (length - 1) / (maxZoomVel - minZoomVel);
			// Gdx.app.log("Value", "" + value);
			zoomTarget = Interpolation.linear.apply(0.35f, 0.5f, value);
			// Gdx.app.log("Zoom", "" + zoom);
			// zoom = 1f;
		}
		spawner.update(delta);

		camTarget.set(player.getPosition());

		camTimer -= delta;
		float scale = (CAM_UPDATE_TIME - camTimer) / CAM_UPDATE_TIME;
		if (camTimer < 0) {
			camTimer = CAM_UPDATE_TIME;
			// zoom = zoomTarget;
			camPosition.set(camTarget);
			lastTarget.set(nextTarget);
			nextTarget.set(camTarget);
			lastZoom = nextZoom;
			nextZoom = zoomTarget;
			camPosition.set(lastTarget);
			zoom = lastZoom;
		} else {

			// Gdx.app.log("GameWorld", "Scale: " + scale);
			camPosition.x = Interpolation.linear.apply(lastTarget.x, nextTarget.x, scale);
			camPosition.y = Interpolation.linear.apply(lastTarget.y, nextTarget.y, scale);
			zoom = Interpolation.linear.apply(lastZoom, nextZoom, scale);
			renderer.setSceneZoom(zoom);
		}
	}

	public void draw() {
		renderer.draw(camPosition);
	}

	@Override
	public void eat(Entity cell, Entity food) {
		Gdx.app.log("Contact", "Eat");
		Assets.blip01.play(0.5f);
		cell.cell.eat(food.food.foodValue);
		while (cell.cell.levelUps > 0) {
			Gdx.app.log("Eat", "LevelUp");
			cell.cell.levelUps--;
			cell.cell.levelUp(random);
		}
		this.food.remove(food);
		spawner.remove(food);
	}

	@Override
	public void spawn(Entity entity) {
		switch (entity.type) {
		case Cell:
			cells.add(entity);
			enemies.add(entity);
			break;
		case Food:
			food.add(entity);
			break;
		default:
			break;
		}

	}

	public Spawner getSpawner() {
		return spawner;
	}

	public Entity findNearestFood(Entity source) {
		return findNearestEntity(source, EntityType.Food);
	}

	public Entity findNearestCell(Entity source) {
		return findNearestEntity(source, EntityType.Cell);
	}

	public Entity findNearestEntity(Entity source, EntityType type) {
		List<Entity> list = null;
		if (type == EntityType.Food) {
			list = food;
		} else if (type == EntityType.Cell) {
			list = cells;
		} else {
			return null;
		}
		float length = 0;
		Entity candidate = null;
		for (Entity test : list) {
			if (test != source) {
				if (candidate == null) {
					candidate = test;
					v1.set(candidate.getX(), candidate.getY());
					v2.set(source.getX(), source.getY());

					length = v1.sub(v2).len();
				} else {
					v1.set(test.getX(), test.getY());
					float v2Length = v1.sub(v2).len();
					Gdx.app.log("Ai", "Old Length: " + length + ", New Length: " + v2.len());
					if (v2Length < length) {
						Gdx.app.log("Ai", "Taking new length");
						candidate = test;
						length = v2Length;
					}
				}
			}
		}
		return candidate;
	}
}
