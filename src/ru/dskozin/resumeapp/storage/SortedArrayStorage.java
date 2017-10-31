package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    public void insert(Resume resume, int index) {
        //формируем индекс вставки
        index = Math.abs(index) - 1;

        //если вставляем не в конец, то копируем массив на один элемент дальше
        if(index != size)
            System.arraycopy(storage, index, storage, index + 1, size - index);

        storage[index] = resume;
    }

    @Override
    public void reject(int index) {
        //если элемент не последний, то сдвигаем массив на единицу влево
        if(index + 1 != size)
            System.arraycopy(storage, index + 1, storage, index, size - (index + 1));
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
