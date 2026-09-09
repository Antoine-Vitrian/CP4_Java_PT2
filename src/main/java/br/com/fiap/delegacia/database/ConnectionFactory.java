package br.com.fiap.delegacia.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final ConnectionFactory INSTANCE = new ConnectionFactory();
    private final String url = System.getenv().getOrDefault(
            "DB_URL",
            "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL"
    );

    private ConnectionFactory() {}

    public static ConnectionFactory getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC nao encontrado. Verifique lib/jdbc17.jar", e);
        }

        String user = System.getenv().getOrDefault("DB_USER", "rm562573");
        String password = System.getenv().getOrDefault("DB_PASSWORD", "041106");
        return DriverManager.getConnection(url, user, password);
    }
}
