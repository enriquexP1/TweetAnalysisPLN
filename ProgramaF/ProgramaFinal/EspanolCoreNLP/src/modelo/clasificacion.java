/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author enriq
 */
public class clasificacion {
    String clasficiacion;
    int contador;
    public clasificacion(String clasificacion)
    {
        this.clasficiacion=clasificacion;
    }

    public String getClasficiacion() {
        return clasficiacion;
    }

    public void setClasficiacion(String clasficiacion) {
        this.clasficiacion = clasficiacion;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }
    
    @Override
    public String toString()
    {
        return "La clasificación: " + clasficiacion +"\n Tiene " + contador + " tweets\n";
    }
}
