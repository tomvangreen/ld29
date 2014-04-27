package ch.digitalmeat.ld29;

import java.util.ArrayList;
import java.util.List;

import ch.digitalmeat.ld29.event.Events;
import ch.digitalmeat.ld29.event.MessageComplete;
import ch.digitalmeat.ld29.event.MessageCompleteHandler;
import ch.digitalmeat.ld29.event.ScriptEvent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;

public class Script implements Serializable, MessageCompleteHandler {
	public ScriptEvent current = null;
	public List<ScriptEvent> events = new ArrayList<ScriptEvent>();
	private GameWorld gameWorld;
	private float timer = 0;
	private boolean sequenceFinished;

	public Script() {
		Events.factory.getQueue().listen(MessageComplete.class, this);
	}

	public void update(float delta) {
		timer -= delta;
		if (current != null) {
			switch (current.action) {
			default:
				break;

			case Wait:
				if (timer < 0) {
					current = null;
				}
				break;
			case WaitForSequence:
				if (sequenceFinished) {
					current = null;
				}
				break;
			}
		} else if (events.size() > 0) {
			current = events.remove(0);
			switch (current.action) {
			default:
				break;
			case Message:
				Gdx.app.log("Script", "Message");
				Events.factory.message(current.key, false, false);
				current = null;
				break;
			case Sequence:
				Gdx.app.log("Script", "Sequence");
				Events.factory.message(current.key, true, false);
				current = null;
				break;
			case Wait:
				Gdx.app.log("Script", "Wait");
				timer = current.value;
				break;
			case WaitForSequence:
				Gdx.app.log("Script", "WaitForSequence");
				sequenceFinished = false;
				break;
			case EnemyCap:
				Gdx.app.log("Script", "EnemyCap");
				gameWorld.getSpawner().enemyCap = (int) current.value;
				current = null;
				break;
			case FoodCap:
				Gdx.app.log("Script", "FoodCap");
				gameWorld.getSpawner().foodCap = (int) current.value;
				current = null;
				break;
			case Populate:
				Gdx.app.log("Script", "Populate");
				gameWorld.getSpawner().populate();
				current = null;
				break;
			}
		}
	}

	@Override
	public void write(Json json) {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		JsonValue data = jsonData.get("script");
		if (data != null) {
			data = data.child();
		}
		while (data != null) {
			events.add(json.readValue(ScriptEvent.class, data));
			data = data.next();
		}
	}

	public void setGameWorld(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}

	@Override
	public void messagesCompleted() {
		Gdx.app.log("Script", "Sequence Finished");
		this.sequenceFinished = true;
	}
}
