package logica;

public class Pagina {
    private int idL;           // Dirección lógica
    private Elemento elemento; // Contenido
    private boolean estado;// libre=true   ocupado=false 

    public Pagina(int idL, Elemento elemento) {
        this.idL = idL;
        this.elemento = elemento;
        calcEstado(); // Actualiza el estado automáticamente al crear la página
    }

    public Elemento getElemento() {
        return elemento;
    }

    public int getIdL() {
        return idL;
    }

    public int getIdP() {
    if (elemento != null) {
        return elemento.getIdP();
    } else {
        // Depende de lo que quieras devolver si no hay elemento, por ejemplo:
        return -1; // o lanzar una excepción personalizada
    }
}



    public void calcEstado(){
      if(elemento==null){

        this.estado=true; 
      }else{

        this.estado=false;
      }
    }
    public boolean getEstado(){
      return estado;

    }
}
  
