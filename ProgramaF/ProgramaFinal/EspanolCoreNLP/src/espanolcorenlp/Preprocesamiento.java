package espanolcorenlp;

import Constantes.PalabrasVacias;
import java.util.List;
import edu.stanford.nlp.ling.CoreAnnotations.*;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.*;
import java.io.IOException;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import modelo.PalabraDTO;
import org.annolab.tt4j.TokenHandler;
import org.annolab.tt4j.TreeTaggerException;
import org.annolab.tt4j.TreeTaggerWrapper;


public class Preprocesamiento {
    private final String[] PALABRAS_VACIA;
    
    public Preprocesamiento(){
       PALABRAS_VACIA = PalabrasVacias.PALABRAS_VACIA;
    
    }
       
    // Método para Segmentar los textos
    public List<CoreMap> Segmentacion(String texto, Annotation documento){
        
	// Obtiene todas las sentencias del documento(texto)
	// un CoreMap es necesario para el manejo posterior de sentencia por sentencia
	List<CoreMap> sentencias = documento.get(SentencesAnnotation.class);
        
        //regresa las sentencias
        return sentencias;
    }
   
    //Método para Etiquetado POS los textos
    public ArrayList<PalabraDTO> EtiquetadoPOS(CoreMap sentencia){
       
        ArrayList<PalabraDTO> textoLematizado = new ArrayList<PalabraDTO>();
        // recorre los tokens de cada sentencia
	for(CoreLabel token: sentencia.get(TokensAnnotation.class)) {
            PalabraDTO palabraProcesada = new PalabraDTO(); 
            token.tag();
            // Se obtiene la palabra original del token
            String palabra = token.get(TextAnnotation.class);
            
            // Se obtiene la categoria del token
           String  pos= token.get(PartOfSpeechAnnotation.class);

            
            //Se agregan la palabra y la ctageoria al arreglo
            palabraProcesada.setPalabra(palabra); 
            palabraProcesada.setCategoria(pos); 
            
              
            //Se acumulan las palabras lematizadas y tokenizadas de todo el texto
            textoLematizado.add(palabraProcesada);
	}
        return textoLematizado;
    }
    
    // Método para quitar URL  
    public String quitarURL(String texto) {
      String textoSinURL="";
     	for(String palabra:texto.split(" ")){
     	    if(!palabra.startsWith("http") && !palabra.startsWith("@"))  	
                textoSinURL=textoSinURL+palabra+" ";
     	}
     	return textoSinURL;
    }
    
    // Método para eliminar palabras vacias    
    public String Limpieza(String sentencia) {
        String textoLimpio="";
     	for(String palabra:sentencia.split(" ")){
            palabra=palabra.toLowerCase();
            palabra=palabra.replaceAll("[^a-záéíóúñü]","");
            textoLimpio=textoLimpio+palabra+" ";
        }
      return textoLimpio;  
    }
    
    
    // Método para eliminar palabras vacias    
    public String eliminarPalabrasVacias(String sentencia) {
    	  String textoSinPalabrasVacias="";
     	for(String palabra:sentencia.split(" ")){
            boolean estaPresentePalVacia=false;
            for(String palVacia:PALABRAS_VACIA){
                if(palVacia.equals(palabra))
                   estaPresentePalVacia=true;
                }
 	        if(!estaPresentePalVacia)
                    textoSinPalabrasVacias=textoSinPalabrasVacias+palabra+" ";
  	    }
     	return textoSinPalabrasVacias;
     	
     }
       
    
    //Método para Etiquetado POS y Lematizados con TreeTagger los textos
    public ArrayList<PalabraDTO> EtiquetadoLematizadoTreeTagger(String sentencia) throws IOException, TreeTaggerException{
       
        ArrayList<PalabraDTO> textoLematizado = new ArrayList<PalabraDTO>();
 
        System.setProperty("treetagger.home", "recursos/TreeTagger");
        TreeTaggerWrapper<String> tt = new TreeTaggerWrapper<String>();
        try {
                tt.setModel("recursos/TreeTagger/modelos/spanish.par:iso8859-1");
                tt.setHandler(new TokenHandler<String>() {
                        public void token(String token, String pos, String lemma) {
                                textoLematizado.add(new PalabraDTO(token,pos,lemma));
                              
                        }
                });
                
                tt.process(asList(sentencia.toString().split(" ")));
        }
        finally {
            tt.destroy();
        }
      return textoLematizado;
    }
  //Método para Etiquetado POS los textos
    public Tree ObtenerArbolDependencias(CoreMap sentencia){
        // Se obtiene el arbol de dependencias de la sentencia
        Tree arbol = (Tree) sentencia.get(TreeCoreAnnotations.TreeAnnotation.class);

        return arbol;
        
    }
    
   
}
