/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caredog;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import static java.awt.SystemColor.text;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.font.TextLayout;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 *
 * @author Napoleon
 */
class ColorPanel extends JPanel{

    ImageIcon bar = new ImageIcon("hunger_bar.png");
    ImageIcon bg = new ImageIcon("bg.png");
    public static Item bone = new Item();
    public static Item water = new Item(50, 0);
    public static NPC dog = new NPC("dog", -10, 350);
    public static Color greenish = new Color(22, 87, 0);
    public static Color gray = new Color(222, 222, 220);
    public static Color redish = new Color(232, 46, 46);
    public static Color blueish = new Color(103, 195, 252);
    public static Color gold = new Color(251,189,0);
    public static int x = 0;
    public static int y = 0;
    public int scoreboard_x = 60;
    public int scoreboard_y = 200;
    public double speed = 0.025;
    public static int score = 0;
    public static int hunger = 1;
    public static int health = 1;
    public static int thurst = 1;
    int highscore = 0;
    public static boolean water_pressed =false;
    public static boolean bone_pressed = false;
    boolean triggerHunger = false;
    boolean end = false;
    boolean input = true;
    String highscoreHolder = "";
    String user_input = "";
    public static Font block_font;
    public static Font block_fontB;
    
    public ColorPanel() {
        try {
            new Text();
        } catch (FontFormatException ex) {
            Logger.getLogger(ColorPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ColorPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setBackground(Color.BLACK);
        addMouseListener(new PanelListener());
        addMouseMotionListener(new PanelMotionListener());
    }
    
    public void init(){
            bar = new ImageIcon("hunger_bar.png");
            bg = new ImageIcon("bg.png");
            bone = new Item();
            water = new Item(50, 0);
            dog = new NPC("dog", -10, 350);
            greenish = new Color(22, 87, 0);
            gray = new Color(222, 222, 220);
            redish = new Color(232, 46, 46);
            blueish = new Color(103, 195, 252);
            gold = new Color(251,189,0);
            x = 0;
            y = 0;
            scoreboard_x = 60;
            scoreboard_y = 200;
            speed = 0.025;
            score = 0;
            hunger = 1;
            health = 1;
            thurst = 1;
            water_pressed =false;
            bone_pressed = false;
            triggerHunger = false;
            end = false;
    }
    
    public void paintComponent(Graphics g){
        if(input == true){
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setFont(block_font);
            g.setColor(gold);
             g.drawString("Enter Name", getWidth() / 4, getHeight() / 4);
            g.drawString(user_input, getWidth() / 4, getHeight() / 2);
        }
        else if(health < 170){
            g.drawImage(bg.getImage(), 0, 0, this);
            g.drawImage(bar.getImage(), 55, 30, this);
            g.drawImage(bar.getImage(), 55, 80, this);
            g.drawImage(bar.getImage(), 55, 130, this);
            drawBar("hunger", g);
            drawBar("health", g);
            drawBar("water", g);

            dog.draw(g);
            dog.idle();

            g.setFont(block_font);
            g.setColor(gold);
            g.drawString(Integer.toString(score), scoreboard_x, scoreboard_y );

            updateHealth();
            randomize();

            bone.spawnItem("bone", g);
            water.spawnItem("water", g);
        }
        else{
            if(highscore < score){
                highscoreHolder = user_input;
                highscore = score;
            }

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setFont(block_font);
            g.setColor(Color.RED);
            g.drawString("GAMEOVER", getWidth() / 4, getHeight() / 2);
            g.drawString("HighScore:", getWidth() / 4, getHeight() / 2 + 35);
            if(highscore > 0) {
                g.drawString(highscoreHolder + ":" + score, getWidth() / 4, getHeight() / 2 + 55);
            }
            end = true;
        }
        
        try {
            Thread.sleep(10);
            repaint();
        } catch (InterruptedException ex) {
            Logger.getLogger(ColorPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateHealth(){
        if(thurst >= 170 || hunger >= 170 && health < 170){
            health += 1;
        }
    }
    
    public void randomize(){
        Random rng = new Random();
        if(rng.nextDouble() < 0.95 + speed){
            return;
        }
        else{
            if(hunger < 170){
                hunger += 2;
            }
        }
        
        if(rng.nextDouble() < 0.6 + speed){
            return;
        }
        else{
            if(thurst < 170){
                thurst += 4;
            }
        }
    }
    
    public void drawBar(String type, Graphics g){
        if("hunger".equals(type)){
            Color oldColor = g.getColor();
            g.setColor(gray);
            g.drawRect(58, 33, 174 - hunger, 17);
            g.setColor(Color.WHITE);
            g.fillRect(60, 35, 171 - hunger, 14);
            g.setColor(oldColor);
        }
        else if("health".equals(type)){
            Color oldColor = g.getColor();
            g.setColor(redish);
            g.drawRect(58, 33 + 50, 174 - health, 17);
            g.setColor(Color.RED);
            g.fillRect(60, 35 + 50, 171 - health, 14);
            g.setColor(oldColor);
        }
        else if("water".equals(type)){
            Color oldColor = g.getColor();
            g.setColor(blueish);
            g.drawRect(58, 33 + 100, 174 - thurst, 17);
            g.setColor(Color.BLUE);
            g.fillRect(60, 35 + 100, 171 - thurst, 14);
            g.setColor(oldColor);
        }
    }
    

}
