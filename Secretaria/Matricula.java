/**
 * Matricula: anoLetivo, serie, referência para Aluno, referência para Disciplina,
 * e quatro notas de bimestre.
 */
public class Matricula {
    private int anoLetivo;
    private String serie;
    private Aluno aluno;
    private Disciplina disciplina;
    private double notaBimestre1;
    private double notaBimestre2;
    private double notaBimestre3;
    private double notaBimestre4;

    public Matricula(int anoLetivo, String serie, Aluno aluno, Disciplina disciplina) {
        this.anoLetivo = anoLetivo;
        this.serie = serie;
        this.aluno = aluno;
        this.disciplina = disciplina;
        // Notas iniciadas em zero
        this.notaBimestre1 = 0.0;
        this.notaBimestre2 = 0.0;
        this.notaBimestre3 = 0.0;
        this.notaBimestre4 = 0.0;
    }

    public int getAnoLetivo() {
        return anoLetivo;
    }

    public String getSerie() {
        return serie;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public double getNota(int bimestre) {
        switch (bimestre) {
            case 1: return notaBimestre1;
            case 2: return notaBimestre2;
            case 3: return notaBimestre3;
            case 4: return notaBimestre4;
            default: throw new IllegalArgumentException("Bimestre inválido: " + bimestre);
        }
    }

    public void setNota(int bimestre, double valor) {
        switch (bimestre) {
            case 1: notaBimestre1 = valor; break;
            case 2: notaBimestre2 = valor; break;
            case 3: notaBimestre3 = valor; break;
            case 4: notaBimestre4 = valor; break;
            default: throw new IllegalArgumentException("Bimestre inválido: " + bimestre);
        }
    }

    public double media() {
        return (notaBimestre1 + notaBimestre2 + notaBimestre3 + notaBimestre4) / 4.0;
    }

    @Override
    public String toString() {
        return "Matricula{" +
                "ano=" + anoLetivo +
                ", serie='" + serie + '\'' +
                ", aluno=" + aluno +
                ", disciplina=" + disciplina +
                ", notas=[" + notaBimestre1 + ", " + notaBimestre2 + ", " + notaBimestre3 + ", " + notaBimestre4 + "]" +
                '}';
    }
}