package co.edu.poli.examen2_prada.test.integracion;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import co.edu.poli.examen2_prada.modelo.*;
import co.edu.poli.examen2_prada.servicios.DAOSeguro;

public class TestDAOSeguro {

    private DAOSeguro dao = new DAOSeguro();

    @Test
    public void testFlujoCompletoSeguro() throws Exception {
        // 1. PREPARACIÓN DE DATOS
        // Importante: El ID 'A001' debe existir en tu tabla 'asegurado'
        Asegurado asegurado = new Asegurado("A001", "Juan Prada");
        
        // Generamos un número único basado en el tiempo actual para que nunca falle por duplicados
        String numeroUnico = "TEST-" + System.currentTimeMillis();
        String beneficiarioEsperado = "Maria Lopez";
        
        SeguroVida sv = new SeguroVida(
            numeroUnico, 
            "2026-04-15", 
            true, 
            asegurado, 
            beneficiarioEsperado
        );

        // 2. PRUEBA DE CREACIÓN (CREATE)
        String respuesta = dao.create(sv);
        
        // Verificamos que la respuesta contenga el check de éxito
        assertNotNull(respuesta, "La respuesta del DAO no debería ser nula");
        assertTrue(respuesta.contains("✔"), "El mensaje de éxito debería contener el símbolo ✔. Respuesta recibida: " + respuesta);

        // 3. PRUEBA DE CONSULTA (READONE)
        // Buscamos exactamente el mismo número que acabamos de crear
        Seguro buscado = dao.readone(numeroUnico);

        // 4. VERIFICACIONES FINALES
        assertNotNull(buscado, "Error: El seguro guardado no se encontró al consultar con readone. Revisa los JOINs en el DAO.");
        
        // Verificamos que sea del tipo correcto (SeguroVida)
        assertTrue(buscado instanceof SeguroVida, "El objeto recuperado debería ser una instancia de SeguroVida");
        
        // Verificamos que los datos internos coincidan
        SeguroVida buscadoVida = (SeguroVida) buscado;
        assertEquals(numeroUnico, buscadoVida.getNumero(), "El número de seguro no coincide");
        assertEquals(beneficiarioEsperado, buscadoVida.getBeneficiario(), "El beneficiario no coincide");
        assertEquals("Juan Prada", buscadoVida.getAsegurado().getNombre(), "El nombre del asegurado no coincide");
        
        System.out.println("✅ Test completado con éxito para el seguro: " + numeroUnico);
    }
}
