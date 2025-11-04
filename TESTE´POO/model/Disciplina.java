package model;

public class Disciplina {

    private String codigo;
    private String nome;
    private int cargaHoraria;
    private Curso curso;

    public Disciplina() {}

    public Disciplina(String codigo, String nome, int cargaHoraria, Curso curso) {
        this.codigo = codigo;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.curso = curso;
    }


    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(int cargaHoraria) { this.cargaHoraria = cargaHoraria; }

    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }

    @Override
    public String toString() {
        String cursoInfo = (curso != null) ? curso.getNome() + " (" + curso.getCodigo() + ")" : "Sem curso definido";
        return "DISCIPLINA: " + nome + " | Código: " + codigo + " | Carga: " + cargaHoraria + "h | Curso: " + cursoInfo;
    }
}
