package ru.dskozin.resumeapp.storage;

import org.postgresql.util.PSQLException;
import ru.dskozin.resumeapp.Config;
import ru.dskozin.resumeapp.exception.ExistStorageException;
import ru.dskozin.resumeapp.exception.NotExistStorageException;
import ru.dskozin.resumeapp.exception.StorageException;
import ru.dskozin.resumeapp.model.*;
import ru.dskozin.resumeapp.sql.SqlUtils;
import ru.dskozin.resumeapp.util.JSONParser;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {

    private final SqlUtils sqlUtils;

    private static final String CONTACT_TABLE = "contact";
    private static final String SECTION_TABLE = "section";

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlUtils = new SqlUtils(dbUrl, dbUser, dbPassword);
    }

    public SqlStorage() {
        this(Config.getInstance().getDBUrl(),
                Config.getInstance().getDBUser(),
                Config.getInstance().getDBPassword());
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
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
            saveOrUpdateSections(connection, r);
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        sqlUtils.<Void>executeTransactional(connection -> {
            String sql = "UPDATE resume SET full_name = ? WHERE uuid = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, r.getFullName());
                preparedStatement.setString(2, r.getUuid());
                if (preparedStatement.executeUpdate() == 0)
                    throw new NotExistStorageException(r.getUuid());
            }

            deleteEntity(r, connection, CONTACT_TABLE);
            deleteEntity(r, connection, SECTION_TABLE);
            saveOrUpdateContacts(connection, r);
            saveOrUpdateSections(connection, r);
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

        String sql = "SELECT * FROM resume ORDER BY full_name, uuid";

        return sqlUtils.executeTransactional(connection -> {

            Map<String, Resume> resumes;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                ResultSet resultSet = preparedStatement.executeQuery();

                if (!resultSet.next())
                    return Collections.<Resume>emptyList();

                resumes = getResumesMap(resultSet);
            }

            fetchContacts(null, resumes, connection);
            fetchSections(null, resumes, connection);

            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public Resume get(String uuid) {
        String sql = "SELECT * FROM resume r WHERE uuid = ?";

        return sqlUtils.executeTransactional(connection -> {
            Map<String, Resume> resumes;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (!resultSet.next())
                    throw new NotExistStorageException(uuid);

                resumes = getResumesMap(resultSet);
            }
            fetchContacts(uuid, resumes, connection);
            fetchSections(uuid, resumes, connection);

            //берем нужный элемент
            return resumes.get(uuid);
        });
    }


    private Map<String, Resume> getResumesMap(ResultSet resultSet) throws SQLException {

        Map<String, Resume> resumes = new LinkedHashMap<>();

        String uuid;
        Resume resume;

        do {
            //если в хэше нет такого ключа создаем резюме
            if (!resumes.containsKey(uuid = resultSet.getString("uuid"))) {
                resume = new Resume(resultSet.getString("full_name"), uuid);
                ;
                resumes.put(uuid, resume);
            }
        } while (resultSet.next());

        return resumes;
    }

    private void fetchContacts(String uuid, Map<String, Resume> resumes, Connection connection) throws SQLException {
        String sql = "SELECT * FROM contact";

        if (uuid != null)
            sql += " WHERE resume_uuid = ?";

        sqlUtils.execute(sql, preparedStatement -> {
            if (uuid != null)
                preparedStatement.setString(1, uuid);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                resumes.get(resultSet.getString("resume_uuid"))
                        .addContact(ContactType.valueOf(resultSet.getString("type"))
                                , resultSet.getString("value"));
            }

            return null;
        });
    }

    private void fetchSections(String uuid, Map<String, Resume> resumes, Connection connection) throws SQLException {
        String sql = "SELECT * FROM section";

        if (uuid != null)
            sql += " WHERE resume_uuid = ?";

        sqlUtils.execute(sql, preparedStatement -> {
            if (uuid != null)
                preparedStatement.setString(1, uuid);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                SectionType type = SectionType.valueOf(resultSet.getString("type"));
                Section section = JSONParser.read(resultSet.getString("value"), Section.class);

                resumes.get(resultSet.getString("resume_uuid"))
                        .addSection(type, section);
            }

            return null;
        });
    }

    private void saveOrUpdateContacts(Connection connection, Resume r) throws SQLException {

        String sql = "INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (Map.Entry<ContactType, String> contact : r.getContacts().entrySet()) {
                preparedStatement.setString(1, r.getUuid());
                preparedStatement.setString(2, contact.getKey().name());
                preparedStatement.setString(3, contact.getValue());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    private void saveOrUpdateSections(Connection connection, Resume r) throws SQLException {

        String sql = "INSERT INTO section (resume_uuid, type, value) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (Map.Entry<SectionType, Section> sections : r.getSections().entrySet()) {
                preparedStatement.setString(1, r.getUuid());
                preparedStatement.setString(2, sections.getKey().name());
                preparedStatement.setString(3, JSONParser.write(sections.getValue(), Section.class));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    private void deleteEntity(Resume r, Connection connection, String entity) throws SQLException {
        String sql = "DELETE FROM " + entity + " WHERE resume_uuid = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, r.getUuid());
            preparedStatement.execute();
        }
    }
}
