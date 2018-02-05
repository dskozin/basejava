package ru.dskozin.resumeapp.sql;

import ru.dskozin.resumeapp.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlUtils {

    private final ConnectionFactory connectionFactory;

    public SqlUtils(String dbUrl, String dbUser, String dbPassword) {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public void execute(String sql) {
        execute(sql, PreparedStatement::execute);
    }

    public <T> T execute(String sql, SqlExecutor<T> executor) {
        try (Connection connection = this.connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            return executor.execute(preparedStatement);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public <T> T execute(String sql, Connection connection, SqlExecutor<T> executor) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            return executor.execute(preparedStatement);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public <T> T executeTransactional(SqlTransactionalExecutor<T> tExecutor) {
        try (Connection connection = this.connectionFactory.getConnection()) {
            connection.setAutoCommit(false);
            T ret = tExecutor.execute(connection);
            connection.commit();
            return ret;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}