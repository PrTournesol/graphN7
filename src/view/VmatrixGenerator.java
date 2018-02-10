package view;

import javax.swing.*;
import java.awt.*;

public class VmatrixGenerator extends JFrame {

    public static void main (String[] args){
        VmatrixGenerator vm = new VmatrixGenerator();

    }

    public VmatrixGenerator() throws HeadlessException {
        this.setTitle("Matrix generator");
        this.setLocationRelativeTo(null);

        //Instanciation d'un objet JPanel
        JPanel pan = new JPanel();
        //Définition de sa couleur de fond
        pan.setBackground(Color.ORANGE);
        //On prévient notre JFrame que notre JPanel sera son content pane
        this.setContentPane(pan);
        this.setVisible(true);
    }
}
