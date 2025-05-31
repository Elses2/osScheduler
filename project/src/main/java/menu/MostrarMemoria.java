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

        for (int i = 0; i < array.length; i++) {
            Pagina pagina = array[i];

            HBox celda = new HBox(10); // Espaciado entre elementos dentro de la celda
            celda.setAlignment(Pos.CENTER_LEFT);
            celda.getStyleClass().add(pagina.getEstado() ? "celda-libre" : "celda-ocupada");

            Label indiceLabel = new Label(i + ":");
            indiceLabel.getStyleClass().add("texto-celda");

            String texto = pagina.getEstado() ? "Libre" : "proceso: " + pagina.getIdP();
            Label label = new Label(texto);
            label.getStyleClass().add("texto-celda");

            celda.getChildren().addAll(indiceLabel, label);
            grafico.getChildren().add(celda);
        }
    }

    public VBox getTabla() {
        return grafico;
    }
}

