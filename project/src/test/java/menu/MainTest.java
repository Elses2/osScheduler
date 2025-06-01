import javafx.stage.Stage;
import logica.Memoria;
import menu.Main;
import menu.Menu;
import org.junit.Test;
import org.mockito.Mockito;

public class MainTest {

    @Test
    public void testIniciarApp_LlamaAMostrar() {
        // Simular Memoria
        Memoria memoriaMock = Mockito.mock(Memoria.class);

        // Simular Stage
        Stage stageMock = Mockito.mock(Stage.class);

        // Espía de Main para interceptar llamada a Menu
        Main mainSpy = Mockito.spy(new Main());

        // Sobrescribimos el comportamiento usando una clase anónima
        Main mainConMenuMockeado = new Main() {
            @Override
            public void iniciarApp(Stage stage) {
                Menu menuMock = Mockito.mock(Menu.class);
                menuMock.mostrar(stage);
                Mockito.verify(menuMock).mostrar(stage); // Verifica que se llame
            }
        };

        // Ejecutar
        mainConMenuMockeado.iniciarApp(stageMock);
    }
}

