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

/**
 *
 * @author felipe
 */
public class procesador extends Thread {

    private Queue<proceso> cola;
    private Queue<proceso> cola_bloqueo;
    private Queue<proceso> cola_terminado;
    private ArrayList<proceso> procesos;
    private float tiempo_ejecucion;
    private boolean libre;
    private float tiempo_llegada_ultimo_proceso;

    public procesador() {
        this.cola = new LinkedList();
        this.cola_bloqueo = new LinkedList();
        this.cola_terminado = new LinkedList();
        this.tiempo_ejecucion = 0;
        this.libre = true;
        this.tiempo_llegada_ultimo_proceso = 0;
        this.procesos = new ArrayList<proceso>();
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
        return cola;
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

    public boolean setBloquearProceso() {
        if (!this.cola.isEmpty()) {
            if(this.cola.peek().getRafaga()>0){
                this.cola.peek().setBloqueo(true);
                float numRan = numeroRadom();
                this.cola.peek().setTiempo_bloqueo(numRan);
                this.cola.peek().setBloqueadoV(numRan);
                return true;
            }else{
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean setProceso(proceso p) {
        if (p.getTiempo_llegada() >= this.tiempo_llegada_ultimo_proceso) {
            this.tiempo_llegada_ultimo_proceso = p.getTiempo_llegada();
            this.cola.add(p);
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
        if (cola.peek().getTiempo_ejecucion() == 0) {
            cola.peek().setTiempo_comienzo(this.tiempo_ejecucion);
        }
    }
    
    private void procesos_bloqueados() {
        if(!this.cola.isEmpty()){
            for(proceso p: this.cola){
                if(p.isBloqueo()){
                    this.cola_bloqueo.add(this.cola.poll());
                }
            }
        }
        if (!this.cola_bloqueo.isEmpty()) {
            for (proceso p : this.cola_bloqueo) {
                if (p.getTiempo_bloqueo() == 0) {
                    p.setBloqueo(false);
                    this.cola.add(this.cola_bloqueo.poll());
                }
                if (p.getTiempo_bloqueo() > 0) {
                    p.setTiempo_bloqueo(p.getTiempo_bloqueo() - 1);

                }
            }
        }
    }

    private void procesos_en_cola() {
        if (cola.peek() != null) {
            try {
                System.out.println("TIEMPO: " +this.tiempo_ejecucion);
                
                if (cola.peek().getRafaga() == 0) {
                    asignarTiemposAProceso(cola.peek());
                    this.cola_terminado.add(cola.poll());
                    this.libre = true;
                    procesador.sleep(2000);
                }

                if (cola.peek().getRafaga() > 0) {
                    set_tiempo_comienzo();
                    this.cola.peek().setTiempo_ejecucion(this.cola.peek().getTiempo_ejecucion() + 1);
                    cola.peek().setRafaga(cola.peek().getRafaga() - 1);
                    this.libre = false;
                }

            } catch (InterruptedException ex) {
            }

        }
    }
    
    private void tiempo_ejecucion_procesador(){
        if(!this.isLibre()){
            if(!this.cola.isEmpty() || !this.cola_bloqueo.isEmpty()){
                    this.tiempo_ejecucion = this.tiempo_ejecucion + 1;
            }
        }
        
    }

    @Override
    public void run() {
        while (true) {
            try {
                procesos_bloqueados();
                procesos_en_cola();
                this.sleep(1000);
                tiempo_ejecucion_procesador();
            } catch (Exception e) {
            }
        }
    }

}
