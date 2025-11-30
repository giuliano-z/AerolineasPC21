package com.aerolineaspc21.modelo;

import java.util.*;

public class Vuelo {
    private String codigo;
    private String origen;
    private String destino;
    private AVL reservas;
    private Map<String, Integer> ocupacionPorSeccion;
    private List<String> asientos;

    public Vuelo(String codigo, String origen, String destino) {
        this.codigo = codigo;
        this.origen = origen;
        this.destino = destino;
        this.reservas = new AVL();
        this.ocupacionPorSeccion = new HashMap<>();
        this.asientos = new ArrayList<>();
        // Simulación: 3 secciones (A, B, C), 10 asientos cada una
        for (char seccion = 'A'; seccion <= 'C'; seccion++) {
            for (int num = 1; num <= 10; num++) {
                String asiento = seccion + String.valueOf(num);
                asientos.add(asiento);
                ocupacionPorSeccion.put(seccion + "", 0);
            }
        }
    }

    public String asignarAsientoAleatorioBalanceado() {
        // Busca la sección con menor ocupación
        String seccionMenor = "A";
        int minOcupacion = 10;
        for (String seccion : ocupacionPorSeccion.keySet()) {
            int ocupacion = ocupacionPorSeccion.get(seccion);
            if (ocupacion < minOcupacion) {
                minOcupacion = ocupacion;
                seccionMenor = seccion;
            }
        }
        // Busca un asiento libre en esa sección
        for (String asiento : asientos) {
            if (asiento.startsWith(seccionMenor) && reservas.buscarPorAsiento(asiento) == null) {
                ocupacionPorSeccion.put(seccionMenor, ocupacionPorSeccion.get(seccionMenor) + 1);
                return asiento;
            }
        }
        return null; // Vuelo lleno
    }

    public AVL getReservas() {
        return reservas;
    }

    public Map<String, Integer> getOcupacionPorSeccion() {
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
}
