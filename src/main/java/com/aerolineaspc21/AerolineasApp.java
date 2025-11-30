package com.aerolineaspc21;
import com.aerolineaspc21.modelo.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;

public class AerolineasApp {
    private Grafo redVuelos;
    private Map<String, Vuelo> vuelosActivos; // Almacena vuelos por su código

    public AerolineasApp() {
        this.redVuelos = new Grafo();
        this.redVuelos.cargarDatosIniciales(); // Carga las 7 ciudades y las aristas [11]
        this.vuelosActivos = new HashMap<>(); // Uso de Hash Table [12]
    }

    public static void main(String[] args) {
    AerolineasApp app = new AerolineasApp();
    app.mostrarMenu();
    }

    private void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n--- Aerolíneas PC21 - Menú Principal ---");
            System.out.println("1. Alta de Vuelo (Tramo Directo)");
            System.out.println("2. Consulta de Ruta Mínima (Dijkstra)");
            System.out.println("3. Reserva de Pasaje");
            System.out.println("4. Consulta de Ocupación por Vuelo (AVL InOrder)");
            System.out.println("5. Recorridos de la Red (BFS/DFS)");
            System.out.println("6. Demo automática (crear y mostrar ejemplos)");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

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
                    case 0: System.out.println("Saliendo de la aplicación."); break;
                    default: System.out.println("Opción no válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine();
            }
        }
    scanner.close();
    }

    // --- Opción 1: Alta de Vuelo ---
    private void altaVuelo(Scanner scanner) {
        System.out.println("--- Alta de Vuelo Directo ---");
        System.out.print("Código de Vuelo (ej: AR101): ");
        String codigo = scanner.nextLine().toUpperCase();
        System.out.print("Origen: ");
        String origen = scanner.nextLine();
        System.out.print("Destino: ");
        String destino = scanner.nextLine();
        
        Vuelo nuevoVuelo = new Vuelo(codigo, origen, destino); 
        vuelosActivos.put(codigo, nuevoVuelo);
        System.out.println("Vuelo " + codigo + " creado exitosamente.");
    }

    // --- Opción 2: Consulta de Ruta Mínima (Dijkstra) ---
    private void consultarRutaMinima(Scanner scanner) {
        System.out.print("Ciudad Origen: ");
        String origen = scanner.nextLine();
        System.out.print("Ciudad Destino: ");
        String destino = scanner.nextLine();

        // Llama al método Dijkstra, que optimiza tiempo y desempata por precio [3, 4]
        List<String> ruta = redVuelos.calcularRutaMinima(origen, destino);
        
        if (ruta == null || ruta.size() < 2) {
            System.out.println("No se encontró una ruta válida de " + origen + " a " + destino + ".");
            return;
        }

        double tiempoTotal = 0.0;
        double precioBaseTotal = 0.0;
        
        for (int i = 0; i < ruta.size() - 1; i++) {
            Vertice vOrigen = redVuelos.getVertice(ruta.get(i));
            String nombreDestino = ruta.get(i + 1);
            
            // Buscar la arista para sumar tiempos y precios [11]
            for (Arista arista : vOrigen.getAdyacentes()) {
                if (arista.getDestino().getNombre().equals(nombreDestino)) {
                    tiempoTotal += arista.getTiempo();
                    precioBaseTotal += arista.getPrecioBase();
                    break;
                }
            }
        }

        System.out.println("\n--- Ruta Mínima Encontrada (Dijkstra) ---");
        System.out.println("Ruta: " + String.join(" -> ", ruta));
        System.out.printf("Tiempo Total de Vuelo: %.2f horas\n", tiempoTotal);
        System.out.printf("Precio Base Total: ARS %.2f\n", precioBaseTotal);
    }

    // --- Opción 3: Reserva de Pasaje ---
    private void reservarPasaje(Scanner scanner) {
        System.out.print("Ciudad Origen: ");
        String origen = scanner.nextLine();
        System.out.print("Ciudad Destino: ");
        String destino = scanner.nextLine();

        List<String> ruta = redVuelos.calcularRutaMinima(origen, destino);

        if (ruta == null || ruta.size() < 2) {
            System.out.println("Ruta no encontrada.");
            return;
        }
        
        boolean esDirecto = (ruta.size() == 2);
        double precioFinal = 0.0;
        List<String> detallesReservas = new ArrayList<>();
        List<Arista> tramosRuta = new ArrayList<>();

        // 1. Procesar tramos y asignar reservas (AVL)
        for (int i = 0; i < ruta.size() - 1; i++) {
            String ciudadOrigen = ruta.get(i);
            String ciudadDestino = ruta.get(i + 1);
            String codigoVueloTramo = "V" + ciudadOrigen.substring(0, 3).toUpperCase() + ciudadDestino.substring(0, 3).toUpperCase();
            
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

            // Asignar asiento aleatorio balanceado (Lógica en Vuelo)
            String asiento = vueloTramo.asignarAsientoAleatorioBalanceado(); 
            
            if (asiento != null) {
                Reserva nuevaReserva = new Reserva(asiento, ciudadOrigen, ciudadDestino);
                vueloTramo.getReservas().insertar(nuevaReserva); 
                
                detallesReservas.add(String.format("Vuelo %s: %s -> %s | Asiento: %s", 
                                                codigoVueloTramo, ciudadOrigen, ciudadDestino, asiento));
            } else {
                System.out.println("ERROR: Vuelo " + codigoVueloTramo + " está lleno. Reserva fallida.");
                return;
            }
        }
        
        // 2. Cálculo del Precio Final (Lógica Dinámica)
        double ocupacionSimulada = 0.96;
        precioFinal = CalculadoraPrecio.calcularPrecioFinal(tramosRuta, esDirecto, ocupacionSimulada);
        
        // Imprimir Comprobante
        System.out.println("\n--- COMPROBANTE DE RESERVA ---");
        System.out.println("Itinerario: " + String.join(" -> ", ruta));
        detallesReservas.forEach(System.out::println);
        System.out.printf("PRECIO FINAL TOTAL (con ajustes): ARS %.2f\n", precioFinal);
        System.out.println("---------------------------------");
    }

    // --- Opción 4: Consulta de Ocupación (AVL InOrder) ---
    private void consultarOcupacion(Scanner scanner) {
        System.out.print("Código de Vuelo a consultar: ");
        String codigo = scanner.nextLine().toUpperCase();
        
        Vuelo vuelo = vuelosActivos.get(codigo);
        if (vuelo == null) {
            System.out.println("Vuelo no encontrado.");
            return;
        }
        
        System.out.println("\n--- Ocupación Vuelo " + codigo + " ---");
        int totalAsientos = 30;
        int ocupados = vuelo.getReservas().contarNodos(); 
        System.out.printf("Ocupación Global: %d/%d (%.2f%%)\n", ocupados, totalAsientos, (ocupados / (double)totalAsientos) * 100);
        Map<String, Integer> ocupacionSecciones = vuelo.getOcupacionPorSeccion(); 
        System.out.println("Ocupación por Sección: " + ocupacionSecciones);
        System.out.println("\nListado de Reservas (InOrder del AVL):");
        vuelo.getReservas().inOrder(); 
    }

    // --- Opción 5: Ejecución de Recorridos (BFS/DFS) ---
    private void ejecutarRecorridos(Scanner scanner) {
        System.out.print("Ciudad Origen para recorridos (BFS/DFS): ");
        String origen = scanner.nextLine();
        
        if (redVuelos.getVertice(origen) == null) {
            System.out.println("Ciudad de origen no válida.");
            return;
        }

        // BFS/DFS para listar nodos alcanzables [4, 9]
        List<String> bfsResultado = redVuelos.recorridoBFS(origen);
        System.out.println("\nRecorrido BFS (Amplitud) desde " + origen + ":");
        System.out.println(String.join(" -> ", bfsResultado));

        List<String> dfsResultado = redVuelos.recorridoDFS(origen);
        System.out.println("\nRecorrido DFS (Profundidad) desde " + origen + ":");
        System.out.println(String.join(" -> ", dfsResultado));
    }

    // Módulo demo para crear y mostrar ejemplos de vuelos y reservas
    private void ejecutarDemoAutomatica() {
        System.out.println("\n--- DEMO AUTOMÁTICA ---");
        // Crear vuelos directos
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

        // Reservar pasajes en vuelos directos
        for (int i = 0; i < 5; i++) {
            String asiento1 = vuelo1.asignarAsientoAleatorioBalanceado();
            if (asiento1 != null) vuelo1.getReservas().insertar(new Reserva(asiento1, "Buenos Aires", "Córdoba"));
            String asiento2 = vuelo2.asignarAsientoAleatorioBalanceado();
            if (asiento2 != null) vuelo2.getReservas().insertar(new Reserva(asiento2, "Buenos Aires", "Bariloche"));
        }

        // Reservar pasajes en vuelos con trasbordo (ejemplo: Buenos Aires -> Santa Cruz)
        // Ruta: Buenos Aires -> Bariloche -> Santa Cruz
        Vuelo vueloTramo1 = vuelosActivos.get("AR102"); // BUE-BRC
        Vuelo vueloTramo2 = new Vuelo("AR201", "Bariloche", "Santa Cruz");
        vuelosActivos.put("AR201", vueloTramo2);
        String asientoTramo1 = vueloTramo1.asignarAsientoAleatorioBalanceado();
        if (asientoTramo1 != null) vueloTramo1.getReservas().insertar(new Reserva(asientoTramo1, "Buenos Aires", "Bariloche"));
        String asientoTramo2 = vueloTramo2.asignarAsientoAleatorioBalanceado();
        if (asientoTramo2 != null) vueloTramo2.getReservas().insertar(new Reserva(asientoTramo2, "Bariloche", "Santa Cruz"));

        // Mostrar ocupación y reservas de los vuelos creados
        mostrarOcupacionDemo("AR101");
        mostrarOcupacionDemo("AR102");
        mostrarOcupacionDemo("AR201");
    }

    // Mostrar ocupación y reservas de un vuelo por código
    private void mostrarOcupacionDemo(String codigo) {
        Vuelo vuelo = vuelosActivos.get(codigo);
        if (vuelo == null) {
            System.out.println("Vuelo " + codigo + " no encontrado.");
            return;
        }
        System.out.println("\n--- Ocupación Vuelo " + codigo + " ---");
        int totalAsientos = 30;
        int ocupados = vuelo.getReservas().contarNodos();
        System.out.printf("Ocupación Global: %d/%d (%.2f%%)\n", ocupados, totalAsientos, (ocupados / (double)totalAsientos) * 100);
        Map<String, Integer> ocupacionSecciones = vuelo.getOcupacionPorSeccion();
        System.out.println("Ocupación por Sección: " + ocupacionSecciones);
        System.out.println("Listado de Reservas (InOrder del AVL):");
        vuelo.getReservas().inOrder();
    }

}
