package caredog;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static caredog.CareDog.amount_input;
import static caredog.CareDog.panel;

public class EventHandler implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {
            //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void keyPressed(KeyEvent e) {

            if(panel.input == true && amount_input < 10){
                if(e.getKeyCode() == 10){
                    panel.input = false;
                }
                if(e.getKeyCode() == 20){
                    return;
                }
                if(e.getKeyCode() == 8){
                    amount_input -= 1;
                    panel.user_input = panel.user_input.substring(0, Math.max(0, amount_input));
                }
                else{
                    panel.user_input +=  Character.toString(e.getKeyChar());
                    amount_input += 1;
                }
            }
            else{
                if(e.getKeyCode() == 32 && panel.end == true){
                    panel.init();
                }
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
            //To change body of generated methods, choose Tools | Templates.
        }


}
