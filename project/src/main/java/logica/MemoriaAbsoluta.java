package logica;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// AÑADE ESTE IMPORT SI QUIERES SER EXPLÍCITO (opcional si están en el mismo paquete)
// import logica.TablaPagina;

/**
 * La clase MemoriaAbsoluta simula una memoria que almacena todas las tablas de páginas
 * de los procesos activos en el sistema.
 */
public class MemoriaAbsoluta {
    // Lista de tablas de páginas almacenadas en la memoria absoluta
    private List<TablaPagina> lista;

    /** Constructor: inicializa la lista de tablas de páginas. */
    public MemoriaAbsoluta() {
        lista = new ArrayList<>();
    }

    /** Agrega una tabla de páginas a la lista. */
    public void addTablaPagina(TablaPagina tabla) {
        lista.add(tabla);
    }

    /** Elimina la tabla de páginas asociada a un proceso dado su id. */
    public boolean deleteTabla(int idP) {
        Iterator<TablaPagina> it = lista.iterator();
        boolean eliminado = false;
        while (it.hasNext()) {
            TablaPagina t = it.next();
            if (t.getIdP() == idP) {
                it.remove();
                eliminado = true;
                break; // asumiendo que solo hay una tabla por proceso
            }
        }
        return eliminado;
    }

    /** Obtiene la tabla de páginas correspondiente a un proceso dado su id. */
    public TablaPagina getTabla(int idP) {
        for (TablaPagina t : lista) {
            if (t.getIdP() == idP) {
                return t;
            }
        }
        return null;
    }

    /** Devuelve la lista de tablas de páginas. */
    public List<TablaPagina> getLista() {
        return lista;
    }
}
