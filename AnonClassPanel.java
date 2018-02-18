import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AnonClassPanel extends JFrame implements ActionListener {


	private JButton a_two, a_three, b_one, b_three, c_one, c_two;
	private JPanel one, two, three, main;
	private CardLayout cardLayout = new CardLayout();


	public AnonClassPanel() {
		super("hey");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);

		a_two = new JButton("one to two");
		a_two.addActionListener(this);
		a_three = new JButton("one to three");
		a_three.addActionListener(this);
		b_one = new JButton("two to one");
		b_one.addActionListener(this);
		b_three = new JButton("two to three");
		b_three.addActionListener(this);
		c_one = new JButton("three to one");
		c_one.addActionListener(this);
		c_two = new JButton("three to two");
		c_two.addActionListener(this);

		one = new JPanel() {
			private int boxx = 170,boxy = 170;

			@Override
			public void paintComponent(Graphics g) {
				g.setColor(new Color(222,222,222));
				g.fillRect(0,0,getWidth(),getHeight());
				g.setColor(Color.blue);
				g.fillRect(boxx,boxy,40,40);
				boxx ++;
			}
		};

		one.setLayout(null);
		a_two.setSize(200, 150);
		a_two.setLocation(100, 400);
		a_three.setSize(200, 150);
		a_three.setLocation(500, 400);
		one.add(a_two);
		one.add(a_three);

		two = new JPanel() {
			private int boxx = 270,boxy = 270;

			@Override
			public void paintComponent(Graphics g) {
				g.setColor(new Color(222,222,222));
				g.fillRect(0,0,getWidth(),getHeight());
				g.setColor(Color.red);
				g.fillRect(boxx,boxy,40,40);
				boxx ++;
			}
		};

		two.setLayout(null);
		b_one.setSize(200, 150);
		b_one.setLocation(100, 400);
		b_three.setSize(200, 150);
		b_three.setLocation(500, 400);
		two.add(b_one);
		two.add(b_three);

		three = new JPanel() {
			private int boxx = 370,boxy = 370;

			@Override
			public void paintComponent(Graphics g) {
				g.setColor(new Color(222,222,222));
				g.fillRect(0,0,getWidth(),getHeight());
				g.setColor(Color.green);
				g.fillRect(boxx,boxy,40,40);
				boxx ++;
			}
		};

		three.setLayout(null);
		c_one.setSize(200, 150);
		c_one.setLocation(100, 400);
		c_two.setSize(200, 150);
		c_two.setLocation(500, 400);
		three.add(c_one);
		three.add(c_two);


		main = new JPanel(cardLayout);
		main.add(one, "one");
		main.add(two, "two");
		main.add(three, "three");

		add(main);

		setResizable(false);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src == b_one || src == c_one) {
			System.out.println("lel");
			cardLayout.show(main, "one");
			one.repaint();
		} else if (src == a_two || src == c_two) {
			System.out.println("hey");
			cardLayout.show(main, "two");
			two.repaint();
		} else if (src == a_three || src == b_three) {
			System.out.println("wow");
			cardLayout.show(main, "three");
			three.repaint();
		}
//		main.repaint();
	}

	public static void main(String[] args) {
		new AnonClassPanel();
	}
}
