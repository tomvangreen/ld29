package ch.digitalmeat.ld29;

import java.util.Random;

public class CellData {
	public int food = 0;
	public int foodCap = 5;
	public int life = 10;
	public int lifeCap = 10;
	public int levelUps = 0;
	public int attack = 5;
	public int attackCap = 5;

	public void eat(int value) {
		food += value;
		while (food >= foodCap) {
			levelUps++;
			food -= foodCap;
			foodCap = foodCap * 5 / 4;
		}
	}

	public void levelUp(Random random) {
		int choice = random.nextInt(2);
		switch (choice) {
		case 0:
		default:
			attackCap += 2;
			break;
		case 1:
			lifeCap += 5;
			break;
		}
		life = lifeCap;
		attack = attackCap;
	}
}
