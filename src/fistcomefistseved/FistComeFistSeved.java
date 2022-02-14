/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package fistcomefistseved;

/**
 *
 * @author felipe
 */
public class FistComeFistSeved {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        procesador p1 = new procesador();
        p1.setProceso(new proceso("A", 0, 8));
        p1.setProceso(new proceso("B", 1, 4));
        p1.setProceso(new proceso("C", 2, 9));
        p1.start();
        p1.setProceso(new proceso("proceso4", 0, 0));
        p1.setProceso(new proceso("proceso5", 0, 0));
        p1.setProceso(new proceso("proceso6", 0, 0));
        
    }
    
}
