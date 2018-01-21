package ru.dskozin.resumeapp.storage;

import org.postgresql.util.PSQLException;
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

    private final SqlUtils sqlUtils;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword){
        sqlUtils = new SqlUtils(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        String sql = "DELETE FROM resume";
        sqlUtils.execute(sql, PreparedStatement::execute);
    }

    @Override
    public void save(Resume r) {
        String sql = "INSERT INTO resume (uuid, full_name) VALUES (?, ?)";
        sqlUtils.execute(sql, preparedStatement -> {
            preparedStatement.setString(1, r.getUuid());
            preparedStatement.setString(2, r.getFullName());
            try {
                preparedStatement.execute();
            } catch (PSQLException e){
                throw new ExistStorageException(r.getUuid());
            }
        });
    }

    @Override
    public void update(Resume r) {
        String sql = "UPDATE resume SET full_name = ? WHERE uuid = ?";
        sqlUtils.execute(sql, preparedStatement -> {
            preparedStatement.setString(1, r.getFullName());
            preparedStatement.setString(2, r.getUuid());
            if (preparedStatement.executeUpdate() == 0)
                throw new NotExistStorageException(r.getUuid());
        });
    }

    @Override
    public void delete(String uuid) {
        String sql = "DELETE FROM resume WHERE uuid = ?";
        sqlUtils.execute(sql, preparedStatement -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0)
                throw new NotExistStorageException(uuid);
        });
    }

    @Override
    public int size() {
        String sql = "SELECT count(*) cnt FROM resume";
        return sqlUtils.executeWithResult(sql, preparedStatement -> {
           ResultSet resultSet = preparedStatement.executeQuery();

           if(!resultSet.next())
               throw new StorageException("Error with count", "none");

           return resultSet.getInt("cnt");
        });
    }

    @Override
    public List<Resume> getAllSorted() {

        String sql = "SELECT * FROM resume ORDER BY full_name, uuid";

        return sqlUtils.executeWithResult(sql, preparedStatement -> {
           ResultSet resultSet = preparedStatement.executeQuery();
           List<Resume> resumes = new ArrayList<>();

           while (resultSet.next()){
              resumes.add(new Resume(
                      resultSet.getString("full_name"),
                      resultSet.getString("uuid").trim()));
           }
           return resumes;
        });
    }

    @Override
    public Resume get(String uuid) {
        String sql = "SELECT * FROM resume WHERE uuid = ?";

        return sqlUtils.executeWithResult(sql, preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(resultSet.getString("full_name"), uuid);
        });
    }
}
