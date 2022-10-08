/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.IOException;
import org.annolab.tt4j.TreeTaggerException;

/**
 *
 * @author enriq
 */
public class ManRead {

    String archivo;
    Reader reader;

    public ManRead() {

    }

    public void leerArchivo(String archivo) {
        this.archivo = archivo;
        
        reader = new Reader(archivo);
        reader.leer();
        //Aqui es donde escribirmos el resultado solo del texto
        //reader.write();
        //reader.EscribirResultados();
        
      // reader.imprimir();
       
      //Reader reader2 = new Reader("Resultado.txt");
      //reader2.leerResultado();
       //reader2.writeResultados();
    }
    
    
    public void leerArchivoPos(String archivo) throws IOException, TreeTaggerException
    {
        this.archivo = archivo;
        reader = new Reader(archivo);
        reader.leer();
        reader.write();
        reader.arbolMet();
        //reader.WriteResultadosPos();
        //reader.MostrarResultados();
        reader.EscribirResultados();
    }
}
