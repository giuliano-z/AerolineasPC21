package com.aerolineaspc21.modelo;

import java.util.*;

/**
 * Representa un vuelo con gestión de reservas usando AVL y asignación aleatoria balanceada de asientos.
 * Implementa la regla de balance: la diferencia de ocupación entre secciones no debe superar 1.
 * 
 * @author Giuliano Zulatto
 * @version 1.0
 * @since 2025-12-01
 */
public class Vuelo {
    private String codigo;
    private String origen;
    private String destino;
    private AVL reservas;
    private Map<Character, Integer> ocupacionPorSeccion; // Cambiado a Character para mejor manejo
    private Set<String> asientosOcupados; // Set para verificación rápida O(1)
    private Random random; // Generador de números aleatorios
    private static final int ASIENTOS_POR_SECCION = 10;
    private static final int TOTAL_ASIENTOS = 30; // 3 secciones x 10 asientos

    public Vuelo(String codigo, String origen, String destino) {
        this.codigo = codigo;
        this.origen = origen;
        this.destino = destino;
        this.reservas = new AVL();
        this.ocupacionPorSeccion = new HashMap<>();
        this.asientosOcupados = new HashSet<>();
        this.random = new Random();
        
        // Inicializar contadores de ocupación (A=0, B=0, C=0)
        for (char seccion = 'A'; seccion <= 'C'; seccion++) {
            ocupacionPorSeccion.put(seccion, 0);
        }
    }

    /**
     * Constructor para tests (permite inyectar semilla para aleatoriedad predecible).
     * @param codigo Código del vuelo
     * @param origen Ciudad de origen
     * @param destino Ciudad de destino
     * @param seed Semilla para Random (para tests)
     */
    public Vuelo(String codigo, String origen, String destino, long seed) {
        this(codigo, origen, destino);
        this.random.setSeed(seed);
    }

    /**
     * Asigna un asiento aleatorio respetando la regla de balance:
     * - La diferencia de ocupación entre secciones no debe superar 1
     * - Si hay empate en la sección menos cargada, se elige una al azar
     * - Dentro de la sección elegida, se asigna un asiento aleatorio
     * 
     * @return String con el asiento asignado (ej: "A5") o null si el vuelo está lleno
     */
    public String asignarAsientoAleatorioBalanceado() {
        // Verificar si el vuelo está lleno
        if (asientosOcupados.size() >= TOTAL_ASIENTOS) {
            return null;
        }

        // PASO 1: Encontrar la ocupación mínima
        int minOcupacion = Collections.min(ocupacionPorSeccion.values());

        // PASO 2: Encontrar todas las secciones con ocupación mínima (para desempate aleatorio)
        List<Character> seccionesMenosCargadas = new ArrayList<>();
        for (Map.Entry<Character, Integer> entry : ocupacionPorSeccion.entrySet()) {
            if (entry.getValue() == minOcupacion) {
                seccionesMenosCargadas.add(entry.getKey());
            }
        }

        // PASO 3: Elegir una sección al azar entre las menos cargadas (desempate aleatorio)
        char seccionElegida = seccionesMenosCargadas.get(random.nextInt(seccionesMenosCargadas.size()));

        // PASO 4: Obtener todos los asientos libres en la sección elegida
        List<String> asientosLibresEnSeccion = new ArrayList<>();
        for (int num = 1; num <= ASIENTOS_POR_SECCION; num++) {
            String asiento = seccionElegida + String.valueOf(num);
            if (!asientosOcupados.contains(asiento)) {
                asientosLibresEnSeccion.add(asiento);
            }
        }

        // PASO 5: Elegir un asiento aleatorio de los disponibles en la sección
        if (asientosLibresEnSeccion.isEmpty()) {
            // No debería ocurrir, pero por seguridad
            return null;
        }

        String asientoAsignado = asientosLibresEnSeccion.get(random.nextInt(asientosLibresEnSeccion.size()));

        // PASO 6: Actualizar contadores y registros
        asientosOcupados.add(asientoAsignado);
        ocupacionPorSeccion.put(seccionElegida, ocupacionPorSeccion.get(seccionElegida) + 1);

        return asientoAsignado;
    }

    /**
     * Calcula el porcentaje de ocupación del vuelo.
     * @return Porcentaje de ocupación (0-100)
     */
    public double calcularOcupacionPorcentual() {
        return (asientosOcupados.size() / (double) TOTAL_ASIENTOS) * 100.0;
    }

    /**
     * Verifica si el vuelo tiene alta ocupación (≥95%) para aplicar recargo.
     * @return true si la ocupación es >= 95%
     */
    public boolean tieneAltaOcupacion() {
        return calcularOcupacionPorcentual() >= 95.0;
    }

    // Getters
    public AVL getReservas() {
        return reservas;
    }

    public Map<Character, Integer> getOcupacionPorSeccion() {
        return ocupacionPorSeccion;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public int getTotalAsientosOcupados() {
        return asientosOcupados.size();
    }

    public int getTotalAsientos() {
        return TOTAL_ASIENTOS;
    }

    /**
     * Obtiene la ocupación por sección en formato String (para compatibilidad).
     * @return Mapa con claves String
     */
    public Map<String, Integer> getOcupacionPorSeccionString() {
        Map<String, Integer> result = new HashMap<>();
        for (Map.Entry<Character, Integer> entry : ocupacionPorSeccion.entrySet()) {
            result.put(String.valueOf(entry.getKey()), entry.getValue());
        }
        return result;
    }
}
