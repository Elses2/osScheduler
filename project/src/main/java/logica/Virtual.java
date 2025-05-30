package logica;

public class Virtual implements Memoria {
    private static Virtual instancia = null;

    protected int tamano;
    protected Pagina[] arrayMemoria; //Array de paginas

    // Constructor privado
    private Virtual(int tamanoTotal) {
        this.tamano = Memoria.getTamanoArray(tamanoTotal);
        this.arrayMemoria = new Pagina[this.tamano]; //instancia un nuevo array con un tamaño establecido por la funcion getTamañoArray de memoria
    }

    // Método estático para obtener la única instancia
    public static Virtual getInstancia(int tamanoTotal) {
        if (instancia == null) {
            instancia = new Virtual(tamanoTotal);
        }
        return instancia;
    }

    @Override
    public Pagina setPagina(int idL, Elemento elemento) { //Agrega una nueva pagina siempre y cuando esten disponibles las paginas dentro del disco
        Pagina nuevaPagina = new Pagina(idL, elemento);
        for (int i = 0; i < arrayMemoria.length; i++) {
            if (arrayMemoria[i] == null) {
                arrayMemoria[i] = nuevaPagina;
                return nuevaPagina;
            }
        }
        throw new RuntimeException("Memoria Virtual llena, no se puede cargar más páginas.");
    }

    @Override
    public Pagina getPagina(int idL) { //Devuelve la pagina para luego hacer el intercambio
        for (Pagina pagina : arrayMemoria) {
            if (pagina != null && pagina.getIdL() == idL) {
                return pagina;
            }
        }
        return null;
    }

    public Pagina[] getArrayMemoria() {
        return arrayMemoria;
    }
}

