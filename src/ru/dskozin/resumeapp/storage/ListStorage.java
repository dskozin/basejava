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
        storage.add(r);
    }

    @Override
    void storageUpdate(Resume r) {
        storage.set(getIndex(r), r);
    }

    @Override
    void storageDelete(String uuid) {
        storage.remove(getIndex(new Resume(uuid)));
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
        return storage.get(getIndex(new Resume(uuid)));
    }

    int getIndex(Resume r){
        return storage.indexOf(r);
    }
}
