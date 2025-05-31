package logica;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RamTest {

    private Ram ram;

    @Before
    public void setUp() {
        resetSingleton(); // reset entre pruebas
        ram = Ram.getInstancia(8); // 8 → 8 / 4 = 2 páginas
        ram.setPagina(0, new Elemento(1, 10, 5));
    }

    @Test
    public void testGetInstanciaRetornaMismaInstancia() {
        Ram otra = Ram.getInstancia(16);
        assertSame(ram, otra); // debe ser la misma instancia
    }

    @Test
    public void testSetPaginaInsertaCorrectamente() {
        Elemento elemento = new Elemento(1, 100, 7);
        Pagina pagina = ram.setPagina(1, elemento);
        assertNotNull(pagina);
        assertEquals(1, pagina.getIdL());
        assertEquals(100, pagina.getElemento().getValor());
    }

    @Test(expected = RuntimeException.class)
    public void testSetPaginaLanzaExcepcionCuandoRAMLlena() {
        ram.setPagina(0, new Elemento(0, 1, 1));
        ram.setPagina(1, new Elemento(1, 2, 2));
        ram.setPagina(2, new Elemento(2, 3, 3)); // RAM ya está llena
    }

    @Test
    public void testGetPaginaEncuentraPaginaCorrecta() {
        ram.setPagina(0, new Elemento(0, 10, 5));
        Pagina encontrada = ram.getPagina(0);
        assertNotNull(encontrada);
        assertEquals(10, encontrada.getElemento().getValor());
    }

    @Test
    public void testGetPaginaDevuelveNullSiNoExiste() {
        Pagina resultado = ram.getPagina(99);
        assertNull(resultado);
    }

    @Test
    public void testGetArrayMemoriaDevuelveArrayCorrecto() {
        Pagina[] array = ram.getArrayMemoria();
        assertNotNull(array);
        assertEquals(2, array.length); // 8 / 4 = 2 páginas
    }

    // Utilidad para reiniciar la instancia del singleton (por testeo)
    private void resetSingleton() {
        try {
            java.lang.reflect.Field instancia = Ram.class.getDeclaredField("instancia");
            instancia.setAccessible(true);
            instancia.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo reiniciar el singleton", e);
        }
    }
}

