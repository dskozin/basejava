package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.model.Resume;

abstract public class AbstractStorage implements Storage {

    abstract void storageClear();
    abstract void storageSave(Resume r);
    abstract void storageUpdate(Resume r);
    abstract void storageDelete(String uuid);
    abstract int storageSize();
    abstract Resume[] storageGetAll();
    abstract Resume storageGet(String uuid);


    @Override
    public void clear() {
        storageClear();
    }

    @Override
    public void save(Resume r) {
        storageSave(r);
    }

    @Override
    public void update(Resume r) {
        storageUpdate(r);
    }

    @Override
    public void delete(String uuid) {
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
        return storageGet(uuid);
    }


}
