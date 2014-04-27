package ch.digitalmeat.ld29;

import ch.digitalmeat.ld29.Entity.EntityType;
import ch.digitalmeat.ld29.event.Events;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class EntityContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		Fixture a = contact.getFixtureA();
		Fixture b = contact.getFixtureB();
		if (eats(a, b)) {
			Events.factory.eat(getEntity(a), getEntity(b));
		} else if (eats(b, a)) {
			Events.factory.eat(getEntity(b), getEntity(a));
		}
	}

	public boolean eats(Fixture a, Fixture b) {
		return isCell(a) && isFood(b);
	}

	public boolean isCell(Fixture fixture) {
		Entity entity = getEntity(fixture);
		if (entity == null) {
			return false;
		}
		return EntityType.Cell == entity.type;
	}

	public boolean isFood(Fixture fixture) {
		Entity entity = getEntity(fixture);
		if (entity == null) {
			return false;
		}
		return EntityType.Food == entity.type;
	}

	private Entity getEntity(Fixture fixture) {
		Object data = fixture.getBody().getUserData();
		if (data == null || !(data instanceof Entity)) {
			return null;
		}
		Entity entity = (Entity) data;
		return entity;
	}

	@Override
	public void endContact(Contact contact) {

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}

}
