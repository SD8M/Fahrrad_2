package fahrrad_2;

import javax.swing.JFrame;

import javax.swing.*;

public class Fahrrad_2 {

    public static void main(String[] args) {

        JFrame f = new JFrame("Java Fahrrad");  //окнo верхнего уровня
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //Этот метод останавливает действия по закрытию Frame. Beim Drucken auf schließen --> Verlassen der Anwendung
        f.setSize(1100, 600);                   //Diese Methode stellt die Größe der Anwendung / Frame ein (Breite x Höhe)
        f.add(new Road ());                     //Fügen die Straße ein
        f.setVisible(true);                     //Macht die Anwendung sichtbar


    }

}
