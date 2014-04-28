package ch.digitalmeat.ld29;

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
			Vector2 force = v2.sub(v1).nor().scl(LINEAR_FORCE);
			if (cell.state == CellState.Evade) {
				force = force.scl(-1);
			}
			entity.body.applyForceToCenter(force, true);

		}
	}

	private Vector2 va = new Vector2();
	private Vector2 vb = new Vector2();

	private float distance(Entity a, Entity b) {
		va.set(a.getX(), a.getY());
		vb.set(b.getX(), b.getY());
		return va.sub(vb).len();
	}

	private void refreshAi(Entity entity) {
		CellData cell = entity.cell;
		// check hp
		float hpScale = (0f + cell.life) / cell.lifeCap;
		if (hpScale < 0.4f) {
			cell.target = gameWorld.findNearestCell(entity);
			if (cell.target != null) {
				if (distance(entity, cell.target) < 10) {
					cell.state = CellState.Evade;
					return;
				} else {
					cell.target = null;
				}
			}
		} else {
			// Only attack when enough hp
			// Keep on attacking if still suitable
			if (cell.state == CellState.Attack && cell.target != null && cell.target.active) {
				if (canAttack(entity, cell.target)) {
					return;
				}
			}

			// See if there is a target
			for (Entity other : gameWorld.getCells()) {
				if (other != entity) {
					if (canAttack(entity, other)) {
						cell.state = CellState.Attack;
						cell.target = other;
						return;
					}
				}
			}
		}
		// roam mode
		if (cell.target != null && !cell.target.active) {
			cell.target = null;
		}
		if (cell.target == null) {
			cell.target = gameWorld.findNearestFood(entity);

			// if (cell.target != null) {
			// // Gdx.app.log("Ai", "Targeting " + cell.target.type);
			// }
		}
	}

	private boolean canAttack(Entity entity, Entity other) {
		int a = entity.cell.attack + entity.cell.attackCap + entity.cell.life;
		int b = entity.cell.attack + entity.cell.attackCap + entity.cell.life;
		return (a + 2 > b);
	}

	private void evade(Entity entity, CellData cell) {
		// TODO Auto-generated method stub

	}

	public enum CellState {
		Roam, Evade, Attack
	}
}
