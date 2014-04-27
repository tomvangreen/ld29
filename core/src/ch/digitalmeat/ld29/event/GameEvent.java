package ch.digitalmeat.ld29.event;

public interface GameEvent<L> {
   public void notify(final L listener);
}
