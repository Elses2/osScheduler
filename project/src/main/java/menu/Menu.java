package menu;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Menu {

    public void mostrar(Stage stage) {
        StackPane root = new StackPane();
        root.getStyleClass().add("fondo");

        Label label = new Label("Hola");
        label.getStyleClass().add("mensaje-hola");

        root.getChildren().add(label);

        Scene scene = new Scene(root, 600, 400); // Tamaño de ventana
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
    
        stage.setTitle("Mi Aplicación");
        stage.setScene(scene);
        stage.show();
    }
}
                       
