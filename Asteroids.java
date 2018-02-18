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

	private CardLayout cardLayout = new CardLayout();
	JPanel main = new JPanel(cardLayout);

	public Asteroids() {
		super("Shooty Circles");
		setSize(1280, 720);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GameButton exit = new GameButton(this, new Dimension(338, 96), new Point(100, 100), new ImageIcon("Images/Button_EXIT0"), new ImageIcon("Images/Button_EXIT1"), "close");

		/*
		* to add a Menu to the main panel:
		* main.add(menu, menu.getName())
		* */

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		for (Menu menu : menus) {
			for (GameButton button : menu.getButtons()) {
				if (((GameButton) src).getMenuName().equals("close")) {
					System.exit(0);
				}
				else if (src == button){
					cardLayout.show(main, button.getMenuName());
				}
			}
		}
	}
}