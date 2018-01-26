package ru.dskozin.resumeapp.storage;

import org.postgresql.util.PSQLException;
import ru.dskozin.resumeapp.exception.ExistStorageException;
import ru.dskozin.resumeapp.exception.NotExistStorageException;
import ru.dskozin.resumeapp.exception.StorageException;
import ru.dskozin.resumeapp.model.ContactType;
import ru.dskozin.resumeapp.model.Resume;
import ru.dskozin.resumeapp.sql.SqlUtils;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {

    private final SqlUtils sqlUtils;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlUtils = new SqlUtils(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        String sql = "DELETE FROM resume";
        sqlUtils.execute(sql);
    }

    @Override
    public void save(Resume r) {
        sqlUtils.<Void>executeTransactional(connection -> {

            String sql = "INSERT INTO resume (uuid, full_name) VALUES (?, ?)";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, r.getUuid());
                preparedStatement.setString(2, r.getFullName());
                try {
                    preparedStatement.execute();
                } catch (PSQLException e) {
                    if (e.getSQLState().equals("23505"))
                        throw new ExistStorageException(r.getUuid());

                    throw e;
                }
            }

            saveOrUpdateContacts(connection, r);

            return null;
        });
    }

    @Override
    public void update(Resume r) {
        sqlUtils.<Void>executeTransactional(connection -> {
            String sql = "UPDATE resume SET full_name = ? WHERE uuid = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setString(1, r.getFullName());
                preparedStatement.setString(2, r.getUuid());
                if (preparedStatement.executeUpdate() == 0)
                    throw new NotExistStorageException(r.getUuid());
            }

            sql = "DELETE FROM contact WHERE resume_uuid = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setString(1, r.getUuid());
                preparedStatement.execute();
            }

            saveOrUpdateContacts(connection, r);

            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        String sql = "DELETE FROM resume WHERE uuid = ?";
        sqlUtils.<Void>execute(sql, preparedStatement -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0)
                throw new NotExistStorageException(uuid);

            return null;
        });
    }

    @Override
    public int size() {
        String sql = "SELECT count(*) cnt FROM resume";
        return sqlUtils.<Integer>execute(sql, preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next())
                throw new StorageException("Error with count", "none");

            return resultSet.getInt("cnt");
        });
    }

    @Override
    public List<Resume> getAllSorted() {

        String sql = "SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid " +
                "ORDER BY full_name, uuid";

        return sqlUtils.execute(sql, preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next())
                return Collections.<Resume>emptyList();

            return getResumesWithSections(resultSet);
        });
    }

    @Override
    public Resume get(String uuid) {
        String sql = "SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid WHERE uuid = ?";

        return sqlUtils.execute(sql, preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next())
                throw new NotExistStorageException(uuid);

            //берем первый элемент
            return getResumesWithSections(resultSet).get(0);
        });
    }

    private List<Resume> getResumesWithSections(ResultSet resultSet) throws SQLException {

        Map<String, Resume> resumes = new HashMap<>();

        //мы сейчас стоим на первом элементе, пожтому можем брать
        String uuid = resultSet.getString("uuid");
        Resume resume = new Resume(resultSet.getString("full_name"), uuid);

        do {

            //если в хэше нет такого ключа создаем резюме
            if (!resumes.containsKey(uuid = resultSet.getString("uuid"))) {
                resume = new Resume(resultSet.getString("full_name"), uuid);;
                resumes.put(uuid, resume);
            }

            //проверка - если вообще контактов нет, то продолжаем цикл
            if (resultSet.getString("value").isEmpty())
                continue;

            resume.addContact(ContactType.valueOf(resultSet.getString("type")),
                    resultSet.getString("value"));

        } while (resultSet.next());

        return new ArrayList<>(resumes.values());
    }

    private void saveOrUpdateContacts(Connection connection, Resume r) throws SQLException {

        String sql = "INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (Map.Entry<ContactType, String> contact : r.getContacts().entrySet()) {
                preparedStatement.setString(1, r.getUuid());
                preparedStatement.setString(2, contact.getKey().name());
                preparedStatement.setString(3, contact.getValue());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }
}
