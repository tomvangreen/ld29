package ch.digitalmeat.ld29;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Entity extends Actor {
	public Body body;

	@Override
	public void act(float delta) {
		super.act(delta);
		setPosition(body.getPosition().x, body.getPosition().y);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Color.RED);

	}
}
