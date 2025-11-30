package com.aerolineaspc21.modelo;

import com.aerolineaspc21.modelo.Reserva;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class AVLTest {
    @Test
    public void testRotacionLL() {
        AVL arbol = new AVL();
    arbol.insertar(new Reserva("3", "A", "V1"));
    arbol.insertar(new Reserva("2", "B", "V2"));
    arbol.insertar(new Reserva("1", "C", "V3"));
    List<Reserva> orden = arbol.inOrder();
    assertEquals("1", orden.get(0).getId());
    assertEquals("2", orden.get(1).getId());
    assertEquals("3", orden.get(2).getId());
    }

    @Test
    public void testRotacionLR() {
        AVL arbol = new AVL();
    arbol.insertar(new Reserva("3", "A", "V1"));
    arbol.insertar(new Reserva("1", "B", "V2"));
    arbol.insertar(new Reserva("2", "C", "V3"));
    List<Reserva> orden = arbol.inOrder();
    assertEquals("1", orden.get(0).getId());
    assertEquals("2", orden.get(1).getId());
    assertEquals("3", orden.get(2).getId());
    }

    @Test
    public void testBuscarYEliminar() {
        AVL arbol = new AVL();
    arbol.insertar(new Reserva("2", "A", "V1"));
    arbol.insertar(new Reserva("1", "B", "V2"));
    arbol.insertar(new Reserva("3", "C", "V3"));
    assertNotNull(arbol.buscar("2"));
    arbol.eliminar("2");
    assertNull(arbol.buscar("2"));
    }
}
