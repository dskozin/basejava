package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileStorage extends AbstractFileStorage{

    private final SerializationStrategy strategy;

    FileStorage(String dir, SerializationStrategy strategy){
        super(dir);
        this.strategy = strategy;
    }

    @Override
    void doWrite(Resume r, OutputStream out) throws IOException {
        strategy.doWrite(r, out);
    }

    @Override
    Resume doRead(InputStream in) throws IOException {
        return strategy.doRead(in);
    }
}
