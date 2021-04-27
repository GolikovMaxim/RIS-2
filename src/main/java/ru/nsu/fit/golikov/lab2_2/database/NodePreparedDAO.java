package ru.nsu.fit.golikov.lab2_2.database;

import lombok.Getter;
import org.apache.commons.text.StringEscapeUtils;
import ru.nsu.fit.golikov.lab2_2.generated.Node;
import ru.nsu.fit.golikov.lab2_2.generated.Tag;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Getter
public class NodePreparedDAO implements NodeDAO {
    private PreparedStatement statement;

    public NodePreparedDAO(DBConnection dbConnection) throws SQLException {
        statement = dbConnection.getConnection().prepareStatement(
                "INSERT INTO nodes (id, username, lon, lat) VALUES (?, ?, ?, ?)");
    }

    public void prepareStatement(Node node) throws SQLException {
        statement.setLong(1, node.getId().longValue());
        statement.setString(2, node.getUser().replace("'", "\\'"));
        statement.setDouble(3, node.getLon());
        statement.setDouble(4, node.getLat());
    }

    @Override
    public void insertNode(Node node) throws SQLException {
        prepareStatement(node);
        statement.executeUpdate();

        for(Tag tag : node.getTag()) {
            DAOManager.daoManager.getTagDAO().insertTag(tag, node.getId());
        }
    }
}
