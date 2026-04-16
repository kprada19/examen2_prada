package co.edu.poli.examen2_prada.modelo;

public abstract class Seguro {
    private String numero;
    private String fechaExpedicion;
    private boolean estado;
    private Asegurado asegurado;

    public Seguro(String numero, String fechaExpedicion, boolean estado, Asegurado asegurado) {
        this.numero = numero;
        this.fechaExpedicion = fechaExpedicion;
        this.estado = estado;
        this.asegurado = asegurado;
    }

    // Getters y Setters
    public String getNumero() { return numero; }
    public String getFechaExpedicion() { return fechaExpedicion; }
    public boolean isEstado() { return estado; }
    public Asegurado getAsegurado() { return asegurado; }

    @Override
    public String toString() {
        return "Número: " + numero + ", Fecha: " + fechaExpedicion + ", Estado: " + (estado ? "Activo" : "Inactivo") + 
               "\nAsegurado: " + asegurado.getNombre();
    }
}