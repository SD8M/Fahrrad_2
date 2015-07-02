package fahrrad_2;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Enemy {
    
    int x;              //Koordinate
    int y;              //Koordinate
    int v;              //Geschwindigkeit
    Image img = new ImageIcon("res/Enemy.png").getImage();                      //Importieren den Gegner 
    Road road;
    
    public Rectangle getRect() {
        return new Rectangle(x, y, 10, 10);
    }
    
    public Enemy(int x, int y, int v, Road road) {
        this.x = x;
        this.y = y;
        this.v = v;
        this.road = road;
    }
    
    //Geschwindigkeit von Gegnern
    public void move () {                   
        x = x - road.p.v + v;
        
      
    }
}
