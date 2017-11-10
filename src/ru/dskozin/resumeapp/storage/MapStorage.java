package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.exception.ExistStorageException;
import ru.dskozin.resumeapp.exception.NotExistStorageException;
import ru.dskozin.resumeapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage{

    Map<String, Resume> storage = new HashMap<>();

    @Override
    void storageClear() {
        storage.clear();
    }

    @Override
    void storageSave(Resume r) {
        if (storage.containsKey(r.getUuid()))
            throw new ExistStorageException(r.getUuid());

        storage.put(r.getUuid(), r);
    }

    @Override
    void storageUpdate(Resume r) {
        containCheck(r.getUuid());

        storage.put(r.getUuid(), r);
    }

    @Override
    void storageDelete(String uuid) {
        containCheck(uuid);

        storage.remove(uuid);
    }

    @Override
    int storageSize() {
        return storage.size();
    }

    @Override
    Resume[] storageGetAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    Resume storageGet(String uuid) {
        containCheck(uuid);

        return storage.get(uuid);
    }

    private void containCheck(String uuid){
        if (!storage.containsKey(uuid))
            throw new NotExistStorageException(uuid);
    }
}
