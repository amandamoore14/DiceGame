/*
 * Name: Amanda Moore
 * Date: 1/31/16
 * Course: CSC212
 * Program: ProgramGUI
 * 
 * Description:
 * Creates a GUI for the dice game Two Dice Pig.
 * When the player rolls a one, they lose their points
 * for that turn, and if they roll two ones, they lose
 * all of their points. If they hold, their turn score
 * is added to their total score.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TwoDicePigGame implements ActionListener {

	// main method
	public static void main(String[] args) {
		TwoDicePigGame game = new TwoDicePigGame();
	}

	// Score to win the game
	public static final int GOAL_SCORE = 100;

	// constants
	private final int FRAME_HEIGHT = 330;
	private final int FRAME_WIDTH = 250;
	private final int FRAME_LOCATION_X = 800;
	private final int FRAME_LOCATION_Y = 400;
	private final ImageIcon[] DIE_IMAGES = { null,
			new ImageIcon("Images/die1.jpg"), new ImageIcon("Images/die2.jpg"),
			new ImageIcon("Images/die3.jpg"), new ImageIcon("Images/die4.jpg"),
			new ImageIcon("Images/die5.jpg"), new ImageIcon("Images/die6.jpg") };
	private final ImageIcon HOLD_IMAGE = new ImageIcon("Images/hold.jpg");
	private final ImageIcon ROLL_IMAGE = new ImageIcon("Images/roll.jpg");

	private final int DELAY = 2000;

	// data fields
	private JButton roll;
	private JButton hold;
	private JLabel playerScoreNumLabel;
	private JLabel computerScoreNumLabel;
	private JLabel turnTotal;
	private int[] diceRoll;
	private Dice dice;
	private JLabel imageLabel1;
	private JLabel imageLabel2;
	private JLabel whoseTurn;
	private String name;
	private Timer timer;
	private HumanPlayer player;
	private ComputerPlayer computer;

	// constructor
	public TwoDicePigGame() {
		name = JOptionPane.showInputDialog(null,
				"Welcome to Two Dice Pig! \nEnter your name:");
		JOptionPane
				.showMessageDialog(
						null,
						"See if you can get to "
								+ GOAL_SCORE
								+ " before I do. \n\nRoll until you hold or roll a 1. \nIf you roll a 1 before you hold, "
								+ "\nyou lose the points for that turn. \nIf you roll snake eyes before you hold, \nyou lose all your points. "
								+ "\n\nYou can roll first.");
		player = new HumanPlayer(name);
		computer = new ComputerPlayer("Computer");
		timer = new Timer(DELAY, this);
		whoseTurn = new JLabel(name);
		imageLabel1 = new JLabel("", ROLL_IMAGE, JLabel.LEFT);
		imageLabel2 = new JLabel("", ROLL_IMAGE, JLabel.RIGHT);
		playerScoreNumLabel = new JLabel("0");
		computerScoreNumLabel = new JLabel("0");
		turnTotal = new JLabel("0");
		dice = new Dice();
		roll = new JButton("Roll");
		hold = new JButton("Hold");
		JFrame frame = new JFrame("Pig Game");
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setLocation(FRAME_LOCATION_X, FRAME_LOCATION_Y);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		JPanel northPanel = makeNorthPanel();
		frame.add(northPanel, BorderLayout.NORTH);
		JPanel centerPanel = makeCenterPanel();
		frame.add(centerPanel, BorderLayout.CENTER);
		JPanel southPanel = makeSouthPanel();
		frame.add(southPanel, BorderLayout.SOUTH);
		frame.setResizable(false);
		frame.setVisible(true);

		roll.addActionListener(this);
		hold.addActionListener(this);
	}

	// constructs and returns the title and words in the top portion of the
	// window
	public JPanel makeNorthPanel() {
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BorderLayout());
		JLabel title = new JLabel("Two Dice Pig");
		Font titleFont = new Font("Serif", Font.ITALIC, 24);
		title.setFont(titleFont);
		title.setHorizontalAlignment(JLabel.CENTER);
		northPanel.add(title, BorderLayout.NORTH);
		northPanel.add(makeScoreInfo(), BorderLayout.SOUTH);
		return northPanel;
	}

	// constructs the score info beneath the title
	public JPanel makeScoreInfo() {
		JPanel playerScorePanel = new JPanel();
		JPanel computerScorePanel = new JPanel();
		JPanel currentPlayerPanel = new JPanel();
		JPanel turnTotalPanel = new JPanel();
		playerScorePanel.setLayout(new FlowLayout());
		computerScorePanel.setLayout(new FlowLayout());
		currentPlayerPanel.setLayout(new FlowLayout());
		turnTotalPanel.setLayout(new FlowLayout());
		JLabel playerScoreLabel = new JLabel(name + "'s Score: ");
		playerScoreLabel.setHorizontalAlignment(JLabel.CENTER);
		JLabel computerScoreLabel = new JLabel("Computer's Score: ");
		computerScoreLabel.setHorizontalAlignment(JLabel.CENTER);
		JLabel currentPlayer = new JLabel("Current Player: ");
		currentPlayer.setHorizontalAlignment(JLabel.CENTER);
		JLabel turnTotalLabel = new JLabel("Turn Total: ");
		turnTotalLabel.setHorizontalAlignment(JLabel.CENTER);
		playerScorePanel.add(playerScoreLabel);
		playerScorePanel.add(playerScoreNumLabel);
		computerScorePanel.add(computerScoreLabel);
		computerScorePanel.add(computerScoreNumLabel);
		currentPlayerPanel.add(currentPlayer);
		currentPlayerPanel.add(whoseTurn);
		turnTotalPanel.add(turnTotalLabel);
		turnTotalPanel.add(turnTotal);
		JPanel scoreInfo = new JPanel();
		scoreInfo.setLayout(new GridLayout(4, 1));
		scoreInfo.add(playerScorePanel);
		scoreInfo.add(computerScorePanel);
		scoreInfo.add(currentPlayerPanel);
		scoreInfo.add(turnTotalPanel);
		return scoreInfo;

	}

	// constructs and returns the images in the center of the window
	public JPanel makeCenterPanel() {
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout());
		centerPanel.add(imageLabel1);
		centerPanel.add(imageLabel2);
		centerPanel.setBackground(Color.BLACK);
		return centerPanel;
	}

	// constructs and returns the buttons on the bottom of the window
	public JPanel makeSouthPanel() {
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout());
		southPanel.add(roll);
		southPanel.add(hold);
		hold.setEnabled(false);
		return southPanel;
	}

	// when action event is triggered
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == roll) {
			rollAction(player, playerScoreNumLabel);
		} else if (source == hold) {
			holdAction(player, turnTotal);
		} else {
			if (computer.isRolling(player.getCurrentScore())) {
				rollAction(computer, computerScoreNumLabel);
			} else {
				holdAction(computer, turnTotal);
			}
		}
	}

	// controls action for roll accepts a Player object and JLabel with
	// the player's score as parameters
	public void rollAction(Player player, JLabel score) {
		diceRoll = dice.getRoll();
		imageLabel1.setIcon(DIE_IMAGES[diceRoll[0]]);
		imageLabel2.setIcon(DIE_IMAGES[diceRoll[1]]);
		System.out.println(diceRoll[0] + " " + diceRoll[1]);
		if (diceRoll[0] == 1 && diceRoll[1] == 1) {
			player.snakeEyes();
			if (player instanceof HumanPlayer) {
				playerScoreNumLabel.setText("0");
			} else {
				computerScoreNumLabel.setText("0");
			}
			imageLabel1.setIcon(ROLL_IMAGE);
			imageLabel2.setIcon(ROLL_IMAGE);
			turnIsOver(player);
		} else if (diceRoll[0] == 1 || diceRoll[1] == 1) {
			player.rolledOne();
			turnIsOver(player);
			imageLabel1.setIcon(ROLL_IMAGE);
			imageLabel2.setIcon(ROLL_IMAGE);
		} else {
			if (player instanceof HumanPlayer) {
				hold.setEnabled(true);
			}
			player.addTotalToScore(diceRoll[0] + diceRoll[1]);
			System.out.println("adding to turn: " + player.getTurnTotal());
			turnTotal.setText("" + player.getTurnTotal());
			if (player.getTurnTotal() + player.getCurrentScore() >= GOAL_SCORE) {
				JOptionPane.showMessageDialog(null, player.getName() + " won!");
				hold.setEnabled(false);
				roll.setEnabled(false);
			}
		}
	}

	// controls action for hold button
	public void holdAction(Player player, JLabel score) {
		player.playerHeld();
		if (player instanceof HumanPlayer) {
			playerScoreNumLabel.setText("" + player.getCurrentScore());
		} else {
			computerScoreNumLabel.setText("" + player.getCurrentScore());
		}
		imageLabel1.setIcon(HOLD_IMAGE);
		imageLabel2.setIcon(HOLD_IMAGE);
		if (player.getTurnTotal() + player.getCurrentScore() >= GOAL_SCORE) {
			hold.setEnabled(false);
			roll.setEnabled(false);
		} else {
		turnIsOver(player);
		}
	}

	// executes when turn ends
	public void turnIsOver(Player player) {
		turnTotal.setText("0");
		if (player instanceof HumanPlayer) {
			roll.setEnabled(false);
			hold.setEnabled(false);
			JOptionPane.showMessageDialog(null, "It's my turn.");
			whoseTurn.setText("Computer");
			timer.start();
		} else {
			timer.stop();
			JOptionPane.showMessageDialog(null, "It's your turn.");
			roll.setEnabled(true);
			whoseTurn.setText(name);
		}
	}

}