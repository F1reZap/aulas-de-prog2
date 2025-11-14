/**
 * Aluno: código sequencial (int) e nome.
 */
public class Aluno {
    private int codigo;
    private String nome;

    public Aluno(int codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return codigo + " - " + nome;
    }
}