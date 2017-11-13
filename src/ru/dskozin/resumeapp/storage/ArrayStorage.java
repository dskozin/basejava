package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public void insert(Resume resume, int index) {
        //добавляем в конец
        storage[size] = resume;
    }

    @Override
    public void reject(int index) {
        //ставим на место текущего элемента последний
        storage[index] = storage[size - 1];
    }

    @Override
    protected Object getIndex(String uuid) {
        //перебираем резюме по uuid, ищем совпадение
        for (int i = 0; i < size; i++) {
            if(uuid.equals(storage[i].getUuid())){
                return i;
            }
        }
        return -1;
    }
}