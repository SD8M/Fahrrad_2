package fahrrad_2;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Wrag {
    
    int x;              //Koordinate
    int y;              //Koordinate
    int v;              //Geschwindigkeit
    Image img = new ImageIcon("res/Wrag.png").getImage();
    Road road;
    
    public Rectangle getRect() {
        return new Rectangle(x, y, 10, 10);
    }
    
    public Wrag(int x, int y, int v, Road road) {
        this.x = x;
        this.y = y;
        this.v = v;
        this.road = road;
    }
    
    public void move () {
        x = x - road.p.v + v;
        
      
    }
}

    

