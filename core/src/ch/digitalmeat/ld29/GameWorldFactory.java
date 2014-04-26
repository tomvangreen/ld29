package ch.digitalmeat.ld29;

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

	public GameWorldFactory(World world, Stage stage) {
		this.world = world;
		this.stage = stage;
	}

	public Entity createCell(float x, float y) {
		Entity entity = new Entity();
		entity.body = createCellBody(x, y);
		stage.addActor(entity);
		return entity;
	}

	public Body createCellBody(float x, float y) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);

		CircleShape bodyShape = new CircleShape();
		bodyShape.setRadius(1f);
		Body body = world.createBody(bodyDef);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = bodyShape;
		fixtureDef.density = 1f;
		fixtureDef.friction = 0.3f;
		fixtureDef.restitution = 1f;

		body.createFixture(fixtureDef);

		return body;
	}
}
