package logica;

public interface Memoria {
    static int getTamanoArray(int tamano) {
        if (tamano % 4 != 0) {
            throw new IllegalArgumentException("El tamaño debe ser múltiplo de 4.");
        }
        return tamano / 4;
    }

    Pagina setPagina(int idL, Elemento elemento);
    Pagina getPagina(int idL);
    public Pagina[] getArrayMemoria();
}
         
