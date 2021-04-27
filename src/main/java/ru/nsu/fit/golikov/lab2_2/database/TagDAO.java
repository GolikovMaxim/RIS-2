package ru.nsu.fit.golikov.lab2_2.database;

import ru.nsu.fit.golikov.lab2_2.generated.Tag;

import java.math.BigInteger;
import java.sql.SQLException;

public interface TagDAO extends AutoCloseable {
    void insertTag(Tag tag, BigInteger nodeId) throws SQLException;

    @Override
    default void close() throws Exception {}
}
