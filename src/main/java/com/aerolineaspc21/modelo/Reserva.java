package com.aerolineaspc21.modelo;

/**
 * Representa una reserva de asiento en un vuelo.
 * Incluye información detallada del asiento (sección y número) y código único de reserva.
 * 
 * @author Giuliano Zulatto
 * @version 1.0
 * @since 2025-12-01
 */
public class Reserva {
    private String codigoReserva;  // Código único de la reserva (clave para AVL)
    private String asientoCompleto; // Ej: "A5", "B10", "C3"
    private char seccion;           // Sección: 'A', 'B' o 'C'
    private int numeroAsiento;      // Número de asiento: 1-10
    private String origen;          // Ciudad de origen del tramo
    private String destino;         // Ciudad de destino del tramo
    private String codigoVuelo;     // Código del vuelo

    private static int contadorReservas = 1000; // Contador para generar códigos únicos

    /**
     * Constructor que genera automáticamente el código de reserva.
     * @param asientoCompleto Asiento completo (ej: "A5")
     * @param origen Ciudad de origen
     * @param destino Ciudad de destino
     * @param codigoVuelo Código del vuelo
     */
    public Reserva(String asientoCompleto, String origen, String destino, String codigoVuelo) {
        this.asientoCompleto = asientoCompleto;
        this.seccion = asientoCompleto.charAt(0);
        this.numeroAsiento = Integer.parseInt(asientoCompleto.substring(1));
        this.origen = origen;
        this.destino = destino;
        this.codigoVuelo = codigoVuelo;
        this.codigoReserva = generarCodigoReserva();
    }

    /**
     * Genera un código único de reserva.
     * @return Código en formato "RES-XXXX"
     */
    private String generarCodigoReserva() {
        return "RES-" + String.format("%04d", contadorReservas++);
    }

    // Getters
    public String getCodigoReserva() {
        return codigoReserva;
    }

    public String getId() {
        return codigoReserva; // Para compatibilidad con AVL
    }

    public String getAsientoCompleto() {
        return asientoCompleto;
    }

    public char getSeccion() {
        return seccion;
    }

    public int getNumeroAsiento() {
        return numeroAsiento;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public String getCodigoVuelo() {
        return codigoVuelo;
    }

    @Override
    public String toString() {
        return String.format("Reserva %s | Vuelo: %s | %s → %s | Asiento: %s", 
                           codigoReserva, codigoVuelo, origen, destino, asientoCompleto);
    }
}
