package dsa.upc.edu.listapp.models;

import java.io.Serializable;

public class CategoriaObjeto implements Serializable {

    private String id_categoria;

    private String nombre;

    public CategoriaObjeto() {

    }

    public CategoriaObjeto(String id_categoria, String nombre) {
        setId_categoria(id_categoria);
        setNombre(nombre);
    }

    public String getId_categoria() {return id_categoria;}
    public void setId_categoria(String id_categoria) {this.id_categoria = id_categoria;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    @Override
    public String toString() {
        return "CategoriaObjeto [id_categoria=" + id_categoria + ", nombre=" + nombre + "]";
    }
}