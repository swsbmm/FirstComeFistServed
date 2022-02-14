/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fistcomefistseved;

import java.awt.Color;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author felipe
 */
public class conexion extends Thread{
    private FCFSJFrame ventana;
    private procesador p;
    DefaultTableModel modeloCola;
    public conexion(FCFSJFrame ventana, procesador p){
        this.ventana = ventana;
        this.p = p;
        this.start();
        procesosEnColaTable();
    }
    
    
    private void procesosEnColaTable(){
        ArrayList<Object> titulo = new ArrayList<Object>();
        titulo.add("Nombre");
        titulo.add("Tiempo de llegada");
        titulo.add("Rafaga");
        for(Object col: titulo){
            modeloCola.addColumn(col);
        }
        ventana.getjTableCola().setModel(modeloCola);
    }
    
    private void tablaCola(DefaultTableModel modeloCola){
        ArrayList<Object[]> datos = new ArrayList<Object[]>();
        for(proceso p: p.getCola()){
            datos.add(new Object[]{String.valueOf(p.getNombre()),String.valueOf(p.getTiempo_llegada()),String.valueOf(p.getTiempo_ejecucion())});
        }
        
        for(Object []dato: datos){
            modeloCola.addRow(dato);
        }
        ventana.getjTableCola().setModel(modeloCola);
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
            
            //tabla cola
            tablaCola();
            
            //VELOCIDAD DE RENDERISADO
            try {
                this.sleep(60);
            } catch (InterruptedException ex) {
                Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
