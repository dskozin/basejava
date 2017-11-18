package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.exception.ExistStorageException;
import ru.dskozin.resumeapp.exception.NotExistStorageException;
import ru.dskozin.resumeapp.model.Resume;

import java.util.Collections;
import java.util.List;

abstract public class AbstractStorage implements Storage {

    abstract void storageUpdate(Resume r, Object index);
    abstract void storageDelete(Object index);
    abstract Resume storageGet(Object index);
    abstract Object getIndex(String uuid);
    abstract void storageSave(Resume r, Object index);
    abstract List<Resume> getStorageAsList();

    @Override
    public void save(Resume r) {
        String uuid = r.getUuid();
        Object index;

        if(found(index = getIndex(uuid)))
            throw new ExistStorageException(r.getUuid());

        storageSave(r, index);
    }

    @Override
    public void update(Resume r) {
        Object index = notFoundOrKey(r.getUuid());
        storageUpdate(r, index);
    }

    @Override
    public void delete(String uuid) {
        Object index = notFoundOrKey(uuid);
        storageDelete(index);
    }

    @Override
    public Resume get(String uuid) {
        Object index = notFoundOrKey(uuid);
        return storageGet(index);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = getStorageAsList();
        Collections.sort(list, Resume.nameComparator());
        return list;
    }

    boolean found(Object index){
        return index != null;
    }

    private Object notFoundOrKey(String uuid){
        Object index;
        if(!found(index = getIndex(uuid)))
            throw new NotExistStorageException(uuid);

        return index;
    }
}
