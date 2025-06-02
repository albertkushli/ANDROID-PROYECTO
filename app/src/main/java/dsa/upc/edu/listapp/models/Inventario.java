package dsa.upc.edu.listapp.models;

public class Inventario {

    private String id_inventario;

    private String id_partida;

    private String id_objeto;

    public Inventario() {

    }

    public Inventario(String id_inventario, String id_partida, String id_objeto) {
        setId_inventario(id_inventario);
        setId_partida(id_partida);
        setId_objeto(id_objeto);
    }

    public String getId_inventario() { return id_inventario; }
    public void setId_inventario(String id_inventario) { this.id_inventario = id_inventario; }

    public String getId_partida() { return id_partida; }
    public void setId_partida(String id_partida) { this.id_partida = id_partida; }

    public String getId_objeto() { return id_objeto; }
    public void setId_objeto(String id_objeto) { this.id_objeto = id_objeto; }

    @Override
    public String toString() {
        return "Inventario [id_inventario=" + id_inventario + ", id_partida=" + id_partida + ", id_objeto=" + id_objeto + "]";
    }
}
