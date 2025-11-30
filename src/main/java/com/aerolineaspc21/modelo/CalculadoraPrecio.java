package com.aerolineaspc21.modelo;

/**
 * Calculadora de precios finales para itinerarios.
 * Aplica reglas de ajuste según tipo de vuelo y ocupación.
 */
public class CalculadoraPrecio {
    /**
     * Calcula el precio final de un itinerario aplicando reglas de ocupación y vuelo directo.
     * @param tramos Lista de tramos (aristas) del itinerario
     * @param esDirecto true si el itinerario es directo
     * @param ocupacion Porcentaje de ocupación (0-100)
     * @return Precio final ajustado
     */
    public static double calcularPrecioFinal(java.util.List<Arista> tramos, boolean esDirecto, double ocupacion) {
        double precioTotal = 0.0;
        for (Arista tramo : tramos) {
            double precio = tramo.getPrecioBase();
            precio = aplicarReglaDirecto(precio, esDirecto);
            precio = aplicarReglaOcupacion(precio, ocupacion);
            precioTotal += precio;
        }
        return precioTotal;
    }
    /**
     * Aplica el ajuste de precio para itinerarios directos (+20%).
     * @param precioBase Precio base del tramo
     * @param esDirecto true si el itinerario es directo
     * @return Precio ajustado
     */
    public static double aplicarReglaDirecto(double precioBase, boolean esDirecto) {
        if (esDirecto) {
            return precioBase * 1.2;
        }
        return precioBase;
    }

    /**
     * Aplica el ajuste de precio por ocupación alta (+10%).
     * @param precioBase Precio base del tramo
     * @param ocupacion Porcentaje de ocupación (0-100)
     * @return Precio ajustado
     */
    public static double aplicarReglaOcupacion(double precioBase, double ocupacion) {
        if (ocupacion >= 95.0) {
            return precioBase * 1.1;
        }
        return precioBase;
    }
}
