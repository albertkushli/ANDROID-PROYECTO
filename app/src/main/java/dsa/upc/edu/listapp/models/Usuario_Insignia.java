package edu.upc.dsa.models;

public class Usuario_Insignia {

    private Integer id_usuario_insignia;
    private String id_usuario;
    private Integer id_insignia;

    public Usuario_Insignia() {}

    public Usuario_Insignia(Integer id_usuario_insignia, String id_usuario, Integer id_insignia) {
        this.id_usuario_insignia = id_usuario_insignia;
        this.id_usuario = id_usuario;
        this.id_insignia = id_insignia;
    }

    public Integer getId_usuario_insignia() {
        return id_usuario_insignia;
    }

    public void setId_usuario_insignia(Integer id_usuario_insignia) {
        this.id_usuario_insignia = id_usuario_insignia;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Integer getId_insignia() {
        return id_insignia;
    }

    public void setId_insignia(Integer id_insignia) {
        this.id_insignia = id_insignia;
    }
}
