package ru.nsu.fit.golikov.lab2_2.database;

import ru.nsu.fit.golikov.lab2_2.generated.Node;
import ru.nsu.fit.golikov.lab2_2.generated.Tag;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeBatchDAO extends NodePreparedDAO {
    private static final int BATCH_SIZE = 1000;

    private List<Node> nodesToInsert = new ArrayList<>();

    public NodeBatchDAO(DBConnection dbConnection) throws SQLException {
        super(dbConnection);
    }

    @Override
    public void insertNode(Node node) throws SQLException {
        nodesToInsert.add(node);
        if(nodesToInsert.size() >= BATCH_SIZE) {
            insertNodes();
        }
    }

    private void insertNodes() throws SQLException {
        Map<Tag, BigInteger> tagsToInsert = new HashMap<>();

        for(Node node : nodesToInsert) {
            prepareStatement(node);
            getStatement().addBatch();

            for(Tag tag : node.getTag()) {
                tagsToInsert.put(tag, node.getId());
            }
        }
        getStatement().executeBatch();

        ((TagBatchDAO)DAOManager.daoManager.getTagDAO()).insertTags(tagsToInsert);

        nodesToInsert.clear();
    }

    @Override
    public void close() throws Exception {
        insertNodes();
    }
}
