package caredog;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import static caredog.ColorPanel.*;

public class PanelMotionListener extends MouseMotionAdapter {
    public void mouseDragged(MouseEvent e){
        if(bone_pressed == true){
            x = e.getX() - 20;
            y = e.getY() - 15;
            bone.move(x, y);
        }
        else if(water_pressed == true){
            x = e.getX() - 20;
            y = e.getY() - 15;
            water.move(x, y);
        }

    }
}
