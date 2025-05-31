package logica;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Test unitario para la clase MemoriaAbsoluta.
 * Se asume que la clase tablaPagina tiene al menos un constructor tablaPagina(int idProceso)
 * y un método getIdProceso().
 */
public class MemoriaAbsolutaTest {
    private MemoriaAbsoluta memoria;
    private tablaPagina tabla1;
    private tablaPagina tabla2;

    @Before
    public void setUp() {
        memoria = new MemoriaAbsoluta();
        tabla1 = new tablaPagina(1);
        tabla2 = new tablaPagina(2);
    }

    @Test
    public void testAddTablaPagina() {
        memoria.addTablaPagina(tabla1);
        List<tablaPagina> lista = memoria.getLista();
        assertEquals(1, lista.size());
        assertTrue(lista.contains(tabla1));
    }

    @Test
    public void testGetTabla() {
        memoria.addTablaPagina(tabla1);
        memoria.addTablaPagina(tabla2);
        tablaPagina encontrada = memoria.getTabla(2);
        assertNotNull(encontrada);
        assertEquals(2, encontrada.getIdProceso());
        assertNull(memoria.getTabla(99));
    }

    @Test
    public void testDeleteTabla() {
        memoria.addTablaPagina(tabla1);
        memoria.addTablaPagina(tabla2);
        // Eliminamos tabla1
        boolean eliminado = memoria.deleteTabla(1);
        assertTrue(eliminado);
        assertNull(memoria.getTabla(1));
        // Intentamos eliminar una tabla inexistente
        boolean eliminadoInexistente = memoria.deleteTabla(42);
        assertFalse(eliminadoInexistente);
    }

    @Test
    public void testGetLista() {
        assertNotNull(memoria.getLista());
        assertTrue(memoria.getLista().isEmpty());
        memoria.addTablaPagina(tabla1);
        assertEquals(1, memoria.getLista().size());
    }
}
