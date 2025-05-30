import static org.junit.Assert.*;
import org.junit.Test;

public class PaginaTest {

    @Test
    public void testConstructorYGettersConElementoNoNull() {
        Elemento elem = new Elemento(10, 100, 3); // Se pasa idL, valor, idP
        Pagina pagina = new Pagina(5, elem);

        assertEquals(5, pagina.getIdL());
        assertEquals(elem, pagina.getElemento());
        assertEquals(3, pagina.getIdP());
        assertFalse(pagina.getEstado());  // estado == false porque elemento != null
    }

    @Test
    public void testConstructorYGettersConElementoNull() {
        Pagina pagina = new Pagina(7, null);

        assertEquals(7, pagina.getIdL());
        assertNull(pagina.getElemento());
        assertEquals(-1, pagina.getIdP());  // comportamiento cuando no hay elemento
        assertTrue(pagina.getEstado());     // estado == true porque elemento == null
    }

    @Test
    public void testCalcEstadoCambiaEstado() {
        // Crear página sin elemento
        Pagina pagina = new Pagina(1, null);
        assertTrue(pagina.getEstado());

        // Crear página con elemento
        pagina = new Pagina(1, new Elemento(10, 200, 2));
        assertFalse(pagina.getEstado());
    }
}

