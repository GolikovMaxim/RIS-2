package ru.nsu.fit.golikov.lab2_2.database;

import ru.nsu.fit.golikov.lab2_2.generated.Node;

import java.sql.SQLException;

public interface NodeDAO extends AutoCloseable {
    void insertNode(Node node) throws SQLException;

    @Override
    default void close() throws Exception {}
}
