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

		startButton = new GameButton(this, new Dimension(338, 96), new Point(100, 200), new ImageIcon("Images\\Button_START0.png"), new ImageIcon("Images\\Button_START1.png"), "main");
		startMenu = new Menu (
			new GameButton[] { startButton/*, optionsButton, exitButton */},
			"open"
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