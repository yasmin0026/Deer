package sv.edu.usam.deer.Models;

public class ContenidoUser {
    private String id,nombres,apellidos,usuario,clave,id_rol;

    public ContenidoUser() {
    }

    public ContenidoUser(String id, String nombres, String apellidos, String usuario, String clave, String id_rol) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.clave = clave;
        this.id_rol = id_rol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getId_rol() {
        return id_rol;
    }

    public void setId_rol(String id_rol) {
        this.id_rol = id_rol;
    }
}
