package ch.digitalmeat.ld29;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;

public class MessageData implements Serializable {
	public List<Message> messages = new ArrayList<Message>();

	@Override
	public void write(Json json) {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void read(Json json, JsonValue data) {
		Gdx.app.log("MessageData", "Reading messages");
		if (data != null) {
			data = data.get("messages");
			if (data != null) {
				data = data.child();
			}
		}
		while (data != null) {
			Message message = json.readValue(Message.class, data);
			messages.add(message);
			data = data.next();
		}
	}
}
