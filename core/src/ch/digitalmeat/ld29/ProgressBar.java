package ch.digitalmeat.ld29;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ProgressBar extends Actor {
	private float value;
	private float max;
	private Label label;

	private Sprite remainingSprite;
	private Sprite backgroundSprite;
	private ProgressFormatter formatter;

	public ProgressBar(Skin skin, Color remainingColor, Color missingColor) {
		this(new ResourceFormatter(null), skin, remainingColor, missingColor);
	}

	public ProgressBar(ProgressFormatter formatter, Skin skin, Color remainingColor, Color missingColor) {
		if (formatter == null) {
			formatter = new ResourceFormatter(null);
		}
		this.formatter = formatter;
		this.label = new Label("", skin, "progress");
		TextureRegion blank = Assets.blank;
		this.remainingSprite = new Sprite(blank);
		remainingSprite.setColor(remainingColor);
		this.backgroundSprite = new Sprite(blank);
		this.backgroundSprite.setColor(missingColor);
		setValue(0, 0);
	}

	public void setValue(float value, float max) {
		if (max < 0) {
			max = 0;
		}
		this.max = max;
		setValue(value);
	}

	public void setValue(float value) {
		if (value < 0) {
			value = 0;
		}
		if (value > max) {
			value = max;
		}
		this.value = value;
		label.setText(formatter.formatProgress(value, max));
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// Gdx.app.log("Progress Bar", "Draw");
		float x = getX();
		float y = getY();
		float width = getWidth();
		float height = getHeight();

		backgroundSprite.setBounds(x, y, width, height);
		label.setBounds(x + 1, y - 1, width, height);

		backgroundSprite.draw(batch, parentAlpha);

		if (value > 0) {
			float remainingWidth = width / max * value;
			remainingSprite.setBounds(x, y, remainingWidth, height);
			remainingSprite.draw(batch, parentAlpha);
		}
		label.draw(batch, parentAlpha);
	}

}
