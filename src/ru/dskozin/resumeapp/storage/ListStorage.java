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
    void storageSave(Resume r, Object index) {
        storage.add(r);
    }

    @Override
    void storageUpdate(Resume r, Object index) {
        storage.set((int)index, r);
    }

    @Override
    void storageDelete(Object index) {
        storage.remove((int)index);
    }

    @Override
    Resume storageGet(Object index) {
        return storage.get((int)index);
    }

    Object getIndex(String uuid){
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid))
                return i;
        }

        return null;
    }
}
