package ch.digitalmeat.ld29;

import java.util.Random;

public class CellData {
	public int food = 0;
	public int foodCap = 5;
	public int life = 10;
	public int lifeCap = 10;
	public int levelUps = 0;
	public int attack = 0;
	public int attackCap = 5;

	public float aiTimer = 0f;
	public Entity target = null;
	public float thrustTimer;

	public void eat(int value) {
		food += value;
		while (food >= foodCap) {
			levelUps++;
			food -= foodCap;
		}
		life += value;
		if (life > lifeCap) {
			life = lifeCap;
		}
		attack += value;
		if (attack > attackCap) {
			attack = attackCap;
		}
	}

	public void levelUp(Random random) {
		int choice = random.nextInt(2);
		switch (choice) {
		case 0:
		default:
			attackCap += 5;
			break;
		case 1:
			lifeCap += 7;
			break;
		}
		// Gdx.app.log("Food Cap", "" + foodCap);
		foodCap = foodCap + 1 + (foodCap / 10);
		// life = lifeCap;
		// attack = attackCap;
	}
}
