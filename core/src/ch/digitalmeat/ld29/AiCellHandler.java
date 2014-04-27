package ch.digitalmeat.ld29;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class AiCellHandler extends CellHandler {
	public final static float AI_UPDATE_INTERVAL = 0.1f;

	private Vector2 v1 = new Vector2();
	private Vector2 v2 = new Vector2();
	private GameWorld gameWorld;

	public AiCellHandler(GameWorld gw) {
		this.gameWorld = gw;
	}

	public void update(Entity entity, float delta) {
		CellData cell = entity.cell;
		cell.aiTimer -= delta;
		if (cell.aiTimer < 0) {
			cell.aiTimer = AI_UPDATE_INTERVAL;
			refreshAi(entity);
		}

		if (cell.target != null) {
			v1.set(entity.getX(), entity.getY());
			v2.set(cell.target.getX(), cell.target.getY());

			entity.body.applyForceToCenter(v2.sub(v1).nor().scl(LINEAR_FORCE), true);

		}
	}

	private void refreshAi(Entity entity) {
		CellData cell = entity.cell;
		if (cell.target != null && !cell.target.active) {
			cell.target = null;
		}
		if (cell.target == null) {
			cell.target = gameWorld.findNearestFood(entity);
			if (cell.target != null) {
				Gdx.app.log("Ai", "Targeting " + cell.target.type);
			}
		}
	}
}
