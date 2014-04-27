package ch.digitalmeat.ld29.event;

import ch.digitalmeat.ld29.Entity;

public class Attack implements GameEvent<AttackListener> {
	public Entity a;
	public Entity b;

	@Override
	public void notify(AttackListener listener) {
		listener.attack(a, b);
	}

}
