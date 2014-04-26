package ch.digitalmeat.ld29;

import com.badlogic.gdx.scenes.scene2d.Stage;

public interface UiScreen {
	public void create(Stage stage);

	public void show();

	public void hide();

	public void update(float delta);
}
