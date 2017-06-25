/*
 * Amanda Moore
 * 2/5/16
 */
import javax.swing.JOptionPane;

public class ComputerPlayer extends Player {

	public final int HOLD_VALUE = 20;

	// constructor
	public ComputerPlayer(String name) {
		super(name);
	}

	// accepts opponent's score as a parameter and
	// returns true if the computer should continue
	// rolling and false if not
	public boolean isRolling(int opponentScore) {
		if (super.getTurnTotal() < HOLD_VALUE) {
			if (super.getCurrentScore() + super.getTurnTotal() >= TwoDicePigGame.GOAL_SCORE) {
				return false;
			} else {
				return true;
			}
		} else {
			if (TwoDicePigGame.GOAL_SCORE - opponentScore < HOLD_VALUE) {
				return true;
			} else {
				return false;
			}
		}
	}
}
