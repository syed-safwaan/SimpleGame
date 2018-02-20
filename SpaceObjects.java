/*
    Adam Mehdi & Syed Safwaan
    SpaceObjects.java
    A collection of methods for objects in use during the actual gameplay of the program.

    Classes:
    - SpaceObjects  To manage the other objects (maybe)
    - Asteroid      The "enemies" of the game
    - Ship          The playable objects
    - > Bullet      The Ship's main weapon
    - Space         The playground of the other objects
    - > Wall        A Space component that involves barriers and interesting physics
*/

import javax.swing.*;
import java.awt.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.util.*;
import java.awt.geom.Area;

class Asteroid {

	/* Template for Asteroid objects, the main antagonistic entities in the game. */

	// Fields //

	private static int count = 0;  // to count active asteroids
	private static int weight = 0, maxWeight = 30;
	private double x, y, vx, vy, rotation, rotationVel;  // asteroid state of motion
	private int size, rectSize, hp;
	private int sizes[] = {30, 80, 200}, hpSizes[] = {3, 10, 30};  // to assign asteroid properties on init
	private int bodyType;

	// Polygon points
	private int[][][] polygonX = {
		{
			{10, 30, 25, 0},
			{15, 30, 10, 0},
			{15, 30, 0}
		},
		{
			{40, 55, 80, 35, 0},
			{0, 0, 65, 80, 45},
			{0, 15, 50, 80, 25}
		},
		{
			{65, 200, 170, 80, 0, 0},
			{0, 0, 160, 200, 175, 120, 40},
			{0, 0, 70, 200, 170}
		}
	};
	private int[][][] polygonY = {
		{
			{0, 25, 30, 20},
			{15, 30, 10, 0},
			{15, 30, 0}
		},
		{
			{0, 0, 60, 80, 50},
			{50, 30, 0, 40, 80},
			{45, 15, 0, 40, 80}
		},
		{
			{0, 35, 170, 200, 160, 30},
			{150, 40, 0, 130, 200, 180, 200},
			{120, 60, 0, 20, 200}
		}
	};
	private Polygon body;
	private boolean exists;

	// Constructor //

	public Asteroid(int size, double x, double y, double vx, double vy, double rotationVel) {

		/* Constructs and returns a new Asteroid object. */

		this.size = size;
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.rotationVel = rotationVel;
		this.rectSize = sizes[size];
		this.hp = hpSizes[size];
		this.exists = true;
		this.bodyType = (new Random()).nextInt(3);
		this.makeShape();

		weight += (int)Math.pow(3, size);
	}

	private void makeShape() {

		/* Constructs a Polygon for the Asteroid. */

		int pointCount = polygonX[this.size][bodyType].length;
		int[] xCoords = new int[pointCount];
		int[] yCoords = new int[pointCount];

		for (int i = 0; i < pointCount; i++) {
			xCoords[i] = (int) (this.x + polygonX[this.size][this.bodyType][i]);
			yCoords[i] = (int) (this.y + polygonY[this.size][this.bodyType][i]);
		}

		double centerX = this.rectSize / 2, centerY = this.rectSize / 2;
		for (int i = 0; i < pointCount; i++) {
			double dist = Math.hypot(xCoords[i] - centerX, yCoords[i] - centerY);
			double ang = this.rotation + Math.acos((xCoords[i] - centerX));
			double newX = dist * (Math.cos(ang)) + centerX;
			double newY = dist * (Math.sin(ang)) + centerY;
			xCoords[i] = (int) newX;
			yCoords[i] = (int) newY;
		}

		this.body = new Polygon(xCoords, yCoords, pointCount);
	}

	// Accessors //

	public static int getWeight() {

		/* Returns the current weight of all Asteroids. */

		return weight;
	}

	public static int getMaxWeight() {

		/* Returns the weight limit. */

		return maxWeight;
	}

	public static void setMaxWeight(int max) {

		/* Sets the weight limit. */

		maxWeight = max;
	}

	public void move() {

		/* Moves asteroid depending on velocity. */

		this.x += this.vx;
		this.y += this.vy;
		this.makeShape();
	}

	public void takeDmg(int damage) {

		/* Reduces Asteroid health by a given amount. */

		this.hp -= damage;
	}

	public int getHp() {

		/* Returns Asteroid health. */

		return this.hp;
	}

	public Polygon getShape() {

		/* Returns the body of the Asteroid. */

		return this.body;
	}

	public double getVX() {

		/* Returns the x-velocity of the Asteroid. */

		return this.vx;
	}

	public void setVX(double vx) {

		/* Sets the x-velocity of the Asteroid to a given value. */

		this.vx = vx;
	}

	public double getVY() {

		/* Returns the y-velocity of the Asteroid. */

		return this.vy;
	}

	public void setVY(double vy) {

		/* Sets the y-velocity of the Asteroid to a given value. */

		this.vy = vy;
	}

	public int getSize() {

		/* Returns the size of the Asteroid. */

		return this.size;
	}

	public boolean exists() {

		/* Returns whether the Asteroid exists or not. */

		return this.exists;
	}

	public void setExists(boolean exists) {

		/* Sets the existence of the Asteroid. */

		this.exists = exists;
		if (!exists) count--;
	}

	public Asteroid[] shatter() {

		/* Sets the existence of the current Asteroid to false and returns 3 new smaller asteroids in an array. */

		if (size > 0) {
			return new Asteroid[]{
				new Asteroid(this.size - 1, this.x, this.y, vx - 1, vy - 1, rotation + Math.random() * 2 - 1),
				new Asteroid(this.size - 1, this.x + this.rectSize / 2, this.y, vx + 1, vy - 1, rotation + Math.random() * 2 - 1),
				new Asteroid(this.size - 1, this.x + this.rectSize / 3, this.y + this.rectSize / 2, vx + 1, vy + 1, rotation + Math.random() * 2 - 1)
			};
		} else return new Asteroid[]{};  // asteroid is broken, no new ones to return
	}

	public void update(Graphics g) {

		/* Draws the Asteroid onto a given Graphics component. */
        g.setColor(Color.LIGHT_GRAY);
        g.fillPolygon(this.body);
        g.setColor(Color.BLACK);
        g.drawPolygon(this.body);
	}
}

class Ship {

	/* Used to make Ships, the PC bodies that can do stuff. */

	// Fields //

	private static int count = 0;  // to count active Ships
	private static int maxCount = 2;  // max active Ships
	// Controls
	private static int controls[][] = {
		{KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_SPACE},
		{KeyEvent.VK_W, KeyEvent.VK_D, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_R}
	};
	// Name vars for direction
	private static int FORWARD = 0, RIGHT = 1, BACK = 2, LEFT = 3, SHOOT = 4;
	private double x, y, vx = 0, vy = 0, angle, accel, drag, turnSpeed;  // state of motion
	private int ID;  // ID used for identification
	private int ammo, attackRate, shootingCooldown;
	private boolean isAccelerating;
	private Polygon body;
	private int[] polygonX = {10, 20, 0};
	private int[] polygonY = {0, 30, 30};
	private final int width = 20, height = 30;
	private Image[] bodyImages = {new ImageIcon("Images//Sprite_ShipSimple1.png").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT), new ImageIcon("Images//Sprite_ShipSimple2.png").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT)};
	private Image fireImage = new ImageIcon("Images//Sprite_ShipFire.png").getImage();;
	private Image image;
	private boolean exists;

	public Ship(double x, double y, double accel, double drag, double turnSpeed, int ammo) {

		/* Constructs and returns a new Ship object. */

		this.ID = count++;
		this.x = x;
		this.y = y;
		this.accel = accel;
		this.drag = drag;
		this.turnSpeed = turnSpeed;
		this.ammo = ammo;
		this.exists = true;
		this.image = bodyImages[this.ID];
		this.makeShape();
	}

	public static int getCount() {

		/* Returns the number of active Ships. */

		return count;
	}

	private void makeShape() {

		/* Constructs a Polygon for the Ship. */

		int pointCount = 3;
		int[] xCoords = new int[pointCount];
		int[] yCoords = new int[pointCount];

		for (int i = 0; i < pointCount; i++) {
			xCoords[i] = (int) (this.x + polygonX[i]);
			yCoords[i] = (int) (this.y + polygonY[i]);
		}

		double centerX = this.width / 2, centerY = this.height / 2;
		for (int i = 0; i < pointCount; i++) {
			double dist = Math.hypot(xCoords[i] - centerX, yCoords[i] - centerY);
			double ang = this.angle + Math.acos((xCoords[i] - centerX));
			double newX = dist * (Math.cos(ang)) + centerX;
			double newY = dist * (Math.sin(ang)) + centerY;
			xCoords[i] = (int) newX;
			yCoords[i] = (int) newY;
		}

		this.body = new Polygon(xCoords, yCoords, pointCount);

	}

	public Polygon getShape() {

		/* Returns the Polygon of the Ship. */

		return this.body;
	}

	public Bullet fire() {

		/* Returns a new Bullet. */

		return new Bullet(this.x, this.y, this.angle, 4, 3);
	}

	public double getVX() {

		/* Returns the x-velocity of the Ship. */

		return this.vx;
	}

	public void setVX(double vx) {

		/* Sets the x-velocity of the Ship to a given value. */

		this.vx = vx;
	}

	public double getVY() {

		/* Returns the y-velocity of the Ship. */

		return this.vy;
	}

	public void setVY(double vy) {

		/* Sets the y-velocity of the Ship to a given value. */

		this.vy = vy;
	}

	public boolean exists() {

		/* Returns whether the Ship exists or not. */

		return this.exists;
	}

	public void setExists(boolean exists) {

		/* Sets the existence of the Ship. */

		this.exists = exists;
		if (!exists) count--;
	}

	public void accelerate(boolean[] keys) {

		/* Moves the Ship considering the currently pressed keys. */

		isAccelerating = false;
		if (keys[controls[this.ID][FORWARD]]) {  // moving forward
			this.vx += this.accel * Math.cos(this.angle);
			this.vy += this.accel * Math.sin(this.angle);
			isAccelerating = true;
		}
		if (keys[controls[this.ID][RIGHT]]) {  // turning right
			this.angle -= this.turnSpeed;
		}
		if (keys[controls[this.ID][LEFT]]) {  // turning left
			this.angle += this.turnSpeed;
		}

		// Slowing ship down by a factor gives ship a max speed
		this.vx *= this.drag;
		this.vy *= this.drag;

	}

	public void move() {

		/* Moves the Ship. */

		this.x += this.vx;
		this.y += this.vy;
		this.makeShape();
	}

	public void update(Graphics g, ImageObserver observer) {

		/* Draws the Ship onto a given Graphics component. */
		Graphics2D g2D = (Graphics2D)g;
		AffineTransform saveXform = g2D.getTransform();
		AffineTransform at = new AffineTransform();
		at.rotate(this.angle,this.x + this.width/2,this.y+this.height/2);
		g2D.transform(at);
		g2D.drawImage(this.image, (int)this.x, (int)this.y, observer);
		if(this.isAccelerating){
			g2D.drawImage(this.fireImage, (int)this.x+this.width, (int)this.y+this.height, observer);
		}
		g2D.setTransform(saveXform);

	}


	public class Bullet {

		/* Template for Bullet objects, the primary offensive projectile of the game. */

		// Fields //

		private double x, y, vx, vy, angle, speed;
		private boolean exists;
		private Rectangle hitbox;
		private int radius = 30;
		private int damage, durability;

		// Constructor //

		private Bullet(double x, double y, double angle, double speed, int damage) {

			/* Constructs and returns a new Bullet object. */

			this.x = x;
			this.y = y;
			this.vx = this.speed * Math.cos(this.angle);
			this.vy = this.speed * Math.sin(this.angle);
			this.angle = angle;
			this.speed = speed;
			this.exists = true;
			this.damage = damage;
			this.durability = 3;
			this.makeShape();
		}

		public void makeShape() {

			/* Constructs the Bullet hitbox Rectangle. */

			this.hitbox = new Rectangle((int) this.x - this.radius, (int) this.y - this.radius, this.radius * 2, this.radius * 2);
		}

		// Accessors //

		public Rectangle getShape() {

			/* Returns the Bullet hitbox. */

			return this.hitbox;
		}

		public int getDamage() {

			/* Returns the Bullet's damage potential. */

			return this.damage;
		}

		public void takeDmg() {
			this.durability--;
			if (durability == 0) this.setExists(false);
		}

		public double getVX() {

			/* Returns the x-velocity of the Bullet. */

			return this.vx;
		}

		public void setVX(double vx) {

			/* Sets the x-velocity of the Bullet to a given value. */

			this.vx = vx;
		}

		public double getVY() {

			/* Returns the y-velocity of the Bullet. */

			return this.vy;
		}

		public void setVY(double vy) {

			/* Sets the y-velocity of the Bullet to a given value. */

			this.vy = vy;
		}

		public boolean exists() {

			/* Returns whether the Bullets exists or not. */

			return this.exists;
		}

		public void setExists(boolean exists) {

			/* Sets the existence of the Bullet. */

			this.exists = exists;
		}

		public void move() {

			/* Moves the Bullet. */

			this.x += this.vx;
			this.y += this.vy;
		}

		public void update(Graphics g) {

			/* Draws The Bullet onto a Graphics component.*/
			g.setColor(Color.BLACK);
			g.drawOval((int)this.x, (int)this.y, this.radius*2, this.radius*2);
			g.setColor(Color.WHITE);
			g.fillOval((int)this.x, (int)this.y, this.radius*2, this.radius*2);

		}
	}
}

class Space extends JPanel implements ActionListener, KeyListener {

	/* Used for managing the rest of the active game objects. */

	// Fields //

	// Arraylists for the objects
	private ArrayList<Ship> ships = new ArrayList<>();
	private ArrayList<Asteroid> asteroids = new ArrayList<>();
	private ArrayList<Ship.Bullet> bullets = new ArrayList<>();
	private ArrayList<Space.Wall> walls = new ArrayList<>();

	// game score and difficulty
	private int score, difficulty;

	// Things to work with events
	private Timer timer, asteroidsTimer;
	private boolean[] keys;

	private Image background;

	public Space() {

		/* Constructs and returns a new Space object. */

		// Set up the panel for keyboard input
		keys = new boolean[KeyEvent.KEY_LAST + 1];
		this.addKeyListener(this);
		this.setFocusable(true);
		this.requestFocusInWindow();

		// Use null LM
		this.setLayout(null);

		this.background = new ImageIcon("Images\\Background_GameScreen.png").getImage().getScaledInstance(1280, 720, Image.SCALE_DEFAULT);
	}

	public void init(int difficulty, int playerCount) {

		/* Initializes the Space for game activity. */

		this.difficulty = difficulty;
		Asteroid.setMaxWeight(30 + 18 * (difficulty - 1));
		for(int i = 0; i < playerCount; i++) {
			this.addShip(new Ship(620, 260 + i * 100, 1, 0.1, 0.1, 9999999));
		}

		timer = new Timer(10, this);
		timer.start();
		asteroidsTimer = new Timer(5, this);
		asteroidsTimer.start();
	}

	public Space(String asteroidData, boolean asteroidSpawn, int width, int height) {

		/* Constructs and returns a new Space object. */

	}

	public void spawnAsteroid(){

    }

	public void addAsteroid(Asteroid a) {

		/* Adds a Asteroid to the Space. */

		asteroids.add(a);
	}

	public void addShip(Ship s) {

		/* Adds a Ship to the Space. */

		ships.add(s);
	}

	public void queryCollisions() {

		/* Checking for collisions between pairs of objects. */

		// Asteroids colliding
		for (int i = 0; i < asteroids.size(); i++) {
			for (int j = 0; j < i; j++) {
				if (Physics.collide(asteroids.get(i).getShape(), asteroids.get(j).getShape())) {
					Physics.colliding(asteroids.get(i), asteroids.get(j));
				}
			}
		}

		// Asteroids with ship
		for (Asteroid asteroid : asteroids) {
			for (Ship ship : ships) {
				if (Physics.collide(asteroid.getShape(), ship.getShape())) {
					Physics.colliding(ship, asteroid);
				}
			}
		}

		// Asteroids with bullets
		for (Asteroid asteroid : asteroids) {
			for (Ship.Bullet bullet : bullets) {
				if (Physics.collide(asteroid.getShape(), bullet.getShape())) {
					Physics.colliding(bullet, asteroid);
				}
			}
		}

		// Asteroids with wall
		for (Asteroid asteroid : asteroids) {
			for (Wall wall : walls) {
				if (Physics.collide(asteroid.getShape(), wall.getShape())) {
					Physics.colliding(asteroid, wall);
				}
			}
		}

		// Ship with wall
		for (Ship ship : ships) {
			for (Wall wall : walls) {
				if (Physics.collide(ship.getShape(), wall.getShape())) {
					Physics.colliding(ship, wall);
				}
			}
		}

		// Bullet with wall
		for (Ship.Bullet bullet : bullets) {
			for (Wall wall : walls) {
				if (Physics.collide(bullet.getShape(), wall.getShape())) {
					Physics.colliding(bullet, wall);
				}
			}
		}
	}

	public void playerAction(boolean[] keys) {

		/*  */

		for (Ship ship : ships) {
			ship.accelerate(keys);
			ship.move();
		}
	}

	public void filterExistingObjects() {
		for (int i = this.asteroids.size() - 1; i >= 0; i--) {
			if (!asteroids.get(i).exists()) {
				this.score += 100 * asteroids.get(i).getSize();
				for (Asteroid asteroid : asteroids.get(i).shatter()) addAsteroid(asteroid);
				asteroids.remove(i);
			}
		}
		for (int i = this.ships.size() - 1; i >= 0; i--) {
			if (!ships.get(i).exists()) {
				ships.remove(i);
			}
		}
		for (int i = this.bullets.size() - 1; i >= 0; i--) {
			if (!bullets.get(i).exists()) {
				bullets.remove(i);
			}
		}
	}

	public int getScore() {
		return this.score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setAsteroidSpawnRate(int delay) {
		asteroidsTimer.setDelay(delay);
	}

	public void update(Graphics g) {
		for (Ship ship : ships) ship.update(g, this);
		for (Asteroid asteroid : asteroids) asteroid.update(g);
		for (Ship.Bullet bullet : bullets) bullet.update(g);

		// Displays score in the top left of the screen
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Monospaced", Font.BOLD, 20));
        g.drawString(Integer.toString(this.score), 20, 20);
	}

	public boolean getKeyPress(int keycode) {
		return keys[keycode];
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		if (src == timer) {
			this.repaint();
			this.requestFocusInWindow();
			this.queryCollisions();
			this.playerAction(keys);
			this.setAsteroidSpawnRate(50 * (Asteroid.getWeight()/Asteroid.getMaxWeight()) + 5);
		} else if (src == asteroidsTimer) {
			// addAsteroid(); here
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		System.out.println("ay pressed");
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		System.out.println("ay released");
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(this.background, 0, 0, this);
		Space.this.update(g);
	}

	private static class Physics {


		/* Checks if ship collided with an asteroid */

		public static boolean collide(Shape a, Shape b) {
			Area areaA = new Area(a);
			areaA.intersect(new Area(b));
			return !areaA.isEmpty();
		}

		public static void colliding(Asteroid asteroidA, Asteroid asteroidB) {
			double newAvx = asteroidA.getVX() + asteroidB.getVX() * asteroidA.getSize() / asteroidB.getSize();
			double newAvy = asteroidA.getVY() + asteroidB.getVY() * asteroidA.getSize() / asteroidB.getSize();
			double newBvx = asteroidB.getVX() + asteroidA.getVX() * asteroidB.getSize() / asteroidA.getSize();
			double newBvy = asteroidB.getVY() + asteroidA.getVY() * asteroidB.getSize() / asteroidA.getSize();
			asteroidA.setVX(newAvx);
			asteroidA.setVY(newAvy);
			asteroidB.setVX(newBvx);
			asteroidB.setVY(newBvy);
		}

		public static void colliding(Ship ship, Asteroid asteroid) {
			ship.setExists(false);
		}
		//Asteroid gets damaged

		public static void colliding(Ship.Bullet bullet, Asteroid asteroid) {
			bullet.setExists(false);
			asteroid.takeDmg(bullet.getDamage());
			asteroid.setVX(asteroid.getVX() + bullet.getVX() / (5 * asteroid.getSize()));
			asteroid.setVY(asteroid.getVY() + bullet.getVY() / (5 * asteroid.getSize()));
		}
		//Asteroid bounces off of wall

		public static void colliding(Asteroid asteroid, Space.Wall wall) {
			if (wall.getWidth() > wall.getHeight()) { //Checks if wall is vertical or horizontal
				asteroid.setVY(-asteroid.getVY());
			} else {
				asteroid.setVX(-asteroid.getVX());
			}
		}
		//Ship bounces off of wall

		public static void colliding(Ship ship, Space.Wall wall) {
			if (wall.getWidth() > wall.getHeight()) { //Checks if wall is vertical or horizontal
				ship.setVY(-ship.getVY());
			} else {
				ship.setVX(-ship.getVX());
			}
		}
		//Kills the bullet
		public static void colliding(Ship.Bullet bullet, Wall wall) {
			if (wall.getWidth() > wall.getHeight()) { //Checks if wall is vertical or horizontal
				bullet.setVY(-bullet.getVY());
			} else {
				bullet.setVX(-bullet.getVX());
			}
			bullet.takeDmg();
		}

	}

	// will add some new update methods later
	private class Wall {


		/* Used to make Wall objects, impervious barriers with some special effects. */
		private int x, y, width, height;

		private Rectangle rect;


		public Wall(int x, int y, int width, int height) {

			/* Constructs and returns a new Wall object. */

			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.rect = new Rectangle(this.x, this.y, this.width, this.height);

		}

		public Rectangle getShape() {
			return this.rect;
		}

		public int getWidth() {
			return this.width;
		}

		public int getHeight() {
			return this.height;
		}

	}
}
