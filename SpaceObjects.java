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
    private static final int maxShipCount = 4;
    private static final int movementKeys[][] = new int[][]{
            {KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT},
            {KeyEvent.VK_W, KeyEvent.VK_D, KeyEvent.VK_S, KeyEvent.VK_A,}
    };
    //private static Image[] shipPics = new Image[maxShipCount]{Insert ship images};
    private int xPos;
    private int yPos;
    private int xVel;
    private int yVel;
    private int acceleration;  // strength of thrusters when ship is moved


    public Ship() {

    }

    /* Gets user input and changes velocities of the ship */

    public void accelerate(boolean[] keys){

    }

    private class Bullet {
        public Bullet() {

        }
    }

    private class PowerUp {

    }
}

class Wall {
    public Wall() {

    }
}


