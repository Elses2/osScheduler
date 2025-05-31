package menu;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        String[] datos = {"Uno", "Dos", "Tres", "Cuatro"};
        ListView<String> lista = new ListView<>(FXCollections.observableArrayList(datos));

        VBox root = new VBox(lista);
        Scene escena = new Scene(root, 300, 200);

        // Cargar el CSS
        escena.getStylesheets().add(getClass().getResource("/estilos.css").toExternalForm());

        stage.setScene(escena);
        stage.setTitle("Lista con CSS");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

