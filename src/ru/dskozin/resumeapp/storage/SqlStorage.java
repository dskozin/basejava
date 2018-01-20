package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.exception.ExistStorageException;
import ru.dskozin.resumeapp.exception.NotExistStorageException;
import ru.dskozin.resumeapp.exception.StorageException;
import ru.dskozin.resumeapp.model.Resume;
import ru.dskozin.resumeapp.sql.ConnectionFactory;
import ru.dskozin.resumeapp.sql.SqlUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SqlStorage implements Storage {

    private final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        String sql = "DELETE FROM resume";
        SqlUtils.execute(connectionFactory, sql, PreparedStatement::execute);
    }

    @Override
    public void save(Resume r) {
        if (isExist(r.getUuid()))
            throw new ExistStorageException(r.getUuid());

        String sql = "INSERT INTO resume (uuid, full_name) VALUES (?, ?)";
        SqlUtils.execute(connectionFactory, sql, preparedStatement -> {
            preparedStatement.setString(1, r.getUuid());
            preparedStatement.setString(2, r.getFullName());
            preparedStatement.execute();
        });
    }

    @Override
    public void update(Resume r) {
        String sql = "UPDATE resume SET full_name = ? WHERE uuid = ?";
        SqlUtils.execute(connectionFactory, sql, preparedStatement -> {
            preparedStatement.setString(1, r.getFullName());
            preparedStatement.setString(2, r.getUuid());
            if (preparedStatement.executeUpdate() == 0)
                throw new NotExistStorageException(r.getUuid());
        });
    }

    @Override
    public void delete(String uuid) {
        String sql = "DELETE FROM resume WHERE uuid = ?";
        SqlUtils.execute(connectionFactory, sql, preparedStatement -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0)
                throw new NotExistStorageException(uuid);
        });
    }

    @Override
    public int size() {
        String sql = "SELECT count(*) cnt FROM resume";
        return SqlUtils.executeWithResult(connectionFactory, sql, preparedStatement -> {
           ResultSet resultSet = preparedStatement.executeQuery();

           if(!resultSet.next())
               throw new StorageException("Error with count", "none");

           return resultSet.getInt("cnt");
        });
    }

    @Override
    public List<Resume> getAllSorted() {

        String sql = "SELECT * FROM resume";

        return SqlUtils.executeWithResult(connectionFactory, sql, preparedStatement -> {
           ResultSet resultSet = preparedStatement.executeQuery();
           List<Resume> resumes = new ArrayList<>();

           while (resultSet.next()){
              resumes.add(new Resume(
                      resultSet.getString("full_name"),
                      resultSet.getString("uuid").trim()));
           }

           resumes.sort(Comparator.naturalOrder());
           return resumes;
        });
    }

    @Override
    public Resume get(String uuid) {
        String sql = "SELECT * FROM resume WHERE uuid = ?";

        return SqlUtils.executeWithResult(connectionFactory, sql, preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(resultSet.getString("full_name"), uuid);
        });
    }

    private boolean isExist(String uuid){
        String sql = "SELECT * FROM resume WHERE uuid = ?";

        return SqlUtils.executeWithResult(connectionFactory, sql, preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        });
    }
}
