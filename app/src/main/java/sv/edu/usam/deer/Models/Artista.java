package sv.edu.usam.deer.Models;

public class Artista {
    private String artista;
    private String portada;

    public Artista(String artista, String portada) {
        this.artista = artista;
        this.portada = portada;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }
}
