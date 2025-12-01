# � GUÍA DE EJECUCIÓN Y PRUEBAS

## Cómo ejecutar el programa

```bash
# Compilar
mvn clean compile

# Ejecutar tests
mvn test

# Ejecutar aplicación
./run.sh
# o
mvn exec:java -Dexec.mainClass="com.aerolineaspc21.AerolineasApp"
```

**Resultado esperado:** 9/9 tests pasando ✅

---

## 📊 Datos Pre-cargados

El sistema incluye datos de ejemplo automáticamente:
- 7 ciudades con 11 conexiones bidireccionales
- Cada vuelo tiene 30 asientos (A1-A10, B1-B10, C1-C10)

**Ver datos completos:** Opción 9 del menú

---

## 🧪 Casos de Prueba

### 1. Dijkstra con Trasbordo
**Opción 2:** Origen `Buenos Aires` → Destino `Santa Cruz`  
**Verifica:** Ruta óptima con trasbordo, tiempo y precio total

### 2. Árbol AVL
**Opción 6** (crear datos) → **Opción 4** (código `AR101`)  
**Verifica:** Recorrido InOrder, ocupación por sección

### 3. Balance de Asientos
**Opción 8** (automático)  
**Verifica:** Diferencia entre secciones ≤ 1, uso de `Random`

### 4. BFS y DFS
**Opción 5:** Origen `Buenos Aires`  
**Verifica:** Ciudades alcanzables, orden diferente

### 5. Cálculo de Precios
**Opción 3:** Origen `Buenos Aires` → Destino `Córdoba`  
**Verifica:** Recargo +20% vuelo directo

### 6. Demo Completa
**Opción 6** (automático)  
**Verifica:** Integración de todos los componentes

---

## 📊 Dónde Ver Cada Estructura

| Opción | Estructura | Datos |
|--------|-----------|-------|
| **9** | Grafo completo | Red pre-cargada |
| **7** | HashMap | Vuelos activos |
| **4** | AVL InOrder | Reservas ordenadas |
| **2** | Dijkstra + PriorityQueue | Ruta óptima |
| **5** | BFS (Queue) + DFS | Recorridos |
| **8** | Random + Balance | Asientos balanceados |
