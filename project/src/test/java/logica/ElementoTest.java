package logica;

import static org.junit.Assert.*;
import org.junit.Test;

public class ElementoTest {

    @Test
    public void testConstructorYGetters() {
        Elemento elemento = new Elemento(1, 100, 5);

        assertEquals(1, elemento.getIdL());
        assertEquals(100, elemento.getValor());
        assertEquals(5, elemento.getIdP());
    }
}

