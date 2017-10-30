package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume r) {
        if( getIndex(r.getUuid()) != -1) {
                System.out.println("ERROR SAVE");
        } else if (this.size == this.STORAGE_SIZE) {
            System.out.println("ERROR OVERFLOW");
        } else {
            this.storage[this.size] = r;
            ++this.size;
        }
    }

    @Override
    public void delete(String uuid) {
        int i = getIndex(uuid);

        //если находим заданный элемент
        if (i == -1) {
            System.out.println("ERROR DELETE");
            return;
        }

        //ставим на его место последний
        this.storage[i] = this.storage[this.size - 1];
        //последний зануляем, просто освободить ячейку, удалить объект
        this.storage[this.size - 1] = null;
        //и выходим
        --this.size;
    }

    @Override
    protected int getIndex(String uuid) {
        //перебираем резюме по uuid, ищем совпадение
        for (int i = 0; i < this.size; i++) {
            if(uuid.equals(this.storage[i].getUuid())){
                return i;
            }
        }
        return -1;
    }
}