package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String>{

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
    List<Resume> getStorageAsList() {
        return new ArrayList<>(storage.values());
    }

    //----методы абстрактного класса-------
    @Override
    protected void storageSave(Resume r, String index) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void storageUpdate(Resume r, String index) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void storageDelete(String index) {
        storage.remove(index);
    }

    @Override
    protected Resume storageGet(String index) {
        return storage.get(index);
    }

    protected String getIndex(String uuid){
        return storage.containsKey(uuid) ? uuid : null;
    }
}
