package model;

import java.util.ArrayList;
import java.util.List;

public class Curso {

    private String codigo;
    private String nome;
    private String turno;
    private List<Disciplina> disciplinas;

    public Curso() {
        this.disciplinas = new ArrayList<>();
    }

    public Curso(String codigo, String nome, String turno) {
        this.codigo = codigo;
        this.nome = nome;
        this.turno = turno;
        this.disciplinas = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public void addDisciplina(Disciplina disciplina) {
        this.disciplinas.add(disciplina);
    }

    @Override
    public String toString() {
        return "CURSO: " + nome + " (Código: " + codigo + " | Turno: " + turno +
                " | Disciplinas cadastradas: " + disciplinas.size() + ")";
    }
}

