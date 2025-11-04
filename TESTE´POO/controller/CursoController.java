package controller;

import dao.CursoDAO;
import model.Curso;
import view.CursoView;

import java.util.List;

public class CursoController {

    public static void Criar() {
        Curso curso = new Curso();
        CursoView.Criar(curso);

        if (curso != null && curso.getCodigo() != null && !curso.getCodigo().trim().isEmpty()) {
            CursoDAO.Add(curso);
            System.out.println("Curso criado e salvo no banco.");
        } else {
            System.out.println("Criação de curso cancelada ou inválida.");
        }
    }

    public static void Consultar() {
        String codigo = CursoView.GetCodigo();
        Curso curso = CursoDAO.Get(codigo);

        if (curso != null) {
            CursoView.Consultar(curso);
        } else {
            System.out.println("Curso com código " + codigo + " não encontrado.");
        }
    }

    public static void Listar() {
        List<Curso> cursos = CursoDAO.GetAll();
        CursoView.Listar(cursos);
    }

    public static void Atualizar() {
        String codigo = CursoView.GetCodigo();
        Curso curso = CursoDAO.Get(codigo);

        if (curso != null) {
            CursoView.Atualizar(curso);
            CursoDAO.Update(curso);
            System.out.println("Curso atualizado com sucesso no banco.");
        } else {
            System.out.println("Curso com código " + codigo + " não encontrado para atualização.");
        }
    }

    public static void Deletar() {
        String codigo = CursoView.GetCodigo();
        CursoDAO.Delete(codigo);
    }
}

