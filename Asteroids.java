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

	public Asteroids() {
		super("Shooty Circles");
		setSize(1280, 720);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		BufferedImage background = null;
		try {
			background = ImageIO.read(new File("Images/Background_MainScreen.jpg"));
		} catch (IOException e) {
			System.out.println("Resources not found...");
			System.exit(0);
		}

		GameButton exit = new GameButton(this, new Dimension(338, 96), new Point(100, 100), new ImageIcon("Images/Button_EXIT0"), new ImageIcon("Images/Button_EXIT1"), "start");

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}