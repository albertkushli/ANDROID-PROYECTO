package dsa.upc.edu.listapp.models;

public class Insignia {

    private String id_insignia;

    private String nombre;

    private String avatar;

    public Insignia(){

    }

    public Insignia(String id_insignia, String nombre, String avatar){
        setId_insignia(id_insignia);
        setNombre(nombre);
        setAvatar(avatar);
    }

    public String getId_insignia() {return id_insignia;}
    public void setId_insignia(String id_insignia) {this.id_insignia=id_insignia;}

    public String getNombre() {return  nombre;}
    public void setNombre(String nombre) {this.nombre=nombre;}

    public String getAvatar() {return avatar;}
    public void setAvatar(String avatar) {this.avatar=avatar;}

    @Override
    public String toString(){
        return "Insignia [id_insignia="+id_insignia+", nombre="+nombre+", avatar="+avatar+"]";
    }
}
