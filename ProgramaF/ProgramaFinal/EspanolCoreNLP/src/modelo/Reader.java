/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.PropertiesUtils;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import espanolcorenlp.Preprocesamiento;
import java.util.List;
import modelo.PalabraDTO;
import org.annolab.tt4j.TreeTaggerException;

/**
 *
 * @author enriq
 */
public class Reader {

    String archivo, archivoRes;
    String patron = "[a-zA-Z].*";
    ArrayList<String> palabras;
    ArrayList<FraseLema> palabrasRes;
    ArrayList<PalabraPOS> palabrasPos;
    ArrayList<Palabra> palabrasC;
    clasificacion cla1 , cla2 , cla3;
    public Reader(String archivo) {
        this.archivo = archivo;
        palabras = new ArrayList<String>();
        palabrasRes = new ArrayList<FraseLema>();
        palabrasPos = new ArrayList<PalabraPOS>();
        palabrasC = new ArrayList<Palabra>();
        

    }

    public void arbolMet() throws IOException, TreeTaggerException {
        Preprocesamiento procesador = new Preprocesamiento();
        // Construcción del Conjunto de componentes (pipeline)
        StanfordCoreNLP componentes = new StanfordCoreNLP(
                // Inicialización de paramatros para el español
                PropertiesUtils.asProperties(
                        "annotators", "tokenize, ssplit, pos, lemma, parse, ner",
                        "ssplit.isOneSentence", "true",
                        "parse.model", "edu/stanford/nlp/models/srparser/spanishSR.ser.gz",
                        "pos.model", "edu/stanford/nlp/models/pos-tagger/spanish/spanish-distsim.tagger",
                        "ner.model", "edu/stanford/nlp/models/ner/spanish.ancora.distsim.s512.crf.ser.gz",
                        "tokenize.language", "es"));

        // Texto a procesar
        //String texto = "Juan y Guadalupe piden al Gobierno Federal transporte en la ciudad Guadalajara para /# compensar * el disminuir los ingresos por COVID.";
        File doc = new File("Resultado.txt");
        BufferedReader obj = new BufferedReader(new FileReader(doc));
        String texto;

        while ((texto = obj.readLine()) != null) {

            //Crea el objeto documento a partir e los textos 
            Annotation documento = new Annotation(texto);
            // Ejecutar anotador sobre el texto
            componentes.annotate(documento);

            //Llamado al método de segmentación 
            List<CoreMap> sentencias = procesador.Segmentacion(texto, documento);

            //Recorre sentencias del documento
            for (CoreMap sentencia : sentencias) {
                // System.out.println("\n====== Texto original ========\n");
                //System.out.println(sentencia);
                String sentenciaLimpia = procesador.Limpieza(sentencia.toString());
                String sentenciaSinPalabrasVacias = procesador.eliminarPalabrasVacias(sentenciaLimpia);

                //System.out.println("\n====== Texto limpio (sin carcateres y palabras vacias ========\n");
                //System.out.println(sentenciaSinPalabrasVacias);
                //Llamado al método de Tokenizacion y Lematizacion con Libreria TreeTagger
                ArrayList<PalabraDTO> palabrasLematizadas = procesador.EtiquetadoLematizadoTreeTagger(sentenciaSinPalabrasVacias);

                PalabraPOS palabraPOS = new PalabraPOS(sentenciaSinPalabrasVacias, palabrasLematizadas);
                palabrasPos.add(palabraPOS);

                /*System.out.println("\n====== Texto lematizados y Etiquetado POS ========\n");
                //Imprimiendo por palabra 
                for (PalabraDTO palabra : palabrasLematizadas) {
                    System.out.println(String.format("[%s] "
                            + "[%s] "
                            + "[%s] ", palabra.getPalabra(), palabra.getLema(), palabra.getCategoria()));
                }*/
            }
        }
        estadisticas esta = new estadisticas(palabrasPos);
        //esta.hacerEstadisticas();
        palabrasC= esta.palabrasClave();
        esta.hacerEstadisticas(palabrasC);
        //MostrarResultados();
        
    }

    public void leerResultado() {
        try {
            // System.out.println("\n\n\n\n\n\n\n-----------------------------------------");
            File doc = new File("Resultado.txt");
            BufferedReader obj = new BufferedReader(new FileReader(doc));
            String strng;

            while ((strng = obj.readLine()) != null) {
                // System.out.println(strng );
                Preprocesamiento procesamiento = new Preprocesamiento();

                strng = procesamiento.quitarURL(strng);
                strng = procesamiento.Limpieza(strng);
                strng = procesamiento.eliminarPalabrasVacias(strng);
                //System.out.println(strng);
                ArrayList<PalabraDTO> palabrasLematizadas = procesamiento.EtiquetadoLematizadoTreeTagger(strng);
                //System.out.println(palabrasLematizadas + "\n");
                FraseLema lematizado = new FraseLema(palabrasLematizadas, strng);
                palabrasRes.add(lematizado);

            }

            /* System.out.println("---------------------------- aqui imprimimos el arraylist---------------------");
            for(int i= 0 ; i< palabrasRes.size() ; i++)
            {
                System.out.println(palabrasRes.get(i).getLematizado());
                System.out.println("\n" +  palabrasRes.get(i).getPalabras());
            }*/
        } catch (Exception e) {
        }
    }

    public void leer() {
        try {
            cla1 = new clasificacion("ironia");
            int contadorIronia = 0;
            cla2 = new clasificacion("seriedad");
            int contadorSeriedad = 0;
            cla3 = new clasificacion("sarcasmo");
            int contadorSarcasmo = 0;
            String linea = "";
            Pattern patron = Pattern.compile(this.patron);
            File doc = new File(archivo);

            BufferedReader obj = new BufferedReader(new FileReader(doc));
            String strng;
            while ((strng = obj.readLine()) != null) {
                if (strng.length() > 40) {
                    strng = strng.substring(43);
                }
                StringTokenizer st = new StringTokenizer(strng);
                StringBuilder builder = new StringBuilder();
                while (st.hasMoreElements()) {

                    String token = st.nextToken().toLowerCase();
                    
                    if(token.equals("ironia"))
                    {
                        contadorIronia++ ;
                    }
                    else if(token.equals("seriedad"))
                    {
                        contadorSeriedad++;
                    }
                    else if(token.equals("sarcasmo"))
                    {
                        contadorSarcasmo++;
                    }
                    if (token.equals("b") || token.equals("ironia") || token.equals("sarcasmo")) {
                        break;
                    }
                    Matcher mat = patron.matcher(token);
                    if (token.contains("[.,\\/¿?<>¡!=(0-9)$%\\^&\\*;:{}=\\-_]")) {
                        String nuevaPal = token.replaceAll("[.,\\/¿?<>¡!=(0-9)$%\\^&\\*;:{}=\\-_]", "");
                        builder.append(nuevaPal + " ");
                    } else if (token.contains("https")) {
                        String nuevaPal = token.replace(token, "");
                        builder.append(nuevaPal + " ");
                    } else if (token.contains("@")) {
                        String nuevaPal = token.replace("@", "@user ");
                        builder.append(nuevaPal + " ");
                    } else if (token.contains("null")) {
                        String nuevaPal = token.replace("null", "");
                        builder.append(nuevaPal + " ");
                    } else if (mat.matches()) {
                        builder.append(token + " ");
                        token = "";
                    } else {
                        continue;
                    }
                     
                    linea = builder.toString();

                }
                if (linea.equals("")) {
                    continue;
                }
                else
                {
                    palabras.add(linea);
                    //System.out.println(linea);
                }
                cla1.setContador(contadorIronia);
                cla2.setContador(contadorSeriedad);
                cla3.setContador(contadorSarcasmo);
//Aqui vamos a concatenar
            }
            obj.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void imprimir() {
        for (int i = 0; i < palabras.size(); i++) {
            System.out.println(palabras.get(i) + "\n");

        }
    }

    public void WriteResultadosPos() {
        try {
            FileWriter fw = new FileWriter("ResultadoPOS.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < palabrasPos.size(); i++) {
                bw.write(palabrasPos.get(i).getLematizado() + "\n");
                ArrayList<PalabraDTO> palabrasLematizadas = palabrasPos.get(i).getPalabrasLematizadas();
                for (PalabraDTO palabra : palabrasLematizadas) {

                    bw.write(String.format("[%s] "
                            + "[%s] "
                            + "[%s] \n", palabra.getPalabra(), palabra.getLema(), palabra.getCategoria()));

                }
                bw.write("\n\n");
            }
            bw.flush();
            bw.close();
            fw.close();
        } catch (Exception e) {
        }
    }

    public void writeResultados() {
        try {
            FileWriter fw = new FileWriter("ResultadoLema.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < palabrasRes.size(); i++) {
                bw.write(palabrasRes.get(i).getLematizado() + "\n" + palabrasRes.get(i).getPalabras() + "\n\n");
            }
            bw.flush();
            bw.close();
            fw.close();
        } catch (Exception e) {
        }
    }

    public void write() {
        try {
            FileWriter fw = new FileWriter("Resultado.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < palabras.size(); i++) {

                bw.write(palabras.get(i) + "\n");

            }
            bw.flush();
            bw.close();
            fw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void EscribirResultados() {
            
        try {
            FileWriter fw = new FileWriter("Res.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(cla1.toString());
            System.out.println(cla1.toString());
            bw.write(cla2.toString());
            System.out.println(cla2.toString());
            bw.write(cla3.toString());
            System.out.println(cla3.toString());
            for (int i = 0; i < palabrasC.size(); i++) {
            float tamañoTotal = palabrasPos.size();
            float tamañoParcial = palabrasC.get(i).getContador();
            float porcentaje = (tamañoParcial / tamañoTotal) * 100;
            bw.write("De " + palabrasPos.size() + " hay " + palabrasC.get(i).getContador() + " coincidencias"
                    + "\n Lo que representa el  " + porcentaje + "%");
            bw.write(palabrasC.get(i).toString());
            ArrayList<String> lemas = palabrasC.get(i).getLemas();
            for (int j = 0; j < lemas.size(); j++) {
                bw.write("-- " + lemas.get(j) + "\n");
            }
            bw.write("\n\n");
        }
            bw.flush();
            bw.close();
            fw.close();
        }
         catch (FileNotFoundException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
     public void MostrarResultados() {
           
         /*System.out.println("-----------------------------------------------------");
         System.out.println(palabrasPos.size());
         System.out.println(palabrasC.size());
         System.out.println(palabrasC.get(0).contador);
         System.out.println(palabrasC.get(0).getLemas());
         System.out.println("´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´´");
         */
            for (int i = 0; i < palabrasC.size(); i++) {
            float tamañoTotal = palabrasPos.size();
            float tamañoParcial = palabrasC.get(i).getContador();
            float porcentaje = (tamañoParcial / tamañoTotal) * 100;
                System.out.println("De " + palabrasPos.size() + " hay " + palabrasC.get(i).getContador() + " coincidencias"
                    + "\n Lo que representa el  " + porcentaje + "%");
                System.out.println(palabrasC.get(i).toString());
            ArrayList<String> lemas = palabrasC.get(i).getLemas();
            for (int j = 0; j < lemas.size(); j++) {
                System.out.println("-- " + lemas.get(j) + "\n");
            }
                System.out.println("\n\n");
        }
           
        }
        
    }

