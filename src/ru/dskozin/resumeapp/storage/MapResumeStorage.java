package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage{

    Map<String, Resume> storage = new HashMap<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    List<Resume> getStorageAsList() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected void storageSave(Resume r, Object index) {
        storage.put(r.getUuid(), r);
    }

    @Override
    void storageDelete(Object index) {
        storage.remove(((Resume)index).getUuid());
    }

    @Override
    Resume storageGet(Object index) {
        return (Resume) index;
    }

    @Override
    protected void storageUpdate(Resume r, Object index) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume getIndex(String uuid) {
        return storage.get(uuid);
    }

    public void clear() {
        storage.clear();
    }

}
