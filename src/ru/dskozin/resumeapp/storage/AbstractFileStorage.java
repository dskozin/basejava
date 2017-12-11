package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.exception.StorageException;
import ru.dskozin.resumeapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File>{

    private final File storage;

    abstract void doWrite(Resume r, File file) throws IOException;
    abstract Resume doRead(File file) throws IOException;


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
        storageSave(r, file);
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
            file.createNewFile();
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
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
        File[] arr = fileList();

        for(File f : arr) {
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
            return doRead(file);
        } catch (IOException e){
            throw new StorageException("File read error", file.getName(), e);
        }
    }
}
