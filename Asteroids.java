/*
    Adam Mehdi & Syed Safwaan
    Asteroids.java
    The main file of the program.

    Classes:
    -
*/


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

public class Asteroids extends JFrame implements ActionListener{

	public static void main(String[] args) {
		new Asteroids();
	}

	private Timer timer;
	private Menu[] menus;
	private Space space;

	private CardLayout cardLayout = new CardLayout();

	Menu startMenu, optionsMenu, diffMenu, modeMenu, creationMenu;
	GameButton startButton, optionsButton, exitButton, storyButton, arcadeButton, arcadeMultButton, easyDiffButton, medDiffButton, hardDiffButton;

	JPanel main;

	public Asteroids() {
		super("Shooty Circles");
		setSize(1280, 720);

		startButton = new GameButton(this, new Dimension(338, 96), new Point(471, 250), new ImageIcon("Images\\Button_START0.png"), new ImageIcon("Images\\Button_START1.png"), "modes");
		optionsButton = new GameButton(this, new Dimension(338, 96), new Point(471, 400), new ImageIcon("Images\\Button_OPTIONS0.png"), new ImageIcon("Images\\Button_OPTIONS1.png"), "options");
		exitButton = new GameButton(this, new Dimension(338, 96), new Point(471, 550), new ImageIcon("Images\\Button_EXIT0.png"), new ImageIcon("Images\\Button_EXIT1.png"), "close");

		startMenu = new Menu (
			new GameButton[] { startButton, optionsButton, exitButton },
			"open"
		);

		storyButton = new GameButton(this, new Dimension(338, 96), new Point(471, 250), new ImageIcon("Images\\Button_STORY0.png"), new ImageIcon("Images\\Button_STORY1.png"), "modes");
		arcadeButton = new GameButton(this, new Dimension(338, 96), new Point(471, 400), new ImageIcon("Images\\Button_ARCADE0.png"), new ImageIcon("Images\\Button_ARCADE1.png"), "options");
		arcadeMultButton = new GameButton(this, new Dimension(338, 96), new Point(471, 550), new ImageIcon("Images\\Button_EXIT0.png"), new ImageIcon("Images\\Button_EXIT1.png"), "close");

		modeMenu = new Menu (
			new GameButton[] { storyButton, arcadeButton, arcadeMultButton },
			"modes"
		);

		main = new JPanel(cardLayout);
		main.add(startMenu, startMenu.getName());
//		main.add(startButton);
		add(main);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src == startButton) {
			System.out.println("wow");
		}
//		for (Menu menu : menus) {
//			for (GameButton button : menu.getButtons()) {
//				if (((GameButton) src).getMenuName().equals("close")) {
//					System.exit(0);
//				}
//				else if (src == button){
//					cardLayout.show(main, button.getMenuName());
//				}
//			}
//		}
	}
}