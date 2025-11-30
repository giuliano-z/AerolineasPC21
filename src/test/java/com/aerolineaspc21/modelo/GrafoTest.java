package com.aerolineaspc21.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class GrafoTest {
    // Test 3.1: Validación de Dijkstra con doble criterio (Tiempo y Precio)
    @Test
    void testDijkstraDobleCriterio() {
        Grafo grafo = new Grafo();
        grafo.cargarDatosIniciales();

        String origen = "Buenos Aires";
        String destino = "Santa Cruz";

        // El enunciado propone dos rutas cercanas para BUE -> SCZ:
        // Ruta A (Tiempo Mínimo): BUE -> BRC -> SCZ. Tiempo: 2.2h + 2.0h = 4.2h.
        // Ruta B (Más Larga): BUE -> MDZ -> SCZ. Tiempo: 1.7h + 2.6h = 4.3h.
        // Dijkstra debe elegir la Ruta A (4.2h) sobre la Ruta B (4.3h) por ser la de menor tiempo [1].

        // Debemos implementar un método que devuelva el itinerario (lista de vértices o tramos)
        List<String> rutaOptima = grafo.calcularRutaMinima(origen, destino);

        // 1. Verificar la ruta seleccionada (debe ser la más rápida BUE -> BRC -> SCZ)
        assertNotNull(rutaOptima, "La ruta no debe ser nula.");
        assertEquals(3, rutaOptima.size(), "El itinerario debe tener 3 ciudades.");
        assertEquals("Buenos Aires", rutaOptima.get(0));
        assertEquals("Bariloche", rutaOptima.get(1), "El primer trasbordo debe ser Bariloche (Ruta A).");
        assertEquals("Santa Cruz", rutaOptima.get(2));
        // 2. Opcional: Verificar el tiempo total (Requiere un método adicional en Grafo)
        // assertEquals(4.2, grafo.getTiempoTotal(origen, destino), 0.001); 
    }

    // Test 3.2: Implementación de la prueba para recorrido BFS (Requisito obligatorio) [1, 6]
    @Test
    void testRecorridoBFS() {
        Grafo grafo = new Grafo();
        grafo.cargarDatosIniciales();

        // Asumiendo que BFS devuelve una lista de nombres de vértices visitados
        List<String> alcanzables = grafo.recorridoBFS("Buenos Aires");

        // BUE está conectado a todos los destinos mediante trasbordos [7, 8]
        assertEquals(7, alcanzables.size(), "BFS debe alcanzar las 7 ciudades.");
        assertTrue(alcanzables.contains("Mendoza"));
        assertTrue(alcanzables.contains("Santa Cruz")); // Santa Cruz es alcanzable mediante trasbordo [8].
    }
}
