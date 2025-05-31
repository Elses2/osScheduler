package logica;

import org.junit.Before;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.*;

public class MemoriaAbsolutaTest {
    private MemoriaAbsoluta memoria;
    private TablaPagina tabla1;
    private TablaPagina tabla2;

    @Before
    public void setUp() {
        memoria = new MemoriaAbsoluta();
        tabla1 = new TablaPagina(3, 1); // 3 elementos, idP = 1
        tabla2 = new TablaPagina(2, 2); // 2 elementos, idP = 2
    }

    @Test
    public void testAddTablaPagina() {
        assertTrue(memoria.getLista().isEmpty());
        memoria.addTablaPagina(tabla1);
        assertEquals(1, memoria.getLista().size());
        assertSame(tabla1, memoria.getLista().get(0));
    }

    @Test
    public void testGetTabla() {
        memoria.addTablaPagina(tabla1);
        memoria.addTablaPagina(tabla2);
        assertSame(tabla1, memoria.getTabla(1));
        assertSame(tabla2, memoria.getTabla(2));
        assertNull(memoria.getTabla(999)); // no existe ese id
    }

    @Test
    public void testDeleteTabla() {
        memoria.addTablaPagina(tabla1);
        memoria.addTablaPagina(tabla2);
        assertTrue(memoria.deleteTabla(1));
        assertNull(memoria.getTabla(1));
        assertEquals(1, memoria.getLista().size());
        assertFalse(memoria.deleteTabla(999)); // no existe ese id
        assertTrue(memoria.deleteTabla(2)); // elimina el otro
        assertTrue(memoria.getLista().isEmpty());
    }

    @Test
    public void testGetLista() {
        List<TablaPagina> lista = memoria.getLista();
        assertNotNull(lista);
        assertTrue(lista.isEmpty());
        memoria.addTablaPagina(tabla1);
        assertEquals(1, lista.size());
        memoria.addTablaPagina(tabla2);
        assertEquals(2, lista.size());
    }

    @Test
    public void testAddDeleteMultiple() {
        TablaPagina tabla3 = new TablaPagina(1, 3);
        memoria.addTablaPagina(tabla1);
        memoria.addTablaPagina(tabla2);
        memoria.addTablaPagina(tabla3);
        assertEquals(3, memoria.getLista().size());
        assertTrue(memoria.deleteTabla(2));
        assertNull(memoria.getTabla(2));
        assertEquals(2, memoria.getLista().size());
        assertTrue(memoria.deleteTabla(3));
        assertNull(memoria.getTabla(3));
        assertTrue(memoria.deleteTabla(1));
        assertTrue(memoria.getLista().isEmpty());
    }
}
