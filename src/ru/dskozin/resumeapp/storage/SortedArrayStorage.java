package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    protected void insert(Resume resume, Integer index) {
        //формируем индекс вставки
        index = Math.abs(index) - 1;

        //если вставляем не в конец, то копируем массив на один элемент дальше
        if(index != size)
            System.arraycopy(storage, index, storage, index + 1, size - index);

        storage[index] = resume;
    }

    @Override
    protected void reject(Integer index) {
        //если элемент не последний, то сдвигаем массив на единицу влево
        if(index + 1 != size)
            System.arraycopy(storage, index + 1, storage, index, size - (index + 1));
    }

    @Override
    protected Integer getIndex(String uuid) {
        Resume searchKey = new Resume("", uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
    }
}
