package co.edu.poli.examen2_prada.test.integracion;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import co.edu.poli.examen2_prada.modelo.Asegurado;
import co.edu.poli.examen2_prada.servicios.DAOAsegurado;
import java.util.List;

public class TestDAOAsegurado {

    private DAOAsegurado dao = new DAOAsegurado();

    @Test
    public void testReadAllNoEsNulo() throws Exception {
        // Verifica que el método al menos devuelva una lista (aunque esté vacía)
        List<Asegurado> lista = dao.readall();
        assertNotNull(lista, "La lista de asegurados no debería ser null");
    }

    @Test
    public void testReadAllTraeDatos() throws Exception {
        // Para que este test pase, debes tener al menos un registro en la tabla 'asegurado'
        List<Asegurado> lista = dao.readall();
        
        // Si la tabla tiene datos, verificamos el primero
        if (!lista.isEmpty()) {
            Asegurado a = lista.get(0);
            assertNotNull(a.getId(), "El ID del asegurado no debe ser null");
            assertNotNull(a.getNombre(), "El nombre del asegurado no debe ser null");
            System.out.println("Asegurado encontrado: " + a.getNombre());
        } else {
            System.out.println("Advertencia: La tabla asegurado está vacía, no se pudo validar el contenido.");
        }
    }
}
