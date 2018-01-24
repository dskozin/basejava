package ru.dskozin.resumeapp.sql;

import java.sql.Connection;

public interface SqlTransactionalExecutor<T> {
    T execute(Connection connection);
}
