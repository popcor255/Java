package caredog;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static caredog.ColorPanel.*;

public class PanelListener extends MouseAdapter {

    public void mousePressed(MouseEvent e){

        x = e.getX();
        y = e.getY();


        if(bone.containsPoint(x, y)){
            bone_pressed = true;
        }
        else if(water.containsPoint(x,y)){
            water_pressed = true;
        }
        else if(dog.containsPoint(x,y)){
            int per_thurst = Math.abs(thurst - 170);
            int per_hunger = Math.abs(hunger - 170);
            int per_health = Math.abs(health - 170);


            score += (per_thurst + per_hunger + per_health) / 20;
        }
    }

    public void mouseReleased(MouseEvent e){
        bone_pressed = false;
        water_pressed = false;

        if(bone.feed() == true){
            hunger = Math.max(hunger - 10, 0);
        }
        else if(water.feed() == true){
            thurst = Math.max(thurst - 10, 0);
        }
    }
}
