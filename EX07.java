import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

public class EX07 {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Random random = new Random();

        int n;

        System.out.print("Digite o numero de casas no Vetor: ");
        n = teclado.nextInt();

        int[] vet = new int[n];

        System.out.println("====================");

        for (int i=0; i<vet.length; i++){
            System.out.print("Digite um numero: ");
            vet[i] = teclado.nextInt();
        }

        System.out.println("====================");
        con(vet);
    }
    private static void con(int[] vet) {
        System.out.println(Arrays.toString(vet));
        double med1=0;
        for (int i=0; i< vet.length; i++){
            med1=med1+vet[i];
        }
        double med2=med1/vet.length;

        System.out.println("A média do vetor é: "+med2);
    }
}
