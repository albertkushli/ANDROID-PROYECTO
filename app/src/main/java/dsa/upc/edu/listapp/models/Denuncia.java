package dsa.upc.edu.listapp.models;

import java.io.Serializable;
import java.util.UUID;

public class Denuncia implements Serializable {
    private String id_denuncia;
    private String id_usuario;
    private String fecha;  // Fecha en formato String ISO
    private String titulo;
    private String mensaje;

    // Constructor vacío requerido para JSON
    public Denuncia() {
    }

    // Constructor para crear nueva denuncia
    public Denuncia(String titulo, String mensaje) {
        this();  // Llama al constructor vacío para generar ID
        this.titulo = titulo;
        this.mensaje = mensaje;
    }

    // Constructor completo (para cuando viene del servidor)
    public Denuncia(String id_denuncia, String fecha, String titulo, String mensaje, String id_usuario) {
        this.id_denuncia = id_denuncia;
        this.fecha = fecha;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.id_usuario = id_usuario;
    }

    // Getters y setters
    public String getId_denuncia() {
        return id_denuncia;
    }

    public void setId_denuncia(String id_denuncia) {
        this.id_denuncia = id_denuncia;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    // Método auxiliar para obtener el remitente (nombre del usuario)
    public String getRemitente() {
        // Si tienes el nombre del usuario guardado, úsalo
        // Si no, devuelve el id_usuario o "Anónimo"
        return id_usuario != null ? id_usuario : "Anónimo";
    }
}