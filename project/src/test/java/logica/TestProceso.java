import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProcesoTest {

    private Proceso proceso;

    @Before
    public void setUp() {
        proceso = new Proceso(123);
    }

    @Test
    public void testIdProcesoSeGuardaCorrectamente() {
        assertEquals(123, proceso.getIdProceso());
    }

    @Test
    public void testCargaInicialDeDiezElementos() {
        assertEquals(10, proceso.getCantidadElementos());
    }

    @Test
    public void testAgregarElemento() {
        proceso.agregarElemento(new Elementos(999));
        assertEquals(11, proceso.getCantidadElementos());
    }

    @Test
    public void testEliminarElemento() {
        Elementos nuevo = new Elementos(777);
        proceso.agregarElemento(nuevo);
        assertEquals(11, proceso.getCantidadElementos());

        proceso.eliminarElemento(nuevo);
        assertEquals(10, proceso.getCantidadElementos());
    }

    @Test
    public void testEliminarElementoInexistenteNoFalla() {
        Elementos fantasma = new Elementos(555); // Nunca fue agregado
        proceso.eliminarElemento(fantasma);
        assertEquals(10, proceso.getCantidadElementos());
    }
}
