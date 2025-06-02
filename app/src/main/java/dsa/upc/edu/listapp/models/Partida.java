package dsa.upc.edu.listapp.models;

public class Partida {

    private String id_partida;

    private String id_usuario;

    private Integer vidas;

    private Integer monedas;

    private Integer puntuacion;

    public Partida() {

    }

    public Partida(String id_partida, String id_usuario, Integer vidas, Integer monedas, Integer puntuacion) {
        setId_partida(id_partida);
        setId_usuario(id_usuario);
        setVidas(vidas);
        setMonedas(monedas);
        setPuntuacion(puntuacion);
    }

    public String getId_partida() { return id_partida; }
    public void setId_partida(String id_partida) { this.id_partida = id_partida; }

    public String getId_usuario() { return id_usuario; }
    public void setId_usuario(String id_usuario) { this.id_usuario = id_usuario; }

    public Integer getVidas() { return vidas; }
    public void setVidas(Integer vidas) { this.vidas = vidas; }

    public Integer getMonedas() { return monedas; }
    public void setMonedas(Integer monedas) { this.monedas = monedas; }

    public Integer getPuntuacion() { return puntuacion; }
    public void setPuntuacion(Integer puntuacion) { this.puntuacion = puntuacion; }

    @Override
    public String toString() {
        return "Partida [id_partida=" + id_partida + ", id_usuario=" + id_usuario + ", vidas=" + vidas +
                ", monedas=" + monedas + ", puntuacion=" + puntuacion + "]";
    }

}
