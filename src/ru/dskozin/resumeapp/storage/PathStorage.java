package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PathStorage extends AbstractPathStorage {
    private final SerializationStrategy strategy;

    PathStorage(String dir, SerializationStrategy strategy){
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
