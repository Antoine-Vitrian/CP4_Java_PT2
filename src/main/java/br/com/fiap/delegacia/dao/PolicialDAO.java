package br.com.fiap.delegacia.dao;

import br.com.fiap.delegacia.database.ConnectionFactory;
import br.com.fiap.delegacia.exception.PolicialException;
import br.com.fiap.delegacia.model.Policial;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PolicialDAO {

    private final ConnectionFactory connectionFactory;

    public PolicialDAO() {
        this.connectionFactory = ConnectionFactory.getInstance();
    }

    // CADASTRAR
    public void cadastrar(Policial policial) throws PolicialException {

        if (policial == null) {
            throw new PolicialException("O policial não pode ser nulo.");
        }

        if (policial.getNome() == null || policial.getNome().isBlank()) {
            throw new PolicialException("O nome do policial é obrigatório.");
        }

        if (policial.getCpf() == null || policial.getCpf().isBlank()) {
            throw new PolicialException("O CPF do policial é obrigatório.");
        }

        if (policial.getMatricula() == null || policial.getMatricula().isBlank()) {
            throw new PolicialException("A matrícula do policial é obrigatória.");
        }

        if (policial.getCargo() == null || policial.getCargo().isBlank()) {
            throw new PolicialException("O cargo do policial é obrigatório.");
        }

        if (policial.getDelegaciaId() == null || policial.getDelegaciaId() <= 0) {
            throw new PolicialException("A delegacia deve ser informada.");
        }

        String sqlId = "SELECT seq_policial.NEXTVAL FROM dual";
        String sql = """
                INSERT INTO policial
                (id, nome, cpf, matricula, cargo, delegacia_id)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = connectionFactory.getConnection()) {
            long id;

            try (PreparedStatement idStatement = connection.prepareStatement(sqlId);
                 ResultSet result = idStatement.executeQuery()) {
                if (!result.next()) {
                    throw new PolicialException("Nao foi possivel gerar o ID do policial.");
                }
                id = result.getLong(1);
            }

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id);
                statement.setString(2, policial.getNome());
                statement.setString(3, policial.getCpf());
                statement.setString(4, policial.getMatricula());
                statement.setString(5, policial.getCargo());
                statement.setLong(6, policial.getDelegaciaId());
                statement.executeUpdate();
            }
            policial.setId(id);

        } catch (SQLException e) {
            throw new PolicialException(
                    "Erro ao cadastrar policial: " + e.getMessage()
            );
        }
    }

    // LISTAR TODOS
    public List<Policial> listar() throws PolicialException {

        List<Policial> policiais = new ArrayList<>();

        String sql = """
                SELECT id, nome, cpf, matricula, cargo, delegacia_id
                FROM policial
                ORDER BY id
                """;

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                Policial policial = new Policial();

                policial.setId(result.getLong("id"));
                policial.setNome(result.getString("nome"));
                policial.setCpf(result.getString("cpf"));
                policial.setMatricula(result.getString("matricula"));
                policial.setCargo(result.getString("cargo"));
                policial.setDelegaciaId(result.getLong("delegacia_id"));

                policiais.add(policial);
            }

        } catch (SQLException e) {
            throw new PolicialException(
                    "Erro ao listar policiais: " + e.getMessage()
            );
        }

        return policiais;
    }

    // BUSCAR POR MATRÍCULA
    public Policial buscarPorMatricula(String matricula)
            throws PolicialException {

        if (matricula == null || matricula.isBlank()) {
            throw new PolicialException(
                    "A matrícula deve ser informada."
            );
        }

        String sql = """
                SELECT id, nome, cpf, matricula, cargo, delegacia_id
                FROM policial
                WHERE matricula = ?
                """;

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, matricula);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    Policial policial = new Policial();

                    policial.setId(result.getLong("id"));
                    policial.setNome(result.getString("nome"));
                    policial.setCpf(result.getString("cpf"));
                    policial.setMatricula(result.getString("matricula"));
                    policial.setCargo(result.getString("cargo"));
                    policial.setDelegaciaId(
                            result.getLong("delegacia_id")
                    );

                    return policial;
                }
            }

        } catch (SQLException e) {
            throw new PolicialException(
                    "Erro ao buscar policial: " + e.getMessage()
            );
        }

        throw new PolicialException(
                "Nenhum policial encontrado com a matrícula: " + matricula
        );
    }
    // ALTERAR POLICIAL
    public void alterar(Policial policial) throws PolicialException {

        if (policial == null || policial.getId() == null || policial.getId() <= 0) {
            throw new PolicialException("ID do policial inválido.");
        }

        if (policial.getNome() == null || policial.getNome().isBlank()) {
            throw new PolicialException("O nome do policial é obrigatório.");
        }

        if (policial.getCpf() == null || policial.getCpf().isBlank()) {
            throw new PolicialException("O CPF do policial é obrigatório.");
        }

        if (policial.getMatricula() == null || policial.getMatricula().isBlank()) {
            throw new PolicialException("A matrícula do policial é obrigatória.");
        }

        if (policial.getCargo() == null || policial.getCargo().isBlank()) {
            throw new PolicialException("O cargo do policial é obrigatório.");
        }

        if (policial.getDelegaciaId() == null || policial.getDelegaciaId() <= 0) {
            throw new PolicialException("A delegacia deve ser informada.");
        }

        String sql = """
            UPDATE policial
            SET nome = ?,
                cpf = ?,
                matricula = ?,
                cargo = ?,
                delegacia_id = ?
            WHERE id = ?
            """;

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, policial.getNome());
            statement.setString(2, policial.getCpf());
            statement.setString(3, policial.getMatricula());
            statement.setString(4, policial.getCargo());
            statement.setLong(5, policial.getDelegaciaId());
            statement.setLong(6, policial.getId());

            int linhasAlteradas = statement.executeUpdate();

            if (linhasAlteradas == 0) {
                throw new PolicialException(
                        "Nenhum policial encontrado com o ID informado."
                );
            }

        } catch (SQLException e) {
            throw new PolicialException(
                    "Erro ao alterar policial: " + e.getMessage()
            );
        }
    }
    // EXCLUIR POLICIAL
    public void excluir(Long id) throws PolicialException {

        if (id == null || id <= 0) {
            throw new PolicialException("ID do policial inválido.");
        }

        String sql = """
            DELETE FROM policial
            WHERE id = ?
            """;

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            int linhasAlteradas = statement.executeUpdate();

            if (linhasAlteradas == 0) {
                throw new PolicialException(
                        "Nenhum policial encontrado com o ID informado."
                );
            }

        } catch (SQLException e) {
            throw new PolicialException(
                    "Erro ao excluir policial: " + e.getMessage()
            );
        }
    }
}