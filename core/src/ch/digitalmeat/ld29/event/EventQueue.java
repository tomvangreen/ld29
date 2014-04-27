package ch.digitalmeat.ld29.event;

import java.util.ArrayList;
import java.util.List;

public class EventQueue {
	private boolean iterating;
	private EventsDisptatcher dispatcher = new EventsDisptatcher();
	private List<GameEvent<?>> events = new ArrayList<GameEvent<?>>();
	private List<GameEvent<?>> eventsBack = new ArrayList<GameEvent<?>>();

	public <L> void listen(Class<? extends GameEvent<L>> eventClass, L listener) {
		dispatcher.listen(eventClass, listener);
	}

	public <L> void mute(Class<? extends GameEvent<L>> eventClass, L listener) {
		dispatcher.mute(eventClass, listener);
	}

	public void queue(GameEvent<?> event) {
		if (iterating) {
			eventsBack.add(event);
		} else {
			events.add(event);
		}
	}

	public void fire(GameEvent<?> event) {
		dispatcher.notify(event);
	}

	public void dispatch() {
		if (iterating) {
			return;
		}
		if (events.size() == 0) {
			return;
		}
		iterating = true;
		for (GameEvent<?> event : events) {
			dispatcher.notify(event);
		}
		events.clear();
		List<GameEvent<?>> tmp = events;
		events = eventsBack;
		eventsBack = tmp;
		iterating = false;
	}
}
