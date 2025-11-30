package com.aerolineaspc21.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas TDD para la lógica de precios dinámicos.
 */
public class CalculadoraPrecioTest {
    @Test
    public void testReglaDirecto() {
        double base = 1000.0;
        double ajustado = CalculadoraPrecio.aplicarReglaDirecto(base, true);
        assertEquals(1200.0, ajustado, 0.01);
        ajustado = CalculadoraPrecio.aplicarReglaDirecto(base, false);
        assertEquals(1000.0, ajustado, 0.01);
    }

    @Test
    public void testReglaOcupacion() {
        double base = 1000.0;
        double ajustado = CalculadoraPrecio.aplicarReglaOcupacion(base, 95.0);
        assertEquals(1100.0, ajustado, 0.01);
        ajustado = CalculadoraPrecio.aplicarReglaOcupacion(base, 94.9);
        assertEquals(1000.0, ajustado, 0.01);
    }
}
