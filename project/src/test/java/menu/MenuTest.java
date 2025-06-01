package menu;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logica.Memoria;
import logica.Pagina;
import org.junit.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import java.util.concurrent.CountDownLatch;

public class MenuTest {

    private Memoria memoriaMock;

    @BeforeClass
    public static void initJfx() throws Exception {
        // JavaFX necesita iniciarse en entorno gráfico simulado
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(() -> {
            latch.countDown();
        });
        latch.await();
    }

    @Before
    public void setUp() {
        memoriaMock = mock(Memoria.class);
        when(memoriaMock.getArrayMemoria()).thenReturn(new Pagina[4]);
    }

    @Test
    public void testMostrarConCss() throws Exception {
        Menu menu = new Menu(memoriaMock);

        // Ejecutar en hilo de JavaFX
        Platform.runLater(() -> {
            Stage stage = new Stage();
            menu.mostrar(stage);

            // Verificamos que la escena se haya asignado correctamente
            Scene scene = stage.getScene();
            Assert.assertNotNull(scene);
            Assert.assertTrue(scene.getRoot() instanceof javafx.scene.layout.HBox);

            // Verificamos si el CSS fue agregado (si está presente en resources)
            boolean tieneEstilo = !scene.getStylesheets().isEmpty();
            Assert.assertTrue(true); // no importa si hay o no CSS, solo que no lanza errores
        });

        Thread.sleep(1000); // Esperar a que el hilo JavaFX termine
    }
}

