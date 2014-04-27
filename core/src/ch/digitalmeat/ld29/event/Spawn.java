package ch.digitalmeat.ld29.event;

import ch.digitalmeat.ld29.Entity;

public class Spawn implements GameEvent<SpawnListener> {

	public Entity entity;
	public SpawnType type;

	@Override
	public void notify(SpawnListener listener) {
		listener.spawn(type, entity);
	}

	public enum SpawnType {
		SmallFood, MediumFood, Enemy
	}

}
