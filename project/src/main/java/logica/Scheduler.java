package logica;

import java.util.*;

/**
 * Scheduler encargado de gestionar el intercambio de páginas/procesos entre la RAM y la Memoria Virtual.
 * Permite cargar procesos en RAM, descargar procesos a la Virtual, y realizar swaps según necesidad.
 * Implementa una política FIFO simple, pero modular para mejoras.
 */
public class Scheduler {
    private Ram ram;
    private Virtual virtual;
    private final int ramSize;
    private final int virtualSize;
    // Mantiene el orden de llegada de procesos para swapping FIFO
    private Queue<Integer> procesosEnRam = new LinkedList<>();

    /**
     * Inicializa el Scheduler con tamaños de RAM y Memoria Virtual.
     */
    public Scheduler(int ramSize, int virtualSize) {
        this.ramSize = ramSize;
        this.virtualSize = virtualSize;
        this.ram = Ram.getInstancia(ramSize);
        this.virtual = Virtual.getInstancia(virtualSize);
    }

    /**
     * Intenta cargar un proceso completo en la RAM. Si no hay espacio, realiza swap a Virtual.
     * 
     * @param proceso Proceso a cargar.
     * @return true si se cargó en RAM, false si se tuvo que ir a Virtual.
     */
    public boolean cargarProcesoEnRam(Proceso proceso) {
        int paginasLibres = contarEspaciosLibres(ram.getArrayMemoria());
        int paginasNecesarias = proceso.getCantidadaElementos();

        // Si hay espacio suficiente en RAM, cargar todas las páginas
        if (paginasLibres >= paginasNecesarias) {
            for (Elemento elem : proceso.getElementos()) {
                ram.setPagina(elem.getIdL(), elem);
            }
            procesosEnRam.add(proceso.getIdProceso());
            return true;
        } else {
            // No hay espacio suficiente
            swapProcesoARam(proceso);
            return false;
        }
    }

    /**
     * Realiza swap: elige un proceso en RAM para mandar a Virtual y libera espacio, 
     * luego carga el nuevo proceso en RAM.
     */
    public void swapProcesoARam(Proceso nuevoProceso) {
        int paginasNecesarias = nuevoProceso.getCantidadaElementos();

        while (contarEspaciosLibres(ram.getArrayMemoria()) < paginasNecesarias) {
            if (procesosEnRam.isEmpty()) {
                throw new RuntimeException("No hay procesos en RAM para hacer swap.");
            }
            // FIFO: saca el primero cargado
            int idProcesoASacar = procesosEnRam.poll();
            descargarProcesoDeRam(idProcesoASacar);
        }

        // Ahora hay espacio, cargar el nuevo proceso
        cargarProcesoEnRam(nuevoProceso);
    }

    /**
     * Descarga todas las páginas de un proceso de la RAM y las lleva a la memoria virtual.
     */
    public void descargarProcesoDeRam(int idProceso) {
        Pagina[] paginasRam = ram.getArrayMemoria();
        for (int i = 0; i < paginasRam.length; i++) {
            Pagina pag = paginasRam[i];
            if (pag != null && pag.getIdP() == idProceso) {
                // Mueve a Virtual
                virtual.setPagina(pag.getIdL(), pag.getElemento());
                paginasRam[i] = null; // Libera el espacio en RAM
            }
        }
    }

    /**
     * Descarga todas las páginas de un proceso de la Virtual y las elimina (opcional).
     */
    public void descargarProcesoDeVirtual(int idProceso) {
        Pagina[] paginasVirtual = virtual.getArrayMemoria();
        for (int i = 0; i < paginasVirtual.length; i++) {
            Pagina pag = paginasVirtual[i];
            if (pag != null && pag.getIdP() == idProceso) {
                paginasVirtual[i] = null;
            }
        }
    }

    /**
     * Cuenta los espacios libres en un array de páginas.
     */
    private int contarEspaciosLibres(Pagina[] array) {
        int libres = 0;
        for (Pagina pagina : array) {
            if (pagina == null) libres++;
        }
        return libres;
    }

    /**
     * Muestra el estado de la RAM y la Virtual para debug.
     */
    public void mostrarEstado() {
        System.out.println("Estado de RAM:");
        for (Pagina p : ram.getArrayMemoria()) {
            if (p != null)
                System.out.println("Proceso: " + p.getIdP() + ", Pagina: " + p.getIdL());
        }
        System.out.println("Estado de Virtual:");
        for (Pagina p : virtual.getArrayMemoria()) {
            if (p != null)
                System.out.println("Proceso: " + p.getIdP() + ", Pagina: " + p.getIdL());
        }
    }
}
