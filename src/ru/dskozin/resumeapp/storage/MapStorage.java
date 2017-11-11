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
    void storageSave(Resume r, int index) {
        storage.put(r.getUuid(), r);
    }

    @Override
    void storageUpdate(Resume r, int index) {
        storage.put(r.getUuid(), r);
    }

    @Override
    void storageDelete(String uuid, int index) {
        storage.remove(uuid);
    }

    @Override
    Resume storageGet(String uuid, int index) {
        return storage.get(uuid);
    }

    int getIndex(String uuid){
        return storage.containsKey(uuid) ? 1 : -1;
    }
}
