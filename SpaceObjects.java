/*
    Adam Mehdi & Syed Safwaan
    SpaceObjects.java
    A collection of methods for objects in use during the actual gameplay of the program.

    Classes:
    - SpaceObjects  To manage the other objects (maybe)
    - Asteroid      The "enemies" of the game
    - Ship          The playable objects
    - > Bullet      The Ship's main weapon
    - > PowerUp     For augmenting the Ship's abilities
    - Space         The playground of the other objects
    - > Wall        A Space component that involves barriers and interesting physics
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.geom.Area;

class Asteroid {

    /* Template for Asteroid objects, the main antagonistic entities in the game. */

    // Fields //

    private static int count = 0;  // to count active asteroids
    private Random rand = new Random();
    private double x, y, xVel, yVel, rotation, rotationVel;  // asteroid state of motion
    private int size, rectSize, hp;
    private int sizes[] = {30, 80, 200}, hpSizes[] = {3, 10, 30};  // to assign asteroid properties on init
    private int bodyType;
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

    public Asteroid(int size, double x, double y, double xVel, double yVel, double rotationVel) {

        /* Constructs and returns a new Asteroid object. */

        this.size = size;
        this.x = x; this.y = y;
        this.xVel = xVel;
        this.yVel = yVel;
        this.rotationVel = rotation;
        this.rectSize = sizes[size];
        this.hp = hpSizes[size];
        this.exists = true;
        this.bodyType = rand.nextInt(3);
        this.makeShape();

    }

    /* Constructs a Polygon for the Asteroid. */

    private void makeShape() {
        int pointCount = polygonX[this.size][bodyType].length;
        int[] xCoords = new int[pointCount];
        int[] yCoords = new int[pointCount];
        for (int i = 0; i < pointCount; i++) {
            xCoords[i] = (int) (this.x + polygonX[this.size][this.bodyType][i]);
            yCoords[i] = (int) (this.y + polygonY[this.size][this.bodyType][i]);
        }
        this.body = new Polygon(xCoords, yCoords, pointCount);

    }

    public Asteroid[] shatter(){

        /* Sets the existence of the current Asteroid to false and returns 3 new smaller asteroids in an array. */

        this.exists = false;

        if(size > 0) {
            return new Asteroid[] {
                new Asteroid(this.size - 1, this.x, this.y, xVel - 1, yVel - 1, rotation + Math.random() * 2 - 1),
                new Asteroid(this.size - 1, this.x + this.rectSize / 2, this.y, xVel + 1, yVel - 1, rotation + Math.random() * 2 - 1),
                new Asteroid(this.size - 1, this.x + this.rectSize / 3, this.y + this.rectSize / 2, xVel + 1, yVel + 1, rotation + Math.random() * 2 - 1)
            };
        }
        else return new Asteroid[] {};  // asteroid is broken, no new ones to return
    }


    public void move(){

        /* Moves asteroid depending on velocity. */

        this.x += this.xVel;
        this.y += this.yVel;
        this.makeShape();
    }

    public void takeDmg(int damage){

        /* Reduces Asteroid health by a given amount. */

        this.hp -= damage;
    }

    public Polygon getShape(){
        return this.body;
    }


    /* Display asteroid to screen */

    public void update(Graphics comp) {

        /* Draws the Asteroid onto a given Graphics component. */

    }
}

class Ship {

    /* Used to make Ships, the PC bodies that can do stuff. */

    // Fields //

    private static int count = 0;  // to count active Ships
    private static int maxCount = 2;  // max active Ships
    // Controls
    private static int controls[][] = {
        { KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_SPACE},
        { KeyEvent.VK_W,  KeyEvent.VK_D,     KeyEvent.VK_S,    KeyEvent.VK_A,    KeyEvent.VK_R}
    };
    // Name vars for direction
    private static int FORWARD = 0, RIGHT = 1, BACK = 2, LEFT = 3, SHOOT = 4;
    private double x, y, vx = 0, vy = 0, angle, accel, drag, turnSpeed;  // state of motion
    private int ID;  // ID used for identification
    private int ammo, attackRate, shootingCooldown;
    private boolean isAccelerating;
    private Polygon body;
    int[] polygonX = {30, 60, 0};
    int[] polygonY = {0, 100, 100};
    private Image[] imgs;
    private final int width = 50, height = 50;


    public Ship(double x, double y, double accel, double drag, double turnSpeed, int ammo) {

        /* Constructs and returns a new Ship object. */

        this.ID = count ++;
        this.x = x; this.y = y;
        this.accel = accel;
        this.drag = drag;
        this.turnSpeed = turnSpeed;
        this.ammo = ammo;
        this.makeShape();

    }

    private void makeShape() {
        int pointCount = 3;
        int[] xCoords = new int[pointCount];
        int[] yCoords = new int[pointCount];
        for (int i = 0; i < pointCount; i++) {
            xCoords[i] = (int) (this.x + polygonX[i]);
            yCoords[i] = (int) (this.y + polygonY[i]);
        }
        this.body = new Polygon(xCoords, yCoords, pointCount);

    }

    public static int getCount() {
        return count;
    }

    public void accelerate(boolean[] keys) {

        /* Moves the Ship considering the currently pressed keys. */

        isAccelerating = false;
        if (keys[controls[this.ID][FORWARD]]) {  // moving forward
            this.vx += this.accel * Math.cos(this.angle);
            this.vy += this.accel * Math.sin(this.angle);
            isAccelerating = true;
        } if (keys[controls[this.ID][RIGHT]]) {  // turning right
            this.angle -= this.turnSpeed;
        } if (keys[controls[this.ID][LEFT]]) {  // turning left
            this.angle += this.turnSpeed;
        }

        // Slowing ship down by a factor gives ship a max speed
        this.vx *= this.drag;
        this.vy *= this.drag;

    }

    public void move() {
        // must factor in wall and boundary collisions
        this.x += this.vx;
        this.y += this.vy;
        this.makeShape();

    }

    public ArrayList<String> queryAction(boolean[] keys) {

        ArrayList<String> actions = new ArrayList<>();

        if (keys[controls[this.ID][SHOOT]] && this.shootingCooldown < 0) {
            actions.add("fire");
        }

        return actions;
    }

    public Bullet fire() {
        return new Bullet(this.x, this.y, this.angle, 4);
    }

    public void update(Graphics comp) {

        /* Draws the Ship onto a given Graphics component. */

        // magic happens here
    }

    public Polygon getShape(){
        return this.body;
    }


    public class Bullet {


        /* Template for Bullet objects, the primary offensive projectile of the game. */

        // Fields //

        private double x, y, angle, speed;
        private boolean exists;
        private Rectangle hitbox;
        private int radius = 30;

        // Constructor //

        private Bullet(double x, double y, double angle, double speed) {

            /* Constructs and returns a new Bullet object. */

            this.x = x; this.y = y;
            this.angle = angle;
            this.speed = speed;
            this.exists = true;
            this.makeShape();

        }

        public void makeShape(){
            this.hitbox = new Rectangle((int) this.x - 15, (int) this.y - 15, this.radius, this.radius);
        }

        public void move() {

            /* Moves the Bullet. */

            this.x += this.speed * Math.cos(this.angle);
            this.y += this.speed * Math.sin(this.angle);
        }

        public Rectangle getShape(){
            return this.hitbox;
        }


        /* Displays bullet to screen */

        public void update(Graphics screen){

        }
    }


    private class PowerUp {
        private PowerUp() {

        }

    }

}


class Space {

    /* Used for managing the rest of the active game objects. */

    private ArrayList<Ship> ships = new ArrayList<>();
    private ArrayList<Asteroid> asteroids = new ArrayList<>();
    private ArrayList<Ship.Bullet> bullets = new ArrayList<>();
    private ArrayList<Space.Wall> walls = new ArrayList<>();

    private SpacePanel screen;

    public Space(String asteroidData, boolean asteroidSpawn, int width, int height) {

        /* Constructs and returns a new Space object. */

    }

    public void addAsteroid(Asteroid a) {

        /* Adds a Asteroid to the Space. */

        asteroids.add(a);
    }

    public void addShip(Ship s) {

        /* Adds a Ship to the Space. */

        ships.add(s);
    }

    public void update(Graphics screen, boolean[] keys){

        /* Checking for collisions between pairs of objects */

        //Asteroids colliding
        for(int i = 0; i < asteroids.size(); i++){
            for(int j = 0; j < i; j++){
                if(Space.Physics.collide(asteroids.get(i).getShape(), asteroids.get(j).getShape())){
                    Space.Physics.colliding(asteroids.get(i), asteroids.get(j));
                }
            }
        }

        //Asteroids with ship
        for(int i = 0; i < asteroids.size(); i++){
            for(int j = 0; j < ships.size(); j++){
                if(Space.Physics.collide(asteroids.get(i).getShape(), ships.get(j).getShape())){
                    Space.Physics.colliding(ships.get(j), asteroids.get(i));
                }
            }
        }

        //Asteroids with bullets
        for(int i = 0; i < asteroids.size(); i++){
            for(int j = 0; j < bullets.size(); j++){
                if(Space.Physics.collide(asteroids.get(i).getShape(), bullets.get(j).getShape())){
                    Space.Physics.colliding(bullets.get(j), asteroids.get(i));
                }
            }
        }

        //Asteroids with wall
        for(int i = 0; i < asteroids.size(); i++){
            for(int j = 0; j < walls.size(); j++){
                if(Space.Physics.collide(asteroids.get(i).getShape(), walls.get(j).getShape())){
                    Space.Physics.colliding(asteroids.get(j), walls.get(i));
                }
            }
        }

        //Ship with wall
        for(int i = 0; i < ships.size(); i++){
            for(int j = 0; j < walls.size(); j++){
                if(Space.Physics.collide(ships.get(i).getShape(), walls.get(j).getShape())){
                    Space.Physics.colliding(ships.get(j), walls.get(i));
                }
            }
        }

        //Bullet with wall
        for(int i = 0; i < bullets.size(); i++){
            for(int j = 0; j < walls.size(); j++){
                if(Space.Physics.collide(bullets.get(i).getShape(), walls.get(j).getShape())){
                    Space.Physics.colliding(bullets.get(j), walls.get(i));
                }
            }
        }


        for(Ship ship : ships){
            ship.accelerate(keys);
            ship.move();
            ArrayList<String> actions = ship.queryAction(keys);
            ship.update(screen);
        }
        for(Asteroid asteroid : asteroids){

            asteroid.update(screen);
        }
        for(Ship.Bullet bullet : bullets){

            bullet.update(screen);
        }

        // Checking for objects that must be removed
        for(int i = asteroids.size(); i > 0; i--){
            asteroids.get(i).shatter();
        }

    }

    private static class Physics {

        /* Checks if ship collided with an asteroid */

        public static boolean collide(Shape a, Shape b) {
            Area areaA = new Area(a);
            areaA.intersect(new Area(b));
            return !areaA.isEmpty();
        }

        public static void colliding(Asteroid asteroidA, Asteroid asteroidB){
            //bounces asteroids
        }

        public static void colliding(Ship ship, Asteroid asteroid){
            //Kills ship
        }

        public static void colliding(Ship.Bullet bullet, Asteroid asteroid){
            //Hurts asteroid, kills bullet
        }

        public static void colliding(Asteroid asteroid, Space.Wall wall){
            //Bounces asteroid
        }

        public static void colliding(Ship ship, Space.Wall wall){
            //Bounces ship
        }

        public static void colliding(Ship.Bullet bullet, Space.Wall wall){
            //Kills bullet
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

        public Rectangle getShape(){
            return this.rect;
        }
    }

    class SpacePanel extends JPanel implements KeyListener {

    	private boolean[] keys;
    	private ImageIcon background;

    	public SpacePanel() {
			keys = new boolean[KeyEvent.KEY_LAST + 1];
			this.addKeyListener(this);
			this.setLayout(null);
		}

		public boolean getKeyPress(int keycode) {
    		return keys[keycode];
		}

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			keys[e.getKeyCode()] = true;
		}

		@Override
		public void keyReleased(KeyEvent e) {
			keys[e.getKeyCode()] = false;
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			g.drawImage(background.getImage(), 0, 0, this);

			for (Ship ship : ships) ship.update(g);
			for (Ship.Bullet bullet : bullets) bullet.update(g);
			for (Asteroid asteroid : asteroids) asteroid.update(g);
		}
	}
}
