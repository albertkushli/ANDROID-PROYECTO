package dsa.upc.edu.listapp.models;

import java.io.Serializable;
import java.util.Arrays;

public class Consulta implements Serializable {

    private String id_consulta;
    private int[] fecha;  // <-- único cambio
    private String titulo;
    private String mensaje;
    private String id_usuario;

    public Consulta() { }

    public Consulta(String titulo, String mensaje) {
        setTitulo(titulo);
        setMensaje(mensaje);
    }

    public Consulta(String id_consulta, int[] fecha, String titulo, String mensaje, String id_usuario){
        setId_consulta(id_consulta);
        setFecha(fecha);
        setTitulo(titulo);
        setMensaje(mensaje);
        setId_usuario(id_usuario);
    }

    public String getId_consulta() { return id_consulta; }
    public void setId_consulta(String id_consulta) { this.id_consulta = id_consulta; }

    public int[] getFecha() { return fecha; }
    public void setFecha(int[] fecha) { this.fecha = fecha; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getId_usuario() { return id_usuario; }
    public void setId_usuario(String id_usuario) { this.id_usuario = id_usuario; }

    @Override
    public String toString() {
        return "Consulta [id_consulta=" + id_consulta + ", fecha=" + Arrays.toString(fecha) +
                ", titulo=" + titulo + ", mensaje=" + mensaje + ", id_usuario=" + id_usuario + "]";
    }
}
