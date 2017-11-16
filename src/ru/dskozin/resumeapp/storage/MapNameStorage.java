package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapNameStorage extends AbstractStorage{

    Map<String, Resume> storage = new HashMap<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> ret = new ArrayList<>(storage.values());
        ret.sort(Resume.nameComparator());
        return ret;
    }

    @Override
    protected void storageSave(Resume r, Object index) {
        storage.put(r.getFullName(), r);
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
        storage.put(r.getFullName(), r);
    }

    @Override
    protected String getIndex(String uuid) {

        for(Resume r : storage.values()){
            if (r.getUuid().equals(uuid))
                return r.getFullName();
        }
        return null;
    }

    public void clear() {
        storage.clear();
    }

}
