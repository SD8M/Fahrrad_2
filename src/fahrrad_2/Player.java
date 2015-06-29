package fahrrad_2;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon

public class Player {

    public static final int MAX_V = 28;
    public static final int MAX_TOP = 10;
    public static final int MAX_BOTTOM = 350;

    //Verzeichnis mit den Bildern
    Image img_c = new ImageIcon("res/Player.png").getImage();                   
    Image img_l = new ImageIcon("res/Player_left_new.png").getImage();
    Image img_r = new ImageIcon("res/Player_right_new.png").getImage();
    
    Image img = img_c;
    
        public Rectangle getRect() {
        return new Rectangle(x, y, 100, 100);
    }

    int v = 0;              //Anfangsgeschwindigkeit in Pixeln
    int dv = 0;             //Beschleunigung
    int s = 0;              //Zurückgelegte Strecke

    int x = 30;             //Koordinaten des Spielers auf der Straße
    int y = 100;            //Koordinaten des Spielers auf der Straße
    int dy = 0;

    int layer1 = 0;                 //Anfangskoordinaten des ersten Hintergunds
    int layer2 = 1200;              //Der zweite Hintergrund

    public void move() {               //
        s += v;                        //
        v += dv;                        //
        if (v <= 0) {
            v = 0;
        }
        if (v >= MAX_V) {
            v = MAX_V;
        }
        y -= dy;
        if (y <= MAX_TOP) {
            y = MAX_TOP;
        }
        if (y >= MAX_BOTTOM) {
            y = MAX_BOTTOM;
        }
        if (layer2 - v <= 0) {          //Siehe * 
            layer1 = 0;
            layer2 = 1200;
        } else {
            layer1 -= v;
            layer2 -= v;
        }

    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT) {
            dv = 1;

        }
        if (key == KeyEvent.VK_LEFT) {
            dv = -1;
        }
        if (key == KeyEvent.VK_UP) {
            dy = 5;
            img = img_l;

        }

        if (key == KeyEvent.VK_DOWN) {
            dy = -5;
            img = img_r;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_LEFT) {
            dv = 0;
        }
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
            dy = 0;
            img = img_c;
