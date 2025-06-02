package dsa.upc.edu.listapp.models;

public class Carrito {

    private String id_carrito;

    private String id_partida;

    private String id_objeto;

    public Carrito() {

    }

    public Carrito(String id_carrito, String id_partida, String id_objeto) {
        setId_carrito(id_carrito);
        setId_partida(id_partida);
        setId_objeto(id_objeto);
    }

    public String getId_carrito() { return id_carrito; }
    public void setId_carrito(String id_carrito) { this.id_carrito = id_carrito; }

    public String getId_partida() { return id_partida; }
    public void setId_partida(String id_partida) { this.id_partida = id_partida; }

    public String getId_objeto() { return id_objeto; }
    public void setId_objeto(String id_objeto) { this.id_objeto = id_objeto; }

    @Override
    public String toString() {
        return "Carrito [id_carrito=" + id_carrito + ", id_partida=" + id_partida + ", id_objeto=" + id_objeto+"]";
    }

}
