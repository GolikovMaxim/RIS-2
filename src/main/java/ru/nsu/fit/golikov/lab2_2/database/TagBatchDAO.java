package ru.nsu.fit.golikov.lab2_2.database;

import ru.nsu.fit.golikov.lab2_2.generated.Tag;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class TagBatchDAO extends TagPreparedDAO {
    private static final int BATCH_SIZE = 1000;

    private Map<Tag, BigInteger> tagsToInsert = new HashMap<>();

    public TagBatchDAO(DBConnection dbConnection) throws SQLException {
        super(dbConnection);
    }

    @Override
    public void insertTag(Tag tag, BigInteger nodeId) throws SQLException {
        tagsToInsert.put(tag, nodeId);
        if(tagsToInsert.size() >= BATCH_SIZE) {
            insertTags(tagsToInsert);
            tagsToInsert.clear();
        }
    }

    public void insertTags(Map<Tag, BigInteger> tagsToInsert) throws SQLException {
        for(Map.Entry<Tag, BigInteger> tag : tagsToInsert.entrySet()) {
            prepareStatement(tag.getKey(), tag.getValue());
            getStatement().addBatch();
        }
        getStatement().executeBatch();
    }

    @Override
    public void close() throws Exception {
        insertTags(tagsToInsert);
        tagsToInsert.clear();
    }
}
