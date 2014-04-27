package ch.digitalmeat.ld29;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;

public class Message implements Serializable {
	public String key = "";
	public String message = "";
	public float onScreenTime = 3f;
	public String sequence = "";

	@Override
	public void write(Json json) {
		throw new RuntimeException("Not implemented yet");
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		key = json.readValue("key", String.class, jsonData);
		message = json.readValue("message", String.class, jsonData);
		onScreenTime = json.readValue("time", Float.class, jsonData);
		sequence = jsonData.getString("sequence", "");
	}
}
