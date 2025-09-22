import java.util.Random;
import java.util.Scanner;

public class EX04 {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Random random = new Random();

        int a, b, c;

        System.out.print("Digite um numero: ");
        a = teclado.nextInt();
        System.out.println(".");
        System.out.print("Digite um numero: ");
        b = teclado.nextInt();
        System.out.println(".");
        System.out.print("Digite um numero: ");
        c = teclado.nextInt();


        System.out.println("====================");
        con(a, b, c);
    }

    private static void con(int a, int b, int c) {

        double delta = Math.pow(b, 2)-4*a*c;

        System.out.println("O valor da equação é: "+delta);
    }

}

