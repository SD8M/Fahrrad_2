package fahrrad_2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Road extends JPanel implements ActionListener, Runnable {          //Straße füllt das Frame komplett aus

    //Jede 20 Milisek Erfüllt der Timer die Funktion Action Performed von Element this
    Timer mainTimer = new Timer(20, this);                                      //Startet die Funktion "Action Performed"  jede 20 millisec 

    Image img = new ImageIcon("res/Road.jpg").getImage();                       //Verzeichnis mit dem Bild der Straße

    Player p = new Player();        //Fügen den Spieler ein

    Thread enemiesFactory = new Thread(this);
    
    Thread audioThread = new Thread(new AudioThread());

    List<Enemy> enemies = new ArrayList<Enemy>();

    public Road() {
        mainTimer.start();
        enemiesFactory.start();
        audioThread.start();
        addKeyListener(new MyKeyAdapter());
        setFocusable(true);                                                     //Fokusiert sich auf die Tastatur
    }

    @Override
    public void run() {

        while (true) {
            Random rand = new Random();
            try {
                Thread.sleep(rand.nextInt(2000));
                enemies.add(new Enemy(1200, rand.nextInt(600), rand.nextInt(40), this));
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }
    }

    private class MyKeyAdapter extends KeyAdapter {

        public void keyPressed(KeyEvent e) {                                    //Beim drücken auf die Taste   
            p.keyPressed(e);

        }

        public void keyReleased(KeyEvent e) {                                   //Beim loslassen der Taste
            p.keyReleased(e);

        }

    }

    public void paint(Graphics g) {                                             //Methode
        g = (Graphics2D) g;
        g.drawImage(img, p.layer1, 0, null);                                    //Der erste Hintergrund wird gezeichnet
        g.drawImage(img, p.layer2, 0, null);                                    //Der zweite Hintergrund wird gezeichnet
        g.drawImage(p.img, p.x, p.y, null);                                     //Koordinaten des Spielers   

        double v = (50/Player.MAX_V) * p.v;
        
        g.setColor(Color.orange);
        Font font = new Font("Monospaced", Font.ITALIC, 20);                      //Font font = new Font("Arial", Font.ITALIC, 20);
        g.setFont(font);
        g.drawString("Geschwindigkeit: " + v + " km/h", 100, 30);

        Iterator<Enemy> i = enemies.iterator();
        while (i.hasNext()) {
            Enemy e = i.next();
            if (e.x >= 2400 || e.x <= - 2400) {
                i.remove();
            } else {
                e.move();
                g.drawImage(e.img, e.x, e.y, null);

            }

        }
    }

    public void actionPerformed(ActionEvent e) {                                //Wird von Timer ausgeführt.               
        p.move();                                                               //Fahren
        repaint();                                                              //Überzeichnen
        testCollisionWithEnemies();

    }

    private void testCollisionWithEnemies() {

        Iterator<Enemy> i = enemies.iterator();
        while (i.hasNext()) {
            Enemy e = i.next();
            if (p.getRect().intersects(e.getRect())) {
                JOptionPane.showMessageDialog(null, "Sie haben verloren!");
                System.exit(1);
            }
        }

    }

}
