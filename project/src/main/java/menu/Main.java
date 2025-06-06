package menu;

import logica.*;

import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Scheduler scheduler;
    private static MemoriaAbsoluta memoriaAbsoluta = new MemoriaAbsoluta();
    private static Map<Integer, Proceso> procesos = new HashMap<>();
    private static int ramSize;
    private static int virtualSize;

    public static void main(String[] args) {
        clearScreen();
        System.out.println("=== Simulador de Gestión de Memoria ===");
        System.out.println("===============================ADVERTENCIA=======================================");
        System.out.println("=Esto es una version experimental con opciones limitadas y no excenta de errores=");
        System.out.println("=================================================================================\n");

        System.out.println("El tamaño de las paginas es de 4 kylobytes.");

        ramSize = pedirEnteroMultiploDe4("Ingrese el tamaño de RAM (múltiplo de 4): ");
        virtualSize = pedirEnteroMultiploDe4("Ingrese el tamaño de Memoria Virtual (múltiplo de 4): ");

        scheduler = new Scheduler(ramSize, virtualSize);
        boolean exit = false;
        while (!exit) {
            clearScreen();
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Crear y cargar proceso en RAM");
            System.out.println("2. Eliminar proceso");
            System.out.println("3. Mostrar estado de RAM y Virtual");
            System.out.println("4. Mostrar elementos de proceso");
            System.out.println("5. Mostrar tabla de páginas de un proceso");
            System.out.println("6. Salir");
            System.out.println("7. Manual");
            System.out.print("Elige una opción: ");
            int opt = pedirEnteroEnRango(1, 7);

            switch (opt) {
                case 1: crearYcargarProceso(); break;
                case 2: eliminarProceso(); break;
                case 3: mostrarEstado(); break;
                case 4: mostrarElementosProceso(); break;
                case 5: mostrarTablaPaginas(); break;
                case 6: exit = true; break;
                case 7: mostrarManual(); esperarEnter(); break;
                default: System.out.println("Opción inválida.");
            }
        }
        clearScreen();
        System.out.println("¡Hasta luego!");
    }

    private static void crearYcargarProceso() {
        clearScreen();
        System.out.println("--- Crear y Cargar Proceso en RAM ---");
        int id = pedirEnteroPositivo("ID del proceso: ");
        if (procesos.containsKey(id)) {
            System.out.println("Ya existe un proceso con ese ID en el sistema.");
            esperarEnter();
            return;
        }
        if (procesoExisteEnMemorias(id)) {
            System.out.println("Ya existe un proceso con ese ID en RAM o en Memoria Virtual.");
            esperarEnter();
            return;
        }

        int tamanoProceso = pedirEnteroMultiploDe4("Ingrese el tamaño del proceso (numero de paginas, múltiplo de 4): ");
        Proceso p = new Proceso(id, tamanoProceso);
        try {
            boolean cargado = scheduler.cargarProcesoEnRam(p);
            procesos.put(id, p);
            memoriaAbsoluta.addTablaPagina(new TablaPagina(tamanoProceso, id));
            if (cargado) {
                System.out.println("Proceso creado y cargado en RAM con éxito.");
            } else {
                System.out.println("Proceso creado. No había espacio suficiente en RAM, se realizó swap.");
            }
            actualizarTablaPaginas(id);
        } catch (Exception e) {
            System.out.println("Error al cargar el proceso en RAM: " + e.getMessage());
        }
        esperarEnter();
    }

    private static void eliminarProceso() {
        clearScreen();
        System.out.println("--- Eliminar Proceso ---");
        int id = pedirEnteroPositivo("ID del proceso a eliminar: ");

        boolean estabaEnRam = existeEnRam(id);
        boolean estabaEnVirtual = existeEnVirtual(id);

        try {
            scheduler.descargarProcesoDeRam(id);
        } catch (Exception e) { }
        try {
            scheduler.descargarProcesoDeVirtual(id);
        } catch (Exception e) { }

        if (estabaEnRam || estabaEnVirtual) {
            memoriaAbsoluta.deleteTabla(id);
            procesos.remove(id);
            if (estabaEnRam) {
                System.out.println("Proceso eliminado de RAM.");
            }
            if (estabaEnVirtual) {
                System.out.println("Proceso eliminado de Memoria Virtual.");
            }
        } else {
            System.out.println("No se encontró un proceso con ese ID en RAM ni en Memoria Virtual.");
        }
        esperarEnter();
    }

    private static void mostrarEstado() {
        clearScreen();
        System.out.println("--- Estado de la Memoria ---");
        scheduler.mostrarEstado();
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

    // ========== MÉTODOS AUXILIARES Y VALIDACIONES ==========

    // Verifica si existe un proceso con ese ID en RAM o Virtual
    private static boolean procesoExisteEnMemorias(int idProceso) {
        Pagina[] ramPages = Ram.getInstancia(ramSize).getArrayMemoria();
        Pagina[] virtualPages = Virtual.getInstancia(virtualSize).getArrayMemoria();
        for (Pagina pagina : ramPages) {
            if (pagina != null && pagina.getIdP() == idProceso) {
                return true;
            }
        }
        for (Pagina pagina : virtualPages) {
            if (pagina != null && pagina.getIdP() == idProceso) {
                return true;
            }
        }
        return false;
    }

    private static boolean existeEnRam(int idProceso) {
        Pagina[] ramPages = Ram.getInstancia(ramSize).getArrayMemoria();
        for (Pagina pagina : ramPages) {
            if (pagina != null && pagina.getIdP() == idProceso) {
                return true;
            }
        }
        return false;
    }

    private static boolean existeEnVirtual(int idProceso) {
        Pagina[] virtualPages = Virtual.getInstancia(virtualSize).getArrayMemoria();
        for (Pagina pagina : virtualPages) {
            if (pagina != null && pagina.getIdP() == idProceso) {
                return true;
            }
        }
        return false;
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
        System.out.println("1. Crear y cargar proceso en RAM: Permite crear un nuevo proceso ingresando un ID único y lo carga automáticamente en RAM.");
        System.out.println("2. Eliminar proceso: Elimina el proceso de RAM o Memoria Virtual según donde se encuentre.");
        System.out.println("3. Mostrar estado de RAM y Virtual: Muestra el estado actual de ambas memorias.");
        System.out.println("4. Mostrar elementos de proceso: Visualiza los elementos del proceso dado su ID.");
        System.out.println("5. Mostrar tabla de páginas de un proceso: Muestra la tabla de páginas del proceso.");
        System.out.println("6. Salir: Finaliza la ejecución del programa.");
        System.out.println("7. Manual: Muestra esta ayuda.");
    }

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

    private static void clearScreen() {
        // Funciona en la mayoría de consolas modernas (no en todas)
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void esperarEnter() {
        System.out.println("\nPresiona ENTER para continuar...");
        scanner.nextLine();
    }
}
