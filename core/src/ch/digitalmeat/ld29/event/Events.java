package ch.digitalmeat.ld29.event;

import ch.digitalmeat.ld29.Entity;

public class Events {
	public final static Events factory = new Events(new EventQueue());

	private final EventQueue queue;

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

	public void spawn(Entity entity) {
		Spawn event = spawn();
		event.entity = entity;
		queue.queue(event);
	}

	private MessageComplete newMessageComplete() {
		return new MessageComplete();
	}

	public void messageComplete() {
		queue.queue(newMessageComplete());
	}

	private Attack attack() {
		return new Attack();
	}

	public void attack(Entity a, Entity b) {
		Attack event = attack();
		event.a = a;
		event.b = b;
		queue.queue(event);
	}
}
