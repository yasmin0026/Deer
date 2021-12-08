package sv.edu.usam.deer.Models;

public class AlbumsArt {
    private String artista;
    private String album;
    private String portada;

    public AlbumsArt(String artista, String album, String portada) {
        this.artista = artista;
        this.album = album;
        this.portada = portada;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
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
