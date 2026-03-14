package umg.ejercicio.dos;
import java.util.Arrays;
public class Ejercicio2 {
	public static void main(String[] args ) {
		int[] numbers = {7,2,9,4,1,8};
		int[] resultado = secondMinMax(numbers);
		System.out.println("Arreglo original: " + Arrays.toString(numbers));
		System.out.println("Resultado: " + Arrays.toString(resultado));
	}
	public static int[] secondMinMax(int[] numbers) {
		
		
        int min = Integer.MAX_VALUE;
        int secondMin = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int secondMax = Integer.MIN_VALUE;

        for (int n : numbers) {
           
            if (n > max) {
                secondMax = max; 
                max = n;         
            } else if (n > secondMax && n != max) {
                secondMax = n;   
            }

           
            if (n < min) {
                secondMin = min; 
                min = n;        
            } else if (n < secondMin && n != min) {
                secondMin = n;   //
            }
        }

        return new int[]{secondMin, secondMax};
    }
}