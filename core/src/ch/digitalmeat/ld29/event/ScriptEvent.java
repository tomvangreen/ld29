package ch.digitalmeat.ld29.event;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;

public class ScriptEvent implements Serializable, GameEvent<ScriptEventHandler> {

	public Action action;
	public String key;
	public float value;

	@Override
	public void notify(ScriptEventHandler listener) {

	}

	@Override
	public void write(Json json) {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		action = json.readValue("action", Action.class, jsonData);
		if (action == Action.Sequence || action == Action.Message) {
			key = json.readValue("key", String.class, jsonData);
		} else if (action == Action.Wait) {
			value = json.readValue("delay", Float.class, jsonData);
		} else if (action == Action.FoodCap || action == Action.EnemyCap || action == Action.EnemyLevel) {
			value = json.readValue("value", Float.class, jsonData);
		}
	}

	public enum Action {
		WaitForSequence, Wait, Sequence, Message, Populate, FoodCap, EnemyCap, EnemyLevel
	}
}
