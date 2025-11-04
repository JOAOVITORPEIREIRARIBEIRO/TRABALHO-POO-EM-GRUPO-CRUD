package controller;

import dao.DisciplinaDAO;
import model.Disciplina;
import view.DisciplinaView;
import java.util.List;

public class DisciplinaController {

    public static void Criar() {
        Disciplina disciplina = new Disciplina();
        DisciplinaView.Criar(disciplina);

        if (disciplina != null && disciplina.getCodigo() != null && !disciplina.getCodigo().trim().isEmpty()) {
            try {
                DisciplinaDAO.Add(disciplina);
                System.out.println("Disciplina criada e salva no banco.");
            } catch (Exception e) {
                System.out.println("Erro ao criar disciplina: " + e.getMessage());
            }
        } else {
            System.out.println("Criação de disciplina cancelada ou inválida.");
        }
    }

    public static void Consultar() {
        String codigo = DisciplinaView.GetCodigo();
        Disciplina disciplina = null;

        try {
            disciplina = DisciplinaDAO.Get(codigo);
        } catch (Exception e) {
            System.out.println("Erro ao consultar disciplina: " + e.getMessage());
        }

        DisciplinaView.Consultar(disciplina);
    }

    public static void Listar() {
        try {
            List<Disciplina> disciplinas = DisciplinaDAO.GetAll();
            DisciplinaView.Listar(disciplinas);
        } catch (Exception e) {
            System.out.println("Erro ao listar disciplinas: " + e.getMessage());
        }
    }

    public static void Atualizar() {
        String codigo = DisciplinaView.GetCodigo();
        Disciplina disciplina = DisciplinaDAO.Get(codigo);

        if (disciplina != null) {
            DisciplinaView.Atualizar(disciplina);

            try {
                DisciplinaDAO.Update(disciplina);
                System.out.println("Disciplina atualizada com sucesso.");
            } catch (Exception e) {
                System.out.println("Erro ao atualizar disciplina: " + e.getMessage());
            }
        } else {
            System.out.println("Disciplina com código " + codigo + " não encontrada.");
        }
    }

    public static void Deletar() {
        String codigo = DisciplinaView.GetCodigo();

        try {
            DisciplinaDAO.Delete(codigo);
        } catch (Exception e) {
            System.out.println("Erro ao deletar disciplina: " + e.getMessage());
        }
    }
}


