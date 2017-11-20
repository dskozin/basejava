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
    List<Resume> getStorageAsList() {
        //возвращаем безопасно
        return new ArrayList<>(storage);
    }

    //----методы абстрактного класса-------
    @Override
    protected void storageSave(Resume r, Object index) {
        storage.add(r);
    }

    @Override
    protected void storageUpdate(Resume r, Object index) {
        storage.set((int)index, r);
    }

    @Override
    protected void storageDelete(Object index) {
        storage.remove((int)index);
    }

    @Override
    protected Resume storageGet(Object index) {
        return storage.get((int)index);
    }

    protected Integer getIndex(String uuid){
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid))
                return i;
        }

        return null;
    }
}
