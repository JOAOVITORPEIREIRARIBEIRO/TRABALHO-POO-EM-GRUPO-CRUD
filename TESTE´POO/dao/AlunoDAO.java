package dao;

import database.DatabaseConnection;
import model.Aluno;
import model.Curso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlunoDAO {

    private static List<Aluno> listAlunos = new ArrayList<>();


    public static void Add(Aluno aluno) {
        String sql = "INSERT INTO alunos (matricula, nome, idade, curso_codigo) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getMatricula());
            stmt.setString(2, aluno.getNome());
            stmt.setInt(3, aluno.getIdade());
            stmt.setString(4, aluno.getCurso() != null ? aluno.getCurso().getCodigo() : null);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Aluno " + aluno.getNome() + " cadastrado com sucesso!");
                listAlunos.add(aluno);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar aluno: " + e.getMessage());
            throw new RuntimeException("Erro ao cadastrar aluno no banco de dados", e);
        }
    }


    public static Aluno Get(String matricula) {
        String sql = """
            SELECT a.matricula, a.nome, a.idade,
                   c.codigo AS curso_codigo, c.nome AS curso_nome, c.turno AS curso_turno
            FROM alunos a
            LEFT JOIN cursos c ON a.curso_codigo = c.codigo
            WHERE a.matricula = ?
        """;
        Optional<Aluno> aluno = Optional.empty();

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, matricula);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Curso curso = null;
                    String codigoCurso = rs.getString("curso_codigo");
                    if (codigoCurso != null) {
                        curso = new Curso(
                                rs.getString("curso_codigo"),
                                rs.getString("curso_nome"),
                                rs.getString("curso_turno")
                        );
                    }

                    aluno = Optional.of(new Aluno(
                            rs.getString("nome"),
                            rs.getInt("idade"),
                            rs.getString("matricula"),
                            curso
                    ));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar aluno: " + e.getMessage());
            throw new RuntimeException("Erro ao consultar aluno no banco de dados", e);
        }

        return aluno.orElse(null);
    }


    public static List<Aluno> GetAll() {
        return listAlunos;
    }


    public static void Criar() {
        String sqlCreateTable = """
            CREATE TABLE IF NOT EXISTS alunos (
                matricula VARCHAR(10) PRIMARY KEY,
                nome VARCHAR(100) NOT NULL,
                idade INT NOT NULL,
                curso_codigo VARCHAR(10),
                FOREIGN KEY (curso_codigo) REFERENCES cursos(codigo)
            );
        """;

        try (Statement stmt = DatabaseConnection.getConnection().createStatement()) {
            stmt.executeUpdate(sqlCreateTable);
            System.out.println("Tabela 'alunos' criada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao criar a tabela: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void Update(Aluno aluno) {
        String sql = "UPDATE alunos SET nome = ?, idade = ?, curso_codigo = ? WHERE matricula = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setInt(2, aluno.getIdade());
            stmt.setString(3, aluno.getCurso() != null ? aluno.getCurso().getCodigo() : null);
            stmt.setString(4, aluno.getMatricula());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Aluno " + aluno.getMatricula() + " atualizado com sucesso!");
            } else {
                System.out.println("Nenhum aluno encontrado com matrícula " + aluno.getMatricula());
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar aluno: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar aluno no banco de dados", e);
        }
    }


    public static void Delete(String matricula) {
        String sql = "DELETE FROM alunos WHERE matricula = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, matricula);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Aluno " + matricula + " deletado com sucesso!");
            } else {
                System.out.println("Nenhum aluno encontrado com matrícula " + matricula);
            }

            stmt.close();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar aluno: " + e.getMessage());
            throw new RuntimeException("Erro ao deletar aluno no banco de dados", e);
        }
    }
}

