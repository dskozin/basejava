package ru.dskozin.resumeapp.storage.Serialization;

import ru.dskozin.resumeapp.model.*;
import ru.dskozin.resumeapp.util.XMLParser;

import java.io.*;

public class XMLSerializationStrategy implements SerializationStrategy {

    private final XMLParser xmlParser;

    public XMLSerializationStrategy(){
        xmlParser = new XMLParser(
                Resume.class,
                Organization.class,
                Organization.PeriodicEntry.class,
                Section.class,
                OrganizationSection.class,
                SectionList.class,
                SectionString.class
        );
    }

    @Override
    public void doWrite(Resume r, OutputStream out) throws IOException {

        try (Writer writer = new OutputStreamWriter(out)){
            xmlParser.marshall(r,writer);
        }
    }

    @Override
    public Resume doRead(InputStream in) throws IOException {
        try (Reader reader = new InputStreamReader(in)){
            return xmlParser.unmarshall(reader);
        }
    }

}


