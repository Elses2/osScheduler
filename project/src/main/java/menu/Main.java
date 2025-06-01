package menu;

import javafx.application.Application;
import javafx.stage.Stage;
import logica.*;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        iniciarApp(stage); // extraído para facilitar testing
    }

    public void iniciarApp(Stage stage) {
        Ram ram = Ram.getInstancia(16);
        Menu menu = new Menu(ram);
        menu.mostrar(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

