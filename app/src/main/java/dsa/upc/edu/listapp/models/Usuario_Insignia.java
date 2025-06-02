package dsa.upc.edu.listapp.models;

public class Usuario_Insignia {

    private String id_usuario_insignia;

    private String id_usuario;

    private String id_insignia;

    public Usuario_Insignia(){

    }

    public Usuario_Insignia(String id_usuario_insignia, String id_usuario, String id_insignia){
        setId_usuario_insignia(id_usuario_insignia);
        setId_usuario(id_usuario);
        setId_insignia(id_insignia);

    }

    public String getId_usuario_insignia() {return id_usuario_insignia;}
    public void setId_usuario_insignia(String id_usuario_insignia) {this.id_usuario_insignia=id_usuario_insignia;}

    public String getId_usuario() {return id_usuario;}
    public void setId_usuario(String id_usuario) {this.id_usuario=id_usuario;}

    public String getId_insignia() {return id_insignia;}
    public void setId_insignia(String id_insignia) {this.id_insignia=id_insignia;}

    @Override
    public String toString(){
        return "Usuario_Insignia [id_usuario_insignia="+id_usuario_insignia+", id_usuario="+id_usuario+", id_insignia="+id_insignia+"]";
    }
}
