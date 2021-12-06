package sv.edu.usam.deer.Models;

public class Contenido {
    private String id_contenido, nombre_cancion, artista, album,genero,fecha_lanzamiento,link_video,portada;

    public Contenido() {
    }

    public Contenido(String id_contenido, String nombre_cancion, String artista, String album, String genero, String fecha_lanzamiento, String link_video, String portada) {
        this.id_contenido = id_contenido;
        this.nombre_cancion = nombre_cancion;
        this.artista = artista;
        this.album = album;
        this.genero = genero;
        this.fecha_lanzamiento = fecha_lanzamiento;
        this.link_video = link_video;
        this.portada = portada;
    }

    public String getId_contenido() {
        return id_contenido;
    }

    public void setId_contenido(String id_contenido) {
        this.id_contenido = id_contenido;
    }

    public String getNombre_cancion() {
        return nombre_cancion;
    }

    public void setNombre_cancion(String nombre_cancion) {
        this.nombre_cancion = nombre_cancion;
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFecha_lanzamiento() {
        return fecha_lanzamiento;
    }

    public void setFecha_lanzamiento(String fecha_lanzamiento) {
        this.fecha_lanzamiento = fecha_lanzamiento;
    }

    public String getLink_video() {
        return link_video;
    }

    public void setLink_video(String link_video) {
        this.link_video = link_video;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }
}
