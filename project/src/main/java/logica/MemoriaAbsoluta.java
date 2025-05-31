package logica;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * La clase MemoriaAbsoluta simula una memoria que almacena todas las tablas de páginas
 * de los procesos activos en el sistema.
 */
public class MemoriaAbsoluta {
    // Lista de tablas de páginas almacenadas en la memoria absoluta
    private List<tablaPagina> lista;

    /**
     * Constructor: inicializa la lista de tablas de páginas.
     */
    public MemoriaAbsoluta() {
        lista = new ArrayList<>();
    }

    /**
     * Agrega una tabla de páginas a la lista.
     * @param tabla la tabla de páginas a agregar
     */
    public void addTablaPagina(tablaPagina tabla) {
        lista.add(tabla);
    }

    /**
     * Elimina la tabla de páginas asociada a un proceso dado su id.
     * @param idP id del proceso al que pertenece la tabla de páginas
     * @return true si se eliminó alguna tabla, false si no se encontró
     */
    public boolean deleteTabla(int idP) {
        Iterator<tablaPagina> it = lista.iterator();
        boolean eliminado = false;
        while (it.hasNext()) {
            tablaPagina t = it.next();
            if (t.getIdProceso() == idP) {
                it.remove();
                eliminado = true;
                break; // asumiendo que solo hay una tabla por proceso
            }
        }
        return eliminado;
    }

    /**
     * Obtiene la tabla de páginas correspondiente a un proceso dado su id.
     * @param idP id del proceso
     * @return objeto tablaPagina correspondiente, o null si no existe
     */
    public tablaPagina getTabla(int idP) {
        for (tablaPagina t : lista) {
            if (t.getIdProceso() == idP) {
                return t;
            }
        }
        return null;
    }

    /**
     * Devuelve la lista de tablas de páginas.
     * @return la lista interna de tablas de páginas
     */
    public List<tablaPagina> getLista() {
        return lista;
    }
}
