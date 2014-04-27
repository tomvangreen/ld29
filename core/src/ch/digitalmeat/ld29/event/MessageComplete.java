package ch.digitalmeat.ld29.event;

public class MessageComplete implements GameEvent<MessageCompleteHandler> {

	@Override
	public void notify(MessageCompleteHandler listener) {
		listener.messagesCompleted();
	}

}
