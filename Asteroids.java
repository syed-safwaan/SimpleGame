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

	// Panel layout for the program

	private CardLayout cardLayout = new CardLayout();

	// All menus and buttons in use in the game
	private Menu startMenu, instructMenu, diffMenu, modeMenu;
	private GameButton startButton, instructButton, exitButton, storyButton, arcadeButton, arcadeMultButton, easyDiffButton, medDiffButton, hardDiffButton, restartButton;
	private GameButton modeBackButton, instructBackButton, diffBackButton;

	private int playerCount;

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
		instructButton = new GameButton(this, new Dimension(338, 96), new Point(471, 400), "MANUAL", "instruct");
		exitButton = new GameButton(this, new Dimension(338, 96), new Point(471, 550), "EXIT", "close");

		startMenu = new Menu(
			new ImageIcon("Images/Title_SHOOTY CIRCLES.png"),
			new GameButton[]{startButton, instructButton, exitButton},
			"open"
		);

		// Mode Menu

		storyButton = new GameButton(this, new Dimension(338, 96), new Point(471, 250), "STORY", "files");
		arcadeButton = new GameButton(this, new Dimension(338, 96), new Point(471, 400), "ARCADE", "diff");
		arcadeMultButton = new GameButton(this, new Dimension(338, 96), new Point(471, 550), "2-PLAYER", "diff");
		modeBackButton = new GameButton(this, new Dimension(140, 80), new Point(20, 20), "BACK", "open");

		modeMenu = new Menu(
			new ImageIcon("Images/Title_GAMEMODE.png"),
			new GameButton[]{storyButton, arcadeButton, arcadeMultButton, modeBackButton},
			"modes"
		);

		// instruct Menu

		instructBackButton = new GameButton(this, new Dimension(140, 80), new Point(20, 20), "BACK", "open");

		instructMenu = new Menu(
			new ImageIcon("Images/Text_Manual.png"),
			new GameButton[]{instructBackButton},
			"instruct"
		);

		// Difficulty Menu

		easyDiffButton = new GameButton(this, new Dimension(338, 96), new Point(471, 250), "EASY", "space");
		medDiffButton = new GameButton(this, new Dimension(338, 96), new Point(471, 400), "MEDIUM", "space");
		hardDiffButton = new GameButton(this, new Dimension(338, 96), new Point(471, 550), "HARD", "space");
		diffBackButton = new GameButton(this, new Dimension(140, 80), new Point(20, 20), "BACK", "newfile");

		diffMenu = new Menu(
			new ImageIcon("Images/Title_DIFFICULTY.png"),
			new GameButton[]{easyDiffButton, medDiffButton, hardDiffButton, diffBackButton},
			"diff"
		);

		restartButton = new GameButton(this, new Dimension(140, 80), new Point(20, 20), "RESTART", "open");
		restartButton.setVisible(false);

		space = new Space();
		space.add(restartButton);


		// Setting up the panels for the game
		main = new JPanel(cardLayout);
		main.add(startMenu, startMenu.getName());
		main.add(instructMenu, instructMenu.getName());
		main.add(modeMenu, modeMenu.getName());
		main.add(diffMenu, diffMenu.getName());
		main.add(space, "space");
		add(main);

		// Some final window config
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		/* Processes all events from listeners. */

		// Event source
		Object src = e.getSource();

		if (src == startButton) {
			cardLayout.show(main, modeMenu.getName());
		} else if (src == instructButton) {
			cardLayout.show(main, instructMenu.getName());
		} else if (src == exitButton) {
			System.exit(0);
		} else if (src == storyButton) {
			System.out.println("To be incorporated at a later date!");
		} else if (src == arcadeButton) {
			playerCount = 1;
			cardLayout.show(main, diffMenu.getName());
		} else if (src == arcadeMultButton) {
			playerCount = 2;
			cardLayout.show(main, diffMenu.getName());
		} else if (src == easyDiffButton) {
			space.init(1, playerCount);
			cardLayout.show(main, "space");
		} else if (src == medDiffButton) {
			space.init(2, playerCount);
			cardLayout.show(main, "space");
		} else if (src == hardDiffButton) {
			space.init(3, playerCount);
			cardLayout.show(main, "space");
		} else if (src == modeBackButton || src == instructBackButton) {
			cardLayout.show(main, startMenu.getName());
		} else if (src == diffBackButton) {
			cardLayout.show(main, modeMenu.getName());
		}
	}
}
