package br.com.fiap.delegacia.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionFactory {
    private static final ConnectionFactory INSTANCE = new ConnectionFactory();
    private static volatile boolean estruturaGarantida;

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
        Connection connection = DriverManager.getConnection(url, user, password);

        if (!estruturaGarantida) {
            synchronized (ConnectionFactory.class) {
                if (!estruturaGarantida) {
                    garantirEstrutura(connection);
                    estruturaGarantida = true;
                }
            }
        }

        return connection;
    }

    private void garantirEstrutura(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            if (!existe(statement, "USER_TABLES", "TABLE_NAME", "DELEGACIA")) {
                statement.executeUpdate(
                        "CREATE TABLE delegacia (" +
                                "id NUMBER PRIMARY KEY, " +
                                "nome VARCHAR2(255) NOT NULL, " +
                                "endereco VARCHAR2(500) NOT NULL)"
                );
            }

            if (!existe(statement, "USER_SEQUENCES", "SEQUENCE_NAME", "SEQ_DELEGACIA")) {
                statement.executeUpdate("CREATE SEQUENCE seq_delegacia START WITH 1 INCREMENT BY 1");
            }

            if (!existe(statement, "USER_TABLES", "TABLE_NAME", "POLICIAL")) {
                statement.executeUpdate(
                        "CREATE TABLE policial (" +
                                "id NUMBER PRIMARY KEY, " +
                                "nome VARCHAR2(255) NOT NULL, " +
                                "cpf VARCHAR2(20) NOT NULL, " +
                                "matricula VARCHAR2(50) NOT NULL, " +
                                "cargo VARCHAR2(100) NOT NULL, " +
                                "delegacia_id NUMBER NOT NULL, " +
                                "CONSTRAINT uk_policial_cpf UNIQUE (cpf), " +
                                "CONSTRAINT uk_policial_matricula UNIQUE (matricula), " +
                                "CONSTRAINT fk_policial_delegacia FOREIGN KEY (delegacia_id) REFERENCES delegacia(id))"
                );
            }

            if (!existe(statement, "USER_SEQUENCES", "SEQUENCE_NAME", "SEQ_POLICIAL")) {
                statement.executeUpdate("CREATE SEQUENCE seq_policial START WITH 1 INCREMENT BY 1");
            }

            removerTrigger(statement, "TRG_DELEGACIA_BI");
            removerTrigger(statement, "TRG_POLICIAL_BI");

            try (ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM delegacia")) {
                if (result.next() && result.getInt(1) == 0) {
                    statement.executeUpdate(
                            "INSERT INTO delegacia (id, nome, endereco) " +
                                    "VALUES (seq_delegacia.NEXTVAL, " +
                                    "'Delegacia de demonstracao', 'Endereco de demonstracao')"
                    );
                }
            }
        }
    }

    private void removerTrigger(Statement statement, String trigger) {
        try {
            statement.executeUpdate("DROP TRIGGER " + trigger);
        } catch (SQLException ignored) {
        }
    }

    private boolean existe(Statement statement, String visao, String coluna, String nome)
            throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + visao + " WHERE " + coluna + " = '" + nome + "'";
        try (ResultSet result = statement.executeQuery(sql)) {
            return result.next() && result.getInt(1) > 0;
        }
    }
}
