import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

enum GameStates {MAIN, HELP, CREDITS, STARTGAME};

public class Asteroids extends JFrame implements ActionListener,KeyListener{
    Timer myTimer;
    GamePanel game;
    static Asteroids frame;
    final int WIDTH = 1280;
    final int HEIGHT = 720;
    GameStates currentGameState = GameStates.MAIN;

    public Asteroids() {
        super("Shooty Circles");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);

        myTimer = new Timer(10, this);	 // trigger every 10 ms
        myTimer.start();  // start timer

        game = new GamePanel();
        add(game);
        addKeyListener(this);
        setResizable(false);
        setVisible(true);
    }

    public void keyTyped(KeyEvent e) {}


    /* Updates which keys are currently pressed by the user */

    public void keyPressed(KeyEvent e) {
        game.setKey(e.getKeyCode(),true);
    }

    public void keyReleased(KeyEvent e) {
        game.setKey(e.getKeyCode(),false);
    }

    public void actionPerformed(ActionEvent evt){
        switch(currentGameState){
            case MAIN:

            case HELP:

            case CREDITS:

            default:
                break;
        }
    }


    /* Main method where the program starts */

    public static void main(String[] arguments) {
        frame = new Asteroids();
    }
}



class GamePanel extends JPanel {
    private boolean[] keys;

    public GamePanel(){
        keys = new boolean[KeyEvent.KEY_LAST+1];
        setSize(WIDTH, HEIGHT);
    }

    public void setKey(int k, boolean v) {
        keys[k] = v;
    }

    public void refresh(){

    }

    public void paintComponent(Graphics screen){

    }
}