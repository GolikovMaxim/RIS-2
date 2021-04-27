package ru.nsu.fit.golikov.lab2_2.database;

import lombok.Getter;

import java.io.IOException;
import java.sql.SQLException;

@Getter
public class DAOManager {
    public enum DAOType {
        Raw, Prepared, Batch
    }

    public static DAOManager daoManager;

    private NodeDAO nodeDAO;
    private TagDAO tagDAO;
    private DBConnection dbConnection;

    public DAOManager(DAOType daoType) throws IOException, SQLException {
        daoManager = this;

        dbConnection = new DBConnection();

        switch(daoType) {
            case Raw: {
                nodeDAO = new NodeRawDAO(dbConnection);
                tagDAO = new TagRawDAO(dbConnection);
                break;
            }
            case Prepared: {
                nodeDAO = new NodePreparedDAO(dbConnection);
                tagDAO = new TagPreparedDAO(dbConnection);
                break;
            }
            case Batch: {
                nodeDAO = new NodeBatchDAO(dbConnection);
                tagDAO = new TagBatchDAO(dbConnection);
                break;
            }
            default: {
                nodeDAO = null;
                tagDAO = null;
                break;
            }
        }
    }

    public void close() throws Exception {
        nodeDAO.close();
        tagDAO.close();
        dbConnection.getConnection().commit();
        dbConnection.getConnection().close();
    }
}
