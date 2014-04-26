package ch.digitalmeat.ld29;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
	public static TextureRegion cell;
	public static TextureRegion small_food;
	public static TextureRegion medium_food;
	public static TextureRegion ground;
	public static TextureRegion blank;
	public static TextureRegion white;
	public static Skin skin;

	public static void create() {
		Texture spritesheet = new Texture(Gdx.files.internal("spritesheet.png"));
		cell = new TextureRegion(spritesheet, 0, 0, 16, 16);
		small_food = new TextureRegion(spritesheet, 16, 0, 2, 2);
		medium_food = new TextureRegion(spritesheet, 20, 0, 4, 4);
		blank = new TextureRegion(spritesheet, 30, 0, 1, 1);
		blank = new TextureRegion(spritesheet, 26, 0, 1, 1);
		Texture groundTexture = new Texture(Gdx.files.internal("ground.png"));
		ground = new TextureRegion(groundTexture, 0, 0, 16, 16);
		skin = new Skin(Gdx.files.internal("uiskin.json"));
	}
}
