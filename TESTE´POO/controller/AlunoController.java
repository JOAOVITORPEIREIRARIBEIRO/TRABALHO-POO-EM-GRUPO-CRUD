package controller;

import dao.AlunoDAO;
import dao.CursoDAO;
import model.Aluno;
import model.Curso;
import view.AlunoView;
import view.CursoView;

import java.util.List;

public class AlunoController {

    public static void Criar() {
        Aluno aluno = new Aluno();

        // Seleciona um curso existente
        Curso cursoSelecionado = CursoView.SelecionarCurso(CursoDAO.GetAll());
        if (cursoSelecionado == null) {
            System.out.println("Nenhum curso selecionado. Criação de aluno cancelada.");
            return;
        }
        aluno.setCurso(cursoSelecionado);

        AlunoView.Criar(aluno);

        if (aluno != null && aluno.getMatricula() != null && !aluno.getMatricula().trim().isEmpty()) {
            try {
                AlunoDAO.Add(aluno);
                System.out.println("Aluno criado e salvo no banco!");
            } catch (Exception e) {
                System.err.println("Erro ao salvar aluno no banco: " + e.getMessage());
            }
        } else {
            System.out.println("Criação de aluno cancelada ou inválida.");
        }
    }

    public static void Consultar() {
        String matricula = AlunoView.GetMatricula();

        try {
            Aluno aluno = AlunoDAO.Get(matricula);
            AlunoView.Consultar(aluno);

            if (aluno != null && aluno.getCurso() != null) {
                System.out.println("Curso do aluno: " + aluno.getCurso().getNome());
                System.out.println("Disciplinas do curso:");
                for (var d : aluno.getCurso().getDisciplinas()) {
                    System.out.println(" - " + d.getNome() + " (" + d.getCargaHoraria() + "h)");
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao consultar aluno: " + e.getMessage());
            AlunoView.Consultar(null);
        }
    }

    public static void Listar() {
        try {
            List<Aluno> alunos = AlunoDAO.GetAll();
            AlunoView.Listar(alunos);

            for (Aluno a : alunos) {
                if (a.getCurso() != null) {
                    System.out.println("Curso: " + a.getCurso().getNome());
                    System.out.println("Disciplinas do curso:");
                    for (var d : a.getCurso().getDisciplinas()) {
                        System.out.println(" - " + d.getNome() + " (" + d.getCargaHoraria() + "h)");
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao listar alunos: " + e.getMessage());
        }
    }

    public static void Atualizar() {
        String matricula = AlunoView.GetMatricula();

        try {
            Aluno aluno = AlunoDAO.Get(matricula);
            if (aluno != null) {

                // Permitir trocar o curso se desejar
                Curso cursoSelecionado = CursoView.SelecionarCurso(CursoDAO.GetAll());
                if (cursoSelecionado != null) {
                    aluno.setCurso(cursoSelecionado);
                }

                AlunoView.Atualizar(aluno);
                AlunoDAO.Update(aluno);
                System.out.println("Aluno atualizado com sucesso!");
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar aluno: " + e.getMessage());
        }
    }

    public static void Deletar() {
        String matricula = AlunoView.GetMatricula();

        try {
            AlunoDAO.Delete(matricula);
            System.out.println("Aluno com matrícula " + matricula + " deletado do banco.");
        } catch (Exception e) {
            System.err.println("Erro ao deletar aluno: " + e.getMessage());
        }
    }
}


