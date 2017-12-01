package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.exception.StorageException;
import ru.dskozin.resumeapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File>{

    private final File storage;

    public AbstractFileStorage(String directory) {
        Objects.requireNonNull(directory, "Directory can not be null");
        File dir = new File(directory);
        if(!dir.isDirectory())
            throw new IllegalArgumentException(directory + " is not directory");

        if(!dir.canRead() || !dir.canWrite())
            throw new IllegalArgumentException(dir.getAbsolutePath() + " is not writable/readable");

        this.storage = dir;
    }

    @Override
    void storageUpdate(Resume r, File file) {
        storageDelete(file);
        storageSave(r, file);
    }

    @Override
    void storageDelete(File file) {
        if(!file.delete())
            throw new StorageException("Delete error", file.getName());
    }

    @Override
    Resume storageGet(File file) {
        Resume resume;
        //следующий урок
        return null;
    }

    @Override
    File getIndex(String uuid) {
        return new File(storage, uuid);
    }

    @Override
    void storageSave(Resume r, File file) {
        try {
            file.createNewFile();
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    protected abstract void doWrite(Resume r, File file);

    @Override
    List<Resume> getStorageAsList() {
        return null;
    }

    @Override
    public void clear() {
        for(File f : storage.listFiles()) f.delete();
    }

    @Override
    public int size() {
        return storage.listFiles().length;
    }

    boolean found(File index){
        return index.exists();
    }
}
