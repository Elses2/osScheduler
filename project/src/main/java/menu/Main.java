package menu;

import logica.*;

import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Scheduler scheduler;
    private static MemoriaAbsoluta memoriaAbsoluta = new MemoriaAbsoluta();
    private static Map<Integer, Proceso> procesos = new HashMap<>();

    public static void main(String[] args) {
        clearScreen();
        System.out.println("=== Simulador de Gestión de Memoria ===");
        System.out.println("===============================ADVERTENCIA=======================================");
        System.out.println("=Esto es una version experimental con opciones limitadas y no excenta de errores=");
        System.out.println("=================================================================================\n");

        System.out.println("El tamaño de las paginas es de 4 kylobytes.");

        int ramSize = pedirEnteroMultiploDe4("Ingrese el tamaño de RAM (múltiplo de 4): ");
        int virtualSize = pedirEnteroMultiploDe4("Ingrese el tamaño de Memoria Virtual (múltiplo de 4): ");

        scheduler = new Scheduler(ramSize, virtualSize);
        boolean exit = false;
        while (!exit) {
            clearScreen();
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Crear proceso");
            System.out.println("2. Cargar proceso en RAM");
            System.out.println("3. Descargar proceso de RAM");
            System.out.println("4. Descargar proceso de Virtual");
            System.out.println("5. Mostrar estado de RAM y Virtual");
            System.out.println("6. Mostrar elementos de proceso");
            System.out.println("7. Mostrar tabla de páginas de un proceso");
            System.out.println("8. Salir");
            System.out.println("9. Manual");
            System.out.print("Elige una opción: ");
            int opt = pedirEnteroEnRango(1, 9);

            switch (opt) {
                case 1: crearProceso(); break;
                case 2: cargarProcesoEnRam(); break;
                case 3: descargarProcesoDeRam(); break;
                case 4: descargarProcesoDeVirtual(); break;
                case 5: mostrarEstado(); break;
                case 6: mostrarElementosProceso(); break;
                case 7: mostrarTablaPaginas(); break;
                case 8: exit = true; break;
                case 9: mostrarManual(); esperarEnter(); break;
                default: System.out.println("Opción inválida.");
            }
        }
        clearScreen();
        System.out.println("¡Hasta luego!");
    }

    private static void crearProceso() {
        clearScreen();
        System.out.println("--- Crear Proceso ---");
        int id = pedirEnteroPositivo("ID del proceso: ");
        if (procesos.containsKey(id)) {
            System.out.println("Ya existe un proceso con ese ID.");
            esperarEnter();
            return;
        }
        Proceso p = new Proceso(id);
        procesos.put(id, p);
        memoriaAbsoluta.addTablaPagina(new TablaPagina(p.getCantidadaElementos(), id));
        System.out.println("Proceso creado con 40 kylobytes por defecto.");///10 elementos por defecto son 10                                                                                   kylobytes
        esperarEnter();
    }

    private static void cargarProcesoEnRam() {
        clearScreen();
        System.out.println("--- Cargar Proceso en RAM ---");
        int id = pedirEnteroPositivo("ID del proceso a cargar en RAM: ");
        Proceso p = procesos.get(id);
        if (p == null) {
            System.out.println("Proceso no encontrado.");
            esperarEnter();
            return;
        }
        boolean cargado = scheduler.cargarProcesoEnRam(p);
        if (cargado) {
            System.out.println("Proceso cargado en RAM.");
        } else {
            System.out.println("No había espacio suficiente, se realizó swap.");
        }
        actualizarTablaPaginas(id);
        esperarEnter();
    }

    private static void descargarProcesoDeRam() {
        clearScreen();
        System.out.println("--- Descargar Proceso de RAM ---");
        int id = pedirEnteroPositivo("ID del proceso a descargar de RAM: ");
        try {
            scheduler.descargarProcesoDeRam(id);
            System.out.println("Proceso descargado de RAM a Virtual.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        actualizarTablaPaginas(id);
        esperarEnter();
    }

    private static void descargarProcesoDeVirtual() {
        clearScreen();
        System.out.println("--- Descargar Proceso de Virtual ---");
        int id = pedirEnteroPositivo("ID del proceso a descargar de Virtual: ");
        try {
            scheduler.descargarProcesoDeVirtual(id);
            System.out.println("Proceso eliminado de la Virtual.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        actualizarTablaPaginas(id);
        esperarEnter();
    }

    private static void mostrarElementosProceso() {
        clearScreen();
        System.out.println("--- Mostrar Elementos de Proceso ---");
        int id = pedirEnteroPositivo("ID del proceso: ");
        Proceso p = procesos.get(id);
        if (p == null) {
            System.out.println("Proceso no encontrado.");
            esperarEnter();
            return;
        }
        p.mostrarElementos();
        esperarEnter();
    }

    private static void mostrarTablaPaginas() {
        clearScreen();
        System.out.println("--- Mostrar Tabla de Páginas ---");
        int id = pedirEnteroPositivo("ID del proceso: ");
        TablaPagina tabla = memoriaAbsoluta.getTabla(id);
        if (tabla == null) {
            System.out.println("No hay tabla para ese proceso.");
            esperarEnter();
            return;
        }
        tabla.imprimirTabla();
        esperarEnter();
    }

    private static void mostrarEstado() {
        clearScreen();
        System.out.println("--- Estado de la Memoria ---");
        scheduler.mostrarEstado();
        esperarEnter();
    }

    private static void actualizarTablaPaginas(int idProceso) {
        TablaPagina tabla = memoriaAbsoluta.getTabla(idProceso);
        if (tabla != null) {
            tabla.actualizar();
        }
    }

    private static void mostrarManual() {
        clearScreen();
        System.out.println("\n=== Manual de Usuario ===");
        System.out.println("1. Crear proceso: Permite crear un nuevo proceso ingresando un ID único.");
        System.out.println("2. Cargar proceso en RAM: Carga un proceso existente en la memoria RAM.");
        System.out.println("3. Descargar proceso de RAM: Descarga el proceso de RAM y lo mueve a memoria virtual.");
        System.out.println("4. Descargar proceso de Virtual: Elimina el proceso de la memoria virtual.");
        System.out.println("5. Mostrar estado de RAM y Virtual: Muestra el estado actual de ambas memorias.");
        System.out.println("6. Mostrar elementos de proceso: Visualiza los elementos del proceso dado su ID.");
        System.out.println("7. Mostrar tabla de páginas de un proceso: Muestra la tabla de páginas del proceso.");
        System.out.println("8. Salir: Finaliza la ejecución del programa.");
        System.out.println("9. Manual: Muestra esta ayuda.");
    }

    // ========== MÉTODOS DE VALIDACIÓN ==========

    // Valida que sea un entero, positivo y múltiplo de 4
    private static int pedirEnteroMultiploDe4(String mensaje) {
        int valor = -1;
        while (true) {
            System.out.print(mensaje);
            try {
                valor = Integer.parseInt(scanner.nextLine().trim());
                if (valor > 0 && valor % 4 == 0) {
                    break;
                } else {
                    System.out.println("Debe ser un número entero, positivo y múltiplo de 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número entero.");
            }
        }
        return valor;
    }

    // Valida que sea un entero, positivo
    private static int pedirEnteroPositivo(String mensaje) {
        int valor = -1;
        while (true) {
            System.out.print(mensaje);
            try {
                valor = Integer.parseInt(scanner.nextLine().trim());
                if (valor > 0) {
                    break;
                } else {
                    System.out.println("Debe ser un número entero positivo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número entero.");
            }
        }
        return valor;
    }

    // Valida opción de menú entre un rango específico
    private static int pedirEnteroEnRango(int min, int max) {
        int valor = -1;
        while (true) {
            try {
                valor = Integer.parseInt(scanner.nextLine().trim());
                if (valor >= min && valor <= max) {
                    break;
                } else {
                    System.out.println("Opción fuera de rango. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número entero.");
            }
            System.out.print("Elige una opción: ");
        }
        return valor;
    }

    // ========== MÉTODO PARA LIMPIAR LA PANTALLA ==========
    private static void clearScreen() {
        // Funciona en la mayoría de consolas modernas (no en todas)
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // ========== MÉTODO DE PAUSA ==========
    private static void esperarEnter() {
        System.out.println("\nPresiona ENTER para continuar...");
        scanner.nextLine();
    }
}
