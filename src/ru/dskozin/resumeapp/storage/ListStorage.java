package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    List<Resume> storage = new ArrayList<>();

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
        return storage.toArray(new Resume[0]);
    }

    //----методы абстрактного класса-------
    @Override
    void storageSave(Resume r, int index) {
        storage.add(r);
    }

    @Override
    void storageUpdate(Resume r, int index) {
        storage.set(getIndex(r.getUuid()), r);
    }

    @Override
    void storageDelete(String uuid, int index) {
        storage.remove(getIndex(uuid));
    }

    @Override
    Resume storageGet(String uuid, int index) {
        return storage.get(getIndex(uuid));
    }

    int getIndex(String uuid){
        return storage.indexOf(new Resume(uuid));
    }
}
