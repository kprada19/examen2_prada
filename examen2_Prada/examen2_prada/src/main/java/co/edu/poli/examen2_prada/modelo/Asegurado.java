package co.edu.poli.examen2_prada.modelo;

public class Asegurado {

    private String id;
    private String nombre;

    public Asegurado(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }

    @Override
    public String toString() {
        return id + ", " + nombre;
    }
}