import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

enum GameStates {MAIN, HELP, CREDITS, STARTGAME};

public class Asteroids extends JFrame implements ActionListener,KeyListener{
    Timer myTimer;
    GamePanel game;
    static Asteroids frame;
    final int WIDTH = 1280;
    final int HEIGHT = 720;

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

    public void keyPressed(KeyEvent e) {
        game.setKey(e.getKeyCode(),true);
    }

    public void keyReleased(KeyEvent e) {
        game.setKey(e.getKeyCode(),false);
    }

    public void actionPerformed(ActionEvent evt){
        if(game != null){
            game.refresh();
            game.repaint();
        }
    }


    /* Main method where the program starts */

    public static void main(String[] arguments) {
        frame = new Asteroids();
    }
}



class GamePanel extends JPanel {
    private boolean []keys;
    GameStates currentGameState = GameStates.MAIN;

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
        switch(currentGameState){
            case MAIN:


            default:
                break;
        }




    }
}