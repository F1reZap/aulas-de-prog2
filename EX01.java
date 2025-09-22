import java.util.Random;
import java.util.Scanner;

public class EX01 {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Digite um numero: ");
        float num = teclado.nextInt();

        System.out.println("====================");
        isPositive(num);
    }
    public static boolean isPositive(float num){
        boolean isPos = false;
        boolean isNeg = false;

        if (num>0){
            isPos = true;
        } else if (num<=0) {
            isNeg = true;
        }

        if (isPos == true){
            System.out.println(num+". é positivo.");
        } else if (isNeg == true) {
            System.out.println(num+". é negativo");
        }

        return false;
    }
}
