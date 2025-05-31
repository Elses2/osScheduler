package logica;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TablaPaginaTest {

    private Ram ram;
    private TablaPagina tablaPagina;
    private final int CANTIDAD_ELEMENTOS = 4;
    private final int ID_PROCESO = 1;

    @Before
    public void setUp() {
        // Reiniciamos el singleton con reflexión para pruebas limpias (por si es necesario)
        resetSingletonRam();

        // Creamos RAM con espacio suficiente
        ram = Ram.getInstancia(1024);

        // Insertamos elementos del proceso ID_PROCESO en RAM
        for (int i = 0; i < CANTIDAD_ELEMENTOS; i++) {
            Elemento elemento = new Elemento(i, i * 10, ID_PROCESO);
            ram.setPagina(i, elemento);
        }

        // Insertamos elemento de otro proceso para probar que no se incluya
        Elemento otroProceso = new Elemento(0, 99, 99);
        ram.setPagina(99, otroProceso);

        tablaPagina = new TablaPagina(CANTIDAD_ELEMENTOS, ID_PROCESO);
    }

    @Test
    public void testActualizarTabla() {
        tablaPagina.actualizar();
        int[][] tabla = tablaPagina.getTabla();

        for (int i = 0; i < CANTIDAD_ELEMENTOS; i++) {
            assertEquals(i, tabla[i][0]);            // Dirección lógica
            assertTrue(tabla[i][1] >= 0);            // Dirección física asignada
        }

        // Verificamos que no haya ninguna fila sin asignar
        for (int i = 0; i < CANTIDAD_ELEMENTOS; i++) {
            assertNotEquals(-1, tabla[i][1]);
        }
    }

    @Test
    public void testConstructorInicializaTablaCorrectamente() {
        int[][] tabla = tablaPagina.getTabla();
        assertEquals(CANTIDAD_ELEMENTOS, tabla.length);

        for (int i = 0; i < CANTIDAD_ELEMENTOS; i++) {
            assertEquals(i, tabla[i][0]);     // dirección lógica
            assertEquals(-1, tabla[i][1]);    // dirección física aún no asignada
        }
    }

    @Test
    public void testGetIdP() {
        assertEquals(ID_PROCESO, tablaPagina.getIdP());
    }

        @Test
    public void testActualizarTablaConIdLFueraDeRangoYProcesoInvalido() {
        resetSingletonRam();
        ram = Ram.getInstancia(1024);
        tablaPagina = new TablaPagina(2, ID_PROCESO);

        // Elemento con idL fuera del rango permitido
        ram.setPagina(0, new Elemento(5, 123, ID_PROCESO)); // idL fuera de rango

        // Elemento con proceso incorrecto
        ram.setPagina(1, new Elemento(0, 999, 999)); // proceso incorrecto

        tablaPagina.actualizar();
        int[][] tabla = tablaPagina.getTabla();

        for (int i = 0; i < tabla.length; i++) {
            assertEquals(-1, tabla[i][1]); // No debería haberse actualizado
        }
    }
    @Test
    public void testImprimirTabla() {
        tablaPagina.actualizar();

        // Capturamos salida estándar
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        tablaPagina.imprimirTabla();

        String salida = outContent.toString();
        assertTrue(salida.contains("Tabla de Paginación del Proceso ID: " + ID_PROCESO));
        assertTrue(salida.contains("Lógica\tFísica"));
        for (int i = 0; i < CANTIDAD_ELEMENTOS; i++) {
            assertTrue(salida.contains(String.valueOf(i))); // idL visibles
        }

        // Restauramos System.out
        System.setOut(System.out);
    }

    @Test
    public void testActualizarTabla_idLDentroDeRango() {
        resetSingletonRam();
        ram = Ram.getInstancia(1024);

        // Crear tabla con 3 elementos
        tablaPagina = new TablaPagina(3, ID_PROCESO);

        // Elemento con idL válido = 1
        Elemento valido = new Elemento(1, 100, ID_PROCESO);
        ram.setPagina(0, valido); // En posición 0 de RAM

        tablaPagina.actualizar();

        int[][] tabla = tablaPagina.getTabla();

        // Solo idL = 1 debe estar asignado
        assertEquals(-1, tabla[0][1]);
        assertTrue(tabla[1][1] >= 0);  // debe haberse actualizado
        assertEquals(-1, tabla[2][1]);
    }

    @Test
    public void testActualizarTabla_idLFueraDeRango() {
        resetSingletonRam();
        ram = Ram.getInstancia(1024);

        // Crear tabla con 3 elementos
        tablaPagina = new TablaPagina(3, ID_PROCESO);

        // Elemento con idL < 0
        Elemento negativo = new Elemento(-1, 200, ID_PROCESO);
        ram.setPagina(0, negativo);

        // Elemento con idL >= tabla.length
        Elemento fueraDeRango = new Elemento(5, 300, ID_PROCESO);
        ram.setPagina(1, fueraDeRango);

        tablaPagina.actualizar();

        int[][] tabla = tablaPagina.getTabla();

        // Ninguna posición válida debe estar asignada
        assertEquals(-1, tabla[0][1]);
        assertEquals(-1, tabla[1][1]);
        assertEquals(-1, tabla[2][1]);
        }

    
    // Reinicia el singleton de RAM entre pruebas (solo si es necesario en tu proyecto)
    private void resetSingletonRam() {
        try {
            java.lang.reflect.Field instancia = Ram.class.getDeclaredField("instancia");
            instancia.setAccessible(true);
            instancia.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo reiniciar el singleton de RAM para las pruebas", e);
        }
    }
}

