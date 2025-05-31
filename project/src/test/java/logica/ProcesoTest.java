package logica;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class ProcesoTest {
    private Proceso proceso;

    @Before
    public void setUp() {
        proceso = new Proceso(42);
    }

    @Test
    public void testGetIdProceso() {
        assertEquals(42, proceso.getIdProceso());
    }

    @Test
    public void testElementosIniciales() {
        assertEquals(10, proceso.getCantidadaElementos());
    }

    @Test
    public void testAgregarEliminarElemento() {
        Elemento nuevo = new Elemento(99, 1, 42);
        proceso.agregarElemento(nuevo);
        assertEquals(11, proceso.getCantidadaElementos());

        proceso.eliminarElemento(nuevo);
        assertEquals(10, proceso.getCantidadaElementos());
    }

    @Test
    public void testMostrarElementos_ConElementos() {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        System.setOut(new PrintStream(salida));
        proceso.mostrarElementos();
        String output = salida.toString();
        assertTrue(output.contains("Elementos del proceso 42:"));
        assertTrue(output.contains("idL: 1, valor: 0, idP: 42"));
        System.setOut(System.out); // Restaurar salida estándar
    }

    @Test
    public void testMostrarElementos_SinElementos() {
        // Eliminar todos los elementos
        proceso = new Proceso(1);
        for (int i = 1; i <= 10; i++) {
            proceso.eliminarElemento(proceso.elementos.get(0));
        }

        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        System.setOut(new PrintStream(salida));
        proceso.mostrarElementos();
        String output = salida.toString();
        assertTrue(output.contains("No hay elementos en el proceso 1."));
        System.setOut(System.out); // Restaurar salida estándar
    }

    @Test
    public void testGetCantidadaElementos() {
        assertEquals(10, proceso.getCantidadaElementos());
    }
}
