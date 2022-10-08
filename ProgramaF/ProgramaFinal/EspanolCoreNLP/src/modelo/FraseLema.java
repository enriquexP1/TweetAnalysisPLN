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
public class FraseLema {
    
    ArrayList<PalabraDTO> palabras ;
    String lematizado;
    
    public FraseLema(ArrayList<PalabraDTO> palabras ,  String lematizado)
    {
        this.palabras=palabras;
        this.lematizado=lematizado;
    }

    public ArrayList<PalabraDTO> getPalabras() {
        return palabras;
    }

    public void setPalabras(ArrayList<PalabraDTO> palabras) {
        this.palabras = palabras;
    }

    public String getLematizado() {
        return lematizado;
    }

    public void setLematizado(String lematizado) {
        this.lematizado = lematizado;
    }
    
}
