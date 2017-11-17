package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapEmailStorage extends AbstractStorage{

    Map<String, Resume> storage = new HashMap<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> ret = new ArrayList<>(storage.values());
        ret.sort(Resume.nameComparator());
        return ret;
    }

    @Override
    protected void storageSave(Resume r, Object index) {
        storage.put(r.getEmail(), r);
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
        storage.put(r.getEmail(), r);
    }

    @Override
    protected String getIndex(String uuid) {

        for(Resume r : storage.values()){
            if (r.getUuid().equals(uuid))
                return r.getEmail();
        }
        return null;
    }

    public void clear() {
        storage.clear();
    }

}
