package co.edu.poli.examen2_prada.test.unitaria;



import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import co.edu.poli.examen2_prada.modelo.*;



public class TestSeguro {



    @Test

    public void testAtributosYToString() {

        // Esta prueba solo mira que la clase en Java funcione, no usa Base de Datos

        Asegurado a = new Asegurado("A001", "Juan Prada");

        SeguroVehiculo sv = new SeguroVehiculo("S100", "2026-04-15", true, a, "Mazda");



        // Verificamos que los datos se guarden bien en el objeto

        assertEquals("S100", sv.getNumero());

        

        // Verificamos el formato del texto que sale en el TextArea

        String txt = sv.toString();

        assertTrue(txt.contains("Número: S100"));

        assertTrue(txt.contains("Tipo: Vehiculo"));

        // Comprobamos que NO tenga los corchetes feos

        assertFalse(txt.contains("[")); 

    }

}