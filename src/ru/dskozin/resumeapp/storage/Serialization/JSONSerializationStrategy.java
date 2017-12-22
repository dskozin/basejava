package ru.dskozin.resumeapp.storage.Serialization;

import ru.dskozin.resumeapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class JSONSerializationStrategy implements SerializationStrategy {

    @Override
    public void doWrite(Resume r, OutputStream out) throws IOException {


    }

    @Override
    public Resume doRead(InputStream in) throws IOException {
        return null;
    }
}
