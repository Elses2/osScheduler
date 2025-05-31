package menu;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.Node;
import logica.Pagina;

public class MostrarMemoria {
    private VBox grafico;       // Representación visual
    private Pagina[] array;     // Array de páginas a representar

    public MostrarMemoria(Pagina[] array) {
        this.array = array;
        this.grafico = new VBox(5); // Espaciado entre celdas
        representar();
    }

    protected void representar() {
        grafico.getChildren().clear();

        for (Pagina pagina : array) {
            HBox celda = new HBox();
            celda.setAlignment(Pos.CENTER);
            celda.getStyleClass().add(pagina.getEstado() ? "celda-libre" : "celda-ocupada");

            String texto = pagina.getEstado() ? "Libre" : "proceso:" + pagina.getIdP();
            Label label = new Label(texto);
            label.getStyleClass().add("texto-celda");

            celda.getChildren().add(label);
            grafico.getChildren().add(celda);
        }
    }

    public VBox getTabla() {
        return grafico;
    }
}

