package ru.nsu.fit.golikov.lab2_2;

import ru.nsu.fit.golikov.lab2_2.database.DAOManager;
import ru.nsu.fit.golikov.lab2_2.openStreetMap.Statistics;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws Exception {
        CompressorInputStream cis = new CompressorStreamFactory().createCompressorInputStream(CompressorStreamFactory.BZIP2,
                new BufferedInputStream(new FileInputStream("RU-NVS.osm.bz2")));

        long start = System.nanoTime();

        new DAOManager(DAOManager.DAOType.valueOf(args[0]));
        Statistics statistics = new Statistics(cis);

        System.out.println("Time: " + (System.nanoTime() - start) * 0.000000001 + "s");

        System.out.println(statistics.getUsers());
        System.out.println(statistics.getTagNodes());

        DAOManager.daoManager.close();
    }
}
