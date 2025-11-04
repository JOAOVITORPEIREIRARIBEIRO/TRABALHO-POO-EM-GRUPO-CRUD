package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection = null;

    private static final String HOST = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE = "faculdade";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";

    private DatabaseConnection() {}

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                synchronized (DatabaseConnection.class) {
                    if (connection == null || connection.isClosed()) {
                        connection = DriverManager.getConnection(HOST + DATABASE, DB_USER, DB_PASS);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao conectar ao banco de dados", e);
        }

        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

