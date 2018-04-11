/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mousetracker;

/**
 *
 * @author Napoleon
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lc.kra.system.mouse.GlobalMouseHook;
import lc.kra.system.mouse.event.GlobalMouseAdapter;
import lc.kra.system.mouse.event.GlobalMouseEvent;
import static mousetracker.MouseTracker.panel;

/**
 *
 * @author Napoleon
 */
class ColorPanel extends JPanel{

    private Font customFont;
    private int locX, locY;
    private int pressed;
    private String formatLocation = "int cords[][] = {\n";
    private String formatTimeLocate = "int TimeCords[][] = {\n";
    private String formatTimeEvents = "int TimeEvents[][] = {\n";
    private String formatEvents = "int TimeEvents[] = {\n";
    private String formate;
    long timeNow = System.nanoTime();
    public int typed = 0;
    private Graphics2D g2d;
    private final GlobalMouseHook mouseHook;
    private boolean flash = false;
    public int offset = 0;
    public int maxLength = 10000;
    public boolean ApplicationOpen = false;
    String location[] = new String[maxLength];
    String locTime[] = new String[maxLength];
    String events[] = new String[maxLength];
    String eventsTime[] = new String[maxLength];
    
        public ColorPanel() {

            GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook();
            
            keyboardHook.addKeyListener(new GlobalKeyAdapter() {
                @Override public void keyPressed(GlobalKeyEvent event) {

                }

                @Override public void keyReleased(GlobalKeyEvent event) {
                     events[pressed] = Integer.toString((int)event.getVirtualKeyCode());
                     eventsTime[pressed] = Integer.toString((int)(TimeUnit.MILLISECONDS.toSeconds(System.nanoTime() - timeNow)));
                     timeNow = System.nanoTime();
                     System.out.println(eventsTime[pressed]);
                     pressed++;
                    
                    if(event.getVirtualKeyCode() == 67 && event.isControlPressed() == true && ApplicationOpen == true){
                        flash = true;
                        format();
                    }
                    else if(event.getVirtualKeyCode() == 13){
                        if(typed < maxLength - 1){
                            typed++;
                            flash = false;
                        }
                        else{
                            flash = true;
                        }
                    }
                    else if(event.getVirtualKeyCode() == 8){
                        if(typed > 0){
                            typed--;
                            flash = false;
                        }
                        else{
                            flash = true;
                        }
                    }
                    else{
                            flash = false;
                    }
                    repaint();
                }
            });


            mouseHook = new GlobalMouseHook();

            mouseHook.addMouseListener(new GlobalMouseAdapter() {
                @Override public void mousePressed(GlobalMouseEvent event)  {
                    panel.ApplicationOpen = false;
                }
                @Override public void mouseReleased(GlobalMouseEvent event)  {
                    //blank
                }
                @Override public void mouseMoved(GlobalMouseEvent event) {
                    flash = false;
                    locX = event.getX();
                    locY = event.getY();
                    repaint();
                }


                @Override public void mouseWheel(GlobalMouseEvent event) {
                    //blank
                }
            });

        }

        public void paintComponent(Graphics g){

            g2d = (Graphics2D)g;
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setColor(Color.WHITE);
            g.fillRect(0,0,getWidth(), getHeight());
            setFont("type", 50);
            g.setFont(customFont);
            if(flash == true){
                 g.setColor(Color.RED);
            }
            else{
                g.setColor(Color.BLACK);
            }
            location[typed] = Integer.toString(locX) + " , " + Integer.toString(locY); 
            for(int i = 0; i <= typed; i++){
                if(location[i] == null){
                    location[i] = "empty";
                }
                String textCounter = "";
                
                if(i != typed){
                    textCounter = Integer.toString(i + 1) + ") ";
                }
                
                g.drawString(textCounter, (getWidth() / 2) - 175, offset + 125 + ((typed - i) * 50));
                g.drawString(location[i], (getWidth() / 2) - (g.getFontMetrics().stringWidth(location[i]) / 2), offset + 125 + ((typed - i) * 50));
            }
        }
        
        
        public void format(){
            
            formatLocation = "int cords[][] = {\n";
            
            for(int i = 0; i < typed; i++){
                formatLocation += "             ";
                formatLocation += "{" + location[i] + "},\n";
            }
            formatLocation += "        };";
            
            for(int i = 0; i < typed; i++){
                formatLocation += "             ";
                formatLocation += "{" + location[i] + "},\n";
            }
            formatLocation += "        };";
            
            setClipboard(formatLocation);
        }
    
        public static void setClipboard(String value){
            StringSelection selection = new StringSelection(value);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
       }
    
    
        public void setFont(String fontname, int size){
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File(fontname + ".ttf"))).deriveFont(Font.PLAIN,size);
        } catch (FontFormatException ex) {
            Logger.getLogger(ColorPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ColorPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
