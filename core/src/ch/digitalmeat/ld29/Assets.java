package ch.digitalmeat.ld29;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;

public class Assets {
	public static TextureRegion cell;
	public static TextureRegion small_food;
	public static TextureRegion medium_food;
	public static TextureRegion ground;
	public static TextureRegion blank;
	public static TextureRegion white;
	public static Skin skin;
	public static Sound blip01;
	public static Json json;
	private static List<Message> messages;

	public static void create() {
		json = new Json(OutputType.json);
		Texture spritesheet = new Texture(Gdx.files.internal("spritesheet.png"));
		Texture cellTexture = new Texture(Gdx.files.internal("cell-hq.png"));
		cell = new TextureRegion(cellTexture, 0, 0, 128, 128);
		small_food = new TextureRegion(spritesheet, 16, 0, 2, 2);
		medium_food = new TextureRegion(spritesheet, 20, 0, 4, 4);
		white = new TextureRegion(spritesheet, 30, 0, 1, 1);
		blank = new TextureRegion(spritesheet, 26, 0, 1, 1);
		Texture groundTexture = new Texture(Gdx.files.internal("ground-hq.png"));
		ground = new TextureRegion(groundTexture, 0, 0, 64, 64);
		skin = new Skin(Gdx.files.internal("uiskin.json"));

		blip01 = Gdx.audio.newSound(Gdx.files.internal("blip01.wav"));
		getMessages();
	}

	public static List<Message> getMessages() {
		if (messages == null) {
			messages = new ArrayList<Message>();
			MessageData data = json.fromJson(MessageData.class, Gdx.files.internal("msg/messages.json"));
			messages = data.messages;
		}
		return messages;
	}
}
