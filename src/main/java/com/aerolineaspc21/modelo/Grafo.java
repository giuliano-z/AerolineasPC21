package com.aerolineaspc21.modelo;

import java.util.List;

import java.util.HashMap;
import java.util.Map;

public class Grafo {
    public Vertice getVertice(String nombre) {
        return vertices.get(nombre);
    }

    public List<String> recorridoDFS(String origenNombre) {
        return DFS(origenNombre);
    }
    /**
     * Calcula la ruta óptima entre dos ciudades usando Dijkstra (tiempo principal, precio como desempate).
     * @param origenNombre Nombre de la ciudad de origen
     * @param destinoNombre Nombre de la ciudad de destino
     * @return Lista de nombres de ciudades en el itinerario óptimo
     */
    public java.util.List<String> calcularRutaMinima(String origenNombre, String destinoNombre) {
        return calcularRuta(origenNombre, destinoNombre);
    }

    /**
     * Realiza un recorrido BFS desde el vértice de origen y devuelve los nombres de los vértices alcanzados.
     * @param origenNombre Nombre del vértice de origen
     * @return Lista de nombres de vértices alcanzados
     */
    public java.util.List<String> recorridoBFS(String origenNombre) {
        return BFS(origenNombre);
    }
    /**
     * Realiza un recorrido en anchura (BFS) desde el vértice de origen.
     * @param origenNombre Nombre del vértice de origen
     * @return Lista de nombres de vértices alcanzables
     */
    public java.util.List<String> BFS(String origenNombre) {
        java.util.List<String> resultado = new java.util.ArrayList<>();
        java.util.Set<String> visitados = new java.util.HashSet<>();
        java.util.Queue<Vertice> cola = new java.util.LinkedList<>();
        Vertice origen = vertices.get(origenNombre);
        if (origen == null) return resultado;
        cola.add(origen);
        visitados.add(origen.getNombre());
        while (!cola.isEmpty()) {
            Vertice actual = cola.poll();
            resultado.add(actual.getNombre());
            for (Arista arista : actual.getAdyacentes()) {
                Vertice vecino = arista.getDestino();
                if (!visitados.contains(vecino.getNombre())) {
                    cola.add(vecino);
                    visitados.add(vecino.getNombre());
                }
            }
        }
        return resultado;
    }

    /**
     * Realiza un recorrido en profundidad (DFS) desde el vértice de origen.
     * @param origenNombre Nombre del vértice de origen
     * @return Lista de nombres de vértices alcanzables
     */
    public java.util.List<String> DFS(String origenNombre) {
        java.util.List<String> resultado = new java.util.ArrayList<>();
        java.util.Set<String> visitados = new java.util.HashSet<>();
        Vertice origen = vertices.get(origenNombre);
        if (origen == null) return resultado;
        DFSRecursivo(origen, visitados, resultado);
        return resultado;
    }

    private void DFSRecursivo(Vertice actual, java.util.Set<String> visitados, java.util.List<String> resultado) {
        visitados.add(actual.getNombre());
        resultado.add(actual.getNombre());
        for (Arista arista : actual.getAdyacentes()) {
            Vertice vecino = arista.getDestino();
            if (!visitados.contains(vecino.getNombre())) {
                DFSRecursivo(vecino, visitados, resultado);
            }
        }
    }
    private Map<String, Vertice> vertices;

    public Grafo() {
        this.vertices = new HashMap<>();
    }

    public Map<String, Vertice> getVertices() {
        return vertices;
    }

    public void cargarDatosIniciales() {
        // Carga de 7 ciudades según enunciado
        String[] ciudades = {"Buenos Aires", "Córdoba", "Mendoza", "Bariloche", "Santa Cruz", "Santa Fe", "Posadas"};
        for (String ciudad : ciudades) {
            vertices.put(ciudad, new Vertice(ciudad));
        }
        // Conexiones directas desde Buenos Aires
        agregarAristaBidireccional("Buenos Aires", "Córdoba", 1.2, 120000);
        agregarAristaBidireccional("Buenos Aires", "Mendoza", 1.7, 150000);
        agregarAristaBidireccional("Buenos Aires", "Bariloche", 2.2, 220000);
        agregarAristaBidireccional("Buenos Aires", "Santa Fe", 1.0, 100000);
        agregarAristaBidireccional("Buenos Aires", "Posadas", 1.5, 140000);
        // No hay vuelo directo Buenos Aires - Santa Cruz

        // Conexiones para trasbordos (bidireccionales)
        agregarAristaBidireccional("Córdoba", "Mendoza", 1.1, 90000);
        agregarAristaBidireccional("Córdoba", "Santa Fe", 0.8, 70000);
        agregarAristaBidireccional("Mendoza", "Bariloche", 1.6, 120000);
        agregarAristaBidireccional("Bariloche", "Santa Cruz", 2.0, 160000);
        agregarAristaBidireccional("Mendoza", "Santa Cruz", 2.6, 170000);
        agregarAristaBidireccional("Santa Fe", "Posadas", 1.2, 80000);
    }

    private void agregarAristaBidireccional(String origen, String destino, double tiempo, double precioBase) {
        Vertice vOrigen = vertices.get(origen);
        Vertice vDestino = vertices.get(destino);
        if (vOrigen != null && vDestino != null) {
            vOrigen.agregarArista(new Arista(vDestino, tiempo, precioBase));
            vDestino.agregarArista(new Arista(vOrigen, tiempo, precioBase));
        }
    }
    /**
     * Calcula la ruta mínima entre dos ciudades usando Dijkstra.
     * Prioriza tiempo total, y en caso de empate, precio total.
     * Devuelve la lista de nombres de ciudades en el itinerario.
     */
    public java.util.List<String> calcularRuta(String origenNombre, String destinoNombre) {
        // Reiniciar atributos de vértices
        for (Vertice v : vertices.values()) {
            v.clear();
        }
        Vertice origen = vertices.get(origenNombre);
        Vertice destino = vertices.get(destinoNombre);
        if (origen == null || destino == null) return java.util.Collections.emptyList();
    origen.setTiempoMinimoAcumulado(0);
    origen.setPrecioTotalAcumulado(0);
        java.util.PriorityQueue<Vertice> cola = new java.util.PriorityQueue<>(
            (a, b) -> {
                int cmp = Double.compare(a.getTiempoMinimoAcumulado(), b.getTiempoMinimoAcumulado());
                if (cmp == 0) {
                    return Double.compare(a.getPrecioTotalAcumulado(), b.getPrecioTotalAcumulado());
                }
                return cmp;
            }
        );
        cola.add(origen);
        while (!cola.isEmpty()) {
            Vertice actual = cola.poll();
            if (actual.isVisitado()) continue;
            actual.setVisitado(true);
            if (actual == destino) break;
            for (Arista arista : actual.getAdyacentes()) {
                Vertice vecino = arista.getDestino();
                double nuevoTiempo = actual.getTiempoMinimoAcumulado() + arista.getTiempo();
                double nuevoPrecio = actual.getPrecioTotalAcumulado() + arista.getPrecioBase();
                if (nuevoTiempo < vecino.getTiempoMinimoAcumulado() ||
                    (Math.abs(nuevoTiempo - vecino.getTiempoMinimoAcumulado()) < 1e-6 && nuevoPrecio < vecino.getPrecioTotalAcumulado())) {
                    vecino.setTiempoMinimoAcumulado(nuevoTiempo);
                    vecino.setPrecioTotalAcumulado(nuevoPrecio);
                    vecino.setPredecesor(actual);
                    cola.add(vecino);
                }
            }
        }
        // Reconstruir la ruta
        java.util.LinkedList<String> ruta = new java.util.LinkedList<>();
        Vertice v = destino;
        while (v != null) {
            ruta.addFirst(v.getNombre());
            v = v.getPredecesor();
        }
        if (ruta.isEmpty() || !ruta.getFirst().equals(origenNombre)) return java.util.Collections.emptyList();
        return ruta;
    }
}
