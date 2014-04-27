package ch.digitalmeat.ld29.event;

public interface ShowMessageHandler {

	void queueMessage(String key, boolean isSequence, boolean clearExisting);
}
