import java.util.Random;
import java.util.Scanner;

public class EX00 {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Digite um numero: ");
        int n = teclado.nextInt();

        System.out.println("====================");
        numero(n);
    }
    public static void numero (int n){
        if (n <= 0 || n > 12){
            System.out.println("Não existe tal Mês no nosso calendário atual.");
        } else {
            if (n ==1){
                System.out.println("Mês: Janeiro.");
            } else if (n ==2) {
                System.out.println("Mês: Fevereiro.");
            } else if (n ==3) {
                System.out.println("Mês: Março.");
            } else if (n ==4) {
                System.out.println("Mês: Abril.");
            } else if (n ==5) {
                System.out.println("Mês: Maio.");
            } else if (n ==6) {
                System.out.println("Mês: Junho.");
            } else if (n ==7) {
                System.out.println("Mês: julho.");
            } else if (n ==8) {
                System.out.println("Mês: Agosto.");
            } else if (n ==9) {
                System.out.println("Mês: Setembro.");
            } else if (n ==10) {
                System.out.println("Mês: Outubro.");
            } else if (n ==11) {
                System.out.println("Mês: Novembro.");
            } else if (n ==12) {
                System.out.println("Mês: Dezembro.");
            } else {
                System.out.println("...XXXXX... ERRO ...XXXXX...");
            }
        }
    }
}