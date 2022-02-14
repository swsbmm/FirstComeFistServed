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
public class procesador extends Thread{
    private Queue<proceso> cola;
    private Queue<proceso> cola_bloqueo;
    private Queue<proceso> cola_terminado;
    private float tiempo_ejecucion;
    private boolean libre;
    
    public procesador(){
        this.cola = new LinkedList();
        this.cola_bloqueo = new LinkedList();
        this.cola_terminado = new LinkedList();
        this.tiempo_ejecucion = 0;
        this.libre = true;
    }
    
    public void setProceso(proceso p){
        this.cola.add(p);
    }
    
    public void recorrerCola(Queue<proceso> cola)  {
      for(proceso v: cola){
        System.out.println(" nombre: "+v.getNombre()+ " t_llegada: "+v.getTiempo_llegada()+ " rafaga: "+v.getTiempo_ejecucion()+ " t_comienzo "+v.getTiempo_comienzo()+ " t_final "+v.getTiempo_final()+ " t_retorno "+v.getTiempo_retorno()+ " t_espera "+v.getTiempo_espera());
      }
    }
    
    @Override
    public void run(){
        while(true){
            
            recorrerCola(cola_terminado);
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println(this.tiempo_ejecucion);
            System.out.println("");
            System.out.println("");
            System.out.println("");
            
            if(cola.peek()!=null){
                //asignando al proceso el tiempo que lleva ejecutandose
                if(cola.peek().getTiempo_ejecucion() == 0){
                    cola.peek().setTiempo_comienzo(this.tiempo_ejecucion);
                }
                
                

                //agregando tiempo de ejecucion al proceso y durmiendo el hilo 1 segundo
                try {
                    if(cola.peek().getRafaga()>0){
                        this.sleep(1000);
                        this.tiempo_ejecucion = this.tiempo_ejecucion+1;
                        this.cola.peek().setTiempo_ejecucion(this.cola.peek().getTiempo_ejecucion()+1);
                        this.libre = false;
                    }else{
                        this.libre = true;
                        //System.out.println("procesador libre ************************");
                        this.sleep(2000);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(procesador.class.getName()).log(Level.SEVERE, null, ex);
                }

                //Imprimiendo datos del proceso por consola
                //System.out.println("nombre: "+ cola.peek().getNombre()+ "Rafaga: "+cola.peek().getRafaga());
                
                
                //Restandole a la rafaga - 1 que es lo que se demora el hilo en volver a ejecutarse.
                if(cola.peek().getRafaga() >= 0 ){
                    cola.peek().setRafaga(cola.peek().getRafaga()-1);
                }
                
                //Preguntandole al proceso si aun tiene ragafa, si no tiene entonces se calculan se pasa a la cola de terminado
                if(cola.peek().getRafaga()<= 0){
                    
                    //asignandole el tiempo final al proceso debido a que ya no tiene rafaga para seguir en ejecucion
                    cola.peek().setTiempo_final(cola.peek().getTiempo_ejecucion()+cola.peek().getTiempo_comienzo());
                    
                    //calculando y asignando al proceso el tiempo de retorno
                    cola.peek().setTiempo_retorno(cola.peek().getTiempo_final()-cola.peek().getTiempo_llegada());
                    
                    //calculando y asignando al proceso el tiempo de espera
                    cola.peek().setTiempo_espera(cola.peek().getTiempo_retorno()-cola.peek().getTiempo_ejecucion());
                    
                    //agregando a la cola de terminado el proceso que finalizo
                    this.cola_terminado.add(cola.poll());
                    
                    
                    this.libre = true;
                    //System.out.println("procesador libre ************************");
                    try {
                        this.sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(procesador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                
            }
        }
    }
    
}
