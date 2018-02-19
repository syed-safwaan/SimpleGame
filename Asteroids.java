/*
    Adam Mehdi & Syed Safwaan
    Asteroids.java
    The main file of the program.

    Classes:
    - Asteroids		The main class of the whole program.
*/


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Asteroids extends JFrame implements ActionListener {

	/* Main class of entire game. */

	public static void main(String[] args) {

		/* Entry point of program. */

		// Make a new game instance
		new Asteroids();
	}

	// Fields //

	private Timer timer;  // framerate
	private Space space;  // for the actual game
	private boolean gameActive;  // to see if a game is being played

	// Panel layout for the program

	private CardLayout cardLayout = new CardLayout();

	// All menus and buttons in use in the game
	private Menu startMenu, optionsMenu, diffMenu, modeMenu;
	private GameButton startButton, optionsButton, exitButton, storyButton, arcadeButton, arcadeMultButton, easyDiffButton, medDiffButton, hardDiffButton;
	private GameButton modeBackButton, optionsBackButton, diffBackButton;

	// Main panel where everything is put on (uses the CardLayout)
	JPanel main;

	// Constructor //

	public Asteroids() {

		/* Constructs and returns a new Asteroids object. Also sets up the game. */

		// Initial window config
		super("Shooty Circles");
		setSize(1280, 720);

		// Making Menus //

		// Start Menu

		startButton = new GameButton(this, new Dimension(338, 96), new Point(471, 250), "START", "modes");
		optionsButton = new GameButton(this, new Dimension(338, 96), new Point(471, 400), "OPTIONS", "options");
		exitButton = new GameButton(this, new Dimension(338, 96), new Point(471, 550), "EXIT", "close");

		startMenu = new Menu (
			new ImageIcon("Images\\Title_SHOOTY CIRCLES.png"),
			new GameButton[] { startButton, optionsButton, exitButton },
			"open"
		);

		// Mode Menu

		storyButton = new GameButton(this, new Dimension(338, 96), new Point(471, 250), "STORY", "files");
		arcadeButton = new GameButton(this, new Dimension(338, 96), new Point(471, 400), "ARCADE", "diff");
		arcadeMultButton = new GameButton(this, new Dimension(338, 96), new Point(471, 550), "2-PLAYER", "diff");
		modeBackButton =  new GameButton(this, new Dimension(253, 120), new Point(950, 530), "BACK", "open");

		modeMenu = new Menu (
			new ImageIcon("Images\\Title_GAMEMODE.png"),
			new GameButton[] { storyButton, arcadeButton, arcadeMultButton, modeBackButton },
			"modes"
		);

		// Options Menu

		optionsBackButton =  new GameButton(this, new Dimension(253, 120), new Point(950, 530), "BACK", "open");

		optionsMenu = new Menu (
			new GameButton[] { optionsBackButton },
			"options"
		);

		// Difficulty Menu

		easyDiffButton = new GameButton(this, new Dimension(338, 96), new Point(471, 250), "EASY", "space");
		medDiffButton = new GameButton(this, new Dimension(338, 96), new Point(471, 400), "MEDIUM", "space");
		hardDiffButton = new GameButton(this, new Dimension(338, 96), new Point(471, 550), "HARD", "space");
		diffBackButton =  new GameButton(this, new Dimension(253, 120), new Point(950, 530), "BACK", "newfile");

		diffMenu = new Menu (
			new ImageIcon("Images\\Title_DIFFICULTY.png"),
			new GameButton[] { easyDiffButton, medDiffButton, hardDiffButton, diffBackButton },
			"diff"
		);

		// Setting up the panels for the game
		main = new JPanel(cardLayout);
		main.add(startMenu, startMenu.getName());
		main.add(optionsMenu, optionsMenu.getName());
		main.add(modeMenu, modeMenu.getName());
		main.add(diffMenu, diffMenu.getName());
		add(main);

		// Some final window config
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);

//		timer = new Timer(10, this);
//		timer.start();

		// Game is not active initially
		gameActive = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		/* Processes all events from listeners. */

		// Event source
		Object src = e.getSource();

		// Handling the menu buttons
		if (!gameActive) {
			if (src == startButton) {
				cardLayout.show(main, modeMenu.getName());
			} else if (src == optionsButton) {
				cardLayout.show(main, optionsMenu.getName());
			} else if (src == exitButton) {
				System.exit(0);
			} else if (src == storyButton) {
				System.out.println("To be incorporated at a later date!");
			} else if (src == arcadeButton) {
				// set up arcade game creds
				cardLayout.show(main, diffMenu.getName());
			} else if (src == arcadeMultButton) {
				// start arcade mult session
				cardLayout.show(main, diffMenu.getName());
			} else if (src == easyDiffButton) {
				// set up arcade game diff
			} else if (src == medDiffButton) {
				// set 2 med
			} else if (src == hardDiffButton) {
				// set 2 hard
			} else if (src == modeBackButton || src == optionsBackButton) {
				cardLayout.show(main, startMenu.getName());
			} else if (src == diffBackButton) {
				cardLayout.show(main, modeMenu.getName());
			}
		} else {  // where game events are handled

		}
	}
}
