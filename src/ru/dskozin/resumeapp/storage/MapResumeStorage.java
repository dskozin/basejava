package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume>{

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
    protected void storageSave(Resume r, Resume index) {
        storage.put(r.getUuid(), r);
    }

    @Override
    void storageDelete(Resume index) {
        storage.remove(index.getUuid());
    }

    @Override
    Resume storageGet(Resume index) {
        return index;
    }

    @Override
    protected void storageUpdate(Resume r, Resume index) {
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
