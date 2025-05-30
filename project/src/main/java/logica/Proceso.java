import java.util.*;

public class Proceso {
    private int idProceso;
    private List<Elemento> elementos; // Composición de elementos

    public Proceso(int idProceso) {
        this.idProceso = idProceso;
        this.elementos = new ArrayList<>();
        cargarElementosPorDefecto(); // Carga automática de elementos por defecto
    }

    public int getIdProceso() {
        return idProceso;
    }

    // Carga automática de 10 elementos al crear el proceso
    private void cargarElementosPorDefecto() {
        for (int i = 1; i <= 10; i++) {
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
