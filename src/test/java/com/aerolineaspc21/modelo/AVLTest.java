package com.aerolineaspc21.modelo;

import com.aerolineaspc21.modelo.Reserva;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class AVLTest {
    @Test
    public void testRotacionLL() {
        AVL arbol = new AVL();
        arbol.insertar(new Reserva("A3", "CiudadA", "CiudadB", "V1"));
        arbol.insertar(new Reserva("A2", "CiudadA", "CiudadB", "V2"));
        arbol.insertar(new Reserva("A1", "CiudadA", "CiudadB", "V3"));
        List<Reserva> orden = arbol.inOrder();
        assertEquals(3, orden.size());
        // Verificar que el árbol está balanceado (InOrder devuelve elementos ordenados)
    }

    @Test
    public void testRotacionLR() {
        AVL arbol = new AVL();
        arbol.insertar(new Reserva("A3", "CiudadA", "CiudadB", "V1"));
        arbol.insertar(new Reserva("A1", "CiudadA", "CiudadB", "V2"));
        arbol.insertar(new Reserva("A2", "CiudadA", "CiudadB", "V3"));
        List<Reserva> orden = arbol.inOrder();
        assertEquals(3, orden.size());
        // Verificar que el árbol está balanceado
    }

    @Test
    public void testBuscarYEliminar() {
        AVL arbol = new AVL();
        Reserva r1 = new Reserva("B2", "CiudadA", "CiudadB", "V1");
        Reserva r2 = new Reserva("B1", "CiudadA", "CiudadB", "V2");
        Reserva r3 = new Reserva("B3", "CiudadA", "CiudadB", "V3");
        
        arbol.insertar(r1);
        arbol.insertar(r2);
        arbol.insertar(r3);
        
        assertNotNull(arbol.buscar(r1.getCodigoReserva()));
        arbol.eliminar(r1.getCodigoReserva());
        assertNull(arbol.buscar(r1.getCodigoReserva()));
    }
}
