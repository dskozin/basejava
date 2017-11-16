package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapNameStorage extends MapUuidStorage{

    Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void storageSave(Resume r, Object index) {
        super.storageSave(r, index);
    }

    @Override
    protected void storageUpdate(Resume r, Object index) {
        super.storageUpdate(r, index);
    }

    @Override
    protected void storageDelete(Object index) {
        super.storageDelete(index);
    }

    @Override
    protected Resume storageGet(Object index) {
        return super.storageGet(index);
    }

    @Override
    public Resume get(String fullName) {
        return storage.get(fullName);
    }

    @Override
    protected String getIndex(String fullName) {
        return storage.containsKey(fullName) ? fullName : null;
    }
}
