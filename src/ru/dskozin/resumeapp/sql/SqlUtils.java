package ru.dskozin.resumeapp.sql;

import ru.dskozin.resumeapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlUtils {

    public interface Executor{
        void execute(PreparedStatement preparedStatement) throws SQLException;
    }

    public interface ExecutorWithResult<T>{
        T execute(PreparedStatement preparedStatement) throws SQLException;
    }

    public static void execute(ConnectionFactory connectionFactory, String sql, Executor executor){
        try(Connection connection = connectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            executor.execute(preparedStatement);
        } catch (SQLException e){
            throw new StorageException(e);
        }
    }

    public static <T> T executeWithResult(ConnectionFactory connectionFactory, String sql, ExecutorWithResult executorWithResult){
        try(Connection connection = connectionFactory.getConnection();
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