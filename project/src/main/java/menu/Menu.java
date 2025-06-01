package menu;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.net.URL;
import logica.*;
 

public class Menu {

    private Memoria memoria;

    public Menu(Memoria memoria) { 
        this.memoria = memoria;
    }

    public void mostrar(Stage stage) {
        // Obtener array de memoria desde el objeto Memoria (Ram en este caso)
        Pagina[] array = memoria.getArrayMemoria();

        // Crear MostrarMemoria
        MostrarMemoria mostrar = new MostrarMemoria(array);
        VBox graficoRam = mostrar.getTabla(); // VBox con celdas

        // Añadir a un HBox (contenedor general para más elementos en el futuro)
        HBox contenedor = new HBox();
        contenedor.getChildren().add(graficoRam);

        Scene scene = new Scene(contenedor, 600, 400);
        URL styleUrl = getClass().getResource("/style.css");
        if (styleUrl != null) {
           scene.getStylesheets().add(styleUrl.toExternalForm());
        } else {
            System.out.println("style.css no encontrado");
        }     
        stage.setTitle("Mi Aplicación");
        stage.setScene(scene);
        stage.show();
    }
}

