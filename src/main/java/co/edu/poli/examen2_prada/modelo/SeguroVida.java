package co.edu.poli.examen2_prada.modelo;

public class SeguroVida extends Seguro {
    private String beneficiario;

    public SeguroVida(String numero, String fechaExp, boolean estado, Asegurado asegurado, String beneficiario) {
        super(numero, fechaExp, estado, asegurado);
        this.beneficiario = beneficiario;
    }

    public String getBeneficiario() { return beneficiario; }

    @Override
    public String toString() {
        return super.toString() + "\nTipo: Vida, Beneficiario: " + beneficiario;
    }
}