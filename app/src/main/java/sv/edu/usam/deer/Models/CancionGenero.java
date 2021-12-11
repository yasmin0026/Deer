package sv.edu.usam.deer.Models;

public class CancionGenero {
    private String genero;
    private String album;
    private String portada;

    public CancionGenero(String genero, String album, String portada) {
        this.genero = genero;
        this.album = album;
        this.portada = portada;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }
}
