# 🛫 Aerolíneas PC21 - Sistema de Reservas

**Autor:** Giuliano Zulatto  
**Asignatura:** Programación y Concurrencia II  
**Fecha:** Diciembre 2025

---

## 📋 Descripción

Sistema de gestión de vuelos y reservas con:

- **Grafo** (Lista de Adyacencia): Dijkstra, BFS, DFS
- **AVL**: Gestión de reservas con O(log n)
- **Random**: Asignación balanceada de asientos (diferencia ≤ 1)
- **Cálculo de Precios**: Recargos +20% directo, +10% ocupación ≥95%

## 🚀 Ejecución

```bash
# Opción 1: Script
./run.sh

# Opción 2: Maven
mvn clean compile
mvn exec:java -Dexec.mainClass="com.aerolineaspc21.AerolineasApp"
```

## 🧪 Tests

```bash
mvn test  # 9/9 tests pasando ✅
```

**Tests implementados:**
- `AVLTest` - Rotaciones, búsqueda, eliminación
- `GrafoTest` - Dijkstra, rutas óptimas
- `GrafoRecorridosTest` - BFS, DFS
- `CalculadoraPrecioTest` - Recargos

**Metodología TDD:** Tests escritos antes de la implementación.

**Nota:** Los tests de balance están como demo interactiva (Opción 8) por la naturaleza aleatoria del algoritmo.

## 📊 Datos Pre-cargados

**7 ciudades:** Buenos Aires, Córdoba, Mendoza, Bariloche, Santa Cruz, Santa Fe, Posadas

**11 conexiones bidireccionales** con tiempos y precios.

**Ver datos completos:** Opción 9 del menú

## 🎯 Demo Rápida

Ejecuta en este orden:

1. **Opción 6** - Demo automática (crea vuelos y reservas)
2. **Opción 8** - Test de balance
3. **Opción 2** - Ruta Buenos Aires → Santa Cruz (demuestra Dijkstra)
4. **Opción 4** - Ocupación del vuelo AR101 (demuestra AVL)

## 📁 Casos de Prueba

### 1. Dijkstra con Trasbordo
**Opción 2:** Buenos Aires → Santa Cruz  
**Verifica:** Ruta óptima (no hay conexión directa)

### 2. Balance de Asientos
**Opción 8** (automático)  
**Verifica:** Diferencia entre secciones ≤ 1, uso de `Random`

### 3. AVL InOrder
**Opción 6** → **Opción 4** (código: AR101)  
**Verifica:** Reservas ordenadas

### 4. BFS y DFS
**Opción 5:** Origen Buenos Aires  
**Verifica:** Ciudades alcanzables, orden diferente

### 5. Recargos de Precio
**Opción 3:** Buenos Aires → Córdoba  
**Verifica:** +20% vuelo directo

## 🏗️ Estructura del Código

```
src/main/java/com/aerolineaspc21/
├── AerolineasApp.java          # Menú principal
└── modelo/
    ├── Grafo.java              # Dijkstra, BFS, DFS
    ├── Vuelo.java              # Asignación aleatoria
    ├── AVL.java                # Árbol balanceado
    └── CalculadoraPrecio.java  # Lógica de precios
```

## 📜 Funcionalidades del Menú

| Opción | Funcionalidad | Algoritmo/Estructura |
|--------|---------------|---------------------|
| 1 | Alta de vuelo | HashMap |
| 2 | Consulta ruta mínima | Dijkstra + PriorityQueue |
| 3 | Reserva de pasaje | Random + AVL |
| 4 | Consulta ocupación | AVL InOrder |
| 5 | Recorridos de red | BFS (Queue) + DFS |
| 6 | Demo automática | Integración completa |
| 7 | Listar vuelos | HashMap |
| 8 | Test de balance | Random + balance ≤ 1 |
| 9 | Ver datos ejemplo | Grafo completo |

## 🔧 Algoritmos Implementados

- **Dijkstra:** O((V+E) log V) - Ruta mínima con doble criterio (tiempo, precio)
- **BFS:** O(V+E) - Recorrido por niveles con Queue
- **DFS:** O(V+E) - Recorrido recursivo
- **AVL:** O(log n) - Inserción, búsqueda, eliminación con rotaciones
- **Balance:** O(1) - Asignación aleatoria manteniendo diferencia ≤ 1

## 📝 Documentación Adicional

- `GUIA_EVALUACION.md` - Casos de prueba detallados
- `MEJORAS_IMPLEMENTADAS.md` - Detalles técnicos
- `CHECKLIST_CUMPLIMIENTO.md` - Verificación de requisitos
