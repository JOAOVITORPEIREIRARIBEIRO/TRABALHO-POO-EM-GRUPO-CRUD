package dao;

import database.DatabaseConnection;
import model.Curso;
import model.Disciplina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DisciplinaDAO {

    private static List<Disciplina> listDisciplinas = new ArrayList<>();

    public static void Add(Disciplina disciplina) {
        listDisciplinas.add(disciplina);

        String sql = "INSERT INTO disciplinas (codigo, nome, cargaHoraria, curso_codigo) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, disciplina.getCodigo());
            stmt.setString(2, disciplina.getNome());
            stmt.setInt(3, disciplina.getCargaHoraria());
            stmt.setString(4, disciplina.getCurso() != null ? disciplina.getCurso().getCodigo() : null);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Disciplina " + disciplina.getNome() + " cadastrada com sucesso!");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar disciplina: " + e.getMessage());
            throw new RuntimeException("Erro ao cadastrar disciplina no banco de dados", e);
        }
    }

    public static Disciplina Get(String codigo) {
        Optional<Disciplina> disciplina = listDisciplinas.stream()
                .filter(d -> d.getCodigo().equals(codigo))
                .findFirst();

        if (disciplina.isPresent()) return disciplina.get();

        String sql = "SELECT * FROM disciplinas WHERE codigo = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Curso curso = CursoDAO.Get(rs.getString("curso_codigo"));
                    Disciplina d = new Disciplina(
                            rs.getString("codigo"),
                            rs.getString("nome"),
                            rs.getInt("cargaHoraria"),
                            curso
                    );
                    listDisciplinas.add(d);
                    return d;
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao consultar disciplina: " + e.getMessage());
            throw new RuntimeException("Erro ao consultar disciplina no banco de dados", e);
        }

        return null;
    }

    public static List<Disciplina> GetAll() {
        return listDisciplinas;
    }

    public static void Update(Disciplina disciplina) {
        for (int i = 0; i < listDisciplinas.size(); i++) {
            if (listDisciplinas.get(i).getCodigo().equals(disciplina.getCodigo())) {
                listDisciplinas.set(i, disciplina);
                break;
            }
        }

        String sql = "UPDATE disciplinas SET nome = ?, cargaHoraria = ?, curso_codigo = ? WHERE codigo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, disciplina.getNome());
            stmt.setInt(2, disciplina.getCargaHoraria());
            stmt.setString(3, disciplina.getCurso() != null ? disciplina.getCurso().getCodigo() : null);
            stmt.setString(4, disciplina.getCodigo());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Disciplina " + disciplina.getCodigo() + " atualizada com sucesso!");
            } else {
                System.out.println("Nenhuma disciplina encontrada com código " + disciplina.getCodigo());
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar disciplina: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar disciplina no banco de dados", e);
        }
    }

    public static void Delete(String codigo) {
        listDisciplinas.removeIf(d -> d.getCodigo().equals(codigo));

        String sql = "DELETE FROM disciplinas WHERE codigo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, codigo);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Disciplina " + codigo + " deletada com sucesso!");
            } else {
                System.out.println("Nenhuma disciplina encontrada com código " + codigo);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao deletar disciplina: " + e.getMessage());
            throw new RuntimeException("Erro ao deletar disciplina no banco de dados", e);
        }
    }

    public static void Criar() {
        String sqlCreateTable = """
            CREATE TABLE IF NOT EXISTS disciplinas (
                codigo VARCHAR(10) PRIMARY KEY,
                nome VARCHAR(100) NOT NULL,
                cargaHoraria INT NOT NULL,
                curso_codigo VARCHAR(10),
                FOREIGN KEY (curso_codigo) REFERENCES cursos(codigo)
            );
        """;

        try (Statement stmt = DatabaseConnection.getConnection().createStatement()) {
            stmt.executeUpdate(sqlCreateTable);
            System.out.println("Tabela 'disciplinas' criada com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao criar a tabela: " + e.getMessage());
            e.printStackTrace();
        }
    }
}



