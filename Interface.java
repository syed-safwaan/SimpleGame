import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

class Interface {
}

class Menu {
    Image background;
    Graphics screen;
    JButton[] buttons;
    String buttonPressed;



    public Menu(Image background, Graphics screen, JButton... buttons){
        this.buttons = buttons;
        this.background = background;
    }


    /* Main method where the program starts */

    public void update(){
        screen.drawImage(background, 0, 0, Asteroids.frame.game);
    }


    /* Checks if a button was clicked in specific frame */

    public String checkButtons(ActionEvent action){
        buttonPressed = "";
        return buttonPressed;
    }


}
