package ch.digitalmeat.ld29;

import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameWorldFactory {
	private World world;
	private Stage stage;
	private RayHandler rayHandler;

	public GameWorldFactory(World world, Stage stage, RayHandler rayHandler) {
		this.world = world;
		this.stage = stage;
		this.rayHandler = rayHandler;
	}

	public Entity createSmallFood(float x, float y) {
		Color color = Colors.FOOD_SMALL;
		Entity entity = new Entity();
		entity.body = createCellBody(x, y, 0.0625f);
		entity.setColor(color);
		entity.region = Assets.small_food;
		entity.light = new PointLight(rayHandler, 50, color, 0.5f, x, y);
		stage.addActor(entity);
		return entity;
	}

	public Entity createMediumFood(float x, float y) {
		Color color = Colors.FOOD_MEDIUM;
		Entity entity = new Entity();
		entity.body = createCellBody(x, y, 0.125f);
		entity.setColor(color);
		entity.region = Assets.medium_food;
		entity.light = new PointLight(rayHandler, 50, color, 1, x, y);
		stage.addActor(entity);
		return entity;
	}

	public Entity createCell(float x, float y) {
		return createCell(x, y, Color.WHITE);
	}

	public Entity createCell(float x, float y, Color color) {
		Entity entity = new Entity();
		entity.body = createCellBody(x, y, 0.5f);
		entity.setColor(color);
		entity.light = new PointLight(rayHandler, 50, color, 5, x, y);
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
