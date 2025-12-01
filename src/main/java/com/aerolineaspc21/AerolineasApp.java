package com.aerolineaspc21;
import com.aerolineaspc21.modelo.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;

/**
 * Aplicación principal de Aerolíneas PC21.
 * Sistema de gestión de vuelos y reservas con algoritmos avanzados:
 * - Grafo para red de vuelos (Dijkstra, BFS, DFS)
 * - AVL para gestión de reservas
 * - Asignación aleatoria balanceada de asientos
 * 
 * @author Giuliano Zulatto
 * @version 1.0
 * @since 2025-12-01
 */
public class AerolineasApp {
    private Grafo redVuelos;
    private Map<String, Vuelo> vuelosActivos; // Almacena vuelos por su código

    public AerolineasApp() {
        this.redVuelos = new Grafo();
        this.redVuelos.cargarDatosIniciales(); // Carga las 7 ciudades y las aristas
        this.vuelosActivos = new HashMap<>();
    }

    public static void main(String[] args) {
        AerolineasApp app = new AerolineasApp();
        app.mostrarMenu();
    }

    private void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            imprimirSeparador();
            System.out.println("╔════════════════════════════════════════════════════════════╗");
            System.out.println("║         🛫 AEROLÍNEAS PC21 - SISTEMA DE RESERVAS 🛬        ║");
            System.out.println("╚════════════════════════════════════════════════════════════╝");
            System.out.println();
            System.out.println("📋 FUNCIONALIDADES PRINCIPALES:");
            System.out.println("  1. 🆕 Alta de Vuelo (Crear vuelo directo)");
            System.out.println("  2. 🗺️  Consulta de Ruta Mínima (Algoritmo Dijkstra)");
            System.out.println("  3. 🎫 Reserva de Pasaje (Con asignación aleatoria balanceada)");
            System.out.println("  4. 📊 Consulta de Ocupación por Vuelo (AVL InOrder)");
            System.out.println();
            System.out.println("🔍 HERRAMIENTAS DE DEMOSTRACIÓN:");
            System.out.println("  5. 🌐 Recorridos de Red (BFS/DFS)");
            System.out.println("  6. 🎬 Demo Automática Completa");
            System.out.println("  7. 📋 Listar Vuelos Activos");
            System.out.println("  8. 🧪 Test de Balance de Asientos");
            System.out.println("  9. 📊 Ver Datos de Ejemplo Pre-cargados");
            System.out.println();
            System.out.println("  0. 🚪 Salir");
            imprimirSeparador();
            System.out.print("👉 Seleccione una opción: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1: altaVuelo(scanner); break;
                    case 2: consultarRutaMinima(scanner); break;
                    case 3: reservarPasaje(scanner); break;
                    case 4: consultarOcupacion(scanner); break;
                    case 5: ejecutarRecorridos(scanner); break;
                    case 6: ejecutarDemoAutomatica(); break;
                    case 7: listarVuelosActivos(); break;
                    case 8: testBalanceAsientos(); break;
                    case 9: verDatosEjemplo(); break;
                    case 0: 
                        System.out.println("\n✈️  Gracias por usar Aerolíneas PC21. ¡Buen viaje! ✈️");
                        break;
                    default: 
                        System.out.println("❌ Opción no válida. Intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    private void imprimirSeparador() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    // --- Opción 1: Alta de Vuelo ---
    private void altaVuelo(Scanner scanner) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║   🆕 ALTA DE VUELO DIRECTO           ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.print("📝 Código de Vuelo (ej: AR101): ");
        String codigo = scanner.nextLine().toUpperCase();
        
        if (vuelosActivos.containsKey(codigo)) {
            System.out.println("⚠️  El vuelo " + codigo + " ya existe.");
            return;
        }
        
        System.out.print("🏙️  Ciudad Origen: ");
        String origen = scanner.nextLine();
        System.out.print("🏙️  Ciudad Destino: ");
        String destino = scanner.nextLine();
        
        Vuelo nuevoVuelo = new Vuelo(codigo, origen, destino); 
        vuelosActivos.put(codigo, nuevoVuelo);
        System.out.println("✅ Vuelo " + codigo + " creado exitosamente.");
        System.out.println("   📍 Ruta: " + origen + " → " + destino);
    }

    // --- Opción 2: Consulta de Ruta Mínima (Dijkstra) ---
    private void consultarRutaMinima(Scanner scanner) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║   🗺️  CONSULTA DE RUTA MÍNIMA       ║");
        System.out.println("║   (Algoritmo: Dijkstra)              ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.print("🏙️  Ciudad Origen: ");
        String origen = scanner.nextLine();
        System.out.print("🏙️  Ciudad Destino: ");
        String destino = scanner.nextLine();

        // Llama al método Dijkstra, que optimiza tiempo y desempata por precio
        List<String> ruta = redVuelos.calcularRutaMinima(origen, destino);
        
        if (ruta == null || ruta.size() < 2) {
            System.out.println("❌ No se encontró una ruta válida de " + origen + " a " + destino + ".");
            return;
        }

        double tiempoTotal = 0.0;
        double precioBaseTotal = 0.0;
        
        System.out.println("\n✅ RUTA ÓPTIMA ENCONTRADA:");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        for (int i = 0; i < ruta.size() - 1; i++) {
            Vertice vOrigen = redVuelos.getVertice(ruta.get(i));
            String nombreDestino = ruta.get(i + 1);
            
            // Buscar la arista para sumar tiempos y precios
            for (Arista arista : vOrigen.getAdyacentes()) {
                if (arista.getDestino().getNombre().equals(nombreDestino)) {
                    tiempoTotal += arista.getTiempo();
                    precioBaseTotal += arista.getPrecioBase();
                    System.out.printf("  Tramo %d: %s → %s (%.1fh, ARS %.2f)\n", 
                                    i + 1, ruta.get(i), nombreDestino, 
                                    arista.getTiempo(), arista.getPrecioBase());
                    break;
                }
            }
        }

        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("📍 Ruta Completa: " + String.join(" → ", ruta));
        System.out.printf("⏱️  Tiempo Total: %.2f horas\n", tiempoTotal);
        System.out.printf("💰 Precio Base Total: ARS %.2f\n", precioBaseTotal);
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    // --- Opción 3: Reserva de Pasaje ---
    private void reservarPasaje(Scanner scanner) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║   🎫 RESERVA DE PASAJE               ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.print("🏙️  Ciudad Origen: ");
        String origen = scanner.nextLine();
        System.out.print("🏙️  Ciudad Destino: ");
        String destino = scanner.nextLine();

        List<String> ruta = redVuelos.calcularRutaMinima(origen, destino);

        if (ruta == null || ruta.size() < 2) {
            System.out.println("❌ Ruta no encontrada.");
            return;
        }
        
        boolean esDirecto = (ruta.size() == 2);
        double precioFinal = 0.0;
        List<String> detallesReservas = new ArrayList<>();
        List<Arista> tramosRuta = new ArrayList<>();

        System.out.println("\n🔄 Procesando reserva...");
        
        // 1. Procesar tramos y asignar reservas (AVL)
        for (int i = 0; i < ruta.size() - 1; i++) {
            String ciudadOrigen = ruta.get(i);
            String ciudadDestino = ruta.get(i + 1);
            String codigoVueloTramo = "V" + ciudadOrigen.substring(0, 3).toUpperCase() + 
                                      ciudadDestino.substring(0, 3).toUpperCase();
            
            Vuelo vueloTramo = vuelosActivos.get(codigoVueloTramo);
            if (vueloTramo == null) {
                vueloTramo = new Vuelo(codigoVueloTramo, ciudadOrigen, ciudadDestino);
                vuelosActivos.put(codigoVueloTramo, vueloTramo);
            }
            
            // Obtener Arista para precio base
            Vertice vOrigen = redVuelos.getVertice(ciudadOrigen);
            Arista tramo = vOrigen.getAdyacentes().stream()
                .filter(a -> a.getDestino().getNombre().equals(ciudadDestino))
                .findFirst().orElse(null);

            if (tramo == null) continue;
            tramosRuta.add(tramo);

            // Asignar asiento aleatorio balanceado (Lógica en Vuelo con Random)
            String asiento = vueloTramo.asignarAsientoAleatorioBalanceado(); 
            
            if (asiento != null) {
                Reserva nuevaReserva = new Reserva(asiento, ciudadOrigen, ciudadDestino, codigoVueloTramo);
                vueloTramo.getReservas().insertar(nuevaReserva); 
                
                detallesReservas.add(String.format("  ✓ Vuelo %s: %s → %s | Asiento: %s | Código: %s", 
                                                codigoVueloTramo, ciudadOrigen, ciudadDestino, 
                                                asiento, nuevaReserva.getCodigoReserva()));
            } else {
                System.out.println("❌ ERROR: Vuelo " + codigoVueloTramo + " está lleno. Reserva fallida.");
                return;
            }
        }
        
        // 2. Cálculo del Precio Final usando ocupación real
        double ocupacionPromedio = 0.0;
        for (int i = 0; i < ruta.size() - 1; i++) {
            String codigoVueloTramo = "V" + ruta.get(i).substring(0, 3).toUpperCase() + 
                                      ruta.get(i + 1).substring(0, 3).toUpperCase();
            Vuelo vuelo = vuelosActivos.get(codigoVueloTramo);
            if (vuelo != null) {
                ocupacionPromedio += vuelo.calcularOcupacionPorcentual();
            }
        }
        ocupacionPromedio /= (ruta.size() - 1);
        
        precioFinal = CalculadoraPrecio.calcularPrecioFinal(tramosRuta, esDirecto, ocupacionPromedio);
        
        // Imprimir Comprobante
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║           🎫 COMPROBANTE DE RESERVA                      ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        System.out.println("📍 Itinerario: " + String.join(" → ", ruta));
        System.out.println("🎫 Tipo de vuelo: " + (esDirecto ? "DIRECTO (+20%)" : "CON TRASBORDO"));
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("📋 DETALLES DE RESERVAS:");
        detallesReservas.forEach(System.out::println);
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.printf("💰 PRECIO FINAL TOTAL (con recargos aplicados): ARS %.2f\n", precioFinal);
        System.out.printf("📊 Ocupación promedio de vuelos: %.1f%%\n", ocupacionPromedio);
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
    }

    // --- Opción 4: Consulta de Ocupación (AVL InOrder) ---
    private void consultarOcupacion(Scanner scanner) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║   📊 CONSULTA DE OCUPACIÓN           ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.print("📝 Código de Vuelo a consultar: ");
        String codigo = scanner.nextLine().toUpperCase();
        
        Vuelo vuelo = vuelosActivos.get(codigo);
        if (vuelo == null) {
            System.out.println("❌ Vuelo no encontrado.");
            return;
        }
        
        mostrarDetallesOcupacion(vuelo);
    }

    private void mostrarDetallesOcupacion(Vuelo vuelo) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║         📊 OCUPACIÓN VUELO " + vuelo.getCodigo() + "                      ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        
        int totalAsientos = vuelo.getTotalAsientos();
        int ocupados = vuelo.getTotalAsientosOcupados();
        double porcentaje = vuelo.calcularOcupacionPorcentual();
        
        System.out.printf("📈 Ocupación Global: %d/%d asientos (%.2f%%)\n", ocupados, totalAsientos, porcentaje);
        
        if (porcentaje >= 95.0) {
            System.out.println("⚠️  ALTA OCUPACIÓN: Se aplicará recargo del +10%");
        }
        
        System.out.println("\n📊 Ocupación por Sección:");
        Map<Character, Integer> ocupacionSecciones = vuelo.getOcupacionPorSeccion();
        for (char seccion = 'A'; seccion <= 'C'; seccion++) {
            int ocupadosSeccion = ocupacionSecciones.get(seccion);
            double porcentajeSeccion = (ocupadosSeccion / 10.0) * 100.0;
            System.out.printf("  Sección %c: %d/10 asientos (%.0f%%) %s\n", 
                            seccion, ocupadosSeccion, porcentajeSeccion, 
                            generarBarraProgreso(ocupadosSeccion, 10));
        }
        
        System.out.println("\n📋 Listado de Reservas (Recorrido InOrder del AVL):");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        List<Reserva> reservas = vuelo.getReservas().inOrder();
        if (reservas.isEmpty()) {
            System.out.println("  (Sin reservas)");
        } else {
            for (Reserva r : reservas) {
                System.out.println("  " + r.toString());
            }
        }
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    private String generarBarraProgreso(int ocupados, int total) {
        int barras = (ocupados * 10) / total;
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < 10; i++) {
            sb.append(i < barras ? "█" : "░");
        }
        sb.append("]");
        return sb.toString();
    }

    // --- Opción 5: Ejecución de Recorridos (BFS/DFS) ---
    private void ejecutarRecorridos(Scanner scanner) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║   🌐 RECORRIDOS DE RED               ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.print("🏙️  Ciudad Origen para recorridos: ");
        String origen = scanner.nextLine();
        
        if (redVuelos.getVertice(origen) == null) {
            System.out.println("❌ Ciudad de origen no válida.");
            return;
        }

        // BFS/DFS para listar nodos alcanzables
        List<String> bfsResultado = redVuelos.recorridoBFS(origen);
        System.out.println("\n🔵 Recorrido BFS (Búsqueda en Anchura) desde " + origen + ":");
        System.out.println("   " + String.join(" → ", bfsResultado));

        List<String> dfsResultado = redVuelos.recorridoDFS(origen);
        System.out.println("\n🔴 Recorrido DFS (Búsqueda en Profundidad) desde " + origen + ":");
        System.out.println("   " + String.join(" → ", dfsResultado));
    }

    // --- Opción 6: Demo Automática Completa ---
    private void ejecutarDemoAutomatica() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║           🎬 DEMO AUTOMÁTICA COMPLETA                    ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        System.out.println("Esta demo creará vuelos y reservas automáticamente...\n");
        
        // Crear vuelos directos
        System.out.println("📝 Creando vuelos directos...");
        Vuelo vuelo1 = new Vuelo("AR101", "Buenos Aires", "Córdoba");
        Vuelo vuelo2 = new Vuelo("AR102", "Buenos Aires", "Bariloche");
        Vuelo vuelo3 = new Vuelo("AR103", "Buenos Aires", "Mendoza");
        Vuelo vuelo4 = new Vuelo("AR104", "Buenos Aires", "Santa Fe");
        Vuelo vuelo5 = new Vuelo("AR105", "Buenos Aires", "Posadas");
        vuelosActivos.put("AR101", vuelo1);
        vuelosActivos.put("AR102", vuelo2);
        vuelosActivos.put("AR103", vuelo3);
        vuelosActivos.put("AR104", vuelo4);
        vuelosActivos.put("AR105", vuelo5);
        System.out.println("✅ 5 vuelos creados exitosamente.\n");

        // Reservar pasajes en vuelos directos
        System.out.println("🎫 Generando reservas aleatorias...");
        for (int i = 0; i < 5; i++) {
            String asiento1 = vuelo1.asignarAsientoAleatorioBalanceado();
            if (asiento1 != null) {
                vuelo1.getReservas().insertar(new Reserva(asiento1, "Buenos Aires", "Córdoba", "AR101"));
            }
            String asiento2 = vuelo2.asignarAsientoAleatorioBalanceado();
            if (asiento2 != null) {
                vuelo2.getReservas().insertar(new Reserva(asiento2, "Buenos Aires", "Bariloche", "AR102"));
            }
        }
        System.out.println("✅ 10 reservas generadas.\n");

        // Reservar pasajes en vuelos con trasbordo
        System.out.println("🔄 Creando ruta con trasbordo (Buenos Aires → Bariloche → Santa Cruz)...");
        Vuelo vueloTramo1 = vuelosActivos.get("AR102");
        Vuelo vueloTramo2 = new Vuelo("AR201", "Bariloche", "Santa Cruz");
        vuelosActivos.put("AR201", vueloTramo2);
        
        String asientoTramo1 = vueloTramo1.asignarAsientoAleatorioBalanceado();
        if (asientoTramo1 != null) {
            vueloTramo1.getReservas().insertar(new Reserva(asientoTramo1, "Buenos Aires", "Bariloche", "AR102"));
        }
        String asientoTramo2 = vueloTramo2.asignarAsientoAleatorioBalanceado();
        if (asientoTramo2 != null) {
            vueloTramo2.getReservas().insertar(new Reserva(asientoTramo2, "Bariloche", "Santa Cruz", "AR201"));
        }
        System.out.println("✅ Ruta con trasbordo creada.\n");

        // Mostrar ocupación de los vuelos creados
        System.out.println("📊 RESUMEN DE OCUPACIÓN:");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        mostrarDetallesOcupacion(vuelo1);
        mostrarDetallesOcupacion(vuelo2);
        mostrarDetallesOcupacion(vueloTramo2);
        
        System.out.println("\n✅ Demo completada. Use la opción 7 para ver todos los vuelos activos.");
    }

    // --- Opción 7: Listar Vuelos Activos ---
    private void listarVuelosActivos() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║           📋 VUELOS ACTIVOS EN EL SISTEMA               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        
        if (vuelosActivos.isEmpty()) {
            System.out.println("❌ No hay vuelos activos en el sistema.");
            System.out.println("💡 Sugerencia: Use la opción 1 para crear vuelos o la opción 6 para ejecutar la demo.");
            return;
        }
        
        System.out.println("\nTotal de vuelos: " + vuelosActivos.size());
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        for (Vuelo vuelo : vuelosActivos.values()) {
            int ocupados = vuelo.getTotalAsientosOcupados();
            int total = vuelo.getTotalAsientos();
            double porcentaje = vuelo.calcularOcupacionPorcentual();
            
            System.out.printf("✈️  %s | %s → %s | Ocupación: %d/%d (%.1f%%) %s\n",
                            vuelo.getCodigo(),
                            vuelo.getOrigen(),
                            vuelo.getDestino(),
                            ocupados,
                            total,
                            porcentaje,
                            generarBarraProgreso(ocupados, total));
        }
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    // --- Opción 8: Test de Balance de Asientos ---
    private void testBalanceAsientos() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║         🧪 TEST DE BALANCE DE ASIENTOS                   ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        System.out.println("Este test demuestra la asignación aleatoria balanceada.\n");
        
        // Crear un vuelo de prueba
        Vuelo vueloTest = new Vuelo("TEST01", "Test Origen", "Test Destino");
        
        System.out.println("📝 Asignando 15 asientos aleatorios...");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        for (int i = 1; i <= 15; i++) {
            String asiento = vueloTest.asignarAsientoAleatorioBalanceado();
            if (asiento != null) {
                Reserva reserva = new Reserva(asiento, "Test Origen", "Test Destino", "TEST01");
                vueloTest.getReservas().insertar(reserva);
                
                Map<Character, Integer> ocupacion = vueloTest.getOcupacionPorSeccion();
                System.out.printf("  Reserva #%02d: Asiento %s asignado | Balance: A=%d, B=%d, C=%d\n",
                                i, asiento, ocupacion.get('A'), ocupacion.get('B'), ocupacion.get('C'));
            }
        }
        
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("\n✅ VERIFICACIÓN DE BALANCE:");
        Map<Character, Integer> ocupacionFinal = vueloTest.getOcupacionPorSeccion();
        int maxDiferencia = 0;
        
        for (char s1 = 'A'; s1 <= 'C'; s1++) {
            for (char s2 = (char)(s1 + 1); s2 <= 'C'; s2++) {
                int diferencia = Math.abs(ocupacionFinal.get(s1) - ocupacionFinal.get(s2));
                maxDiferencia = Math.max(maxDiferencia, diferencia);
            }
        }
        
        System.out.printf("   Sección A: %d asientos\n", ocupacionFinal.get('A'));
        System.out.printf("   Sección B: %d asientos\n", ocupacionFinal.get('B'));
        System.out.printf("   Sección C: %d asientos\n", ocupacionFinal.get('C'));
        System.out.printf("   Máxima diferencia entre secciones: %d\n", maxDiferencia);
        
        if (maxDiferencia <= 1) {
            System.out.println("   ✅ BALANCE CORRECTO: La diferencia no supera 1 asiento.");
        } else {
            System.out.println("   ❌ BALANCE INCORRECTO: La diferencia supera 1 asiento.");
        }
        
        System.out.println("\n📋 Reservas generadas (InOrder del AVL):");
        List<Reserva> reservas = vueloTest.getReservas().inOrder();
        for (Reserva r : reservas) {
            System.out.println("   " + r.toString());
        }
    }

    // --- Opción 9: Ver Datos de Ejemplo Pre-cargados ---
    private void verDatosEjemplo() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║       📊 DATOS DE EJEMPLO PRE-CARGADOS                   ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        System.out.println("Este sistema incluye datos pre-cargados para demostración:\n");
        
        // Mostrar red de vuelos (Grafo)
        System.out.println("🌐 RED DE VUELOS (GRAFO):");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("📍 CIUDADES DISPONIBLES (7 vértices):");
        String[] ciudades = {"Buenos Aires", "Córdoba", "Mendoza", "Bariloche", "Santa Cruz", "Santa Fe", "Posadas"};
        for (int i = 0; i < ciudades.length; i++) {
            System.out.printf("   %d. %s\n", i + 1, ciudades[i]);
        }
        
        System.out.println("\n✈️  CONEXIONES DIRECTAS (Aristas bidireccionales):");
        System.out.println("   Desde Buenos Aires:");
        System.out.println("   • Buenos Aires ↔ Córdoba      (1.2h, ARS 120,000)");
        System.out.println("   • Buenos Aires ↔ Mendoza      (1.7h, ARS 150,000)");
        System.out.println("   • Buenos Aires ↔ Bariloche    (2.2h, ARS 220,000)");
        System.out.println("   • Buenos Aires ↔ Santa Fe     (1.0h, ARS 100,000)");
        System.out.println("   • Buenos Aires ↔ Posadas      (1.5h, ARS 140,000)");
        
        System.out.println("\n   Conexiones para trasbordos:");
        System.out.println("   • Córdoba ↔ Mendoza          (1.1h, ARS 90,000)");
        System.out.println("   • Córdoba ↔ Santa Fe         (0.8h, ARS 70,000)");
        System.out.println("   • Mendoza ↔ Bariloche        (1.6h, ARS 120,000)");
        System.out.println("   • Bariloche ↔ Santa Cruz     (2.0h, ARS 160,000)");
        System.out.println("   • Mendoza ↔ Santa Cruz       (2.6h, ARS 170,000)");
        System.out.println("   • Santa Fe ↔ Posadas         (1.2h, ARS 80,000)");
        
        System.out.println("\n📝 NOTA: Buenos Aires NO tiene conexión directa con Santa Cruz");
        System.out.println("   (Requiere trasbordo - ideal para demostrar Dijkstra)");
        
        // Mostrar ejemplo de estructura de vuelo
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("✈️  ESTRUCTURA DE VUELO:");
        System.out.println("   • Capacidad total: 30 asientos");
        System.out.println("   • Secciones: A, B, C (10 asientos cada una)");
        System.out.println("   • Asientos por sección: A1-A10, B1-B10, C1-C10");
        System.out.println("   • Gestión: Árbol AVL (O(log n) para insertar/buscar/eliminar)");
        System.out.println("   • Balance: Diferencia entre secciones ≤ 1 asiento");
        
        // Mostrar ejemplo de reserva
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🎫 ESTRUCTURA DE RESERVA:");
        System.out.println("   • Código único: RES-XXXX (autogenerado)");
        System.out.println("   • Asiento: Sección + Número (ej: A5, B10)");
        System.out.println("   • Ruta: Origen → Destino");
        System.out.println("   • Vuelo: Código del vuelo asociado");
        
        // Mostrar cálculo de precios
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("💰 REGLAS DE PRECIOS:");
        System.out.println("   1. Precio base: Suma de precios de todos los tramos");
        System.out.println("   2. Recargo +20%: Si el itinerario es DIRECTO (1 solo tramo)");
        System.out.println("   3. Recargo +10%: Si la ocupación del vuelo es ≥ 95%");
        
        System.out.println("\n   Ejemplo 1 (Vuelo Directo):");
        System.out.println("   • Buenos Aires → Córdoba: ARS 120,000");
        System.out.println("   • Recargo directo (+20%): ARS 24,000");
        System.out.println("   • Total: ARS 144,000");
        
        System.out.println("\n   Ejemplo 2 (Con Trasbordo):");
        System.out.println("   • Buenos Aires → Bariloche: ARS 220,000");
        System.out.println("   • Bariloche → Santa Cruz: ARS 160,000");
        System.out.println("   • Total (sin recargo directo): ARS 380,000");
        
        // Mostrar algoritmos disponibles
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🔬 ALGORITMOS IMPLEMENTADOS:");
        System.out.println("   1. Dijkstra:");
        System.out.println("      • Complejidad: O((V+E) log V)");
        System.out.println("      • Criterio principal: Tiempo mínimo");
        System.out.println("      • Desempate: Precio mínimo");
        
        System.out.println("\n   2. BFS (Búsqueda en Anchura):");
        System.out.println("      • Complejidad: O(V+E)");
        System.out.println("      • Usa: Queue (cola)");
        System.out.println("      • Encuentra: Ciudades alcanzables desde origen");
        
        System.out.println("\n   3. DFS (Búsqueda en Profundidad):");
        System.out.println("      • Complejidad: O(V+E)");
        System.out.println("      • Usa: Recursión");
        System.out.println("      • Encuentra: Ciudades alcanzables desde origen");
        
        System.out.println("\n   4. AVL (Árbol Binario Balanceado):");
        System.out.println("      • Complejidad: O(log N)");
        System.out.println("      • Operaciones: Insertar, Buscar, Eliminar, InOrder");
        System.out.println("      • Balance: Altura izq - Altura der ≤ 1");
        
        System.out.println("\n   5. Asignación Aleatoria Balanceada:");
        System.out.println("      • Usa: java.util.Random");
        System.out.println("      • Garantiza: Diferencia entre secciones ≤ 1");
        System.out.println("      • Desempate: Aleatorio entre secciones con igual ocupación");
        
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("💡 SUGERENCIAS PARA PROBAR:");
        System.out.println("   • Opción 2: Ruta Buenos Aires → Santa Cruz (demuestra Dijkstra)");
        System.out.println("   • Opción 3: Reserva Buenos Aires → Córdoba (vuelo directo)");
        System.out.println("   • Opción 5: Recorridos desde Buenos Aires (BFS/DFS)");
        System.out.println("   • Opción 6: Demo automática (crea vuelos y reservas)");
        System.out.println("   • Opción 8: Test de balance (demuestra aleatoriedad)");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

}
