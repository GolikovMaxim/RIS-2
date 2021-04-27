package ru.nsu.fit.golikov.lab2_2.database;

import ru.nsu.fit.golikov.lab2_2.generated.Node;
import ru.nsu.fit.golikov.lab2_2.generated.Tag;

import java.sql.SQLException;
import java.sql.Statement;

public class NodeRawDAO implements NodeDAO {
    private Statement statement;

    public NodeRawDAO(DBConnection dbConnection) throws SQLException {
        statement = dbConnection.getConnection().createStatement();
    }

    @Override
    public void insertNode(Node node) throws SQLException {
        statement.execute(
                "INSERT INTO nodes (id, username, lon, lat) VALUES ('" +
                node.getId() + "', '" + node.getUser().replace("'", "\\'") + "', '" + node.getLon() + "', '" + node.getLat() + "')"
        );

        for(Tag tag : node.getTag()) {
            DAOManager.daoManager.getTagDAO().insertTag(tag, node.getId());
        }
    }
}
