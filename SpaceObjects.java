/*
    Adam Mehdi & Syed Safwaan
    SpaceObjects.java
    A collection of methods for objects in use during the actual gameplay components of the program.

    Classes:
    - SpaceObjects  To manage the other objects (maybe)
    - Asteroid      The "enemies" of the game
    - Ship          The playable objects
    - Bullet        The Ship's main weapon
    - PowerUp       For augmenting the Ship's abilities
    - Space         The playground of the other objects
    - Space.Wall    A Space component that involves barriers and interesting physics
*/

import java.awt.*;
import java.awt.event.*;
import java.util.*;

class SpaceObjects {

}

class Asteroid {

    /* Template for Asteroid objects, the main antagonistic entities in the game. */

    // Fields //

    private static int count = 0;  // to count active asteroids
    private double x, y, xVel, yVel, rotation;  // asteroid state of motion
    private int hitBoxSideSize[] = {30, 80, 150};
    private int healthSize[] = {3, 10, 30};
    private int size;
    private int rectSide = hitBoxSideSize[size];
    private int health = healthSize[size];
    private Polygon body;  // graphical and structural representation
    private Image[] imgs;

    private boolean exists = true;

    // Constructor //

    public Asteroid(int size, double x, double y, double xVel, double yVel, double rotation) {

        /* Constructs and returns a new Asteroid object. */

        this.size = size;
        this.x = x; this.y = y;
        this.xVel = xVel;
        this.yVel = yVel;
        this.rotation = rotation;
    }


    /* When an asteroid is broken, it breaks into 3 smaller ones */

    public Asteroid[] shatter(){
        this.exists = false;
        if(size != 0){
            Asteroid asteroidOne = new Asteroid(this.size - 1, this.x, this.y, xVel - 1, yVel - 1, rotation + Math.random()*2-1);
            Asteroid asteroidTwo = new Asteroid(this.size - 1, this.x + this.rectSide/2, this.y, xVel + 1, yVel - 1, rotation + Math.random()*2-1);
            Asteroid asteroidThree = new Asteroid(this.size - 1, this.x + this.rectSide/3, this.y + this.rectSide/2, xVel + 1, yVel + 1, rotation + Math.random()*2-1);
            Asteroid[] brokenAsteroids = {asteroidOne, asteroidTwo, asteroidThree};
            return brokenAsteroids;
        }
        else{
            return new Asteroid[]{};  // asteroid is broken, no new ones to return
        }
    }


    /* Moves asteroid depending on velocity */

    public void move(){
        this.x += this.xVel;
        this.y += this.yVel;
    }


    /* Checks for collision with another asteroid */

    public boolean isAsteroidCollision(Asteroid asteroid){
        return true;
    }


    /* Checks for collision with a bullet */

    public boolean isBulletCollision(Ship.Bullet bullet){
        return true;
    }


    /* Checks for collision with a ship */

    public boolean isShipCollision(Ship ship){
        return true;
    }


    /* Health reduction method */

    public void removeHealth(int damage){
        this.health -= damage;
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
        { KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT },
        { KeyEvent.VK_W,  KeyEvent.VK_D,     KeyEvent.VK_S,    KeyEvent.VK_A }
    };

    // Name vars for direction
    private static int FORWARD = 0, RIGHT = 1, LEFT = 2, BACK = 3;

    private double x, y, vx = 0, vy = 0, angle, accel, drag, turnSpeed;  // state of motion
    private int ID, ammo;  // ID used for identification
    private boolean isAccelerating;
    private Polygon body;
    private Image[] imgs;
    final int width = 50;
    final int height = 50;

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

        // slowing ship down by a factor gives ship a max speed
        this.vx *= this.drag;
        this.vy *= this.drag;
    }

    public void move() {
        // must factor in wall and boundary collisions
        this.x += this.vx;
        this.y += this.vy;

    }

    public void fire() {

        /* TBD */

        // will be called on mouseclick
    }

    public void update(Graphics comp) {

        /* Draws the Ship onto a given Graphics component. */

        // magic happens here
    }


    public class Bullet {


        /* Template for Bullet objects, the primary offensive projectile of the game. */

        // Fields //

        private double x, y, angle, speed;

        // Constructor //

        private Bullet(double x, double y, double angle, double speed) {

            /* Constructs and returns a new Bullet object. */

            this.x = x; this.y = y;
            this.angle = angle;
            this.speed = speed;
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

    public Space(String AsteroidData, boolean asteroidSpawn, int width, int height) {

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

    public void update(Graphics screen){

        for(Ship ship : ships){
            ship.update(screen);
        }
        for(Asteroid asteroid : asteroids){
            asteroid.update(screen);
        }
        for(Ship.Bullet bullet : bullets){
            bullet.update(screen);
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


