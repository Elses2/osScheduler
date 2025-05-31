package menu;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import menu.MostrarMemoria;
import logica.Pagina;
import org.junit.BeforeClass;
import org.junit.Test;

public class MostrarMemoriaTest {

    @BeforeClass
    public static void initJFX() {
        // Inicializa el entorno JavaFX para pruebas
        new JFXPanel();
    }

    @Test
    public void testMostrarMemoriaCompleto() {
        // Mock de dos páginas: una libre y otra ocupada
        Pagina libre = mock(Pagina.class);
        when(libre.getEstado()).thenReturn(true);
        when(libre.getIdP()).thenReturn(-1); // no usado si está libre

        Pagina ocupada = mock(Pagina.class);
        when(ocupada.getEstado()).thenReturn(false);
        when(ocupada.getIdP()).thenReturn(42);

        Pagina[] paginas = new Pagina[]{libre, ocupada};

        // Instancia la clase
        MostrarMemoria mostrar = new MostrarMemoria(paginas);
        VBox tabla = mostrar.getTabla();

        // Debe haber dos elementos (VBox con 2 HBox dentro)
        assertEquals(2, tabla.getChildren().size());

        // Verifica la celda 0 (libre)
        Node nodo0 = tabla.getChildren().get(0);
        assertTrue(nodo0 instanceof HBox);
        HBox hbox0 = (HBox) nodo0;

        assertEquals(2, hbox0.getChildren().size());
        Label indice0 = (Label) hbox0.getChildren().get(0);
        Label texto0 = (Label) hbox0.getChildren().get(1);

        assertEquals("0:", indice0.getText());
        assertEquals("Libre", texto0.getText());
        assertTrue(hbox0.getStyleClass().contains("celda-libre"));

        // Verifica la celda 1 (ocupada)
        Node nodo1 = tabla.getChildren().get(1);
        assertTrue(nodo1 instanceof HBox);
        HBox hbox1 = (HBox) nodo1;

        assertEquals(2, hbox1.getChildren().size());
        Label indice1 = (Label) hbox1.getChildren().get(0);
        Label texto1 = (Label) hbox1.getChildren().get(1);

        assertEquals("1:", indice1.getText());
        assertEquals("proceso: 42", texto1.getText());
        assertTrue(hbox1.getStyleClass().contains("celda-ocupada"));
    }
}

