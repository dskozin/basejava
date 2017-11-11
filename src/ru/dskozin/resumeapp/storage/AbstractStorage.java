package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.exception.ExistStorageException;
import ru.dskozin.resumeapp.exception.NotExistStorageException;
import ru.dskozin.resumeapp.model.Resume;

abstract public class AbstractStorage implements Storage {

    abstract void storageClear();
    abstract void storageSave(Resume r);
    abstract void storageUpdate(Resume r);
    abstract void storageDelete(String uuid);
    abstract int storageSize();
    abstract Resume[] storageGetAll();
    abstract Resume storageGet(String uuid);
    abstract int getIndex(Resume r);


    @Override
    public void clear() {
        storageClear();
    }

    @Override
    public void save(Resume r) {
        if(getIndex(r) >= 0)
            throw new ExistStorageException(r.getUuid());

        storageSave(r);
    }

    @Override
    public void update(Resume r) {
        if(getIndex(r) < 0)
            throw new NotExistStorageException(r.getUuid());

        storageUpdate(r);
    }

    @Override
    public void delete(String uuid) {
        if(getIndex(new Resume(uuid)) < 0)
            throw new NotExistStorageException(uuid);

        storageDelete(uuid);
    }

    @Override
    public int size() {
        return storageSize();
    }

    @Override
    public Resume[] getAll() {
        return storageGetAll();
    }

    @Override
    public Resume get(String uuid) {
        if (getIndex(new Resume(uuid)) < 0)
            throw new NotExistStorageException(uuid);

        return storageGet(uuid);
    }
}
