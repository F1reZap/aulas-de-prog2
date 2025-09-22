import java.util.Random;
import java.util.Scanner;

public class EX02 {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Digite um numero: ");
        float num = teclado.nextInt();

        System.out.println("====================");
        isCero(num);
    }
    public static void isCero(float num){
        boolean isCero = false;
        if (num==0){
            isCero = true;
        }

        if (isCero != true){
            System.out.println("não é nulo.");
        } else {
            System.out.println("é nulo.");
        }

    }
}
