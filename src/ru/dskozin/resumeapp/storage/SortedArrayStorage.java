package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume r) {
        //возвращаемое значение указывает индекс вставки -1,
        //для сохранения масива отсортированным
        int index = getIndex(r.getUuid());

        //проверяем что элемента еще нет в списке
        if(index >= 0){
            System.out.println("Resume " + r.getUuid() + " already exist!");
            return;
        }

        //проверяем что размер массива не превышен
        if(size == STORAGE_SIZE){
            System.out.println("Storage overflow!");
            return;
        }

        //формируем индекс вставки
        index = Math.abs(index) - 1;

        //если вставляем не в конец
        if(index != size){
            //берем копию массива начиная с места вставки
            //и копируем ее на один элемент дальше
            System.arraycopy(storage, index, storage, index + 1, size - index);
        }

        //вставляем элемент в указанное место
        storage[index] = r;

        //увеличиваем размер массива
        size++;
    }

    @Override
    public void delete(String uuid) {
        //получаем элемент для удавления
        int index = getIndex(uuid);

        //проверяем что элемент существует
        if(index < 0){
            System.out.println("Resume " + uuid + " not exist!");
            return;
        }

        //если элемент не последний
        if(index + 1 != size){
            //сдвигаем массив на единицу влево
            System.arraycopy(storage, index + 1, storage, index, size - (index + 1));
        }

        //зануляем элемент вышедший из области видимости
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
