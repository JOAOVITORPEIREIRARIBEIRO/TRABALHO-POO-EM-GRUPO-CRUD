package view;

import model.Disciplina;
import model.Curso;
import dao.CursoDAO;
import java.util.List;
import java.util.Scanner;

public class DisciplinaView {

    private static final Scanner scanner = new Scanner(System.in);

    public static String GetCodigo() {
        System.out.print("\nDigite o CÓDIGO da disciplina: ");
        return scanner.nextLine();
    }

    public static void Criar(Disciplina disciplina) {
        System.out.println("\n--- Cadastro de Nova Disciplina ---");

        System.out.print("   Código da Disciplina: ");
        disciplina.setCodigo(scanner.nextLine());

        System.out.print("   Nome: ");
        disciplina.setNome(scanner.nextLine());

        System.out.print("   Carga Horária (em horas): ");
        try {
            int carga = Integer.parseInt(scanner.nextLine());
            disciplina.setCargaHoraria(carga);
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido para carga horária. Configurado para 0.");
            disciplina.setCargaHoraria(0);
        }


        List<Curso> cursos = CursoDAO.GetAll();
        if (cursos.isEmpty()) {
            System.out.println("⚠ Nenhum curso cadastrado. A disciplina ficará sem curso.");
        } else {
            System.out.println("\nEscolha o curso para esta disciplina:");
            for (int i = 0; i < cursos.size(); i++) {
                System.out.println((i + 1) + " - " + cursos.get(i).getNome() + " (Código: " + cursos.get(i).getCodigo() + ")");
            }
            System.out.print("Digite o número do curso: ");
            try {
                int opcao = Integer.parseInt(scanner.nextLine());
                if (opcao >= 1 && opcao <= cursos.size()) {
                    disciplina.setCurso(cursos.get(opcao - 1));
                } else {
                    System.out.println("Opção inválida. Curso não definido.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Curso não definido.");
            }
        }
    }

    public static void Atualizar(Disciplina disciplina) {
        System.out.println("\n--- Atualizando Disciplina: " + disciplina.getCodigo() + " ---");
        System.out.println("[Deixe em branco para manter o valor atual]");

        System.out.print("   Novo Nome (atual: " + disciplina.getNome() + "): ");
        String novoNome = scanner.nextLine();
        if (!novoNome.trim().isEmpty()) {
            disciplina.setNome(novoNome);
        }

        System.out.print("   Nova Carga Horária (atual: " + disciplina.getCargaHoraria() + "): ");
        String cargaStr = scanner.nextLine();
        if (!cargaStr.trim().isEmpty()) {
            try {
                int carga = Integer.parseInt(cargaStr);
                disciplina.setCargaHoraria(carga);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Carga horária não alterada.");
            }
        }


        List<Curso> cursos = CursoDAO.GetAll();
        if (!cursos.isEmpty()) {
            System.out.println("\nEscolha o novo curso (atual: " +
                    (disciplina.getCurso() != null ? disciplina.getCurso().getNome() : "Sem curso") + "):");
            for (int i = 0; i < cursos.size(); i++) {
                System.out.println((i + 1) + " - " + cursos.get(i).getNome() + " (Código: " + cursos.get(i).getCodigo() + ")");
            }
            System.out.print("Digite o número do curso: ");
            try {
                String opcaoStr = scanner.nextLine();
                if (!opcaoStr.trim().isEmpty()) {
                    int opcao = Integer.parseInt(opcaoStr);
                    if (opcao >= 1 && opcao <= cursos.size()) {
                        disciplina.setCurso(cursos.get(opcao - 1));
                    } else {
                        System.out.println("Opção inválida. Curso não alterado.");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Curso não alterado.");
            }
        }
    }

    public static void Consultar(Disciplina disciplina) {
        System.out.println("\n--- Detalhes da Disciplina ---");
        if (disciplina == null) {
            System.out.println("Disciplina não encontrada.");
        } else {
            System.out.println("DISCIPLINA: " + disciplina.getNome() +
                    " | Código: " + disciplina.getCodigo() +
                    " | Carga: " + disciplina.getCargaHoraria() + "h" +
                    " | Curso: " + (disciplina.getCurso() != null ? disciplina.getCurso().getNome() : "Sem curso definido"));
        }
    }

    public static void Listar(List<Disciplina> disciplinas) {
        System.out.println("\n--- Lista de Disciplinas Cadastradas (" + disciplinas.size() + ") ---");
        if (disciplinas.isEmpty()) {
            System.out.println("Nenhuma disciplina cadastrada.");
        } else {
            for (Disciplina disciplina : disciplinas) {
                System.out.println("  * DISCIPLINA: " + disciplina.getNome() +
                        " | Código: " + disciplina.getCodigo() +
                        " | Carga: " + disciplina.getCargaHoraria() + "h" +
                        " | Curso: " + (disciplina.getCurso() != null ? disciplina.getCurso().getNome() : "Sem curso definido"));
            }
        }
    }
}


