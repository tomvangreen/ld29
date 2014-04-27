package ch.digitalmeat.ld29;

import java.util.Random;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import ch.digitalmeat.ld29.Entity.EntityType;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class GameWorldFactory {

	public final static short MASK_CELL = 0x0001;
	public final static short MASK_FOOD = 0x0002;

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
		entity.body = createRoundBody(x, y, 0.1f, MASK_FOOD);
		entity.setColor(color);
		entity.region = Assets.small_food;
		entity.light = new PointLight(rayHandler, 50, color, 1.5f, x, y);
		entity.body.setUserData(entity);
		entity.type = EntityType.Food;
		entity.food = new FoodData();
		entity.food.foodValue = 1;
		entity.setColor(Color.BLACK);
		entity.addAction(Actions.color(color, 2f));
		entity.setZIndex(10);
		applyRandomForce(entity.body, 1f);
		stage.addActor(entity);
		return entity;
	}

	public Entity createMediumFood(float x, float y) {
		Color color = Colors.FOOD_MEDIUM;
		Entity entity = new Entity();
		entity.body = createRoundBody(x, y, 0.15f, MASK_FOOD);
		entity.setColor(color);
		entity.region = Assets.medium_food;
		entity.light = new PointLight(rayHandler, 50, color, 3, x, y);
		stage.addActor(entity);
		entity.body.setUserData(entity);
		entity.type = EntityType.Food;
		entity.food = new FoodData();
		entity.food.foodValue = 2;
		entity.setColor(Color.BLACK);
		entity.addAction(Actions.color(color, 2f));
		applyRandomForce(entity.body, 4f);
		return entity;
	}

	public Entity createCell(float x, float y) {
		return createCell(x, y, Color.WHITE);
	}

	public Entity createCell(float x, float y, Color color) {
		Entity entity = new Entity();
		entity.body = createRoundBody(x, y, 0.5f, MASK_CELL);
		entity.setColor(color);
		entity.light = new PointLight(rayHandler, 300, color, 7.5f, x, y);
		entity.cell = new CellData();
		entity.body.setUserData(entity);
		entity.type = EntityType.Cell;
		entity.regionScale = 1f / 8;
		stage.addActor(entity);
		entity.setColor(Color.BLACK);
		entity.addAction(Actions.color(color, 2f));
		return entity;
	}

	public Body createRoundBody(float x, float y, float radius, short categoryBits) {
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
		// fixtureDef.filter.categoryBits = categoryBits;

		body.createFixture(fixtureDef);
		body.setAngularDamping(0.3f);
		body.setLinearDamping(0.03f);
		return body;
	}

	public Body createSolidBox(float x, float y, float width, float height) {
		float hw = width / 2;
		float hh = height / 2;
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(x + hw, y + hh);

		PolygonShape bodyShape = new PolygonShape();
		bodyShape.setAsBox(hw, hh);

		Body body = world.createBody(bodyDef);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = bodyShape;
		body.createFixture(fixtureDef);
		Image image = new Image(Assets.blank);
		float mtp = Entity.METERS_TO_PIXELS;
		image.setColor(Color.BLACK);
		image.setBounds(x * mtp, y * mtp, width * mtp, height * mtp);
		stage.addActor(image);
		return body;
	}

	public World getWorld() {
		return world;
	}
}
