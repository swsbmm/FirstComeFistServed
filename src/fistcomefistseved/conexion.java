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
public class conexion extends Thread {

    private FCFSJFrame ventana;
    private procesador p;

    public conexion(FCFSJFrame ventana, procesador p) {
        this.ventana = ventana;
        this.p = p;
        this.start();
    }

    private void procesosEnColaTable(DefaultTableModel modelo) {
        try {
            ArrayList<Object> titulo = new ArrayList<Object>();
            titulo.add("Nombre");
            titulo.add("Tiempo de llegada");
            titulo.add("Rafaga");
            for (Object col : titulo) {
                modelo.addColumn(col);
            }
        } catch (Exception e) {
        }

    }

    private void tablaCola() {
        try {
            ArrayList<Object[]> datos = new ArrayList<Object[]>();
            DefaultTableModel modelo_final = new DefaultTableModel();
            procesosEnColaTable(modelo_final);
            for (proceso p : p.getCola()) {
                datos.add(new Object[]{String.valueOf(p.getNombre()), String.valueOf(p.getTiempo_llegada()), String.valueOf(p.getRafaga())});
            }

            for (Object[] dato : datos) {
                modelo_final.addRow(dato);
            }
            ventana.getjTableCola().setModel(modelo_final);
        } catch (Exception e) {
        }

    }

    private void tituloColaTerminado(DefaultTableModel modelo) {
        ArrayList<Object> titulo1 = new ArrayList<Object>();
        titulo1.add("Nombre");
        titulo1.add("Tiempo de llegada");
        titulo1.add("Rafaga");
        titulo1.add("Tiempo de comienzo");
        titulo1.add("Tiempo final");
        titulo1.add("Tiempo de retorno");
        titulo1.add("Tiempo de espera");
        for (Object col1 : titulo1) {
            modelo.addColumn(col1);
        }
    }

    private void tituloColaBloqueado(DefaultTableModel modelo) {
        ArrayList<Object> titulo1 = new ArrayList<Object>();
        titulo1.add("Nombre");
        titulo1.add("Tiempo de llegada");
        titulo1.add("Rafaga");
        titulo1.add("Tiempo de comienzo");
        titulo1.add("Tiempo de bloqueo");
        for (Object col1 : titulo1) {
            modelo.addColumn(col1);
        }
    }

    private void tablaColaBloqueado() {
        try {
            ArrayList<Object[]> datos = new ArrayList<Object[]>();
            DefaultTableModel modelo_final1 = new DefaultTableModel();
            tituloColaBloqueado(modelo_final1);
            for (proceso p : p.getCola_bloqueo()) {
                datos.add(new Object[]{
                    String.valueOf(p.getNombre()),
                    String.valueOf(p.getTiempo_llegada()),
                    String.valueOf(p.getRafaga()),
                    String.valueOf(p.getTiempo_comienzo()),
                    String.valueOf(p.getTiempo_final()),
                    String.valueOf(p.getTiempo_retorno()),
                    String.valueOf(p.getTiempo_espera())});
            }

            for (Object[] dato : datos) {
                modelo_final1.addRow(dato);
            }
            ventana.getjTableBloqueo().setModel(modelo_final1);
        } catch (Exception e) {
        }

    }

    private void tablaColaTerminado() {
        try {
            ArrayList<Object[]> datos = new ArrayList<Object[]>();
            DefaultTableModel modelo_final1 = new DefaultTableModel();
            tituloColaTerminado(modelo_final1);
            for (proceso p : p.getCola_terminado()) {
                datos.add(new Object[]{
                    String.valueOf(p.getNombre()),
                    String.valueOf(p.getTiempo_llegada()),
                    String.valueOf(p.getTiempo_ejecucion()),
                    String.valueOf(p.getTiempo_comienzo()),
                    String.valueOf(p.getTiempo_final()),
                    String.valueOf(p.getTiempo_retorno()),
                    String.valueOf(p.getTiempo_espera())});
            }

            for (Object[] dato : datos) {
                modelo_final1.addRow(dato);
            }
            ventana.getjTableFinalizados().setModel(modelo_final1);
        } catch (Exception e) {
        }

    }

    private Color colorSemasforo() {
        if (p.isLibre()) {
            return Color.green;
        } else {
            return Color.red;
        }
    }

    @Override
    public void run() {
        while (ventana.isEnabled()) {

            try {

                //TIEMPO DE EJECUCION
                ventana.getjLabelTiempoEjecucion().setText(String.valueOf(p.getTiempo_ejecucion()));

                //SEMASFORO
                ventana.getjPanelSemasforo().setBackground(colorSemasforo());

                tablaColaBloqueado();

                this.sleep(30);

                //tabla cola
                tablaCola();

                this.sleep(30);
                
                tablaColaTerminado();
                
                 this.sleep(30);

            } catch (InterruptedException ex) {
                Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
