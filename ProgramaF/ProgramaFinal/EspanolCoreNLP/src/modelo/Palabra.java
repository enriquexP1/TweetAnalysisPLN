/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author enriq
 */
public class Palabra {
    String palabra;
    ArrayList<String> lemas ;
    int contador;
    public Palabra()
    {
       
    }
    public Palabra(String palabra)
    {
        lemas = new ArrayList<>();
        this.palabra= palabra;
        contador = 0 ;
    }
    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public ArrayList<String> getLemas() {
        return lemas;
    }

    public void setLemas(ArrayList<String> lemas) {
        this.lemas = lemas;
    }

    public int getContador() {
        return contador;
    }
    public void AddLemas(String lema)
    {
        lemas.add(lema);
    }
    
    public void removeLemas(int i)
    {
        lemas.remove(i);
    }
    public void setContador(int contador) {
        this.contador = contador;
    }

   
    
    @Override
    public String toString()
    {
        return "La palabra: " + palabra +"\n Tiene :" + contador + " conincidencias\n Con los lemas: " ;
    }
    
}
