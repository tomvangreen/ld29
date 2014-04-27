package ch.digitalmeat.ld29.event;

public class ShowMessage implements GameEvent<ShowMessageHandler> {
	public boolean isSequence;
	public boolean clearExisting;
	public String key;

	@Override
	public void notify(ShowMessageHandler listener) {
		listener.queueMessage(key, isSequence, clearExisting);
	}

}
