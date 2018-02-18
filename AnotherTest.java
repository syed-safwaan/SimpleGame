import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class AnotherTest extends JFrame implements ActionListener {

	public static void main(String[] args){
		new AnotherTest();
	}

	private JPanel cards;   	//a panel that uses CardLayout
	private CardLayout cLayout = new CardLayout();
	private JButton bone = new JButton("one 2 two"), btwo = new JButton("two 2 three"), bthree = new JButton("three 2 one");

	private GamePanel one = new GamePanel(100, 100);
	private GamePanel two = new GamePanel(200, 200);
	private GamePanel three = new GamePanel(300, 300);


	public AnotherTest() {
		super("Menu Eg");
		setSize(800, 600);

		bone.addActionListener(this);
		btwo.addActionListener(this);
		bthree.addActionListener(this);

		one.setLayout(null);
		bone.setSize(100, 30);
		bone.setLocation(350, 400);
		one.add(bone);

		two.setLayout(null);
		btwo.setSize(100, 30);
		btwo.setLocation(350, 400);
		two.add(btwo);

		three.setLayout(null);
		bthree.setSize(100, 30);
		bthree.setLocation(350, 400);
		three.add(bthree);

		cards = new JPanel(cLayout);
		cards.add(one, "one");
		cards.add(two, "two");
		cards.add(three, "three");


		add(cards);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible (true);
	}

	public void actionPerformed(ActionEvent evt) {
		Object src = evt.getSource();
		if (src == bone) {
			cLayout.show(cards, "two");
		} else if (src == btwo) {
			cLayout.show(cards, "three");
		} else if (src == bthree) {
			cLayout.show(cards, "one");
		}
	}

	class GamePanel extends JPanel implements MouseListener {
		private int x, y;

		public GamePanel(int x, int y) {
			this.x = x; this.y = y;

			addMouseListener(this);
			setSize(800, 600);
		}

		public void paintComponent(Graphics g) {
			g.setColor(new Color(222, 222, 255));
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(new Color(255, 111, 111));
			g.fillRect(x, y, 100, 100);
		}

		// ------------ MouseListener ------------------------------------------
		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

		public void mouseClicked(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}
	}
}
