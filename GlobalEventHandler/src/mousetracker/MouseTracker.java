/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mousetracker;


import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;


/**
 *
 * @author Napoleon
 */
public class MouseTracker {
    /**
     * @param args the command line arguments
     */
    public static JFrame gui;
    public static ColorPanel panel;
    
    public static void main(String[] args) {
        // TODO code application logic here
                // might throw a UnsatisfiedLinkError if the native library fails to load or a RuntimeException if hooking fails 

        
        
            gui = new JFrame();
            gui.setTitle("MouseTracker");
            ImageIcon img = new ImageIcon("arrow.png");
            gui.setIconImage(img.getImage());
            gui.setSize(400,300);
            gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            panel = new ColorPanel();
            Container pane = gui.getContentPane();
            pane.add(panel);
            gui.setVisible(true);
            gui.addMouseWheelListener(new MouseWheelListener(){
                @Override
                public void mouseWheelMoved(MouseWheelEvent e) {
                    if(panel.offset <= 0 || e.getWheelRotation() > 0){
                        if(((panel.offset / -48) - 2) < panel.typed || e.getWheelRotation() < 0){
                            panel.offset += (e.getWheelRotation() * -10);
                        }
                    }
                    panel.repaint();
                }
            });
            gui.addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                    panel.ApplicationOpen = true;
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                   
                }
            });
    }
}
    