import java.util.*;

public class Proceso {
    private int idProceso;
    private List<Elementos> elementos; //Composicion de elementos 

    public Proceso(int idProceso) {
        this.idProceso = idProceso;
        this.elementos = new ArrayList<>();
        cargarElementosPorDefecto(); //metodo llamado en el constructor para que se haga por defecto de manera statica
    }

    // Getter
    public int getIdProceso() {
        return idProceso;
    }

    // Carga automática de 10 elementos al crear el proceso
    private void cargarElementosPorDefecto() {
        for (int i = 1; i <= 10; i++) {
            elementos.add(new Elementos(i));
        }
    }
    
    public void agregarElemento(Elementos elemento) {
        elementos.add(elemento);
    }

    public void eliminarElemento(Elementos elemento) {
        elementos.remove(elemento);
    }

    public void mostrarElementos() { //Metodo que muestran los 10 elementos dentro del proceso
        if (elementos.isEmpty()) {
            System.out.println("No hay elementos en el proceso " + idProceso + ".");
        } else {
            System.out.println("Elementos del proceso " + idProceso + ":");
            for (Elementos e : elementos) {
                System.out.println(e);
            }
        }
    }
    public int getCantidadaElementos(){ //Devuelve la cantidad de elementos dentro del proceso
        return elementos.size();
    }
}

