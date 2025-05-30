import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;

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
        List<Elementos> elementos = getElementosInternos();
        assertEquals(10, elementos.size());
    }

    @Test
    public void testAgregarElemento() {
        proceso.agregarElemento(new Elementos(999));
        List<Elementos> elementos = getElementosInternos();
        assertEquals(11, elementos.size());
    }

    @Test
    public void testEliminarElemento() {
        Elementos extra = new Elementos(777);
        proceso.agregarElemento(extra);
        proceso.eliminarElemento(extra);
        List<Elementos> elementos = getElementosInternos();
        assertEquals(10, elementos.size());
        assertFalse(elementos.contains(extra));
    }

    @Test
    public void testEliminarElementoInexistenteNoFalla() {
        Elementos fantasma = new Elementos(555);
        proceso.eliminarElemento(fantasma); // no debería lanzar excepción
        List<Elementos> elementos = getElementosInternos();
        assertEquals(10, elementos.size());
    }

    // Accede a los elementos privados usando reflexión (opcional, si no querés exponerlos con getter)
    private List<Elementos> getElementosInternos() {
        try {
            java.lang.reflect.Field field = Proceso.class.getDeclaredField("elementos");
            field.setAccessible(true);
            return (List<Elementos>) field.get(proceso);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo acceder a los elementos internos", e);
        }
    }
}

