import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class GameButton extends JButton {

	private Dimension dimensions;
	private Point pos;
	private ImageIcon idleImg, hoverImg;
	private String menuName;

    public GameButton(ActionListener frame, Dimension dimensions, Point pos, ImageIcon idleImg, ImageIcon hoverImg, String menuName) {
    	this.dimensions = dimensions;
    	this.pos = pos;
    	this.idleImg = idleImg;
    	this.hoverImg = hoverImg;
    	this.menuName = menuName;

    	this.addActionListener(frame);

    	this.setSize(dimensions);
    	this.setLocation(pos);

    	this.setBorderPainted(false);
    	this.setBorder(null);
    	this.setMargin(new Insets(0, 0, 0, 0));
    	this.setContentAreaFilled(false);

    	this.setIcon(idleImg);
    	this.setRolloverIcon(hoverImg);
    	this.setPressedIcon(hoverImg);
	}

	public Dimension getDimensions() {
		return this.dimensions;
	}

	public Point getPos() {
		return pos;
	}

	public int getWidth() {
    	return (int) this.dimensions.getWidth();
	}

	public int getHeight() {
    	return (int) this.dimensions.getHeight();
	}

	public int getX() {
    	return (int) this.pos.getX();
	}

	public int getY() {
    	return (int) this.pos.getY();
	}

	public String getMenuName() {
    	return this.menuName;
	}
}

class Menu extends JPanel {

//	private ImageIcon background;
	private GameButton[] buttons;
//	private ImageIcon[] images;
//	private Point[] imgPoses;
	private String name;

	public Menu(/*ImageIcon background,*/ GameButton[] buttons, /*ImageIcon[] images, Point[] imgPoses,*/ String name) {

//		this.background = background;
//		this.buttons = buttons;
//		this.images = images;
//		this.imgPoses = imgPoses;
		this.name = name;

		this.setLayout(null);
		for (GameButton button : buttons) this.add(button);
	}

	public GameButton[] getButtons() {
		return this.buttons;
	}

	public String getName() {
		return this.name;
	}

//	@Override
//	public void paintComponent(Graphics g) {
//		g.drawImage(this.background.getImage(), 0, 0, this);
//		for (int i = 0; i < images.length; i ++) {
//			g.drawImage(this.images[i].getImage(), (int) imgPoses[i].getX(), (int) imgPoses[i].getY(), this);
//		}
//	}
}
