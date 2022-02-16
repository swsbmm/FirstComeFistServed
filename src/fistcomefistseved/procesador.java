/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fistcomefistseved;

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
    private float tiempo_ejecucion;
    private boolean libre;
    private float tiempo_llegada_ultimo_proceso;
    private boolean bloquearProceso;

    public procesador() {
        this.cola = new LinkedList();
        this.cola_bloqueo = new LinkedList();
        this.cola_terminado = new LinkedList();
        this.tiempo_ejecucion = 0;
        this.libre = true;
        this.tiempo_llegada_ultimo_proceso = 0;
        this.bloquearProceso = false;
    }

    public boolean isLibre() {
        return libre;
    }

    public void setBloquearProceso(boolean bloquearProceso) {
        this.bloquearProceso = bloquearProceso;
    }

    public void bloquear_p() {
        if (cola.peek() != null) {
            float numero = (int) (Math.random() * 10 + 1);
            this.cola.peek().setBloqueado(numero);
            System.out.println("PROCESO BLOQUEADO --> " + cola.peek().getNombre());
            this.cola_bloqueo.add(this.cola.poll());
            this.bloquearProceso = false;
        }

    }

    public void bloqueo() {
        if (this.cola_bloqueo != null) {
            for (proceso p : this.cola_bloqueo) {
                if (p.getBloqueado() <= 0) {
                    this.cola.add(p);
                    this.cola_bloqueo.remove(p);
                } else {
                    p.setBloqueado(p.getBloqueado() - 1);
                    p.setTiempo_ejecucion(p.getTiempo_ejecucion() + 1);
                }

            }
        }

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

    public boolean setProceso(proceso p) {
        if (p.getTiempo_llegada() >= this.tiempo_llegada_ultimo_proceso) {
            this.tiempo_llegada_ultimo_proceso = p.getTiempo_llegada();
            this.cola.add(p);
            return true;
        } else {
            return false;
        }

    }

    public void recorrerCola(Queue<proceso> cola) {
        for (proceso v : cola) {
            System.out.println(" nombre: " + v.getNombre() + " t_llegada: " + v.getTiempo_llegada() + " rafaga: " + v.getTiempo_ejecucion() + " t_comienzo " + v.getTiempo_comienzo() + " t_final " + v.getTiempo_final() + " t_retorno " + v.getTiempo_retorno() + " t_espera " + v.getTiempo_espera());
        }
    }

    private void asignarTiemposAProceso(proceso p) {
        //asignandole el tiempo final al proceso debido a que ya no tiene rafaga para seguir en ejecucion
        p.setTiempo_final(p.getRafagaV() + p.getTiempo_comienzo() + p.getBloqueadoV());

        //calculando y asignando al proceso el tiempo de retorno
        p.setTiempo_retorno(p.getTiempo_final() - p.getTiempo_llegada());

        //calculando y asignando al proceso el tiempo de espera
        p.setTiempo_espera(p.getTiempo_retorno() - p.getRafagaV() - p.getBloqueadoV());

    }

    @Override
    public void run() {
        while (true) {
            if (bloquearProceso) {
                bloquear_p();
            }
            bloqueo();
            System.out.println(this.tiempo_ejecucion);
//            
            if (cola.peek() != null) {
                try {

                    //asignando al proceso el tiempo que lleva ejecutandose
                    if (cola.peek().getTiempo_ejecucion() == 0) {
                        cola.peek().setTiempo_comienzo(this.tiempo_ejecucion);
                    }

                    if (cola.peek().getRafaga() > 0) {
                        this.sleep(1000);
                        this.tiempo_ejecucion = this.tiempo_ejecucion + 1;
                        this.cola.peek().setTiempo_ejecucion(this.cola.peek().getTiempo_ejecucion() + 1);
                        //Restandole a la rafaga - 1 que es lo que se demora el hilo en volver a ejecutarse.
                        cola.peek().setRafaga(cola.peek().getRafaga() - 1);
                        this.libre = false;
                    } else {
                        this.libre = true;
                        this.sleep(2000);
                    }

                    //Preguntandole al proceso si aun tiene ragafa, si no tiene entonces se calculan se pasa a la cola de terminado
                    if (cola.peek().getRafaga() <= 0) {

                        asignarTiemposAProceso(cola.peek());
                        //agregando a la cola de terminado el proceso que finalizo
                        this.cola_terminado.add(cola.poll());

                        this.libre = true;

                        procesador.sleep(2000);

                    }

                } catch (InterruptedException ex) {
                }

            }
        }
    }

}
