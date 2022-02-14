/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fistcomefistseved;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author felipe
 */
public class conexion extends Thread{
    private FCFSJFrame ventana;
    private procesador p;
    
    public conexion(FCFSJFrame ventana, procesador p){
        this.ventana = ventana;
        this.p = p;
        this.start();
    }
    
    private Color colorSemasforo(){
        if(p.isLibre()){
            return Color.green;
        }else{
            return Color.red;
        }
    }
    
    @Override
    public void run(){
        while(ventana.isEnabled()){
            
            //TIEMPO DE EJECUCION
            ventana.getjLabelTiempoEjecucion().setText(String.valueOf(p.getTiempo_ejecucion()));
            
            //SEMASFORO
            ventana.getjPanelSemasforo().setBackground(colorSemasforo());
            
            //VELOCIDAD DE RENDERISADO
            try {
                this.sleep(60);
            } catch (InterruptedException ex) {
                Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
