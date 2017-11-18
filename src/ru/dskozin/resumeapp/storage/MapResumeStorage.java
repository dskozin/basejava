package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage{

    Map<Resume, Resume> storage = new HashMap<>();

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
        storage.put(r, r);
    }

    @Override
    void storageDelete(Object index) {
        storage.remove(index);
    }

    @Override
    Resume storageGet(Object index) {
        return storage.get(index);
    }

    @Override
    protected void storageUpdate(Resume r, Object index) {
        storage.put(r, r);
    }

    @Override
    protected Resume getIndex(String uuid) {
        for(Resume r : storage.values()){
            if (r.getUuid().equals(uuid))
                return r;
        }
        return null;
    }

    public void clear() {
        storage.clear();
    }

}
