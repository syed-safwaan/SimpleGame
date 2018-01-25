import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

class SpaceObjects {

}


/* Asteroids that can be shot and destroy the ship */

class Asteroid {
    private static int asteroidCount = 0;
    private double xPos, yPos;  // center of mass of the asteroid
    private double xVel, yVel;
    private int asteroidSize;
    private double rotation;  // aesthetic rotation for game to look more dynamic

    public Asteroid() {

    }

    public void update(Graphics screen){

    }
}


/* Ship(s) user(s) control(s) to shoot bullets at asteroids */

class Ship {
    private static int shipCount = 0;  // number of ships existing on the screen
    private static int maxShipCount = 4;  // max number of players allowed by the program
    // keys used to accelerate up/left/down/right for each player
    private static int movementKeys[][] = {
            {KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT},
            {KeyEvent.VK_W, KeyEvent.VK_D, KeyEvent.VK_S, KeyEvent.VK_A,}
    };
    //private static Image[] shipPics = {Insert ship images};
    private double xPos, yPos;  // top left of the ship
    private double xVel = 0, yVel = 0;  // change in position per frame for ship
    private double angle = 0;  // direction ship is pointing in
    private double velMax;
    private double acceleration;  // strength of thrusters when ship is moved
    private double turningStrength;
    private int shipID;  // distinct int value given to each ship to identify, equal to player number
    private int ammo;
    private boolean accelerating = false;
    private double drag;  // How much the ship slows down by if not accelerating


    public Ship(double xPos, double yPos, double acceleration, double drag, double turningStrength, int ammo) {
        this.shipID = shipCount ++;
        this.xPos = xPos;
        this.yPos = yPos;
        this.acceleration = acceleration;
        this.drag = drag;
        this.turningStrength = turningStrength;
        this.ammo = ammo;
    }


    /* Gets user input and changes velocities of the ship accordingly/change direction */

    public void accelerate(boolean[] keys){
        if(keys[movementKeys[this.shipID][0]]){
            this.xVel += this.acceleration*Math.cos(this.angle);
            this.yVel += this.acceleration*Math.sin(this.angle);
        }

        if(keys[movementKeys[this.shipID][1]]){
            this.angle -= this.turningStrength;
        }
        if(keys[movementKeys[this.shipID][3]]){
            this.angle += this.turningStrength;
        }
        this.xVel *= this.drag;
        this.yVel *= this.drag;
    }


    /* Displays the ship to the screen */

    public void update(Graphics screen){

    }


    /* If possible, will shoot bullet in direction ship is pointed with certain attributes depending on the ship upgrades/powerups */

    public void shoot(boolean[] keys){

    }


    /* Class for shots fired by the ship */

    private class Bullet {
        public Bullet() {

        }
    }


    /* Class for powerups the ship could have */

    private class PowerUp {
        public PowerUp() {

        }
    }


    /* Getters for the ship class */

    public static int getShipCount(){
        return shipCount;
    }
}


/* Space that the game takes place in */

class Space {
    ArrayList<Ship> allShips = new ArrayList<Ship>();
    ArrayList<Asteroid> allAsteroids = new ArrayList<Asteroid>();


    /* Used to makeSpace objects. */

    public Space() {

    }


    /* Adds asteroid to space object */

    public void addAsteroid(){

    }


    /* Adds ship to space object */

    public void addShip(){

    }


    /* Does all interactions of objects in the space */

    public void updateSpace(boolean[] keys){
        for(Ship ship: allShips){
            ship.accelerate(keys);
            ship.shoot(keys);
        }
    }


    /* Displays the space to the screen */

    public void update(Graphics screen){
        for(Ship ship : allShips){
            ship.update(screen);
        }
        for(Asteroid asteroid : allAsteroids){
            asteroid.update(screen);
        }
    }


    /* Area the ship, asteroids, and bullets cannot pass through */

    private class Wall {

    /* Used to make Wall objects. */

        public Wall() {

        /* Constructs a Wall. */

        }
    }

}


