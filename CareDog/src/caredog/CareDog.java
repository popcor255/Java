package caredog;

import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Napoleon
 */
public class CareDog {

    /**
     * @param args the command line arguments
     */
    static ColorPanel panel;
    static Container pane;
    static int amount_input = 0;
    
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame gui = new JFrame();
        EventHandler evt = new EventHandler();
        gui.setTitle("Doggy");
        gui.setSize(300,600);
        gui.setResizable(false);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new ColorPanel();
        pane = gui.getContentPane();
        pane.add(panel);
        gui.setVisible(true);
        gui.addKeyListener(evt);
        ImageIcon img = new ImageIcon("icon.png");
        gui.setIconImage(img.getImage());
    }
    
}
