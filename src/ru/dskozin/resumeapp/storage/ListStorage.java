package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

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
    protected void storageSave(Resume r, Integer index) {
        storage.add(r);
    }

    @Override
    protected void storageUpdate(Resume r, Integer index) {
        storage.set(index, r);
    }

    @Override
    protected void storageDelete(Integer index) {
        storage.remove((int)index);
    }

    @Override
    protected Resume storageGet(Integer index) {
        return storage.get(index);
    }

    protected Integer getIndex(String uuid){
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid))
                return i;
        }

        return null;
    }
}
