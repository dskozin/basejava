package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.exception.StorageException;
import ru.dskozin.resumeapp.model.Resume;

import java.io.*;

public class ObjectStreamStorage extends AbstractFileStorage {

    public ObjectStreamStorage(String directory) {
        super(directory);
    }

    @Override
    void doWrite(Resume r, OutputStream out) throws IOException {
        try(ObjectOutputStream oos = new ObjectOutputStream(out)){
            oos.writeObject(r);
        }
    }

    @Override
    Resume doRead(InputStream in) throws IOException {
        try(ObjectInputStream ois = new ObjectInputStream(in)){
            return (Resume)ois.readObject();
        } catch (ClassNotFoundException e){
            throw new StorageException("Class not found","none", e);
        }
    }
}
