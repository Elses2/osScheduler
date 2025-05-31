package menu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        HBox root = new HBox(20); // contenedor horizontal

        // Pila 1
        VBox pila1 = new VBox(10);
        pila1.getChildren().addAll(new Label("P1-A"), new Label("P1-B"));

        // Pila 2
        VBox pila2 = new VBox(10);
        pila2.getChildren().addAll(new Label("P2-A"), new Label("P2-B"), new Label("P2-C"));

        // Matriz (2 columnas, 3 filas)
        GridPane matriz = new GridPane();
        matriz.setHgap(10);
        matriz.setVgap(10);
        String[][] datos = {
            {"Fila 1 Col 1", "Fila 1 Col 2"},
            {"Fila 2 Col 1", "Fila 2 Col 2"},
            {"Fila 3 Col 1", "Fila 3 Col 2"}
        };
        for (int fila = 0; fila < datos.length; fila++) {
            for (int col = 0; col < datos[fila].length; col++) {
                matriz.add(new Label(datos[fila][col]), col, fila);
            }
        }

        // Agregamos todo al HBox
        root.getChildren().addAll(pila1, pila2, matriz);

        // Mostrar escena
        Scene scene = new Scene(root, 600, 300);
        stage.setScene(scene);
        stage.setTitle("2 Pilas + 1 Matriz");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

