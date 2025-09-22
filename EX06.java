import java.util.Random;
import java.util.Scanner;

public class EX06 {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Random random = new Random();

        int a, b;

        System.out.print("Digite um numero: ");
        a = teclado.nextInt();
        System.out.print("Digite um numero: ");
        b = teclado.nextInt();

        System.out.println("====================");
        con(a, b);
    }
    private static void con(int a, int b) {
        if (a>b){
            System.out.println("Maior numero é 'a' = "+a);
        } else if (a<b) {
            System.out.println("Maior numero é 'b' = "+b);
        } else {
            System.out.println("numeros iguais");
        }
    }
}
