package ch.digitalmeat.ld29.event;

import ch.digitalmeat.ld29.Entity;
import ch.digitalmeat.ld29.event.Spawn.SpawnType;

public interface SpawnListener {
	public void spawn(SpawnType type, Entity entity);
}
