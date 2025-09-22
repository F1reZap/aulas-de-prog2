import java.util.Scanner;

public class EX09 {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        System.out.print("Digite o valor de n (inteiro positivo): ");
        int n = teclado.nextInt();
        System.out.print("Digite o valor de k (inteiro positivo): ");
        int k = teclado.nextInt();

        while (n <= 0) {
            System.out.print("O valor de n deve ser inteiro positivo. Digite novamente: ");
            n = teclado.nextInt();
        }
        while (k < 0 || k > n) {
            System.out.print("k deve ser inteiro positivo e menor ou igual a n. Digite novamente: ");
            k = teclado.nextInt();
        }

        int rstPerm = permutacoes(n);
        System.out.println("Permutações = " + rstPerm);

        int rstArnj = arranjos(n, k);
        System.out.println("Arranjos = " + rstArnj);

        int rstComb = combinacoes(n, k);
        System.out.println("Combinações = " + rstComb);
    }
    public static int fatorial(int n) {
        int fat = 1;
        for (int i = 1; i <= n; i++) {
            fat *= i;
        }
        return fat;
    }
    public static int permutacoes(int n) {
        return fatorial(n);
    }
    public static int arranjos(int n, int k) {
        return fatorial(n) / fatorial(n - k);
    }
    public static int combinacoes(int n, int k) {
        return fatorial(n) / (fatorial(k) * fatorial(n - k));
    }
}
