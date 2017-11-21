package ru.dskozin.resumeapp.storage;

import com.sun.org.apache.regexp.internal.RE;
import ru.dskozin.resumeapp.exception.ExistStorageException;
import ru.dskozin.resumeapp.exception.NotExistStorageException;
import ru.dskozin.resumeapp.exception.StorageException;
import ru.dskozin.resumeapp.model.Resume;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer>{

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
    List<Resume> getStorageAsList() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    //----методы абстрактного класса-------
    @Override
    protected void storageSave(Resume resume, Integer index){
         if(size == STORAGE_SIZE){
            throw new StorageException("Storage overflow", resume.getUuid());
        }

        //вызываем специфичный метод вставки элемента
        insert(resume, index);
        ++size;
    }

    @Override
    protected void storageUpdate(Resume r, Integer i){
        storage[i] = r;
    }

    @Override
    protected void storageDelete(Integer index){
        //вызываем специфичный метод удаления
        reject(index);

        //последний зануляем, просто освободить ячейку, удалить объект
        storage[--size] = null;
    }

    @Override
    protected Resume storageGet(Integer index) {
        return storage[index];
    }

    @Override
    protected boolean found(Integer index){
        return index >= 0;
    }

    protected abstract void reject(Integer index);
    protected abstract void insert(Resume r, Integer index);
}
