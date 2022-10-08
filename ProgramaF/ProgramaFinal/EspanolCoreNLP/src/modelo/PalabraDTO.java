
package modelo;

public class PalabraDTO {
   private String  palabra;
    private String categoria;
    private String lema;
    private String Ne;

    public PalabraDTO(String palabra, String categoria, String lema) {
        this.palabra = palabra;
        this.categoria = categoria;
        this.lema = lema;
    }

    public PalabraDTO() {
    }

    
    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getLema() {
        return lema;
    }

    public void setLema(String lema) {
        this.lema = lema;
    }

    public String getNe() {
        return Ne;
    }

    public void setNe(String Ne) {
        this.Ne = Ne;
    }

    
    @Override
    public String toString() {
        return "Token{" + "palabra=" + palabra + ", categoria=" + categoria + ", lema=" + lema + "Ne" + Ne +'}';
    }
    
    
    
}
