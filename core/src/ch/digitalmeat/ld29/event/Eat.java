package ch.digitalmeat.ld29.event;

import ch.digitalmeat.ld29.Entity;

public class Eat implements GameEvent<EatListener> {
	public Entity cell;
	public Entity food;

	@Override
	public void notify(EatListener listener) {
		listener.eat(cell, food);
	}
}
