package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.exception.StorageException;
import ru.dskozin.resumeapp.model.Resume;
import ru.dskozin.resumeapp.storage.Serialization.ObjectSerialization;
import ru.dskozin.resumeapp.storage.Serialization.SerializationStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File>{

    private final File storage;

    private final SerializationStrategy strategy;

    public FileStorage(String directory, SerializationStrategy strategy) {
        Objects.requireNonNull(directory, "Directory can not be null");
        Objects.requireNonNull(strategy, "Strategy can not be null");
        File dir = new File(directory);
        if(!dir.isDirectory())
            throw new IllegalArgumentException(directory + " is not directory");

        if(!dir.canRead() || !dir.canWrite())
            throw new IllegalArgumentException(dir.getAbsolutePath() + " is not writable/readable");

        this.storage = dir;
        this.strategy = strategy;
    }

    public FileStorage(String directory){
        this(directory, new ObjectSerialization());
    }

    @Override
    void storageUpdate(Resume r, File file) {
        try {
            strategy.doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e ){
            throw new StorageException("Update error", file.getName(), e);
        }
    }

    @Override
    void storageDelete(File file) {
        if(!file.delete())
            throw new StorageException("Delete error", file.getName());
    }

    @Override
    Resume storageGet(File file) {
        return readFile(file);
    }

    @Override
    File getIndex(String uuid) {
        return new File(storage, uuid);
    }

    @Override
    void storageSave(Resume r, File file) {
        try {
            if (!file.createNewFile())
                throw new StorageException("File creation error", file.getName());
        } catch (IOException e) {
            throw new StorageException("File creation error", file.getName(), e);
        }
        storageUpdate(r, file);
    }

    @Override
    List<Resume> getStorageAsList() {
        List<Resume> list = new ArrayList<>();
        for (File f : fileList()){
            list.add(readFile(f));
        }

        return list;
    }

    @Override
    public void clear() {
        for(File f : fileList()) {
            storageDelete(f);
        }
    }

    @Override
    public int size() {
        return fileList().length;
    }

    private File[] fileList(){
        File[] arr = storage.listFiles();

        if (arr == null)
            throw new StorageException("Storage directory list error", "none");

        return arr;
    }

    boolean found(File index){
        return index.exists();
    }

    private Resume readFile(File file){
        try {
            return strategy.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e){
            throw new StorageException("File read error", file.getName(), e);
        }
    }
}
