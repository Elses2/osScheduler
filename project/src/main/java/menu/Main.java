package menu;



import logica.*;

import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Scheduler scheduler;
    private static MemoriaAbsoluta memoriaAbsoluta = new MemoriaAbsoluta();
    private static Map<Integer, Proceso> procesos = new HashMap<>();
    
    public static void main(String[] args) {
        System.out.println("=== Simulador de Gestión de Memoria ===");
        System.out.print("Tamaño de RAM (múltiplo de 4): ");
        int ramSize = scanner.nextInt();
        System.out.print("Tamaño de Memoria Virtual (múltiplo de 4): ");
        int virtualSize = scanner.nextInt();
        scanner.nextLine();

        scheduler = new Scheduler(ramSize, virtualSize);
        boolean exit = false;
        
        while (!exit) {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Crear proceso");
            System.out.println("2. Cargar proceso en RAM");
            System.out.println("3. Descargar proceso de RAM");
            System.out.println("4. Descargar proceso de Virtual");
            System.out.println("5. Mostrar estado de RAM y Virtual");
            System.out.println("6. Mostrar elementos de proceso");
            System.out.println("7. Mostrar tabla de páginas de un proceso");
            System.out.println("8. Salir");
            System.out.print("Elige una opción: ");
            int opt = scanner.nextInt();
            scanner.nextLine();
            switch (opt) {
                case 1: crearProceso(); break;
                case 2: cargarProcesoEnRam(); break;
                case 3: descargarProcesoDeRam(); break;
                case 4: descargarProcesoDeVirtual(); break;
                case 5: scheduler.mostrarEstado(); break;
                case 6: mostrarElementosProceso(); break;
                case 7: mostrarTablaPaginas(); break;
                case 8: exit = true; break;
                default: System.out.println("Opción inválida.");
            }
        }
        System.out.println("¡Hasta luego!");
    }

    private static void crearProceso() {
        System.out.print("ID del proceso: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        if (procesos.containsKey(id)) {
            System.out.println("Ya existe un proceso con ese ID.");
            return;
        }
        Proceso p = new Proceso(id);
        procesos.put(id, p);
        memoriaAbsoluta.addTablaPagina(new TablaPagina(p.getCantidadaElementos(), id));
        System.out.println("Proceso creado con 10 elementos por defecto.");
    }

    private static void cargarProcesoEnRam() {
        System.out.print("ID del proceso a cargar en RAM: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Proceso p = procesos.get(id);
        if (p == null) {
            System.out.println("Proceso no encontrado.");
            return;
        }
        boolean cargado = scheduler.cargarProcesoEnRam(p);
        if (cargado) {
            System.out.println("Proceso cargado en RAM.");
        } else {
            System.out.println("No había espacio suficiente, se realizó swap.");
        }
        actualizarTablaPaginas(id);
    }

    private static void descargarProcesoDeRam() {
        System.out.print("ID del proceso a descargar de RAM: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        try {
            scheduler.descargarProcesoDeRam(id);
            System.out.println("Proceso descargado de RAM a Virtual.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        actualizarTablaPaginas(id);
    }

    private static void descargarProcesoDeVirtual() {
        System.out.print("ID del proceso a descargar de Virtual: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        try {
            scheduler.descargarProcesoDeVirtual(id);
            System.out.println("Proceso eliminado de la Virtual.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        actualizarTablaPaginas(id);
    }

    private static void mostrarElementosProceso() {
        System.out.print("ID del proceso: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Proceso p = procesos.get(id);
        if (p == null) {
            System.out.println("Proceso no encontrado.");
            return;
        }
        p.mostrarElementos();
    }

    private static void mostrarTablaPaginas() {
        System.out.print("ID del proceso: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        TablaPagina tabla = memoriaAbsoluta.getTabla(id);
        if (tabla == null) {
            System.out.println("No hay tabla para ese proceso.");
            return;
        }
        tabla.imprimirTabla();
    }

    private static void actualizarTablaPaginas(int idProceso) {
        TablaPagina tabla = memoriaAbsoluta.getTabla(idProceso);
        if (tabla != null) {
            tabla.actualizar();
        }
    }
}
