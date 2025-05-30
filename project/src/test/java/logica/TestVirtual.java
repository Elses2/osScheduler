import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class VirtualTest {

    private Virtual virtual;

    @Before
    public void setUp() {
        resetSingleton(); // Reinicia la instancia del Singleton
        virtual = Virtual.getInstancia(12); // 12 / 4 = 3 páginas
    }

    @Test
    public void testGetInstanciaRetornaMismaInstancia() {
        Virtual otra = Virtual.getInstancia(24);
        assertSame(virtual, otra); // Debe ser la misma instancia
    }

    @Test
    public void testSetPaginaInsertaCorrectamente() {
        Elemento elemento = new Elemento(2, 200, 6);
        Pagina pagina = virtual.setPagina(5, elemento);
        assertNotNull(pagina);
        assertEquals(5, pagina.getIdL());
        assertEquals(200, pagina.getElemento().getValor());
    }

    @Test(expected = RuntimeException.class)
    public void testSetPaginaLanzaExcepcionCuandoVirtualLlena() {
        virtual.setPagina(0, new Elemento(0, 10, 1));
        virtual.setPagina(1, new Elemento(1, 20, 2));
        virtual.setPagina(2, new Elemento(2, 30, 3));
        virtual.setPagina(3, new Elemento(3, 40, 4)); // Excede capacidad (3 páginas)
    }

    @Test
    public void testGetPaginaEncuentraPaginaCorrecta() {
        virtual.setPagina(7, new Elemento(4, 99, 9));
        Pagina encontrada = virtual.getPagina(7);
        assertNotNull(encontrada);
        assertEquals(99, encontrada.getElemento().getValor());
    }

    @Test
    public void testGetPaginaDevuelveNullSiNoExiste() {
        Pagina resultado = virtual.getPagina(999);
        assertNull(resultado);
    }

    @Test
    public void testGetArrayMemoriaDevuelveArrayCorrecto() {
        Pagina[] array = virtual.getArrayMemoria();
        assertNotNull(array);
        assertEquals(3, array.length); // 12 / 4 = 3 páginas
    }

    // Método para reiniciar el singleton de Virtual
    private void resetSingleton() {
        try {
            java.lang.reflect.Field instancia = Virtual.class.getDeclaredField("instancia");
            instancia.setAccessible(true);
            instancia.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo reiniciar el singleton de Virtual", e);
        }
    }
}

