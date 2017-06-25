
/*
 * Amanda
 * 2/2/16
 */
import java.util.Random;

public class Dice {
	// data fields
	private int[] currentDice;
	private Random rand;

	// constructor
	public Dice() {
		rand = new Random();
		currentDice = new int[2];
		currentDice = getRoll();
	}

	// returns the array containing the numbers of each of the dice
	public int[] getRoll() {
		currentDice[0] = rand.nextInt(6) + 1;
		currentDice[1] = rand.nextInt(6) + 1;
		return currentDice;
	}

	// returns the current sum of the dice
	public int getSum() {
		return currentDice[0] + currentDice[1];
	}
}
