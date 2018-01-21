package ru.dskozin.resumeapp.sql;

import ru.dskozin.resumeapp.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlUtils {

    private final ConnectionFactory connectionFactory;

    public SqlUtils(String dbUrl, String dbUser, String dbPassword){
        this.connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public interface Executor{
        void execute(PreparedStatement preparedStatement) throws SQLException;
    }

    public interface ExecutorWithResult<T>{
        T execute(PreparedStatement preparedStatement) throws SQLException;
    }

    public void execute(String sql, Executor executor){
        try(Connection connection = this.connectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            executor.execute(preparedStatement);
        } catch (SQLException e){
            throw new StorageException(e);
        }
    }

    public <T> T executeWithResult(String sql, ExecutorWithResult executorWithResult){
        try(Connection connection = this.connectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            @SuppressWarnings("unchecked")
            T ret = (T) executorWithResult.execute(preparedStatement);
            return ret;
        } catch (SQLException e){
            throw new StorageException(e);
        }
    }



}