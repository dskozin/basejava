package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage{

    Map<String, Resume> storage = new HashMap<>();

    //----методы интерфейса Storage ------
    @Override
    public void clear() {
        storage.clear();
    }

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

    //----методы абстрактного класса-------
    @Override
    protected void storageSave(Resume r, Object index) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void storageUpdate(Resume r, Object index) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void storageDelete(Object index) {
        storage.remove(index);
    }

    @Override
    protected Resume storageGet(Object index) {
        return storage.get(index);
    }

    protected String getIndex(String uuid){
        return storage.containsKey(uuid) ? uuid : null;
    }
}
