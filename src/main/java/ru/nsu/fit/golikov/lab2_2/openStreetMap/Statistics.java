package ru.nsu.fit.golikov.lab2_2.openStreetMap;

import ru.nsu.fit.golikov.lab2_2.database.DAOManager;
import ru.nsu.fit.golikov.lab2_2.generated.Node;
import ru.nsu.fit.golikov.lab2_2.generated.Tag;
import ru.nsu.fit.golikov.lab2_2.parser.JAXBParser;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.*;

public class Statistics {
    private static final Logger logger = LogManager.getLogger(Statistics.class);

    private XMLStreamReader xmlStreamReader;
    @Getter private List<User> users;
    @Getter private Map<String, Integer> tagNodes = new HashMap<>();

    public Statistics(InputStream inputStream) throws XMLStreamException, JAXBException {
        xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(inputStream);

        JAXBParser parser = new JAXBParser();
        Map<String, Integer> usersMap = new HashMap<>();

        logger.debug("Start parsing");
        parser.parseXML(xmlStreamReader, Node.class, node -> {
            usersMap.put(node.getUser(), (usersMap.get(node.getUser()) == null ? 0 : usersMap.get(node.getUser())) + 1);

            List<Tag> tags = node.getTag();
            if(tags != null) {
                for(Tag tag : tags) {
                    tagNodes.put(tag.getK(), (tagNodes.get(tag.getK()) == null ? 0 : tagNodes.get(tag.getK())) + 1);
                }
            }

            try {
                DAOManager.daoManager.getNodeDAO().insertNode(node);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        logger.debug("Parsing finished successfully");

        users = new ArrayList<>();
        for(Map.Entry<String, Integer> pair : usersMap.entrySet()) {
            users.add(new User(pair.getKey(), pair.getValue()));
        }
        users.sort(Comparator.comparingInt(User::getChangeCount));
    }
}
