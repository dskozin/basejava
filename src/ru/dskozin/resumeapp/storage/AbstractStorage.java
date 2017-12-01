package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.exception.ExistStorageException;
import ru.dskozin.resumeapp.exception.NotExistStorageException;
import ru.dskozin.resumeapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

abstract public class AbstractStorage<T> implements Storage {

    private static final Logger LOGGER = Logger.getLogger(AbstractStorage.class.getName());

    abstract void storageUpdate(Resume r, T index);
    abstract void storageDelete(T index);
    abstract Resume storageGet(T index);
    abstract T getIndex(String uuid);
    abstract void storageSave(Resume r, T index);
    abstract List<Resume> getStorageAsList();

    @Override
    public void save(Resume r) {
        String uuid = r.getUuid();
        T index;

        if(found(index = getIndex(uuid))){
            LOGGER.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(r.getUuid());
        }

        storageSave(r, index);
    }

    @Override
    public void update(Resume r) {
        T index = notFoundOrKey(r.getUuid());
        storageUpdate(r, index);
    }

    @Override
    public void delete(String uuid) {
        T index = notFoundOrKey(uuid);
        storageDelete(index);
    }

    @Override
    public Resume get(String uuid) {
        T index = notFoundOrKey(uuid);
        return storageGet(index);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = getStorageAsList();
        Collections.sort(list);
        return list;
    }

    boolean found(T index){
        return index != null;
    }

    private T notFoundOrKey(String uuid){
        T index;
        if(!found(index = getIndex(uuid))){
            LOGGER.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }

        return index;
    }
}
