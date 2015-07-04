package fahrrad_2;
 
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Player {

    public static final int MAX_V = 28;                                         //Maximale Geschwindigkeit von dem Spieler
    public static final int MAX_TOP = 10;                                       //Maximale Koordinaten zum oberen Rand des Bildschirms
    public static final int MAX_BOTTOM = 420;                                   //Maximale Koordinaten zum unteren Rand des Bildschirms

    //Verzeichnis mit den Bildern
    Image img_c = new ImageIcon("res/Player.png").getImage();                   
    Image img_l = new ImageIcon("res/Player_left_new.png").getImage();
    Image img_r = new ImageIcon("res/Player_right_new.png").getImage();
    
    Image img = img_c;
    
        public Rectangle getRect() {
        return new Rectangle(x, y, 50, 100);
    }

    int v = 0;              //Anfangsgeschwindigkeit in Pixeln
    int dv = 0;             //Beschleunigung
    int s = 0;              //Zurückgelegte Strecke

    int x = 30;             //Koordinaten des Spielers auf der Straße
    int y = 100;            //Koordinaten des Spielers auf der Straße
    int dy = 0;

    int layer1 = 0;                 //Anfangskoordinaten des ersten Hintergunds
    int layer2 = 1200;              //Der zweite Hintergrund

    //Bremsen
    public void move() {                //
        s += v;                         //wie viel wir gefahren sind (Die Strecke wird größer **)
        v += dv;                        //Kontrolliert die Beschläunigung
        if (v <= 0) {                   //Wenn die Geschwindigkeit negativ ist, bleibt der Fahrrad stehen
            v = 0;
        }
        
        //Fixieren
        if (v >= MAX_V) {               //Keine Beschleunigung mehr
            v = MAX_V;
        }
        
        //Fixierung der Straßenränder. Wenn oben, dann nicht höher.
        y -= dy;
        if (y <= MAX_TOP) {
            y = MAX_TOP;
        }
        //Fixierung der Straßenränder. Wenn unten, dann nicht nicht mehr runter.
        if (y >= MAX_BOTTOM) {
            y = MAX_BOTTOM;
        }
        if (layer2 - v <= 0) {          //Siehe * Wenn die Koordinaten vom ersten Hintergrund <= 0
            layer1 = 0;                 //Wird das ganze neugesetzt!!!
            layer2 = 1200;
        } else {
            layer1 -= v;                //** die Koordinaten des Hintergrunds werden kleiner. (Schafft die Illusion der Bewegung für die erste Straße) (Gezeichnet wird in der Klasse Road)
            layer2 -= v;                //** die Koordinaten des Hintergrunds werden kleiner. (Schafft die Illusion der Bewegung für die zweite Straße) (Gezeichnet wird in der Klasse Road)
        }

    }
    //Legen die Tasten fest ****Siehe Road
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT) {   //Beim betätigen der Taste "rechts" beschleunigt das Rad
            dv = 1;                       //Intervall der Beschleunigung        Beim drücken der Taste, ändert sich dv (Beschleunigung) von 0 auf 1. Und jede 20 millisec, Timer aktiviert die Methode move und die Geschwindigkeit erhöht sich.

        }
        if (key == KeyEvent.VK_LEFT) {     //Beim betätigen der Taste "links" bremst das Rad
            dv = -1;                       //Bremsintervall
        }
        if (key == KeyEvent.VK_UP) {
            dy = 5;                         //Geschwindigkeit nach oben (links) lenken
            img = img_l;

        }

        if (key == KeyEvent.VK_DOWN) {
            dy = -5;                        ////Geschwindigkeit nach unten (rechts) lenken
            img = img_r;
        }
    }

      //Beim loslassen der Taste ----> 
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_LEFT) {              //-----> Geschwindigkeit wird nicht mehr erhöht/gesenkt
            dv = 0;
        }
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN)                   //-----> Fahrrad fährt nicht weiter nach links/rechts
            dy = 0;
            img = img_c;
    }
}

        