package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {

    final private int STORAGE_SIZE = 10000;
    protected Resume[] storage = new Resume[STORAGE_SIZE];
    int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size,null);
        this.size = 0;
    }

    @Override
    public void save(Resume resume){
        //возвращаемое значение указывает индекс вставки -1,
        int index = getIndex(resume.getUuid());

        //проверяем что элемента еще нет и нет переполнения
        if(index >= 0){
            System.out.println("Resume " + resume.getUuid() + " already exist!");
            return;
        } else if(size == STORAGE_SIZE){
            System.out.println("Storage overflow!");
            return;
        }

        //вызываем специфичный метод вставки элемента
        insert(resume, index);
        ++size;
    }

    @Override
    public void update(Resume r){
        int i = getIndex(r.getUuid());
        if(i < 0){
            System.out.println("Resume " + r.getUuid() + " not exist!");
            return;
        }
        storage[i] = r;
    }

    @Override
    public void delete(String uuid){
        //получаем элемент для удаления
        int index = getIndex(uuid);

        //проверяем что элемент существует
        if(index < 0){
            System.out.println("Resume " + uuid + " not exist!");
            return;
        }

        //вызываем специфичный метод удаления
        reject(index);

        //последний зануляем, просто освободить ячейку, удалить объект
        storage[size - 1] = null;
        //и выходим
        size--;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Resume[] getAll() {

        return Arrays.copyOfRange(storage,0,size);
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        //binarySearch возвращает отрицательный lowIndex, а не -1
        //поэтому укажем в условии что меньше 0, а не == -1
        if(index < 0){
            System.out.println("Resume " + uuid + " not exist!");
            return null;
        }
        return storage[index];
    }

    protected abstract int getIndex(String uuid);

    protected abstract void insert(Resume resume, int index);

    protected abstract void reject(int index);
}
