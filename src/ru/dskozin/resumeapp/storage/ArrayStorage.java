package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.model.Resume;

class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insert(Resume resume, Integer index) {
        //добавляем в конец
        storage[size] = resume;
    }

    @Override
    protected void reject(Integer index) {
        //ставим на место текущего элемента последний
        storage[index] = storage[size - 1];
    }

    @Override
    protected Integer getIndex(String uuid) {
        //перебираем резюме по uuid, ищем совпадение
        for (int i = 0; i < size; i++) {
            if(uuid.equals(storage[i].getUuid())){
                return i;
            }
        }
        return -1;
    }
}