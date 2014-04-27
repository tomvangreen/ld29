package ch.digitalmeat.ld29;

public class CellData {
	public int food = 0;
	public int foodCap = 10;
	public int life = 10;
	public int lifeCap = 10;
	public int levelUps = 0;

	public void eat(int value) {
		food += value;
		while (food >= foodCap) {
			food -= foodCap;
			foodCap = foodCap * 3 / 2;
		}
	}
}
