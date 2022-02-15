/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fistcomefistseved;

/**
 *
 * @author felipe
 */
public class proceso {
    private String nombre;
    private float tiempo_llegada;
    private float rafaga;
    //tiempo de ejecucion
    private float tiempo_ejecucion;
    private float tiempo_comienzo;
    private float tiempo_final;
    private float tiempo_retorno;
    private float tiempo_espera;
    
    public proceso(String nombre, float tiempo_llegada, float rafaga){
        this.nombre = nombre;
        this.tiempo_llegada = tiempo_llegada;
        this.rafaga = rafaga;
        this.tiempo_ejecucion = 0;
    }

    public float getTiempo_ejecucion() {
        return tiempo_ejecucion;
    }

    public void setTiempo_ejecucion(float tiempo_ejecucion) {
        this.tiempo_ejecucion = tiempo_ejecucion;
    }
    
    

    public String getNombre() {
        return nombre;
    }

    public void setRafaga(float rafaga) {
        this.rafaga = rafaga;
    }

    public float getRafaga() {
        return rafaga;
    }

    public float getTiempo_comienzo() {
        return tiempo_comienzo;
    }

    public void setTiempo_comienzo(float tiempo_comienzo) {
        this.tiempo_comienzo = tiempo_comienzo;
    }

    
    
    public float getTiempo_llegada() {
        return tiempo_llegada;
    }

    public void setTiempo_llegada(float tiempo_llegada) {
        this.tiempo_llegada = tiempo_llegada;
    }

    public float getTiempo_final() {
        return tiempo_final;
    }

    public void setTiempo_final(float tiempo_final) {
        this.tiempo_final = tiempo_final;
    }

    public float getTiempo_retorno() {
        return tiempo_retorno;
    }

    public void setTiempo_retorno(float tiempo_retorno) {
        this.tiempo_retorno = tiempo_retorno;
    }

    public float getTiempo_espera() {
        return tiempo_espera;
    }

    public void setTiempo_espera(float tiempo_espera) {
        this.tiempo_espera = tiempo_espera;
    }
    
    
    
}
