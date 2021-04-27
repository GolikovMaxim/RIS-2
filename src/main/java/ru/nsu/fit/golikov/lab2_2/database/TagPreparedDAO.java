package ru.nsu.fit.golikov.lab2_2.database;

import lombok.Getter;
import ru.nsu.fit.golikov.lab2_2.generated.Tag;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Getter
public class TagPreparedDAO implements TagDAO {
    private PreparedStatement statement;

    public TagPreparedDAO(DBConnection dbConnection) throws SQLException {
        statement = dbConnection.getConnection().prepareStatement(
                "INSERT INTO tags (node_id, k, v) VALUES (?, ?, ?)");
    }

    public void prepareStatement(Tag tag, BigInteger nodeId) throws SQLException {
        statement.setLong(1, nodeId.longValue());
        statement.setString(2, tag.getK());
        statement.setString(3, tag.getV());
    }

    @Override
    public void insertTag(Tag tag, BigInteger nodeId) throws SQLException {
        prepareStatement(tag, nodeId);
        statement.executeUpdate();
    }
}
