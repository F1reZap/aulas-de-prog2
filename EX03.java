import java.util.Random;
import java.util.Scanner;

public class EX03 {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Digite um numero: ");
        float num = teclado.nextInt();

        System.out.println("====================");
        isEven(num);
    }
    public static void isEven(float num){
        boolean isEven = false;
        if (num%2==0){
            isEven = true;
        }

        if (isEven!=true){
            System.out.println("é impar pae.");
        } else {
            System.out.println("é par.");
        }

    }
}
