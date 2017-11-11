package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.exception.ExistStorageException;
import ru.dskozin.resumeapp.exception.NotExistStorageException;
import ru.dskozin.resumeapp.exception.StorageException;
import ru.dskozin.resumeapp.model.Resume;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractArrayStorage extends AbstractStorage{

    protected static final int STORAGE_SIZE = 10000;
    protected Resume[] storage = new Resume[STORAGE_SIZE];
    int size = 0;

    //----методы интерфейса Storage ------
    @Override
    public void clear() {
        Arrays.fill(storage, 0, size,null);
        this.size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage,0,size);
    }

    //----методы абстрактного класса-------
    @Override
    public void storageSave(Resume resume, int index){
         if(size == STORAGE_SIZE){
            throw new StorageException("Storage overflow", resume.getUuid());
        }

        //вызываем специфичный метод вставки элемента
        insert(resume, index);
        ++size;
    }

    @Override
    public void storageUpdate(Resume r, int i){
        storage[i] = r;
    }

    @Override
    public void storageDelete(String uuid, int index){
        //вызываем специфичный метод удаления
        reject(index);

        //последний зануляем, просто освободить ячейку, удалить объект
        storage[--size] = null;
    }

    @Override
    public Resume storageGet(String uuid, int index) {
        return storage[index];
    }

    abstract void reject(int index);
    abstract void insert(Resume r, int index);
}
