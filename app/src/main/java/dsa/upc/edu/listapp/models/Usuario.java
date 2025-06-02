package dsa.upc.edu.listapp.models;

public class Usuario {

    private String id_usuario;

    private String nombre;

    private String password;

    public Usuario() {

    }

    public Usuario(String id_usuario, String nombre, String password) {
        setId_usuario(id_usuario);
        setNombre(nombre);
        setPassword(password);
    }

    public String getId_usuario() { return id_usuario; }
    public void setId_usuario(String id_usuario) { this.id_usuario = id_usuario; }

    public String getNombre() { return nombre;}
    public void setNombre(String nombre) { this.nombre = nombre;}

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "Usuario [id_usuario=" + id_usuario + ", nombre="+nombre+"]";
    }
}

