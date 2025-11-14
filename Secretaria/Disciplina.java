/**
 * Disciplina: código único (String), nome, carga horária (int) e flag prática (opcional).
 */
public class Disciplina {
    private String codigo;
    private String nome;
    private int cargaHoraria;
    private boolean pratica;

    public Disciplina(String codigo, String nome, int cargaHoraria, boolean pratica) {
        this.codigo = codigo;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.pratica = pratica;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public boolean isPratica() {
        return pratica;
    }

    @Override
    public String toString() {
        return codigo + " - " + nome + " (" + cargaHoraria + "h" + (pratica ? ", prática" : "") + ")";
    }
}