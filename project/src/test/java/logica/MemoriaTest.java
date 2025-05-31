package logica;
import static org.junit.Assert.*;
import org.junit.Test;

public class MemoriaTest {

    @Test
    public void testGetTamanoArrayMultiploDe4() {
        int tamano = 16;
        int resultado = Memoria.getTamanoArray(tamano);
        assertEquals(4, resultado);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetTamanoArrayNoMultiploDe4() {
        Memoria.getTamanoArray(10);
    }
}

