package ru.dskozin.resumeapp.storage.Serialization;

import ru.dskozin.resumeapp.model.Resume;
import ru.dskozin.resumeapp.util.JSONParser;

import java.io.*;

public class JSONSerializationStrategy implements SerializationStrategy {

    @Override
    public void doWrite(Resume r, OutputStream out) throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(out)) {
            JSONParser.write(writer, r);
        }
    }

    @Override
    public Resume doRead(InputStream in) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(in)){
            return JSONParser.read(reader, Resume.class);
        }
    }
}
