import static org.junit.Assert.*;
import org.junit.Test;

public class PaginaTest {

    @Test
    public void testConstructorYGettersConElementoNoNull() {
        Elemento elem = new Elemento(10);
        Pagina pagina = new Pagina(5, elem);

        assertEquals(5, pagina.getIdL());
        assertEquals(elem, pagina.getElemento());
        assertEquals(10, pagina.getIdP());
        assertFalse(pagina.getEstado());  // estado == false porque elemento != null
    }

    @Test
    public void testConstructorYGettersConElementoNull() {
        Pagina pagina = new Pagina(7, null);

        assertEquals(7, pagina.getIdL());
        assertNull(pagina.getElemento());
        assertEquals(-1, pagina.getIdP());
        assertTrue(pagina.getEstado());  // estado == true porque elemento == null
    }

    @Test
    public void testCalcEstadoCambiaEstado() {
        Pagina pagina = new Pagina(1, null);
        assertTrue(pagina.getEstado());

        // Cambiar elemento a no nulo y recalcular estado
        pagina = new Pagina(1, new Elemento(99));
        assertFalse(pagina.getEstado());
    }
}

