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

import java.awt.*;
import java.awt.event.*;
import java.util.*;

class Asteroid {

    /* Template for Asteroid objects, the main antagonistic entities in the game. */

    // Fields //

    private static int count = 0;  // to count active asteroids

    private double x, y, xVel, yVel, rotation;  // asteroid state of motion
    private int size, rectSize, hp;
    private Polygon body;  // graphical and structural representation

    private boolean exists;

    // Constructor //

    public Asteroid(int size, double x, double y, double xVel, double yVel, double rotation) {

        /* Constructs and returns a new Asteroid object. */

        int sizes[] = {30, 80, 150}, hpSizes[] = {3, 10, 30};  // to assign asteroid properties on init

        this.size = size;
        this.x = x; this.y = y;
        this.xVel = xVel;
        this.yVel = yVel;
        this.rotation = rotation;

        this.rectSize = sizes[size];
        this.hp = hpSizes[size];

        this.exists = true;
    }

    private void makeShape() {

        /* Constructs a Polygon for the Asteroid. */

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
    }

    public void takeDmg(int damage){

        /* Reduces Asteroid health by a given amount. */

        this.hp -= damage;
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


    public class Bullet {


        /* Template for Bullet objects, the primary offensive projectile of the game. */

        // Fields //

        private double x, y, angle, speed;
        private boolean exists;

        // Constructor //

        private Bullet(double x, double y, double angle, double speed) {

            /* Constructs and returns a new Bullet object. */

            this.x = x; this.y = y;
            this.angle = angle;
            this.speed = speed;

            this.exists = true;
        }

        public void move() {

            /* Moves the Bullet. */

            this.x += this.speed * Math.cos(this.angle);
            this.y += this.speed * Math.sin(this.angle);
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

        //Checking for objects that must be removed
        for(int i = asteroids.size(); i > 0; i--){
            asteroids.get(i).shatter();
        }

    }

    private class Physics {

        /* Checks if ship collided with an asteroid */

        public boolean collide(Ship s, Asteroid a) {
            return true;
        }
    }

    // will add some new update methods later

    private class Wall {

    /* Used to make Wall objects, impervious barriers with some special effects. */

        public Wall() {

        /* Constructs and returns a new Wall object. */

        }
    }
}
