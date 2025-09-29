import java.util.Scanner;

public class EX01 {
    // Vetores globais
    static String[] VetNome = new String[100];
    static String[] VetAutor = new String[100];
    static String[] VetDesc = new String[100];
    static int[] VetAno = new int[100];
    static double[] VetPrec = new double[100];
    static int qtdLivros = 0; // contador de livros cadastrados

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        int esval = 1;

        while (esval != 0) {
            System.out.println("===Cadastro de Livros===");
            System.out.println("Opções: ");
            System.out.println("(1) Cadastrar Livro");
            System.out.println("(2) Listar Cadastros");
            System.out.println("(0) Sair");
            System.out.println("========================");
            System.out.print("Escolha: ");
            int es = teclado.nextInt();
            teclado.nextLine();
            System.out.println("========================");

            if (es == 0) {
                System.out.println("===Saindo===");
                esval = 0;

            } else if (es == 1) {
                Cadastro(teclado);

            } else if (es == 2) {
                Listar();

            } else {
                System.out.println("========================");
                System.out.println("===Escolha Inválida===");
                System.out.println("========================");
            }
        }
        teclado.close();
    }

    public static void Cadastro(Scanner teclado) {
        if (qtdLivros >= 100) {
            System.out.println("Limite máximo de livros atingido.");
            return;
        }
        System.out.print("Nome: ");
        VetNome[qtdLivros] = teclado.nextLine();
        System.out.print("Autor: ");
        VetAutor[qtdLivros] = teclado.nextLine();
        System.out.print("Ano: ");
        VetAno[qtdLivros] = teclado.nextInt();
        teclado.nextLine(); // Limpa buffer
        System.out.print("Descrição: ");
        VetDesc[qtdLivros] = teclado.nextLine();
        System.out.print("Preço: ");
        VetPrec[qtdLivros] = teclado.nextDouble();

        System.out.println("========================");

        teclado.nextLine(); // Limpa buffer

        qtdLivros++;
        System.out.println("Livro cadastrado com sucesso!\n");
        System.out.println("========================");

    }

    public static void Listar() {
        if (qtdLivros == 0) {
            System.out.println("========================");
            System.out.println("Nenhum livro cadastrado.");
            System.out.println("========================");
            return;
        }
        for (int i = 0; i < qtdLivros; i++) {
            System.out.println("Livro " + (i + 1));
            System.out.println("Nome: " + VetNome[i]);
            System.out.println("Autor: " + VetAutor[i]);
            System.out.println("Ano: " + VetAno[i]);
            System.out.println("Descrição: " + VetDesc[i]);
            System.out.println("Preço: " + VetPrec[i]);
            System.out.println("========================");
        }
    }
}