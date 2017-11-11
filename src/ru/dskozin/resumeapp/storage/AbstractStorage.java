package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.exception.ExistStorageException;
import ru.dskozin.resumeapp.exception.NotExistStorageException;
import ru.dskozin.resumeapp.model.Resume;

abstract public class AbstractStorage implements Storage {

    abstract void storageUpdate(Resume r, int index);
    abstract void storageDelete(String uuid, int index);
    abstract Resume storageGet(String uuid, int index);
    abstract int getIndex(String uuid);
    abstract void storageSave(Resume r, int index);


    @Override
    public void save(Resume r) {
        int index;
        if((index = getIndex(r.getUuid())) >= 0)
            throw new ExistStorageException(r.getUuid());

        storageSave(r, index);
    }

    @Override
    public void update(Resume r) {
        int index;
        if((index = getIndex(r.getUuid())) < 0)
            throw new NotExistStorageException(r.getUuid());

        storageUpdate(r, index);
    }

    @Override
    public void delete(String uuid) {
        int index;
        if((index = getIndex(uuid)) < 0)
            throw new NotExistStorageException(uuid);

        storageDelete(uuid, index);
    }

    @Override
    public Resume get(String uuid) {
        int index;
        if ((index = getIndex(uuid)) < 0)
            throw new NotExistStorageException(uuid);

        return storageGet(uuid, index);
    }
}
