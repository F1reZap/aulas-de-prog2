import java.util.Scanner;

public class EX08 {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        System.out.print("Digite um numero: ");
        int n=teclado.nextInt();

        while (n<=0) {
            System.out.print("O numero deve ser inteiro positivo. Digite outro: ");
            n=teclado.nextInt();
        }

        int resultado=fatorial(n);
        System.out.println(n+"! = "+resultado);
    }
    public static int fatorial(int n) {
        int fat=1;
        for (int i=1; i<=n; i++) {
            fat*=i;
        }
        return fat;
    }
}