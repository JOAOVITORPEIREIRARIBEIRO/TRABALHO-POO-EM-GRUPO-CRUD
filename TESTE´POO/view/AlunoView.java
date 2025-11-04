package view;

import model.Aluno;

import java.util.List;
import java.util.Scanner;

public class AlunoView {

    public static void Criar(Aluno aluno) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Nome: ");
        aluno.setNome(scan.next());

        System.out.print("Idade: ");
        aluno.setIdade(scan.nextInt());

        System.out.print("Matricula: ");
        aluno.setMatricula(scan.next());

    }
    public static void Atualizar(Aluno aluno) {

        Scanner scan = new Scanner(System.in);
        System.out.println("\n--- Atualizando Aluno: " + aluno.getMatricula() + " ---");


        System.out.print("(" + aluno.getNome() + ") - Novo Nome (deixe em branco para manter): ");
        String novoNome = scan.nextLine();

        if (!novoNome.isEmpty()) {
            aluno.setNome(novoNome);
        }

        System.out.print("(" + aluno.getIdade() + ") - Nova Idade (deixe em branco para manter): ");
        String novaIdadeStr = scan.nextLine();


        if (!novaIdadeStr.isEmpty()) {

            aluno.setIdade(Integer.parseInt(novaIdadeStr.trim()));
        }

    }

    public static void Listar(List<Aluno> alunos) {
        for(Aluno a : alunos) {
            Consultar(a);
        }
    }

    public static void Consultar(Aluno aluno) {
        System.out.println("Matricula: " + aluno.getMatricula());
        System.out.println("Nome: " + aluno.getNome());
        System.out.println("Idade: " + aluno.getIdade());
        System.out.println();
    }

    public static String GetMatricula() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Informe sua matricula: ");
        String matricula = scan.next();
        return matricula;
    }

}
