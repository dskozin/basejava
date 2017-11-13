package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage{

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
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    //----методы абстрактного класса-------
    @Override
    void storageSave(Resume r, Object index) {
        storage.put(r.getUuid(), r);
    }

    @Override
    void storageUpdate(Resume r, Object index) {
        storage.put(r.getUuid(), r);
    }

    @Override
    void storageDelete(Object index) {
        storage.remove(index);
    }

    @Override
    Resume storageGet(Object index) {
        return storage.get(index);
    }

    String getIndex(String uuid){
        return storage.containsKey(uuid) ? uuid : null;
    }
}
