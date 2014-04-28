package ch.digitalmeat.ld29.event;

public class ChangeEnemyLevel implements GameEvent<ChangeEnemyLevelListener> {
	public int value;

	@Override
	public void notify(ChangeEnemyLevelListener listener) {
		listener.changeEnemyLevel(value);
	}

}
