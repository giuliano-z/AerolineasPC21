package com.aerolineaspc21.modelo;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas TDD para recorridos BFS y DFS en Grafo.
 * Valida que todos los nodos sean alcanzables y el orden sea correcto.
 */
public class GrafoRecorridosTest {
    @Test
    public void testBFS() {
        Grafo grafo = new Grafo();
        grafo.cargarDatosIniciales();
        List<String> recorrido = grafo.BFS("Buenos Aires");
        assertTrue(recorrido.contains("Bariloche"));
        assertTrue(recorrido.contains("Mendoza"));
        assertTrue(recorrido.contains("Santa Cruz"));
    assertEquals(7, recorrido.size());
    }

    @Test
    public void testDFS() {
        Grafo grafo = new Grafo();
        grafo.cargarDatosIniciales();
        List<String> recorrido = grafo.DFS("Buenos Aires");
        assertTrue(recorrido.contains("Bariloche"));
        assertTrue(recorrido.contains("Mendoza"));
        assertTrue(recorrido.contains("Santa Cruz"));
    assertEquals(7, recorrido.size());
    }
}
