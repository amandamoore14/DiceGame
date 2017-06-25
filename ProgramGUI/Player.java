/*
 * Amanda Moore
 * 2/5/16
 */

public abstract class Player {
	// data fields
	private int currentScore;
	private int turnTotal;
	private String name;

	// constructor
	public Player(String name) {
		this.name = name;
		currentScore = 0;
		turnTotal = 0;
	}

	// returns the player's current score
	public int getCurrentScore() {
		return currentScore;
	}

	// returns the player's turn total
	public int getTurnTotal() {
		return turnTotal;
	}

	// returns the player's name
	public String getName() {
		return name;
	}

	// adds turn total passed as parameter to current score
	public void addTotalToScore(int turnTotal) {
		this.turnTotal += turnTotal;
	}

	// ends player's turn because player held
	public void playerHeld() {
		currentScore += turnTotal;
		turnTotal = 0;
	}

	// ends player's turn because player rolled a 1
	public void rolledOne() {
		turnTotal = 0;
	}

	// ends player's turn because player rolled snake eyes
	public void snakeEyes() {
		turnTotal = 0;
		currentScore = 0;

	}
}
