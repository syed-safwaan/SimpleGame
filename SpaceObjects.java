import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

class SpaceObjects {

}

class Asteroid {
    public Asteroid() {

    }
}

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


    public Ship(double xPos, double yPos, double acceleration) {
        this.shipID = shipCount ++;
        this.xPos = xPos;
        this.yPos = yPos;
        this.acceleration = acceleration;
    }

    /* Gets user input and changes velocities of the ship accordingly/change direction */

    public void accelerate(boolean[] keys){
        if(keys[movementKeys[this.shipID][0]]){
            this.xVel += this.acceleration*Math.cos(this.angle);
            this.yVel += this.acceleration*Math.sin(this.angle);
        }
        if(keys[movementKeys[this.shipID][1]]){
            this.angle += this.turningStrength;
        }
        if(keys[movementKeys[this.shipID][3]]){
            this.angle -= this.turningStrength;
        }
    }

    private class Bullet {
        public Bullet() {

        }
    }

    private class PowerUp {

    }
}

class Wall {

    /* Used to make Wall objects. */

    public Wall() {

        /* Constructs a Wall. */

    }
}


