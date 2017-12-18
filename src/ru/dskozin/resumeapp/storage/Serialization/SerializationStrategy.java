package ru.dskozin.resumeapp.storage.Serialization;

import ru.dskozin.resumeapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializationStrategy {

    void doWrite(Resume r, OutputStream out) throws IOException;
    Resume doRead(InputStream in) throws IOException;

}
