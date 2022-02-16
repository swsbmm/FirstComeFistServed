/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package fistcomefistseved;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author felipe
 */
public class FistComeFistSeved {

    private JFrame ventana;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                FCFSJFrame ventana = new FCFSJFrame();
                ventana.setVisible(true);
            }

        });

    }
    }
