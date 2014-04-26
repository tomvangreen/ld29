package ch.digitalmeat.ld29;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	public static TextureRegion cell;

	public static void create() {
		Texture spritesheet = new Texture(Gdx.files.internal("spritesheet.png"));
		cell = new TextureRegion(spritesheet, 0, 0, 16, 16);
	}
}
