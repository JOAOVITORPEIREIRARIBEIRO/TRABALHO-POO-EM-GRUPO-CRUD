package view;

import model.Curso;
import model.Disciplina;

import java.util.List;
import java.util.Scanner;

public class CursoView {

    private static final Scanner scanner = new Scanner(System.in);

    public static String GetCodigo() {
        System.out.print("\nDigite o CÓDIGO do curso: ");
        return scanner.nextLine();
    }

    public static void Criar(Curso curso) {
        System.out.println("\n--- Cadastro de Novo Curso ---");

        System.out.print("Código do Curso: ");
        curso.setCodigo(scanner.nextLine());

        System.out.print("Nome: ");
        curso.setNome(scanner.nextLine());

        System.out.print("Turno: ");
        curso.setTurno(scanner.nextLine());
    }

    public static void Atualizar(Curso curso) {
        System.out.println("\n--- Atualizando Curso: " + curso.getCodigo() + " ---");

        System.out.print("Novo Nome (atual: " + curso.getNome() + "): ");
        String novoNome = scanner.nextLine();
        if (!novoNome.trim().isEmpty()) {
            curso.setNome(novoNome);
        }

        System.out.print("Novo Turno (atual: " + curso.getTurno() + "): ");
        String novoTurno = scanner.nextLine();
        if (!novoTurno.trim().isEmpty()) {
            curso.setTurno(novoTurno);
        }
    }

    public static void Consultar(Curso curso) {
        System.out.println("\n--- Detalhes do Curso ---");
        if (curso == null) {
            System.out.println("Curso não encontrado.");
            return;
        }

        System.out.println(curso.toString());

        System.out.println("Disciplinas Ofertadas (" + curso.getDisciplinas().size() + "):");
        List<Disciplina> disciplinas = curso.getDisciplinas();

        if (disciplinas.isEmpty()) {
            System.out.println("Nenhuma disciplina cadastrada.");
        } else {
            for (Disciplina d : disciplinas) {
                System.out.println(d.toString());
            }
        }
    }

    public static void Listar(List<Curso> cursos) {
        System.out.println("\n--- Lista de Cursos Cadastrados (" + cursos.size() + ") ---");
        if (cursos.isEmpty()) {
            System.out.println("Nenhum curso cadastrado.");
        } else {
            for (Curso curso : cursos) {
                System.out.println("* " + curso.toString());
            }
        }
    }

    // Novo método para selecionar um curso da lista
    public static Curso SelecionarCurso(List<Curso> cursos) {
        if (cursos.isEmpty()) {
            System.out.println("Não há cursos cadastrados.");
            return null;
        }

        System.out.println("\n--- Seleção de Curso ---");
        for (int i = 0; i < cursos.size(); i++) {
            Curso c = cursos.get(i);
            System.out.println((i + 1) + " - " + c.getNome() + " (Código: " + c.getCodigo() + ")");
        }

        System.out.print("Digite o número do curso: ");
        int escolha = -1;
        try {
            escolha = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
            return null;
        }

        if (escolha < 1 || escolha > cursos.size()) {
            System.out.println("Opção inválida.");
            return null;
        }

        return cursos.get(escolha - 1);
    }
}

