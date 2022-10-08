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
public class PalabraPOS {
    String lematizado;
    ArrayList<PalabraDTO> palabrasLematizadas;
    
    public PalabraPOS(String lematizado ,ArrayList<PalabraDTO> palabrasLematizadas )
    {
        
        this.lematizado=lematizado;
        this.palabrasLematizadas=palabrasLematizadas;
    }

    public String getLematizado() {
        return lematizado;
    }

    public void setLematizado(String lematizado) {
        this.lematizado = lematizado;
    }

    public ArrayList<PalabraDTO> getPalabrasLematizadas() {
        return palabrasLematizadas;
    }

    public void setPalabrasLematizadas(ArrayList<PalabraDTO> palabrasLematizadas) {
        this.palabrasLematizadas = palabrasLematizadas;
    }
    
}
