import java.util.Random;
import java.util.Scanner;

public class EX05 {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Random random = new Random();

        int a, b, c;

        System.out.print("Digite um numero: ");
        a = teclado.nextInt();
        System.out.print("Digite um numero: ");
        b = teclado.nextInt();
        System.out.print("Digite um numero: ");
        c = teclado.nextInt();

        while (a==0){
            System.out.println("'a' nn pode ser zero");
            System.out.print("Digite um numero novo para 'a': ");
            a = teclado.nextInt();
        }

        System.out.println("====================");
        con(a, b, c);
    }
    private static void con(int a, int b, int c) {
        double delta=Math.pow(b, 2)-4*a*c;
        System.out.println("Delta = "+delta);

        if (delta>0) {
            double x1, x2;
            x1=(-b+Math.sqrt(delta))/(2.0*a);
            x2=(-b-Math.sqrt(delta))/(2.0*a);
            System.out.println("Raízes reais e diferentes:");
            System.out.println("x1 = "+x1);
            System.out.println("x2 = "+x2);
        } else if (delta==0) {
            double x =-b/(2.0*a);
            System.out.println("Raízes reais e iguais:");
            System.out.println("x = "+x);
        } else {
            double ptReal, ptImg;
            ptReal =-b/(2.0*a);
            ptImg =Math.sqrt(-delta)/(2.0 * a);
            System.out.println("Raízes complexas: ");
            System.out.println("x1 = "+ ptReal +" + "+ ptImg +"i");
            System.out.println("x2 = "+ ptReal +" - "+ ptImg +"i");
        }
    }

}
