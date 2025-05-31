import javafx.application.Application;
import javafx.stage.Stage;
import menu.Menu;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Menu menu = new Menu();
        menu.mostrar(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}

