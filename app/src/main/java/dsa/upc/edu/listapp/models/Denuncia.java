package dsa.upc.edu.listapp.models;

import java.time.LocalDateTime;

public class Denuncia {

    private String id_denuncia;

    private LocalDateTime fecha;

    private String titulo;

    private String mensaje;

    private String id_usuario;

    public Denuncia() {

    }

    public Denuncia(String id_denuncia, LocalDateTime fecha, String titulo, String mensaje, String id_usuario) {
        setId_denuncia(id_denuncia);
        setFecha(fecha);
        setTitulo(titulo);
        setMensaje(mensaje);
        setId_usuario(id_usuario);
    }

    public String getId_denuncia() {return id_denuncia;}
    public void setId_denuncia(String id_denuncia) {this.id_denuncia = id_denuncia;}

    public LocalDateTime getFecha() {return fecha;}
    public void setFecha(LocalDateTime fecha) {this.fecha = fecha;}

    public String getTitulo() {return titulo;}
    public void setTitulo(String titulo) {this.titulo = titulo;}

    public String getMensaje() {return mensaje;}
    public void setMensaje(String mensaje) {this.mensaje = mensaje;}

    public String getId_usuario() {return id_usuario;}

    public void setId_usuario(String id_usuario) {this.id_usuario = id_usuario;}

    @Override
    public String toString() {
        return "Denuncia [id_denuncia=" + id_denuncia + ", fecha=" + fecha + ", titulo=" + titulo +
                ", mensaje=" + mensaje + ", id_usuario=" + id_usuario + "]";
    }
}
