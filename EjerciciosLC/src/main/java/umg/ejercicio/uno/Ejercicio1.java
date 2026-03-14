package umg.ejercicio.uno;

public class Ejercicio1 {
	static int[] unocadapar = {1,2,3,4,5};
	static int[] trescadaimparmenoscinco = {17,19,21,2,4,6};
	static int[] cincocadacinco = {5,5,5,2,5,2};
	
	public static void main(String[] args) {
		
		System.out.println("La cantidad de pares en el primer arreglo es: ");
        System.out.println(score(unocadapar, trescadaimparmenoscinco, cincocadacinco));
        System.out.println("La cantidad de impares exepto 5 en el segundo arreglo es: ");}
		
	
	  public static int score(int[] unocadapar, int[] trescadaimparmenoscinco, int[] cincocinco) {
	        int count = 0;
	        
	        for (int i = 0; i < unocadapar.length; i++) {
				if (unocadapar[i] % 2 == 0) {
					count++;
				}
	        }	
	        for (int i = 0; i < trescadaimparmenoscinco.length; i++) {
	        				if (trescadaimparmenoscinco[i] % 2 != 0 && trescadaimparmenoscinco[i] - 5 < 0) {
	        									
	        				}
	        }
			      return count; 
	    }
	    
	}

