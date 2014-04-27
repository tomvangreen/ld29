package ch.digitalmeat.ld29.event;

import ch.digitalmeat.ld29.Entity;

public class Spawn implements GameEvent<SpawnListener> {

	public Entity entity;

	@Override
	public void notify(SpawnListener listener) {
		listener.spawn(entity);
	}

}
