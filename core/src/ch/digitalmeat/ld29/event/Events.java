package ch.digitalmeat.ld29.event;

import ch.digitalmeat.ld29.Entity;
import ch.digitalmeat.ld29.event.Spawn.SpawnType;

public class Events {
	public final static Events factory = new Events(new EventQueue());

	private final EventQueue queue;

	private SpawnType type;

	public Events(EventQueue queue) {
		this.queue = queue;
	}

	public EventQueue getQueue() {
		return queue;
	}

	private Eat eat() {
		return new Eat();
	}

	public void eat(Entity cell, Entity food) {
		Eat event = eat();
		event.cell = cell;
		event.food = food;
		queue.queue(event);
	}

	private ShowMessage message() {
		return new ShowMessage();
	}

	public void message(String key, boolean isSequence, boolean clearList) {
		ShowMessage event = message();
		event.key = key;
		event.isSequence = isSequence;
		event.clearExisting = clearList;
		queue.queue(event);
	}

	private Spawn spawn() {
		return new Spawn();
	}

	public void spawn(Entity entity, SpawnType type) {
		Spawn event = spawn();
		event.entity = entity;
		event.type = type;
		queue.queue(event);
	}
}
