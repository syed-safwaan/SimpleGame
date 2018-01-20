import javax.swing.*;
import java.awt.*;

class Interface {
}

class Menu {
    Image background;
    Graphics screen;
    JButton[] buttons;



    public Menu(Image background, Graphics screen, JButton... buttons){
        this.buttons = buttons;
        this.background = background;
    }

    public void update(){
        screen.drawImage(background, 0, 0, Asteroids.frame.game);
    }


}
