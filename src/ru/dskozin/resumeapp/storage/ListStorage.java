package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.exception.ExistStorageException;
import ru.dskozin.resumeapp.exception.NotExistStorageException;
import ru.dskozin.resumeapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    List<Resume> storage = new ArrayList<>();

    @Override
    void storageClear() {
        storage.clear();
    }

    @Override
    void storageSave(Resume r) {
        if (storage.contains(r))
            throw new ExistStorageException(r.getUuid());

        storage.add(r);
    }

    @Override
    void storageUpdate(Resume r) {
        storage.set(getIndex(r.getUuid()), r);
    }

    @Override
    void storageDelete(String uuid) {
        storage.remove(getIndex(uuid));
    }

    @Override
    int storageSize() {
        return storage.size();
    }

    @Override
    Resume[] storageGetAll() {
       return storage.toArray(new Resume[0]);
    }

    @Override
    Resume storageGet(String uuid) {
        return storage.get(getIndex(uuid));
    }

    private int getIndex(String uuid){
        int index;
        if ((index = storage.indexOf(new Resume(uuid))) < 0)
            throw new NotExistStorageException(uuid);

        return index;
    }
}
