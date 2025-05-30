public class Ram implements Memoria {
    private static Ram instancia = null;

    protected int tamano;
    protected Pagina[] arrayMemoria;

    private Ram(int tamanoTotal) {
        this.tamano = Memoria.getTamanoArray(tamanoTotal);
        this.arrayMemoria = new Pagina[this.tamano];
    }

    public static Ram getInstancia(int tamanoTotal) {
        if (instancia == null) {
            instancia = new Ram(tamanoTotal);
        }
        return instancia;
    }

    @Override
    public Pagina setPagina(int idL, Elemento elemento) {
        Pagina nuevaPagina = new Pagina(idL, elemento);
        for (int i = 0; i < arrayMemoria.length; i++) {
            if (arrayMemoria[i] == null) {
                arrayMemoria[i] = nuevaPagina;
                return nuevaPagina;
            }
        }
        throw new RuntimeException("RAM llena, no se puede cargar más páginas.");
    }

    @Override
    public Pagina getPagina(int idL) {
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

