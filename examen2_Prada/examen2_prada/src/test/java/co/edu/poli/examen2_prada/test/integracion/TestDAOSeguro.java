package co.edu.poli.examen2_prada.test.integracion;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import co.edu.poli.examen2_prada.modelo.*;
import co.edu.poli.examen2_prada.servicios.DAOSeguro;

public class TestDAOSeguro {

    // Instanciamos el DAO para probar la conexión real
    private DAOSeguro dao = new DAOSeguro();

    @Test
    public void testGuardarEnBaseDeDatos() throws Exception {
        // Asegúrate que el ID 'A001' exista en tu tabla asegurado de MySQL
        Asegurado a = new Asegurado("A001", "Juan Prada");
        
        // Usamos un número diferente cada vez o bórralo de la BD antes de probar
        SeguroVida sv = new SeguroVida("TEST-BD-02", "2026-04-15", true, a, "Maria Lopez");

        // 1. Probamos que el método create funcione
        String respuesta = dao.create(sv);
        assertTrue(respuesta.contains("✔"));

        // 2. Probamos que al leerlo de la BD traiga el beneficiario correcto
        Seguro buscado = dao.readone("TEST-BD-01");
        assertNotNull(buscado);
        assertEquals("Maria Lopez", ((SeguroVida) buscado).getBeneficiario());
    }
}
