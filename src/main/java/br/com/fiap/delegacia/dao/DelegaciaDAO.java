package br.com.fiap.delegacia.dao;

import br.com.fiap.delegacia.database.ConnectionFactory;
import br.com.fiap.delegacia.exception.PolicialException;
import br.com.fiap.delegacia.model.Delegacia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DelegaciaDAO {

    private final ConnectionFactory connectionFactory;

    public DelegaciaDAO() {
        this.connectionFactory = ConnectionFactory.getInstance();
    }

    // CADASTRAR DELEGACIA
    public void cadastrar(Delegacia delegacia) throws PolicialException {

        if (delegacia.getNome() == null || delegacia.getNome().isBlank()) {
            throw new PolicialException("O nome da delegacia é obrigatório.");
        }

        if (delegacia.getEndereco() == null || delegacia.getEndereco().isBlank()) {
            throw new PolicialException("O endereço da delegacia é obrigatório.");
        }

        String sql = """
                INSERT INTO delegacia (nome, endereco)
                VALUES (?, ?)
                """;

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, delegacia.getNome());
            statement.setString(2, delegacia.getEndereco());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new PolicialException(
                    "Erro ao cadastrar delegacia: " + e.getMessage()
            );
        }
    }

    // LISTAR DELEGACIAS
    public List<Delegacia> listar() throws PolicialException {

        List<Delegacia> delegacias = new ArrayList<>();

        String sql = """
                SELECT id, nome, endereco
                FROM delegacia
                ORDER BY id
                """;

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                Delegacia delegacia = new Delegacia();

                delegacia.setId(result.getLong("id"));
                delegacia.setNome(result.getString("nome"));
                delegacia.setEndereco(result.getString("endereco"));

                delegacias.add(delegacia);
            }

        } catch (SQLException e) {
            throw new PolicialException(
                    "Erro ao listar delegacias: " + e.getMessage()
            );
        }

        return delegacias;
    }
}