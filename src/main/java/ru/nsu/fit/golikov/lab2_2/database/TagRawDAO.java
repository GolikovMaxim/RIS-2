package ru.nsu.fit.golikov.lab2_2.database;

import ru.nsu.fit.golikov.lab2_2.generated.Tag;

import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Statement;

public class TagRawDAO implements TagDAO {
    private Statement statement;

    public TagRawDAO(DBConnection dbConnection) throws SQLException {
        statement = dbConnection.getConnection().createStatement();
    }

    @Override
    public void insertTag(Tag tag, BigInteger nodeId) throws SQLException {
        statement.execute(
                "INSERT INTO tags (node_id, k, v) VALUES ('"
                + nodeId + "', '" + tag.getK() + "', '" + tag.getV() + "')"
        );
    }
}
