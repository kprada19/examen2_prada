package co.edu.poli.examen2_prada.modelo;

public class SeguroVehiculo extends Seguro {

    private String marca;

    public SeguroVehiculo(String numero, String fechaExpedicion, boolean estado,
                          Asegurado asegurado, String marca) {
        super(numero, fechaExpedicion, estado, asegurado);
        this.marca = marca;
    }

    public String getMarca() {
        return marca;
    }

    @Override
    public String toString() {
        return super.toString() + "\nTipo: Vehiculo, Marca: " + marca;
    }
}