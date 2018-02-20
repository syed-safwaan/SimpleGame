/*
    Adam Mehdi & Syed Safwaan
    Asteroids.java
    The main file of the program.

    Classes:
    - GameButton	An extension of the JButton (for convenience)
    - Menu			An extension of the JPanel (for convenience)
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class GameButton extends JButton {

	/* Template for making GameButtons, which are easier to use than normal JButtons. */

	// Fields //

	private Dimension dimensions;
	private Point pos;
	private String menuName;  // references menu to transport to

	public GameButton(ActionListener frame, Dimension dimensions, Point pos, String imgName, String menuName) {

		/* Constructs and returns a new GameButton object. */

		this.dimensions = dimensions;
		this.pos = pos;
		this.menuName = menuName;

		// Add the actionlistener so it actually works
		this.addActionListener(frame);

		this.setSize(dimensions);
		this.setLocation(pos);

		// Setting up button graphics
		this.setBorderPainted(false);
		this.setBorder(null);
		this.setMargin(new Insets(0, 0, 0, 0));
		this.setContentAreaFilled(false);

		ImageIcon idleImg = new ImageIcon(new ImageIcon(String.format("Images/Button_%s0.png", imgName)).getImage().getScaledInstance((int) dimensions.getWidth(), (int) dimensions.getHeight(), Image.SCALE_DEFAULT));
		ImageIcon hoverImg = new ImageIcon(new ImageIcon(String.format("Images/Button_%s1.png", imgName)).getImage().getScaledInstance((int) dimensions.getWidth(), (int) dimensions.getHeight(), Image.SCALE_DEFAULT));
		ImageIcon pressedImg = new ImageIcon(new ImageIcon(String.format("Images/Button_%s2.png", imgName)).getImage().getScaledInstance((int) dimensions.getWidth(), (int) dimensions.getHeight(), Image.SCALE_DEFAULT));

		this.setIcon(idleImg);
		this.setRolloverIcon(hoverImg);
		this.setPressedIcon(pressedImg);
	}

	// Accessors //

	public Dimension getDimensions() {

		/* Returns the dimensions of the GameButton as a Dimension object. */

		return this.dimensions;
	}

	public Point getPos() {

		/* Returns the position of the GameButton as a Point object. */

		return this.pos;
	}

	public int getWidth() {

		/* Returns the width of the GameButton. */

		return (int) this.dimensions.getWidth();
	}

	public int getHeight() {

		/* Returns the height of the GameButton. */

		return (int) this.dimensions.getHeight();
	}

	public int getX() {

		/* Returns the x-position of the GameButton. */

		return (int) this.pos.getX();
	}

	public int getY() {

		/* Returns the y-position of the GameButton. */

		return (int) this.pos.getY();
	}

	public String getMenuName() {

		/* Returns the name of the assigned menu of the GameButton. */

		return this.menuName;
	}
}

class Menu extends JPanel {

	/* Template for making Menus, which are UI-related Panels. */

	// Fields //

	private ImageIcon title;
	private GameButton[] buttons;
	private String name;  // used for CardLayout names
	private Image background = new ImageIcon("Images/Background_MainScreen.jpg").getImage().getScaledInstance(1280, 720, Image.SCALE_DEFAULT);

	// Constructors //

	public Menu(GameButton[] buttons, String name) {

		/* Constructs and returns a Menu without a title. */

		this.buttons = buttons;
		this.name = name;

		// Allow null layout for absolute positioning
		this.setLayout(null);
		for (GameButton button : buttons) this.add(button);
	}

	public Menu(ImageIcon title, GameButton[] buttons, String name) {

		/* Constructs and returns a Menu with a title. */

		this.title = title;
		this.buttons = buttons;
		this.name = name;

		this.setLayout(null);
		for (GameButton button : buttons) this.add(button);

		// Setup title image
		JLabel back = new JLabel(new ImageIcon("Images/Background_MainScreen.jpg"));
		this.add(back);
	}

	// Accessors //

	public GameButton[] getButtons() {

		/* Returns the GameButtons on the Menu. */

		return this.buttons;
	}

	public String getName() {

		/* Returns the name of the Menu. */

		return this.name;
	}

	@Override
	public void paintComponent(Graphics g) {

		/* Draws onto the Menu. */

		// Call the parent constructor to wipe the screen
		super.paintComponent(g);

		// Draw the background and title if possible
		g.drawImage(this.background, 0, 0, this);
		if (this.title != null) g.drawImage(this.title.getImage(), 640 - this.title.getIconWidth() / 2, 30, this);
	}
}
