package logica;
public class TablaPagina {

    private Ram ram;        // Segmento de memoria RAM (singleton)
    private int idP;        // ID del proceso
    private int[][] tabla;  // Tabla de paginación [dirección lógica][dirección física]

    // Constructor
    public TablaPagina(int cantidadElementos, int idProceso) {
        this.idP = idProceso;
        this.ram = Ram.getInstancia(1024); // Por defecto usa 1024, se puede ajustar externamente

        // Cada fila representa una dirección lógica (0 a cantidadElementos - 1)
        // Columna 0: dirección lógica
        // Columna 1: dirección física (índice en RAM)
        this.tabla = new int[cantidadElementos][2];

        for (int i = 0; i < cantidadElementos; i++) {
            tabla[i][0] = i;     // Dirección lógica
            tabla[i][1] = -1;    // Dirección física aún no asignadaj
        }
    }

    // Actualiza la tabla de paginación con base en el contenido actual de RAM
    public void actualizar() {
        Pagina[] memoria = ram.getArrayMemoria();

        for (int i = 0; i < memoria.length; i++) {
            Pagina pagina = memoria[i];
            if (pagina != null && pagina.getElemento() != null) {
                Elemento elemento = pagina.getElemento();

                if (elemento.getIdP() == this.idP) {
                    int idL = elemento.getIdL();
                    if (idL >= 0 && idL < tabla.length) {
                        tabla[idL][1] = i; // índice físico en RAM
                    }
                }
            }
        }
    }

    // Métodos de utilidad opcionales

    // Devuelve la tabla
    public int[][] getTabla() {
        return tabla;
    }

    // Imprime la tabla de paginación (útil para debug)
    public void imprimirTabla() {
        System.out.println("Tabla de Paginación del Proceso ID: " + idP);
        System.out.println("Lógica\tFísica");
        for (int[] fila : tabla) {
            System.out.println(fila[0] + "\t" + fila[1]);
        }
    }

    // Getter del ID del proceso
    public int getIdP() {
        return idP;
    }
}

