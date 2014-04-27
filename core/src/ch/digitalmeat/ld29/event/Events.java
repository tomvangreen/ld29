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

}
