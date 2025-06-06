package logica;
import java.util.*;

public class Proceso {
    private int idProceso;
    private List<Elemento> elementos; // Composición de elementos, 
    private int cantidadElementos;

    public Proceso(int idProceso, int cantidadElementos) {
        this.idProceso = idProceso;
        this.cantidadElementos = cantidadElementos;
        this.elementos = new ArrayList<>();
        cargarElementos(); // Carga automática de elementos
    }

    public int getIdProceso() {
        return idProceso;
    }

    // Carga automática de elementos al crear el proceso
    private void cargarElementos() {
        for (int i = 1; i <= this.cantidadElementos; i++) {
            // Usa el id del proceso actual para el idP y un valor arbitrario (puedes cambiarlo)
            elementos.add(new Elemento(i, 0, idProceso));
        }
    }
    
    public void agregarElemento(Elemento elemento) {
        elementos.add(elemento);
    }

    public void eliminarElemento(Elemento elemento) {
        elementos.remove(elemento);
    }
    List<Elemento> getElementos() {
    return elementos;

    }

    public void mostrarElementos() {
        if (elementos.isEmpty()) {
            System.out.println("No hay elementos en el proceso " + idProceso + ".");
        } else {
            System.out.println("Elementos del proceso " + idProceso + ":");
            for (Elemento e : elementos) {
                System.out.println("idL: " + e.getIdL() + ", valor: " + e.getValor() + ", idP: " + e.getIdP());
            }
        }
    }

    public int getCantidadaElementos() {
        return elementos.size();
    }
}
