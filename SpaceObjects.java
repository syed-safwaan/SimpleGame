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

	private static int weight = 0, maxWeight = 30;
	private double x, y, vx, vy, rotation, rotationVel;  // asteroid state of motion
	private int size, rectSize, hp;
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
			{30, 30, 0, 5},
			{0, 30, 30}
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

	// To check for activity, since Java doesn't have destructors
	private boolean exists;

	// Constructor //

	public Asteroid(int size, double x, double y, double vx, double vy, double rotationVel) {

		/* Constructs and returns a new Asteroid object. */

		int[] sizes = {30, 80, 200};
		int[] hpSizes = {3, 10, 30};

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

		// Adds the weight of the new asteroid to the whole weight
		weight += (int) Math.pow(size, 3);
	}

	private void makeShape() {

		/* Constructs a Polygon for the Asteroid. */

		// Used to construct polygon later
		int pointCount = polygonX[this.size][bodyType].length;
		int[] xCoords = new int[pointCount];
		int[] yCoords = new int[pointCount];

		// Gets all points of original polygon
		for (int i = 0; i < pointCount; i++) {
			xCoords[i] = (int) (this.x + polygonX[this.size][this.bodyType][i]);
			yCoords[i] = (int) (this.y + polygonY[this.size][this.bodyType][i]);
		}

		// Uses winding function to rotate all points from original polygon
		double centerX = this.x + this.rectSize / 2, centerY = this.y + this.rectSize / 2;
		for (int i = 0; i < pointCount; i++) {
			double dist = Math.hypot(xCoords[i] - centerX, yCoords[i] - centerY);
			double ang = this.rotation + Math.atan2(yCoords[i] - centerY, xCoords[i] - centerX);
			double newX = dist * (Math.cos(ang)) + centerX;
			double newY = dist * (Math.sin(ang)) + centerY;
			// Reassigns new points
			xCoords[i] = (int) newX;
			yCoords[i] = (int) newY;
		}
		// Reassigns body to polygon
		this.body = new Polygon(xCoords, yCoords, pointCount);
	}

	// Accessors //

	public static int getWeight() {

		/* Returns the current weight of all Asteroids. */

		return weight;
	}

	public static void resetWeight() {

		/* Resets weight back to 0. */

		weight = 0;
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

		/* Moves the Asteroid depending on velocity. */

		this.vx += 0.1 - Math.random() * 0.2;
		this.vx *= 0.99;
		this.vy += 0.1 - Math.random() * 0.2;
		this.vy *= 0.99;
		this.x += this.vx;
		this.x = ((this.x + 1280 + 2 * this.rectSize) % (1280 + this.rectSize)) - this.rectSize;
		this.y += this.vy;
		this.y = ((this.y + 720 + 2 * this.rectSize) % (720 + this.rectSize)) - this.rectSize;
		this.rotation += this.rotationVel;
		this.makeShape();
	}

	public void takeDmg(int damage) {

		/* Reduces Asteroid health by a given amount and kills it if necessary. */

		this.hp -= damage;
		if (this.hp <= 0) this.setExists(false);
	}

	public int getHp() {

		/* Returns Asteroid health. */

		return this.hp;
	}

	public Polygon getShape() {

		/* Returns the body of the Asteroid. */

		return this.body;
	}

	public double getX() {

		/* Returns the x-position of the Asteroid. */

		return this.x;
	}

	public double getY() {

		/* Returns the y-position of the Asteroid. */

		return this.y;
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

		/* Sets the existence of the Asteroid and edits the weight if necessary. */

		this.exists = exists;
		if (!exists) {
			weight -= (int) Math.pow(this.size, 3);
		}
	}

	public Asteroid[] shatter() {

		/* Returns 3 new smaller asteroids in an array. */

		if (size > 0) {  // if the asteroid is not the smallest size

			// Return 3 asteroids
			return new Asteroid[]{
				new Asteroid(this.size - 1, this.x, this.y, vx - 1, vy - 1, 0.02 - Math.random() * 0.05),
				new Asteroid(this.size - 1, this.x + this.rectSize / 2, this.y, vx + 1, vy - 1, 0.02 - Math.random() * 0.05),
				new Asteroid(this.size - 1, this.x + this.rectSize / 3, this.y + this.rectSize / 2, vx + 1, vy + 1, 0.02 - Math.random() * 0.05)
			};
		} else return new Asteroid[]{};  // asteroid is broken, no new ones to return
	}

	public void update(Graphics g) {

		/* Draws the Asteroid onto a given Graphics component. */

		g.setColor(Color.LIGHT_GRAY);
		g.fillPolygon(this.body);
		g.setColor(Color.BLACK);
		g.drawPolygon(this.body);
		g.setFont(new Font("Monospaced", Font.BOLD, 15));
		g.setColor(Color.WHITE);
		g.drawString("" + this.hp, (int) this.x + this.rectSize / 2 , (int) this.y + this.rectSize / 2);
	}
}

class Ship {

	/* Used to make Ships, the PC bodies that can do stuff. */

	// Fields //

	private static int count = 0;  // to count active Ships

	// Controls
	private static int controls[][] = {
		{KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_SPACE},
		{KeyEvent.VK_W, KeyEvent.VK_D, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_R}
	};
	// Name vars for direction
	private static int FORWARD = 0, RIGHT = 1, BACK = 2, LEFT = 3, SHOOT = 4;
	private double x, y, vx = 0, vy = 0, angle, accel, drag, turnSpeed;  // state of motion
	private int ID;  // ID used for identification
	private int shootingCooldown = 0;
	private boolean isAccelerating;
	private Polygon body;
	private int[] polygonX = {10, 20, 0};
	private int[] polygonY = {0, 30, 30};
	private final int width = 20, height = 30;
	private Image fireImage = new ImageIcon("Images/Sprite_ShipFire.png").getImage().getScaledInstance(width, height * 3 / 5, Image.SCALE_DEFAULT);
	private Image image;
	private boolean exists;

	public Ship(double x, double y, double accel, double drag, double turnSpeed) {

		/* Constructs and returns a new Ship object. */

		this.ID = count++;
		this.x = x;
		this.y = y;
		this.accel = accel;
		this.drag = drag;
		this.turnSpeed = turnSpeed;
		this.exists = true;
		this.image = new ImageIcon(String.format("Images/Sprite_ShipSimple%d.png", this.ID + 1)).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
		this.makeShape();
	}

	public static int getCount() {

		/* Returns the number of active Ships. */

		return count;
	}

	private void makeShape() {

		/* Constructs a Polygon for the Ship. */

		// Used to construct polygon later
		int pointCount = 3;
		int[] xCoords = new int[pointCount];
		int[] yCoords = new int[pointCount];

		// Makes original polygon without rotation
		for (int i = 0; i < pointCount; i++) {
			xCoords[i] = (int) (this.x + polygonX[i]);
			yCoords[i] = (int) (this.y + polygonY[i]);
		}

		// Uses winding function to rotate all points from original polygon
		double centerX = this.x + this.width / 2, centerY = this.y + this.height / 2;
		for (int i = 0; i < pointCount; i++) {
			double dist = Math.hypot(xCoords[i] - centerX, yCoords[i] - centerY);
			double ang = this.angle + Math.atan2(yCoords[i] - centerY, xCoords[i] - centerX);
			double newX = dist * (Math.cos(ang)) + centerX;
			double newY = dist * (Math.sin(ang)) + centerY;
			// Reassigns new points
			xCoords[i] = (int) newX;
			yCoords[i] = (int) newY;
		}
		// Reassigns body
		this.body = new Polygon(xCoords, yCoords, pointCount);

	}

	public Polygon getShape() {

		/* Returns the Polygon of the Ship. */

		return this.body;
	}

	public Bullet shoot(boolean[] keys) {
		this.shootingCooldown--;
		if (keys[controls[this.ID][SHOOT]] && this.shootingCooldown < 0) {
			this.shootingCooldown = 15;
			return this.fire();
		} else {
			return null;
		}
	}

	public Bullet fire() {

		/* Returns a new Bullet. */

		return new Bullet(this.x + this.width / 2, this.y + this.height / 2, this.angle - 1.57, 14, 3);
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
			this.vx += this.accel * Math.cos(this.angle - 1.57);
			this.vy += this.accel * Math.sin(this.angle - 1.57);
			isAccelerating = true;
		}
		if (keys[controls[this.ID][RIGHT]]) {  // turning right
			this.angle += this.turnSpeed;
		}
		if (keys[controls[this.ID][LEFT]]) {  // turning left
			this.angle -= this.turnSpeed;
		}

		// Slowing ship down by a factor gives ship a max speed
		this.vx *= this.drag;
		this.vy *= this.drag;

	}

	public void move() {

		/* Moves the Ship. */

		// Allow screen wrapping with modular operator
		this.x += this.vx + 1280;
		this.x %= 1280;
		this.y += this.vy + 720;
		this.y %= 720;

		this.makeShape();
	}

	public void update(Graphics g, ImageObserver observer) {

		/* Draws the Ship onto a given Graphics component. */

		// Using AffineTransform to rotate images
		Graphics2D g2D = (Graphics2D) g;
		AffineTransform saveXform = g2D.getTransform();
		AffineTransform at = new AffineTransform();
		at.rotate(this.angle, this.x + this.width / 2, this.y + this.height / 2);
		g2D.transform(at);

		g2D.drawImage(this.image, (int) this.x, (int) this.y, observer);
		if (this.isAccelerating) {
			g2D.drawImage(this.fireImage, (int) this.x, (int) this.y + this.height, observer);
		}
		g2D.setTransform(saveXform);

	}


	public class Bullet {

		/* Template for Bullet objects, the primary offensive projectile of the game. */

		// Fields //

		private double x, y, vx, vy;
		private boolean exists;
		private Polygon hitbox;
		private int radius = 3;
		private int damage, durability;

		// Constructor //

		private Bullet(double x, double y, double angle, double speed, int damage) {

			/* Constructs and returns a new Bullet object. */

			this.x = x;
			this.y = y;
			this.vx = speed * Math.cos(angle);
			this.vy = speed * Math.sin(angle);
			this.exists = true;
			this.damage = damage;
			this.durability = 2;
			this.makeShape();
		}

		public void makeShape() {

			/* Constructs the Bullet hitbox Rectangle. */

			int[] xCoords = {(int) this.x, (int) this.x + this.radius * 2, (int) this.x + this.radius * 2, (int) this.x};
			int[] yCoords = {(int) this.y, (int) this.y, (int) this.y + this.radius * 2, (int) this.y + this.radius * 2};
			this.hitbox = new Polygon(xCoords, yCoords, 4);
		}

		// Accessors //

		public Polygon getShape() {

			/* Returns the Bullet hitbox. */

			return this.hitbox;
		}

		public int getDamage() {

			/* Returns the Bullet's damage potential. */

			return this.damage;
		}

		public void takeDmg() {

			/* Reduces Bullet durability and nullifies it if necessary. */

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

			// Set rotation
			this.makeShape();
		}

		public void update(Graphics g) {

			/* Draws The Bullet onto a Graphics component.*/

			g.setColor(Color.BLACK);
			g.drawOval((int) this.x, (int) this.y, this.radius * 2, this.radius * 2);
			g.setColor(Color.WHITE);
			g.fillOval((int) this.x, (int) this.y, this.radius * 2, this.radius * 2);
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

	// Game score
	private int score;

	// Things to work with events
	private Timer timer, asteroidsTimer;
	private boolean[] keys;

	private Image background;

	// For random fun
	private Random rng = new Random();

	public Space() {

		/* Constructs and returns a new Space object. */

		// Set up the panel for keyboard input
		keys = new boolean[KeyEvent.KEY_LAST + 1];
		this.addKeyListener(this);
		this.setFocusable(true);
		this.requestFocusInWindow();

		// Use null LM
		this.setLayout(null);

		this.background = new ImageIcon("Images/Background_GameScreen.png").getImage().getScaledInstance(1280, 720, Image.SCALE_DEFAULT);
	}

	public void init(int difficulty, int playerCount) {

		/* Initializes the Space for game activity. */

		// Reset all the values
		this.score = 0;
		Asteroid.resetWeight();
		Asteroid.setMaxWeight(30 + 18 * (difficulty - 1));

		// Clear all lists
		this.asteroids.clear();
		this.ships.clear();
		this.bullets.clear();

		// Add in players
		for (int i = 0; i < playerCount; i++) {
			this.addShip(new Ship(620, 260 + i * 100, 0.05, 0.995, 0.1));
		}

		// Add in walls
		walls.add(new Wall(-20, 0, 30, 720));
		walls.add(new Wall(1270, 0, 30, 720));
		walls.add(new Wall(0, -20, 1280, 30));
		walls.add(new Wall(0, 710, 1280, 30));

		// Start timers
		timer = new Timer(10, this);
		timer.start();
		asteroidsTimer = new Timer(5, this);
		asteroidsTimer.start();
	}

	private void spawnAsteroid() {

		/* Spawns a new Asteroid and checks its viability. */

		Asteroid newA;
		int aX, aY, aVX, aVY;

		// Pick a random side to start on
		int side = rng.nextInt(4);

		// Set a location based on the side taken
		switch (side) {
			case 0:  // left
				aX = rng.nextInt(150) + 150;
				aY = rng.nextInt(720);
				aVX = rng.nextInt(20) + 5;
				aVY = rng.nextInt(30) - 30;
				break;
			case 1:  // top
				aX = rng.nextInt(1280);
				aY = rng.nextInt(150) + 150;
				aVX = rng.nextInt(30) - 30;
				aVY = rng.nextInt(20) + 5;
				break;
			case 2:  // right
				aX = 1280 - (rng.nextInt(150) + 150);
				aY = rng.nextInt(720);
				aVX = -(rng.nextInt(20) + 5);
				aVY = rng.nextInt(30) - 30;
				break;
			default:  // bottom
				aX = 1280 - (rng.nextInt(150) + 150);
				aY = 720 - (rng.nextInt(720));
				aVX = -(rng.nextInt(20) + 5);
				aVY = -(rng.nextInt(30) - 30);
				break;

		}

		// Make the asteroid
		newA = new Asteroid(2, aX, aY, aVX / 10, aVY / 10, rng.nextDouble() / 100);

		// Check for collisions and kill the asteroid if needed

		for (Asteroid asteroid : asteroids) {
			if (Physics.collide(newA.getShape(), asteroid.getShape())) return;
		}

		for (Ship ship : ships) {
			if (Physics.collide(newA.getShape(), ship.getShape())) return;
		}

		for (Ship.Bullet bullet : bullets) {
			if (Physics.collide(newA.getShape(), bullet.getShape())) return;
		}

		addAsteroid(newA);
	}

	private void addAsteroid(Asteroid a) {

		/* Adds a Asteroid to the Space. */

		asteroids.add(a);
	}

	private void addShip(Ship s) {

		/* Adds a Ship to the Space. */

		ships.add(s);
	}

	private void queryCollisions() {

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

		// Bullets with walls
		for (Ship.Bullet bullet : bullets) {
			for (Wall wall : walls) {
				if (Physics.collide(bullet.getShape(), wall.getShape())) {
					Physics.colliding(bullet, wall);
				}
			}
		}
	}

	private void playerAction(boolean[] keys) {

		/* Queries the player for any possible actions. */

		for (Ship ship : ships) {
			ship.accelerate(keys);
			Ship.Bullet tempBullet = ship.shoot(keys);
			if (tempBullet != null) {
				bullets.add(tempBullet);
			}

			ship.move();
		}
	}

	private void moveBullets() {

		/* Moves all Bullets in the Space. */

		for (Ship.Bullet bullet : bullets) bullet.move();
	}

	private void moveAsteroids() {

		/* Moves all Asteroids in the Space. */

		for (Asteroid asteroid : asteroids) asteroid.move();
	}

	private void filterExistingObjects() {

		/* Removes nonexistent entities. */

		// Removes asteroids
		for (int i = this.asteroids.size() - 1; i >= 0; i--) {
			if (!asteroids.get(i).exists()) {
				this.score += 100 + 100 * asteroids.get(i).getSize();
				for (Asteroid asteroid : asteroids.get(i).shatter()) addAsteroid(asteroid);
				asteroids.remove(i);
			}
		}

		// Removes ships
		for (int i = this.ships.size() - 1; i >= 0; i--) {
			if (!ships.get(i).exists()) {
				ships.remove(i);
			}
		}

		// Removes bullets
		for (int i = this.bullets.size() - 1; i >= 0; i--) {
			if (!bullets.get(i).exists()) {
				bullets.remove(i);
			}
		}
	}

	private void setAsteroidSpawnRate() {

		/* Sets the spawn rate for Asteroids in the Space. */

		asteroidsTimer.setDelay(50 * Asteroid.getWeight() / Asteroid.getMaxWeight() + 500);
	}

	public boolean hasGameEnded() {

		/* Returns whether the game has ended or not. */

		return ships.isEmpty();
	}

	public void update(Graphics g) {

		/* Draws all entities in the Space onto a Graphics component.  */

		for (Ship ship : ships) ship.update(g, this);
		for (Asteroid asteroid : asteroids) asteroid.update(g);
		for (Ship.Bullet bullet : bullets) bullet.update(g);

		// Displays score in the top left of the screen
		g.setColor(Color.white);
		g.setFont(new Font("Monospaced", Font.BOLD, 40));
		g.drawString(String.format("SCORE: %d ASTEROID MASS: %d", this.score, Asteroid.getWeight()), 20, 40);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		/* Processes all events from listeners in the Space. */

		Object src = e.getSource();

		if (src == timer) {  // main timer
			this.filterExistingObjects();
			this.repaint();
			this.requestFocusInWindow();
			this.queryCollisions();
			this.playerAction(keys);
			this.moveBullets();
			this.moveAsteroids();
			this.setAsteroidSpawnRate();
		} else if (src == asteroidsTimer) {  // asteroid spawn timer
			if (Asteroid.getWeight() < Asteroid.getMaxWeight()) spawnAsteroid();
		}
	}

	// KeyListener methods

	@Override
	public void keyTyped(KeyEvent e) {

		/* Registers when key is typed. */

	}

	@Override
	public void keyPressed(KeyEvent e) {

		/* Updates a pressed key to true. */

		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {

		/* Updates a pressed key to false. */

		keys[e.getKeyCode()] = false;
	}

	@Override
	public void paintComponent(Graphics g) {

		/* Draws onto the Space. */

		// Clear the screen
		super.paintComponent(g);

		// Draw the background image and the Space
		g.drawImage(this.background, 0, 0, this);
		Space.this.update(g);
	}

	private static class Physics {

		/* Convenience class to deal with collisions. */

		/* Checks if ship collided with an asteroid */

		private static boolean collide(Shape a, Shape b) {

			/* Checks if two shapes are colliding. */

			Area areaA = new Area(a);
			areaA.intersect(new Area(b));
			return !areaA.isEmpty();
		}

		private static void colliding(Asteroid a, Asteroid b) {

			/* Handles collision between two Asteroids. */

			// Generates new velocities for the two, depending on their current velocities and their distances
			double newAvx = a.getVX() + 0.01 * (a.getX() - b.getX()) * (1 + b.getSize()) / (1 + a.getSize());
			double newAvy = a.getVY() + 0.01 * (a.getY() - b.getY()) * (1 + b.getSize()) / (1 + a.getSize());
			double newBvx = b.getVX() + 0.01 * (b.getX() - a.getX()) * (1 + a.getSize()) / (1 + b.getSize());
			double newBvy = b.getVY() + 0.01 * (b.getY() - a.getY()) * (1 + a.getSize()) / (1 + b.getSize());

			// Sets the velocities
			a.setVX(newAvx);
			a.setVY(newAvy);
			b.setVX(newBvx);
			b.setVY(newBvy);
		}

		private static void colliding(Ship ship, Asteroid asteroid) {

			/* Handles collision between a Ship and Asteroid. */

			// Kills Ship and stops Asteroid
			ship.setExists(false);
			asteroid.setVX(0);
			asteroid.setVY(0);
		}

		private static void colliding(Ship.Bullet bullet, Asteroid asteroid) {

			/* Handles collision between a Bullet and Asteroid. */

			// Kills the bullet
			bullet.setExists(false);

			// Damages and moves the Asteroid
			asteroid.takeDmg(bullet.getDamage());
			asteroid.setVX(asteroid.getVX() + bullet.getVX() / (25 * asteroid.getSize()));
			asteroid.setVY(asteroid.getVY() + bullet.getVY() / (25 * asteroid.getSize()));
		}

		private static void colliding(Ship.Bullet bullet, Wall wall) {

			/* Handles collision between a Bullet and Wall. */

			if (wall.getWidth() > wall.getHeight()) {  // Checks if wall is vertical or horizontal
				bullet.setVY(-bullet.getVY());
			} else {
				bullet.setVX(-bullet.getVX());
			}

			// Damages bullet
			bullet.takeDmg();
		}

	}

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

			/* Returns the Rectangle hitbox of the Wall. */

			return this.rect;
		}

		public int getWidth() {

			/* Returns the width of the Wall. */

			return this.width;
		}

		public int getHeight() {

			/* Returns the height of the Wall. */

			return this.height;
		}
	}
}
