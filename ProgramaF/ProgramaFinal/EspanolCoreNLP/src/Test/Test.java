/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;

import java.io.IOException;
import modelo.ManRead;
import org.annolab.tt4j.TreeTaggerException;

/**
 *
 * @author enriq
 */
public class Test {
    public static void main(String[] args) throws IOException, TreeTaggerException {
        ManRead man = new ManRead();
        man.leerArchivoPos("prueba.txt");
        
    }
}
