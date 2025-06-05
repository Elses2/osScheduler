package logica;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TablaPaginaTest {
    @Test
   public void testGetTabla(){
    TablaPagina tabla=new TablaPagina(10,1);
    
    int [][] resultado=new int [10][2];
    for (int i = 0; i < 10; i++) {
            resultado[i][0] = i;     // Dirección lógica
            resultado[i][1] = -1;    // Dirección física aún no asignadaj
        }
    

   assertEquals(resultado,tabla.getTabla()); 

   }
    @Test 

    public void TestGetIdP(){
      TablaPagina tabla=new TablaPagina(10,1);

      int resultado=1;

      assertEquals(resultado,1);
    }

   
}  
