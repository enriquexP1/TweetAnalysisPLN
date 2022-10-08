/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import static edu.stanford.nlp.scoref.BestFirstCorefSystem.i;
import java.util.ArrayList;

/**
 *
 * @author enriq
 */
public class estadisticas {

    ArrayList<Palabra> palabrasClave;
    ArrayList<PalabraDTO> palabrasLematizadas;
    ArrayList<String> tweetsCovid;
    ArrayList<PalabraPOS> palabrasPos;

    estadisticas(ArrayList<PalabraPOS> palabrasPos) {
        this.tweetsCovid = new ArrayList<>();
        this.palabrasPos = palabrasPos;

//        System.out.println("tiene un size de:" + palabrasLematizadas.size());
    }

    public void hacerEstadisticas(ArrayList palabrasclv) {
        int contador = 0;
        int tamañoTotal = palabrasPos.size();
        //palabrasClave = palabrasClave();
        palabrasClave = palabrasclv;

        for (int i = 0; i < palabrasPos.size(); i++) {

            palabrasLematizadas = palabrasPos.get(i).getPalabrasLematizadas();
            for (PalabraDTO palabra : palabrasLematizadas) {
                for (int j = 0; j < palabrasClave.size(); j++) {
                    if (palabra.getLema().equals(palabrasClave.get(j).getPalabra())) {
                       
                        contador = palabrasClave.get(j).getContador();
                        palabrasClave.get(j).setContador(contador + 1);
                        //System.out.println("texto lematizado");
                        //System.out.println(palabrasPos.get(i).getLematizado());
                        palabrasClave.get(j).AddLemas(palabrasPos.get(i).getLematizado());
                    }
                }

            }

            for (int o = 0; o < palabrasClave.size(); o++) {
                ArrayList<String> lemas = palabrasClave.get(o).getLemas();
                for (int u = 0; u < lemas.size() - 1; u++) {
                    if (lemas.get(u).equals(lemas.get(u + 1))) {
                        palabrasClave.get(o).removeLemas(u);
                        int cont = palabrasClave.get(o).getContador();
                        palabrasClave.get(o).setContador(cont - 1);
                    }
                }

            }
        }

        impimirEestadisticas();
    }

    public ArrayList<Palabra> palabrasClave() {
        palabrasClave = new ArrayList<>();
        String palabras[] = {"covid", "cobija", "vacuna", "vacunar", "mascarillas", "mascarilla", "sintoma", "prueba", "virus", "coronavirus",
            "morir", "contagiar", "fallecer", "cuarentena", "infectar", "infección"};
        for (String palabraClave : palabras) {
            Palabra palabra = new Palabra(palabraClave);
            palabrasClave.add(palabra);
        }
        return palabrasClave;
    }

    //Este te lo copias en el reader para imprimirlo en un archivo 
    public void impimirEestadisticas() {

        for (int i = 0; i < palabrasClave.size(); i++) {
            float tamañoTotal = palabrasPos.size();
            float tamañoParcial = palabrasClave.get(i).getContador();
            float porcentaje = (tamañoParcial / tamañoTotal) * 100;
            System.out.println("De " + palabrasPos.size() + " hay " + palabrasClave.get(i).getContador() + " coincidencias"
                    + "\n Lo que representa el  " + porcentaje + "%");
            System.out.println(palabrasClave.get(i).toString());
            ArrayList<String> lemas = palabrasClave.get(i).getLemas();
            for (int j = 0; j < lemas.size(); j++) {
                System.out.println("-- " + lemas.get(j) + "\n");
            }
            System.out.println("\n\n");
        }

    }
}
