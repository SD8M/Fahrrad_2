package fahrrad_2;

// Bibliotheken
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

    Player p = new Player();                                                    //Fügen den Spieler ein

    Thread enemiesFactory = new Thread(this);                                   
    
    Thread audioThread = new Thread(new AudioThread());

    List<Enemy> enemies = new ArrayList<Enemy>();                               //Kollektion von Gegnern. Hier werden die Gegner aufbewahrt

    public Road() {
        mainTimer.start();                                                      //Timer wird gestartet
        enemiesFactory.start();                                                 //Der Thread mit Gegnern wird gestartet
        audioThread.start();
        addKeyListener(new MyKeyAdapter());
        setFocusable(true);                                                     //Fokusiert sich auf die Tastatur. Ohne diesen Befehl werden die Tasteneinschläge nicht erkannt
    }

    
    
   
    //ENEMY
    @Override
    public void run() {                                                         //Sorgt für endlose Erstellung von Gegnern nach Zufälligkeitsprinzip "random"

        while (true) {
            Random rand = new Random();
            try {
                Thread.sleep(rand.nextInt(2000));                               //Statische Methode für Zufälligkeitsprinzip. Generieren der Gegner zwischen 0 und 2 Sec
                enemies.add(new Enemy(1200, rand.nextInt(600), rand.nextInt(27), this));        //Fügen der Gegner auf die Straße ein. Kommen von Rechts. Zufällige Position auf der Straße. Zufällige Geschwindigkeit bis max 27.
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }
    }
    
    //Bemerkt Tastenaktionen
    private class MyKeyAdapter extends KeyAdapter {

        public void keyPressed(KeyEvent e) {                                    //Beim drücken auf die Taste (Wie reagiert der Spieler) ****Siehe Player
            p.keyPressed(e);

        }

        public void keyReleased(KeyEvent e) {                                   //Beim loslassen der Taste (Wie reagiert der Spieler) ****Siehe Player
            p.keyReleased(e);

        }

    }
    //Methode Paint 
    public void paint(Graphics g) {                                             //Methode wird automatisch aufgerufen jedesmal wenn das Panel übergezeichnet werden soll 
        g = (Graphics2D) g;                    
        //Wo wird gezeichnet, was wird gezeichnet
        g.drawImage(img, p.layer1, 0, null);                                    //Der erste Hintergrund wird gezeichnet         Methode Paint zeichnet Hintergrund 1
        g.drawImage(img, p.layer2, 0, null);                                    //Der zweite Hintergrund wird gezeichnet        Methode Paint zeichnet Hintergrund 2
        g.drawImage(p.img, p.x, p.y, null);                                     //Koordinaten des Spielers                      Methode Paint zeichnet Spieler

        double v = (50/Player.MAX_V) * p.v;
        
        g.setColor(Color.orange);
        Font font = new Font("Monospaced", Font.ITALIC, 20);                    //Font font = new Font("Arial", Font.ITALIC, 20);
        g.setFont(font);
        g.drawString("Geschwindigkeit: " + v + " km/h", 100, 30);

        Iterator<Enemy> i = enemies.iterator();                                 //Methode Paint zeichnet alle Gegner
        while (i.hasNext()) {
            Enemy e = i.next();
            if (e.x >= 2400 || e.x <= - 2400) {                                 //Wenn der Gegner 2400 Pixel nach rechts/links vom Spieler wegfährt, wird er aus der Kollektion gelöscht
                i.remove();
            } else {
                e.move();
                g.drawImage(e.img, e.x, e.y, null);                             //Zeichnet Position und Bild von Gegner

            }

        }
    }

    public void actionPerformed(ActionEvent e) {                                //Wird von Timer jede 20 millisec ausgeführt.                
        p.move();                                                               //Fahren
        repaint();                                                              //Überzeichnen
        testCollisionWithEnemies();

    }
        //Kollision mit Gegnern
    private void testCollisionWithEnemies() {

        Iterator<Enemy> i = enemies.iterator();
        while (i.hasNext()) {
            Enemy e = i.next();
            if (p.getRect().intersects(e.getRect())) {
                JOptionPane.showMessageDialog(null, "Sie haben verloren! \n\nMitwirkende: \nAndrej Radkov \nIlja Schostak \nJannik Feldten \nSimon Benedikt Borgschulze \nFlorian Maaß \n\nVielen Dank an unseren BeatMaker Nikolaj Djatschenko ");
                System.exit(1);
            }
        }

    }

}
