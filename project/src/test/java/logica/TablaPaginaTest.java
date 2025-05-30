import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

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

