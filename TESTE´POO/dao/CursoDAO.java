package dao;

import database.DatabaseConnection;
import model.Curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CursoDAO {

    private static List<Curso> listCursos = new ArrayList<>();

    public static void Add(Curso curso) {
        listCursos.add(curso);

        String sql = "INSERT INTO cursos (codigo, nome, turno) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, curso.getCodigo());
            stmt.setString(2, curso.getNome());
            stmt.setString(3, curso.getTurno());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Curso " + curso.getNome() + " cadastrado com sucesso!");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar curso: " + e.getMessage());
            throw new RuntimeException("Erro ao cadastrar curso no banco de dados", e);
        }
    }

    public static Curso Get(String codigo) {
        Optional<Curso> curso = listCursos.stream()
                .filter(c -> c.getCodigo().equals(codigo))
                .findFirst();

        if (curso.isPresent()) return curso.get();

        String sql = "SELECT * FROM cursos WHERE codigo = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Curso c = new Curso(
                            rs.getString("codigo"),
                            rs.getString("nome"),
                            rs.getString("turno")
                    );
                    listCursos.add(c);
                    return c;
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao consultar curso: " + e.getMessage());
            throw new RuntimeException("Erro ao consultar curso no banco de dados", e);
        }

        return null;
    }

    public static List<Curso> GetAll() {
        return listCursos;
    }

    public static void Update(Curso curso) {
        for (int i = 0; i < listCursos.size(); i++) {
            if (listCursos.get(i).getCodigo().equals(curso.getCodigo())) {
                listCursos.set(i, curso);
                break;
            }
        }

        String sql = "UPDATE cursos SET nome = ?, turno = ? WHERE codigo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getTurno());
            stmt.setString(3, curso.getCodigo());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Curso " + curso.getCodigo() + " atualizado com sucesso!");
            } else {
                System.out.println("Nenhum curso encontrado com código " + curso.getCodigo());
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar curso: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar curso no banco de dados", e);
        }
    }

    public static void Delete(String codigo) {
        listCursos.removeIf(c -> c.getCodigo().equals(codigo));

        String sql = "DELETE FROM cursos WHERE codigo = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, codigo);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Curso " + codigo + " deletado com sucesso!");
            } else {
                System.out.println("Nenhum curso encontrado com código " + codigo);
            }

            stmt.close();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar curso: " + e.getMessage());
            throw new RuntimeException("Erro ao deletar curso no banco de dados", e);
        }
    }

    public static void Criar() {
        String sqlCreateTable = """
            CREATE TABLE IF NOT EXISTS cursos (
                codigo VARCHAR(10) PRIMARY KEY,
                nome VARCHAR(100) NOT NULL,
                turno VARCHAR(50) NOT NULL
            );
        """;

        try (Statement stmt = DatabaseConnection.getConnection().createStatement()) {
            stmt.executeUpdate(sqlCreateTable);
            System.out.println("Tabela 'cursos' criada com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao criar a tabela: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

