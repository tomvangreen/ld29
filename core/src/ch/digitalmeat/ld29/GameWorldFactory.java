package ch.digitalmeat.ld29;

import java.util.Random;

import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameWorldFactory {
	public final static float FOOD_FORCE = 0.25f;
	public final static float FOOD_ROTATION_FORCE = 0.0001f;
	private World world;
	private Stage stage;
	private RayHandler rayHandler;

	private Vector2 force = new Vector2();
	private Random random;

	public GameWorldFactory(World world, Stage stage, RayHandler rayHandler) {
		this.world = world;
		this.stage = stage;
		this.rayHandler = rayHandler;
		this.random = new Random();
	}

	private void applyRandomForce(Body body, float forceScale) {

		float effectiveForce = FOOD_FORCE * forceScale;
		float effectiveAngularForce = FOOD_ROTATION_FORCE * forceScale;

		force.set(random.nextFloat() * effectiveForce * 2 - effectiveForce, random.nextFloat() * effectiveForce * 2 - effectiveForce);
		body.applyForceToCenter(force, true);
		body.applyAngularImpulse(random.nextFloat() * effectiveAngularForce * 2 - effectiveAngularForce, true);
	}

	public Entity createSmallFood(float x, float y) {
		Color color = Colors.FOOD_SMALL;
		Entity entity = new Entity();
		entity.body = createCellBody(x, y, 0.0625f);
		entity.setColor(color);
		entity.region = Assets.small_food;
		entity.light = new PointLight(rayHandler, 50, color, 1.5f, x, y);
		stage.addActor(entity);
		applyRandomForce(entity.body, 1f);
		return entity;
	}

	public Entity createMediumFood(float x, float y) {
		Color color = Colors.FOOD_MEDIUM;
		Entity entity = new Entity();
		entity.body = createCellBody(x, y, 0.125f);
		entity.setColor(color);
		entity.region = Assets.medium_food;
		entity.light = new PointLight(rayHandler, 50, color, 3, x, y);
		stage.addActor(entity);
		applyRandomForce(entity.body, 4f);
		return entity;
	}

	public Entity createCell(float x, float y) {
		return createCell(x, y, Color.WHITE);
	}

	public Entity createCell(float x, float y, Color color) {
		Entity entity = new Entity();
		entity.body = createCellBody(x, y, 0.4f);
		entity.setColor(color);
		entity.light = new PointLight(rayHandler, 50, color, 5, x, y);
		entity.cell = new CellData();
		entity.cell.currentFood = 10;
		entity.cell.foodCap = 50;
		stage.addActor(entity);
		return entity;
	}

	public Body createCellBody(float x, float y, float radius) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);

		CircleShape bodyShape = new CircleShape();
		bodyShape.setRadius(radius);
		Body body = world.createBody(bodyDef);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = bodyShape;
		fixtureDef.density = 1f;
		fixtureDef.friction = 0.3f;
		fixtureDef.restitution = 0.5f;

		body.createFixture(fixtureDef);

		return body;
	}
}
