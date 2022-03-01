/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fistcomefistseved;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.timer.Timer;

/**
 *
 * @author felipe
 */
public class procesador extends Thread {

    private Queue<proceso> cola_espera;
    private Queue<proceso> cola_ejecucion;
    private Queue<proceso> cola_bloqueo;
    private Queue<proceso> cola_terminado;
    private ArrayList<proceso> procesos;
    private float tiempo_ejecucion;
    private boolean libre;
    private float tiempo_llegada_ultimo_proceso;

    public procesador() {
        this.cola_espera = new LinkedList();
        this.cola_ejecucion = new LinkedList();
        this.cola_bloqueo = new LinkedList();
        this.cola_terminado = new LinkedList();
        this.tiempo_ejecucion = 0;
        this.libre = true;
        this.tiempo_llegada_ultimo_proceso = 0;
        this.procesos = new ArrayList<proceso>();
    }

    public Queue<proceso> getCola_espera() {
        return cola_espera;
    }

    public Queue<proceso> getCola_ejecucion() {
        return cola_ejecucion;
    }

    
    
    public boolean isLibre() {
        return libre;
    }

    private float numeroRadom() {
        return (int) (Math.random() * 10 + 1);
    }

    public float getTiempo_ejecucion() {
        return tiempo_ejecucion;
    }

    public Queue<proceso> getCola() {
        return cola_ejecucion;
    }

    public Queue<proceso> getCola_bloqueo() {
        return cola_bloqueo;
    }

    public Queue<proceso> getCola_terminado() {
        return cola_terminado;
    }

    public float getTiempo_llegada_ultimo_proceso() {
        return tiempo_llegada_ultimo_proceso;
    }

    

    public boolean setProceso(proceso p) {
        if (p.getTiempo_llegada() >= this.tiempo_llegada_ultimo_proceso) {
            this.tiempo_llegada_ultimo_proceso = p.getTiempo_llegada();
            if (p.getTiempo_llegada() < this.tiempo_ejecucion) {
                double tiempo_espera = this.tiempo_ejecucion - p.getTiempo_llegada();
                for (int x = 0; x < tiempo_espera; x++) {
                    p.getLista_estados().add("7");
                }
            }
            this.cola_espera.add(p);
            this.procesos.add(p);
            return true;
        } else {
            return false;
        }

    }

    public void recorrerCola(Queue<proceso> cola) {
        for (proceso v : cola) {
            //System.out.println(" nombre: " + v.getNombre() + " t_llegada: " + v.getTiempo_llegada() + " rafaga: " + v.getTiempo_ejecucion() + " t_comienzo " + v.getTiempo_comienzo() + " t_final " + v.getTiempo_final() + " t_retorno " + v.getTiempo_retorno() + " t_espera " + v.getTiempo_espera());
        }
    }

    private void asignarTiemposAProceso(proceso p) {
        //asignandole el tiempo final al proceso debido a que ya no tiene rafaga para seguir en ejecucion
        p.setTiempo_final(this.tiempo_ejecucion);

        //calculando y asignando al proceso el tiempo de retorno
        p.setTiempo_retorno(p.getTiempo_final() - p.getTiempo_llegada());

        //calculando y asignando al proceso el tiempo de espera
        p.setTiempo_espera(p.getTiempo_retorno() - p.getRafagaV());

    }

    private void set_tiempo_comienzo() {
        //asignando al proceso el tiempo que lleva ejecutandose
        //System.out.println("PROCESO--> "+ cola.peek().getNombre()+ "TIEMPO DE EJECUCION DEL PROECSO--> " +cola.peek().getTiempo_ejecucion());
        if (cola_ejecucion.peek().getTiempo_ejecucion() == 0) {
            cola_ejecucion.peek().setTiempo_comienzo(this.tiempo_ejecucion);
            double tiempo_espera_inicial = cola_ejecucion.peek().getTiempo_comienzo() - cola_ejecucion.peek().getTiempo_llegada();
            for (int x = 0; x < tiempo_espera_inicial; x++) {
                //cola_ejecucion.peek().getLista_estados().add("1");
            }
        }
    }

    

    private void procesos_en_ejecucion() {
        if (cola_ejecucion.peek() != null) {
            try {
                System.out.println("TIEMPO: " + this.tiempo_ejecucion);

                if (cola_ejecucion.peek().getRafaga() == 0) {
                    asignarTiemposAProceso(cola_ejecucion.peek());
                    this.cola_terminado.add(cola_ejecucion.poll());
                    this.libre = true;
                    procesador.sleep(2000);
                }

                if (cola_ejecucion.peek().getRafaga() > 0) {
                    set_tiempo_comienzo();
                    this.cola_ejecucion.peek().setTiempo_ejecucion(this.cola_ejecucion.peek().getTiempo_ejecucion() + 1);
                    this.cola_ejecucion.peek().getLista_estados().add("2");
                    cola_ejecucion.peek().setRafaga(cola_ejecucion.peek().getRafaga() - 1);
                    this.libre = false;
                }

            } catch (InterruptedException ex) {
            }

        }
    }

    private void tiempo_ejecucion_procesador() {
        
            if (!this.cola_ejecucion.isEmpty() || !this.cola_bloqueo.isEmpty() || !this.cola_espera.isEmpty()) {
                this.tiempo_ejecucion = this.tiempo_ejecucion + 1;
            }
        

    }

    public void procesos_en_espera() {
        if (!this.cola_espera.isEmpty()) {
            if (this.cola_ejecucion.size() == 0) {
                this.cola_ejecucion.add(this.cola_espera.poll());
                return;
            }
            for (proceso p : this.cola_espera) {
                if (p.getTiempo_llegada() < this.tiempo_ejecucion) {
                    p.getLista_estados().add("7");
                }

            }

        }

    }

    @Override
    public void run() {
        while (true) {
            try {
                procesos_en_espera();
                procesos_en_ejecucion();
                tiempo_ejecucion_procesador();
                this.sleep(1000);
                

            } catch (Exception e) {
            }
        }
    }

}
