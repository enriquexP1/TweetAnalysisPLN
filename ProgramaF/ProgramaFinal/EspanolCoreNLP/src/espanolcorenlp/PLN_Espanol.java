package espanolcorenlp;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.PropertiesUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import modelo.PalabraDTO;
import org.annolab.tt4j.TreeTaggerException;


public class PLN_Espanol {
    public static void main(String[] args) throws IOException, TreeTaggerException {	
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
	String texto = "Juan y Guadalupe piden al Gobierno Federal transporte en la ciudad Guadalajara para /# compensar * el disminuir los ingresos por COVID.";

	
        //Crea el objeto documento a partir e los textos 
        Annotation documento = new Annotation(texto);
	// Ejecutar anotador sobre el texto
	componentes.annotate(documento);
        
       
	//Llamado al método de segmentación 
        List<CoreMap> sentencias= procesador.Segmentacion(texto, documento);
        
        //Recorre sentencias del documento
	for(CoreMap sentencia: sentencias) {
            System.out.println("\n====== Texto original ========\n");
            System.out.println(sentencia);
            String sentenciaLimpia=procesador.Limpieza(sentencia.toString());
            String sentenciaSinPalabrasVacias=procesador.eliminarPalabrasVacias(sentenciaLimpia);
            
             System.out.println("\n====== Texto limpio (sin carcateres y palabras vacias ========\n");
            System.out.println(sentenciaSinPalabrasVacias);
            
            //Llamado al método de Tokenizacion y Lematizacion con Libreria TreeTagger
            ArrayList<PalabraDTO> palabrasLematizadas=procesador.EtiquetadoLematizadoTreeTagger(sentenciaSinPalabrasVacias);
            
              System.out.println("\n====== Texto lematizados y Etiquetado POS ========\n");
            //Imprimiendo por palabra 
            for(PalabraDTO palabra:palabrasLematizadas){
                            System.out.println(String.format("[%s] "
                      + "[%s] "
                      + "[%s] " , palabra.getPalabra(), palabra.getLema(), palabra.getCategoria()));
            }

	}

		
    }

}
