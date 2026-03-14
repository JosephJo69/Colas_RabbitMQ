package umg.ejercicio.uno;

public class Ejercicio1 {
	static int[] numbers = {0,1,2,3,4,5,6,7,8,9,10};
	
	
	public static void main(String[] args) {
		
		System.out.println("El punteo es:  ");
        System.out.println(score(numbers));}
        
		
	
	public static int score(int[] numbers) {
        int totalScore = 0;

        for (int n : numbers) {
            if (n == 5) {
                totalScore += 5;
            } else if (n % 2 == 0) {
                totalScore += 1;
            } else {
                totalScore += 3; 
            }
        }

        return totalScore;
    }
}
