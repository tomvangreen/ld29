package ch.digitalmeat.ld29;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class Hud implements UiScreen {

	private Table table;
	private ProgressBar foodBar;
	private ProgressBar lifeBar;
	private Entity player;
	private ProgressBar attackBar;

	public Hud(Entity player) {
		this.player = player;
	}

	@Override
	public void create(Stage stage) {
		Skin skin = Assets.skin;
		table = new Table(skin);
		table.setBounds(0, 0, LD29.VIEWPORT_WIDTH, LD29.VIEWPORT_HEIGHT);
		table.align(Align.top | Align.left);
		Table bars = new Table(skin);
		bars.row().fillX().expandX();
		foodBar = new ProgressBar(new ResourceFormatter("Food"), skin, Colors.FOOD_BAR, Colors.BAR_BACKGROUND);
		foodBar.setValue(5, 10);
		lifeBar = new ProgressBar(new ResourceFormatter("Life"), skin, Colors.LIFE_BAR, Colors.BAR_BACKGROUND);
		lifeBar.setValue(5, 10);
		attackBar = new ProgressBar(new ResourceFormatter("Strength"), skin, Colors.ATTACK_BAR, Colors.BAR_BACKGROUND);
		attackBar.setValue(5, 100);
		// bars.add("Life", "progress");
		bars.add(lifeBar).prefHeight(20).expandX().fillX();
		// bars.add("Food", "progress");
		bars.add(foodBar).prefHeight(20).expandX().fillX();
		bars.add(attackBar).prefHeight(20).expandX().fillX();
		table.add(bars).expandX().fillX().expandX().prefHeight(20);
		stage.addActor(table);
	}

	@Override
	public void show() {
		table.setVisible(true);
	}

	@Override
	public void hide() {
		table.setVisible(false);
	}

	@Override
	public void update(float delta) {
		CellData cell = player.cell;
		lifeBar.setValue(cell.life, cell.lifeCap);
		foodBar.setValue(cell.food, cell.foodCap);
		attackBar.setValue(cell.attack, cell.attackCap);
	}
}
