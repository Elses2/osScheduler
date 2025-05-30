public class Pagina {
    private int idL;           // Dirección lógica
    private Elemento elemento; // Contenido

    public Pagina(int idL, Elemento elemento) {
        this.idL = idL;
        this.elemento = elemento;
    }

    public Elemento getElemento() {
        return elemento;
    }

    public int getIdL() {
        return idL;
    }

    public int getIdP() {
        return elemento.getIdP();
    }
}

