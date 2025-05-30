public class Elemento {
    private int idL;     // ID lógica
    private int valor;   // Valor encapsulado
    private int idP;     // ID del proceso al que pertenece

    public Elemento(int idL, int valor, int idP) {
        this.idL = idL;
        this.valor = valor;
        this.idP = idP;
    }

    protected int getValor() {
        return valor;
    }

    protected int getIdL() {
        return idL;
    }

    protected int getIdP() {
        return idP;
    }
}

