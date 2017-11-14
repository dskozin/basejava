package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.exception.ExistStorageException;
import ru.dskozin.resumeapp.exception.NotExistStorageException;
import ru.dskozin.resumeapp.exception.StorageException;
import ru.dskozin.resumeapp.model.Resume;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractArrayStorage extends AbstractStorage{

    static final int STORAGE_SIZE = 10000;
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
    protected void storageSave(Resume resume, Object index){
         if(size == STORAGE_SIZE){
            throw new StorageException("Storage overflow", resume.getUuid());
        }

        //вызываем специфичный метод вставки элемента
        insert(resume, (int)index);
        ++size;
    }

    @Override
    protected void storageUpdate(Resume r, Object i){
        storage[(int)i] = r;
    }

    @Override
    protected void storageDelete(Object index){
        //вызываем специфичный метод удаления
        reject((Integer)index);

        //последний зануляем, просто освободить ячейку, удалить объект
        storage[--size] = null;
    }

    @Override
    protected Resume storageGet(Object index) {
        return storage[(int)index];
    }

    @Override
    protected boolean found(Object index){
        return (Integer)index >= 0;
    }

    protected abstract void reject(Integer index);
    protected abstract void insert(Resume r, Integer index);
}
