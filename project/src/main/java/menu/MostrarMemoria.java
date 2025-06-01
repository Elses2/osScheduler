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
    /**
     * Actualiza la representación visual de las páginas.
     * Limpia el VBox y vuelve a crear las celdas para cada página.
     */
    protected void representar() {
    grafico.getChildren().clear();

    for (int i = 0; i < array.length; i++) {
        Pagina pagina = array[i];

        HBox celda = new HBox(10); // Espaciado entre elementos dentro de la celda
        celda.setAlignment(Pos.CENTER_LEFT);

        Label indiceLabel = new Label(i + ":");
        indiceLabel.getStyleClass().add("texto-celda");

        Label label;

        if (pagina != null) {
            celda.getStyleClass().add(pagina.getEstado() ? "celda-libre" : "celda-ocupada");
            String texto = pagina.getEstado() ? "Libre" : "proceso: " + pagina.getIdP();
            label = new Label(texto);
        } else {
            celda.getStyleClass().add("celda-libre"); // o una clase diferente si querés marcarlo como "null"
            label = new Label("Vacío"); // o "Libre", o lo que tenga sentido en tu contexto
        }

        label.getStyleClass().add("texto-celda");

        celda.getChildren().addAll(indiceLabel, label);
        grafico.getChildren().add(celda);
    }
}
    /**
     * Devuelve el VBox que contiene la representación visual de las páginas.
     * @return VBox con la representación de las páginas.
     */

    public VBox getTabla() {
        return grafico;
    }
}
      
